package com.hnyp.axon.connection.service.resource;

import com.hnyp.axon.connection.service.dto.ConnectionDetails;
import com.hnyp.axon.connection.service.dto.CreateConnectionPayload;
import com.hnyp.axon.connection.service.entity.Connection;
import com.hnyp.axon.connection.service.entity.ConnectionState;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@RestController
@RequestMapping("/connection")
public class ConnectionResource {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @PostMapping
    public String create(@RequestBody CreateConnectionPayload payload) {
        Connection connection = new Connection();
        connection.setId(UUID.randomUUID().toString());
        connection.setName(payload.getName());
        connection.setState(ConnectionState.PROVISIONING);

        em.persist(connection);

        return connection.getId();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionDetails> get(@PathVariable String id) {
        Connection connection = em.find(Connection.class, id);
        if (connection != null) {
            return ResponseEntity.ok(new ConnectionDetails(connection.getId(),
                    connection.getName(),
                    connection.getState().toString())
            );
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        Connection connection = em.find(Connection.class, id);
        connection.setState(ConnectionState.DELETED);
    }


}
