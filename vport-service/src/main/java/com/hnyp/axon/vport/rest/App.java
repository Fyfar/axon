package com.hnyp.axon.vport.rest;

import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAxon
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
