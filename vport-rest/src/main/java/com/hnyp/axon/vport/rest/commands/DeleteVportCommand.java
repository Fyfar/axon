package com.hnyp.axon.vport.rest.commands;

import org.axonframework.commandhandling.model.AggregateIdentifier;

public class DeleteVportCommand {

    @AggregateIdentifier
    private String id;

}
