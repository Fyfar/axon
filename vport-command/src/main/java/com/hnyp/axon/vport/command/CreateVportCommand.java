package com.hnyp.axon.vport.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class CreateVportCommand {

    @TargetAggregateIdentifier
    private String uuid;

}
