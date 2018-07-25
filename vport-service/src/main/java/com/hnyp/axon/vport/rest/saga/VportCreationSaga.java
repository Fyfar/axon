package com.hnyp.axon.vport.rest.saga;

import com.hnyp.axon.api.entity.State;
import com.hnyp.axon.api.event.VportCreatedEvent;
import com.hnyp.axon.api.event.VportUpdateStatus;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import java.io.Serializable;

@Slf4j
@Saga
public class VportCreationSaga implements Serializable {

    private String vportId;

    @StartSaga
    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportCreatedEvent event) {
        log.info("VportCreatedEvent saga triggered, vport id = {}", event.getVportId());
        this.vportId = event.getVportId();
        log.info("Set vportId: {}", this.vportId);
        SagaLifecycle.associateWith("vportId", event.getVportId());
    }

    @SagaEventHandler(associationProperty = "vportId")
    public void on(VportUpdateStatus event) {
        log.info("VportUpdateStatus saga triggered, vport id = {}", event.getVportId());
        log.info("Current state of VportId: {}", this.vportId);

        if (event.getState() != State.DEPLOYING) {
            SagaLifecycle.end();
        }
    }

}
