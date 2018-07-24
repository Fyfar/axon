package com.hnyp.axon.connection.service.resource;

import static java.util.UUID.randomUUID;

import com.hnyp.axon.api.entity.State;
import com.hnyp.axon.connection.service.dto.ConnectionDetails;
import com.hnyp.axon.connection.service.dto.CreateConnectionPayload;
import com.hnyp.axon.connection.service.entity.Connection;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        connection.setVportId(payload.getVportId());
        connection.setName(payload.getName());
        connection.setState(State.DEPLOYING);

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
                    connection.getVportId(),
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
