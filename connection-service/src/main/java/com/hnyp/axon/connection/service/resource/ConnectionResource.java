package com.hnyp.axon.connection.service.resource;

import static java.util.UUID.randomUUID;

import com.hnyp.axon.connection.service.dto.ConnectionDetails;
import com.hnyp.axon.connection.service.dto.CreateConnectionPayload;
import com.hnyp.axon.connection.service.entity.Connection;
import com.hnyp.axon.connection.service.entity.ConnectionState;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
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
        connection.setId(randomUUID().toString());
        connection.setName(payload.getName());
        connection.setState(ConnectionState.PROVISIONING);

        em.persist(connection);

        return connection.getId();
    }

    @Transactional
    @PostMapping("/{id}")
    public String update(@RequestBody Connection payload) {
        em.merge(payload);

        return payload.getId();
    }

    @GetMapping("/{vportName}")
    public ResponseEntity<ConnectionDetails> get(@PathVariable String vportName) {
        List<Connection> result = em.createQuery("SELECT c FROM Connection c WHERE c.name LIKE :name",
                Connection.class).setParameter("name", vportName).setMaxResults(1).getResultList();
        if (result != null && !result.isEmpty()) {
            Connection connection = result.get(0);
            return ResponseEntity.ok(new ConnectionDetails(connection.getId(),
                    connection.getName(),
                    connection.getState().toString())
            );
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{vportName}")
    public void delete(@PathVariable String vportName) {
        Query query = em.createQuery("UPDATE Connection c SET c.state = 3 WHERE c.name LIKE :name")
                .setParameter("name", vportName);
        query.executeUpdate();
    }

    @GetMapping("/all")
    public List<Connection> getAll() {
        return em.createQuery("SELECT c from Connection c", Connection.class).getResultList();
    }


}
