package com.example.springboot_films.spring.messaging;

import com.example.springboot_films.spring.config.ActiveMQConfig;
import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.service.FilmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmProducer {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public FilmProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(List<ItemDto> films) {
        jmsTemplate.convertAndSend(ActiveMQConfig.QUEUE, films);
    }
}
