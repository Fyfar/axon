package com.hnyp.axon.test.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class CreateVportCommand {

    @TargetAggregateIdentifier
    private String uuid;

}
