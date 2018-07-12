package com.hnyp.axon.connection.service.resource;

import com.hnyp.axon.connection.service.entity.Connection;
import com.hnyp.axon.connection.service.entity.ConnectionState;
import com.hnyp.axon.connection.service.models.ConnectionDetails;
import com.hnyp.axon.connection.service.models.CreateConnectionPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import javax.persistence.EntityManager;

@RestController
@RequestMapping("/connection")
public class ConnectionResource {

    @Autowired
    private EntityManager em;

    @Transactional
    @PostMapping
    String create(@RequestBody CreateConnectionPayload payload) {
        Connection connection = new Connection();
        connection.setId(UUID.randomUUID().toString());
        connection.setName(payload.getName());
        connection.setState(ConnectionState.PROVISIONING);

        em.persist(connection);

        return connection.getId();
    }

    @GetMapping("/{id}")
    ResponseEntity<ConnectionDetails> get(@PathVariable String id) {
        Connection connection = em.find(Connection.class, id);
        if (connection != null) {
            return ResponseEntity.ok(new ConnectionDetails(connection.getId(),
                    connection.getName(),
                    connection.getState().toString())
            );
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        Connection connection = em.find(Connection.class, id);
        connection.setState(ConnectionState.DELETED);
    }


}
