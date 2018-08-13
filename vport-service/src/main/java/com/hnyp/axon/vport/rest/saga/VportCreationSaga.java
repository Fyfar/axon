package com.hnyp.axon.vport.rest.saga;

import com.hnyp.axon.api.entity.State;
import com.hnyp.axon.api.event.VportCreatedEvent;
import com.hnyp.axon.api.event.VportDeployFailedEvent;
import com.hnyp.axon.api.event.VportUpdateStatus;
import com.hnyp.axon.vport.rest.domain.BaseVport;
import com.hnyp.axon.vport.rest.repositories.VportRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.saga.AssociationValue;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.SagaRepository;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Set;

@Slf4j
@Saga
public class VportCreationSaga implements Serializable {

    @Autowired
    private transient SagaConfiguration<VportCreationSaga> vportCreationSagaConfiguration;
    @Autowired
    private transient VportRepository vportRepository;

    private boolean sagaIsActive;

    @StartSaga
    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportCreatedEvent event) {
        log.info("VportCreatedEvent saga triggered, vport id = {}", event.getVportId());

        CurrentUnitOfWork.get().root().afterCommit((it) -> log.info("+++ Unit Of Work ROOT phase +++"));
        CurrentUnitOfWork.get().afterCommit((x) -> log.info("=== UnitOfWork After Commit phase ==="));

        SagaRepository<VportCreationSaga> sagaRepository = vportCreationSagaConfiguration.getSagaRepository();
        Set<String> sagasId = sagaRepository.find(new AssociationValue("vportId", event.getVportId()));
        boolean active = sagaRepository.load(sagasId.iterator().next()).isActive();
        log.info("Saga is active: {}", active);
        sagaIsActive = active;

        vportRepository.save(new BaseVport(event.getVportId(), event.getName(), State.DEPLOYING));
    }

    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportUpdateStatus event) {
        log.info("VportUpdateStatus saga triggered, vport id = {}", event.getVportId());

        if (event.getState() != State.DEPLOYING) {
            SagaLifecycle.end();

            SagaRepository<VportCreationSaga> sagaRepository = vportCreationSagaConfiguration.getSagaRepository();
            Set<String> sagasId = sagaRepository.find(new AssociationValue("vportId", event.getVportId()));
            boolean active = sagaRepository.load(sagasId.iterator().next()).isActive();
            log.info("Saga is active after lifecycle ended: {}", active);
            sagaIsActive = active;

            vportRepository.save(new BaseVport(event.getVportId(), event.getName(), event.getState()));
        }

        UnitOfWork<?> unitOfWork = CurrentUnitOfWork.get();
        log.info("Unit of work phase after saga ends: {}", unitOfWork.phase().name());
    }

    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportDeployFailedEvent event) {
        log.info("Compensation action was performed...");
        log.info("Remove vport from DB");
        log.info("Call EIS to undeploy vport");
    }

    public boolean isSagaIsActive() {
        return sagaIsActive;
    }
}
