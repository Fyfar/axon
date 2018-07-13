package com.hnyp.axon.vport.rest.domain;

import com.hnyp.axon.vport.rest.commands.CreateVportCommand;
import com.hnyp.axon.vport.rest.events.VportCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static java.util.UUID.randomUUID;

@Slf4j
@NoArgsConstructor
@Aggregate
public class Vport {

    @AggregateIdentifier
    private String id;

    private String name;

    private String description;

    private State state;

    @CommandHandler
    public Vport(CreateVportCommand command) {
        log.info("CreateVportCommand, triggering VportCreatedEvent");
        AggregateLifecycle.apply(new VportCreatedEvent(randomUUID().toString(),
                command.getName(),
                command.getDescription())
        );
    }

    @EventSourcingHandler
    public void on(VportCreatedEvent event) {
        log.info("VportCreatedEvent fired in aggregate");
        this.id = event.getVportId();
        this.name = event.getDescription();
        this.state = State.DEPLOYING;
    }

}
