package com.hnyp.axon.test.aggregate;

import com.hnyp.axon.test.command.CreateVportCommand;
import com.hnyp.axon.test.event.VportCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;

@Slf4j
@NoArgsConstructor
public class VportAggregate {

    @AggregateIdentifier
    private String id;

    @CommandHandler
    public VportAggregate(CreateVportCommand command) {
        log.info("create vport command, uid = {}", command.getUuid());
        AggregateLifecycle.apply(new VportCreatedEvent(command.getUuid()));
    }

    @EventSourcingHandler
    public void on(VportCreatedEvent event) {
        log.info("vport created wit uid = {}", event.getUuid());
        this.id = event.getUuid();
    }


}
