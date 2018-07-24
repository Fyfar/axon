package com.hnyp.axon.api.command;

import com.hnyp.axon.api.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
public class UpdateVportStatusCommand {

    @TargetAggregateIdentifier
    private String vportId;

    private String vportName;

    private State state;

}
