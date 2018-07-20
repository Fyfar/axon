package com.hnyp.axon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnyp.axon.connection.service.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ConnectionServiceClient {

    private static final String URL = "http://localhost:9092/connection";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RestTemplate rest;

    @SneakyThrows
    public void createVportRecord(String vportName) {
        String json = "{\"name\": \"" + vportName + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(json, getJsonHeaders());

        log.info("Sending request to connection-service for creating vport record in DB");
        rest.postForLocation(URL, entity);
    }

    @SneakyThrows
    public void updateVport(Connection connection) {
        String json = OBJECT_MAPPER.writeValueAsString(connection);
        HttpEntity<String> entity = new HttpEntity<>(json, getJsonHeaders());

        log.info("Sending request to connection-service for updating vport status in DB");
        rest.postForLocation(URL + "/" + connection.getId(), entity);
    }

    @SneakyThrows
    public List<Connection> checkVportsStatus() {
        log.info("Get information from connection-service about vports");
        String response = rest.getForObject(URL + "/all", String.class);
        log.info("VPorts (json): {}", response);

        return OBJECT_MAPPER.readValue(response, new TypeReference<List<Connection>>(){});
    }

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

}
