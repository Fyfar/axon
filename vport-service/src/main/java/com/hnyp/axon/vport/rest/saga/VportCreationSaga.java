package com.hnyp.axon.vport.rest.saga;

import com.hnyp.axon.api.event.GenericVportCreatedEvent;
import com.hnyp.axon.api.event.VportCreatedEvent;
import com.hnyp.axon.vport.rest.domain.Vport;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VportCreationSaga {

//    @Autowired
//    private transient Repository<Vport> vportRepository;

    @Autowired
    private transient CommandGateway commandGateway;

    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportCreatedEvent event, Vport vport) {
        log.info("VportCreatedEvent saga triggered, vport id = {}", event.getVportId());

//        commandGateway.send(new CreateGenericVportCommand());

    }

    @SagaEventHandler(associationProperty = "vportId")
    public void on(GenericVportCreatedEvent event, Vport vport) {

    }

}
