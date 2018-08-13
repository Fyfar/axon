package com.hnyp.axon.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VportDeployFailedEvent {

    @AggregateIdentifier
    private String vportId;

    private String name;

    private String description;

}
