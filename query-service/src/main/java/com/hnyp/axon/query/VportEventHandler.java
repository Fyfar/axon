package com.hnyp.axon.query;

import com.hnyp.axon.api.event.VportCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@ProcessingGroup("vportEvents")
@Component
class VportEventHandler {

    @EventHandler
    public void handle(VportCreatedEvent event) {
        log.info("Vport created event, vport id = {}", event.getVportId());
        // todo create read model and save it
    }

}
