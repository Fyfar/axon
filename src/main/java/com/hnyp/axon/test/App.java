package com.hnyp.axon.test;

import com.hnyp.axon.test.aggregate.VportAggregate;
import com.hnyp.axon.test.command.CreateVportCommand;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;

public class App {

    public static void main(String[] args) {
        Configuration config = DefaultConfigurer
                .defaultConfiguration()
                .configureAggregate(VportAggregate.class)
                .buildConfiguration();

        config.commandBus().dispatch(GenericCommandMessage.asCommandMessage(new CreateVportCommand("new vport 1")));


    }

}
