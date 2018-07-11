package com.hnyp.axon.test.controller;

import com.hnyp.axon.test.command.CreateVportCommand;
import com.hnyp.axon.test.command.UpdateVportNameCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.Future;

@RestController
public class VportController {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;

    @PostMapping("/vport")
    Future<String> createVport() {
        return commandGateway.send(new CreateVportCommand(UUID.randomUUID().toString()));
    }

    @PutMapping("/vport/{id}")
    void updateVport(@PathVariable String id, @RequestBody UpdatePayload payload) {
        commandGateway.send(new UpdateVportNameCommand(id, payload.name));
    }

//    @GetMapping("/vport/{id}")
//    String getVport(@PathVariable String id) {
//        queryGateway.query(, )
//    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UpdatePayload {
        private String name;
    }

}
