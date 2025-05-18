package com.example.springboot_films.spring.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ActiveMQConfig {

    public static final String QUEUE = "films-queue";

    @Bean
    public Queue queue() {
        return (Queue) new ActiveMQQueue(QUEUE);
    }
}
