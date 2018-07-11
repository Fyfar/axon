package com.hnyp.axon.test.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class UpdateVportNameCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

}
