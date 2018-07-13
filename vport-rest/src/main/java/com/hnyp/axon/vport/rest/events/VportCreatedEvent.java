package com.hnyp.axon.vport.rest.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Getter
@AllArgsConstructor
public class VportCreatedEvent {

    @AggregateIdentifier
    private String vportId;

    private String name;

    private String description;

}
