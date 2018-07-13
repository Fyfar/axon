package com.hnyp.axon.query;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableAxon
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Serializer jacksonSerializer() {
        return new JacksonSerializer();
    }

    @Primary
    @Bean
    public SpringAMQPMessageSource vportEventsQueue(Serializer serializer, EventBus eventBus) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {
            {
                // register event bus as event processor
                subscribe(eventBus::publish);
            }

            @Transactional
            @RabbitListener(queues = "VportQueue")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                super.onMessage(message, channel);
            }
        };
    }

    // does not work
    // https://docs.axonframework.org/v/3.0/part-iii-infrastructure-components/event-processing
//    @Autowired
//    public void configure(EventHandlingConfiguration ehConfig, SpringAMQPMessageSource myMessageSource) {
//        ehConfig.registerSubscribingEventProcessor("aaa", c -> myMessageSource);
//    }

}