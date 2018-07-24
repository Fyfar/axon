package com.hnyp.axon.vport.rest.domain;

import static java.util.UUID.randomUUID;

import com.hnyp.axon.api.command.CreateVportCommand;
import com.hnyp.axon.api.command.UpdateVportStatusCommand;
import com.hnyp.axon.api.entity.State;
import com.hnyp.axon.api.event.VportCreatedEvent;
import com.hnyp.axon.api.event.VportUpdateStatus;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@NoArgsConstructor
@Aggregate
@ToString
public class Vport {

    @AggregateIdentifier
    private String id;

    private String name;

    private String description;

    private State state;

    private String connectionId;

    private String genericVportId;

    @CommandHandler
    public Vport(CreateVportCommand command) {
        log.info("CreateVportCommand, triggering VportCreatedEvent");
        AggregateLifecycle.apply(new VportCreatedEvent(randomUUID().toString(),
                command.getName(),
                command.getDescription()));
    }

    @CommandHandler
    public void handle(UpdateVportStatusCommand command) {
        log.info("UpdateVportStatusCommand, triggering VportUpdateStatus");
        log.info("UpdateVportStatusCommand vportId is: {}", command.getVportId());

        AggregateLifecycle.apply(new VportUpdateStatus(randomUUID().toString(),
                command.getVportName(),
                "",
                command.getState()));

        //AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(VportCreatedEvent event) {
        log.info("Creating generic VPort...");
        log.info("VportCreatedEvent is: {}", event);

        this.id = event.getVportId();
        this.name = event.getDescription();
        this.state = State.DEPLOYING;

        log.info("Created vPort: {}", this);
    }

    @EventSourcingHandler
    public void on(VportUpdateStatus event) {
        log.info("Updating generic VPort state...");
        log.info("Current vport: {}", this);

        this.state = event.getState();

        log.info("Vport after status updating: {}", this);
    }

}
