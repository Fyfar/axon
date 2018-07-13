package com.hnyp.axon.vport.rest;

import org.axonframework.amqp.eventhandling.spring.SpringAMQPPublisher;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public Exchange vportExchange() {
        return ExchangeBuilder.fanoutExchange("VportEvents").durable(true).build();
    }

    @Bean
    public Queue vportQueue() {
        return QueueBuilder.durable("VportQueue").build();
    }

    @Bean
    public Binding vportBinding() {
        return BindingBuilder.bind(vportQueue()).to(vportExchange()).with("*").noargs();
    }

    @Autowired
    public void configure(AmqpAdmin admin) {
        admin.declareExchange(vportExchange());
        admin.declareQueue(vportQueue());
        admin.declareBinding(vportBinding());
    }

    @Bean
    public Serializer jacksonSerializer() {
        return new JacksonSerializer();
    }

    @Bean(initMethod = "start")
    public SpringAMQPPublisher springAMQPPublisher(EventBus eventBus) {
        SpringAMQPPublisher publisher = new SpringAMQPPublisher(eventBus) {
            @Override
            public void start() {
                super.start();
            }
        };
        publisher.setExchange(vportExchange());
        return publisher;
    }

}
