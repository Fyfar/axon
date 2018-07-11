package com.hnyp.axon.test.aggregate;

import com.hnyp.axon.test.command.CreateVportCommand;
import com.hnyp.axon.test.command.UpdateVportNameCommand;
import com.hnyp.axon.test.event.VportCreatedEvent;
import com.hnyp.axon.test.event.VportNameUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Slf4j
@NoArgsConstructor
@Aggregate
//@Entity
public class VportAggregate {

    @Id
    @AggregateIdentifier
    private String id;

    private String name;

    @CommandHandler
    public VportAggregate(CreateVportCommand command) {
        log.info("create vport command, uid = {}", command.getUuid());
        AggregateLifecycle.apply(new VportCreatedEvent(command.getUuid()));
    }

    @EventSourcingHandler
    public void on(VportCreatedEvent event) {
        log.info("vport created with uid = {}", event.getUuid());
        this.id = event.getUuid();
    }

    @CommandHandler
    public void updateName(UpdateVportNameCommand command) {
        AggregateLifecycle.apply(new VportNameUpdatedEvent(command.getName()));
    }

    @EventSourcingHandler
    public void on(VportNameUpdatedEvent event) {
        log.info("vport '{}' name updated to '{}'", id, event.getName());
        this.name = event.getName();
    }


}
