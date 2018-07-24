package com.hnyp.axon.vport.rest;

import com.hnyp.axon.vport.rest.domain.Vport;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public EventStore eventStore(EventStorageEngine eventStorageEngine) {
        return new EmbeddedEventStore(eventStorageEngine);
    }

    @Bean
    public Repository<Vport> eventSourcingRepository(EventStorageEngine eventStorageEngine) {

        return new EventSourcingRepository<>(Vport.class, eventStore
                (eventStorageEngine));
    }

}
