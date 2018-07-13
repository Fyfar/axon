package com.hnyp.axon.vport.rest.resources;

import com.hnyp.axon.vport.rest.commands.CreateVportCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/vport")
public class VportResource {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public void create(@Valid @RequestBody CreateVportCommand command) {
        commandGateway.send(command);
    }

}
