package com.hnyp.axon.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class CreateGenericVportFailedEvent {
    @AggregateIdentifier
    private String id;
}
