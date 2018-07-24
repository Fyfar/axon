package com.hnyp.axon.vport.rest.saga;

import com.hnyp.axon.api.event.VportCreatedEvent;
import com.hnyp.axon.api.event.VportUpdateStatus;
import com.hnyp.axon.vport.rest.domain.Vport;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VportCreationSaga {

    @StartSaga
    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportCreatedEvent event, Vport vport) {
        log.info("VportCreatedEvent saga triggered, vport id = {}", event.getVportId());
    }

    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportUpdateStatus event, Vport vport) {
        log.info("VportUpdateStatus saga triggered, vport id = {}", event.getVportId());
        log.info("Current state of Vport: {}", vport);
    }

}
