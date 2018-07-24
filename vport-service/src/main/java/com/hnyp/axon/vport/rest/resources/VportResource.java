package com.hnyp.axon.vport.rest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnyp.axon.api.command.CreateVportCommand;
import com.hnyp.axon.api.command.UpdateVportStatusCommand;
import com.hnyp.axon.api.entity.State;
import com.hnyp.axon.vport.rest.models.ConnectionDetails;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/vport")
@Slf4j
public class VportResource {

    private static final String STATUS_NAME = "ACCEPTED";
    private static final String URL = "http://localhost:9092/connection/";

    private CommandGateway commandGateway;

    @PostMapping
    public String create(@Valid @RequestBody CreateVportCommand command) {
        commandGateway.send(command);

        return STATUS_NAME;
    }

    @GetMapping("/{vportName}")
    @ResponseBody
    public ConnectionDetails get(@PathVariable String vportName) {
        ConnectionDetails connectionDetails = sendGet(vportName);
        log.info("ConnectionDetails response: {}", connectionDetails);
        log.info("VportId for updating is: {}", connectionDetails.getVportId());

        commandGateway.send(new UpdateVportStatusCommand(connectionDetails.getVportId(),
                vportName,
                State.valueOf(connectionDetails.getStatus())));

        return connectionDetails;
    }

    @SneakyThrows
    private static ConnectionDetails sendGet(String vportName) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(URL + vportName);
        get.setHeader("Accept", "application/json");

        log.info("Send get request");
        HttpResponse response = client.execute(get);

        return new ObjectMapper().readValue(response.getEntity().getContent(), ConnectionDetails.class);
    }

}
