package com.hnyp.axon.vport.rest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnyp.axon.api.command.CreateVportCommand;
import com.hnyp.axon.api.command.UpdateVportStatusCommand;
import com.hnyp.axon.api.entity.State;
import com.hnyp.axon.vport.rest.domain.BaseVport;
import com.hnyp.axon.vport.rest.domain.SagaInfo;
import com.hnyp.axon.vport.rest.models.ConnectionDetails;
import com.hnyp.axon.vport.rest.repositories.VportRepository;
import com.hnyp.axon.vport.rest.saga.VportCreationSaga;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.saga.AssociationValue;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/vport")
@Slf4j
public class VportResource {

    private static final String STATUS_NAME = "ACCEPTED";
    private static final String URL = "http://localhost:9092/connection/";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private CommandGateway commandGateway;
    private SagaConfiguration<VportCreationSaga> vportCreationSagaConfiguration;
    private VportRepository vportRepository;

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

        commandGateway.send(new UpdateVportStatusCommand(connectionDetails.getVportId(),
                vportName,
                State.valueOf(connectionDetails.getStatus())));

        return connectionDetails;
    }

    @GetMapping("/sagas")
    @ResponseBody
    public List<SagaInfo> getAllSagas() {
        List<BaseVport> allAggregates = vportRepository.findAll();
        SagaStore<? super VportCreationSaga> sagaStore = vportCreationSagaConfiguration.getSagaStore();

        List<SagaInfo> sagas = new ArrayList<>();
        for (BaseVport aggregate : allAggregates) {
            AssociationValue value = new AssociationValue("vportId", aggregate.getId());
            Set<String> sagasId = sagaStore.findSagas(VportCreationSaga.class, value);

            Iterator<String> iteratorById = sagasId.iterator();
            if (!iteratorById.hasNext()) {
                return Collections.emptyList();
            }

            String sagaId = iteratorById.next();
            VportCreationSaga vportSaga = sagaStore.loadSaga(VportCreationSaga.class, sagaId).saga();

            SagaInfo sagaInfo = new SagaInfo();
            sagaInfo.setId(sagaId);
            sagaInfo.setBaseVport(aggregate);
            sagaInfo.setActive(vportSaga.isSagaIsActive());

            sagas.add(sagaInfo);
        }

        return sagas;
    }

    @SneakyThrows
    private static ConnectionDetails sendGet(String vportName) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(URL + vportName);
        get.setHeader("Accept", "application/json");

        log.info("Send get request");
        HttpResponse response = client.execute(get);

        return OBJECT_MAPPER.readValue(response.getEntity().getContent(), ConnectionDetails.class);
    }

}
