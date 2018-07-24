package com.hnyp.axon.api.event;

import com.hnyp.axon.api.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VportUpdateStatus {

    @AggregateIdentifier
    private String vportId;

    private String name;

    private String description;

    private State state;

}
