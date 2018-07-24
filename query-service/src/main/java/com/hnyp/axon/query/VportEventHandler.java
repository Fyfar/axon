package com.hnyp.axon.query;

import com.hnyp.axon.ConnectionServiceClient;
import com.hnyp.axon.api.event.VportCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@ProcessingGroup("vportEvents")
@Component
@AllArgsConstructor
class VportEventHandler {

    private ConnectionServiceClient client;

    @SagaEventHandler(associationProperty = "vportId")
    public void handle(VportCreatedEvent event) {
        log.info("Vport created event, vport id = {}", event.getVportId());
        log.info("Save into DB, vport id = {}", event.getVportId());

        client.createVportRecord(event.getVportId(), event.getName());
        // todo create read model and save it
    }

}
