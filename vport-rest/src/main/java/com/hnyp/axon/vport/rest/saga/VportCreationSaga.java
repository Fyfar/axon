package com.hnyp.axon.vport.rest.saga;

import com.hnyp.axon.vport.rest.domain.Vport;
import com.hnyp.axon.vport.rest.events.VportCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VportCreationSaga {

    @Autowired
    private Repository<Vport> vportRepository;

    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportCreatedEvent event) {
        log.info("VportCreatedEvent saga triggered, vport id = {}", event.getVportId());
    }

}
