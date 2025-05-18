package com.example.springboot_films.spring.messaging;

import com.example.springboot_films.spring.config.ActiveMQConfig;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmConsumer {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmConsumer(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @JmsListener(destination = ActiveMQConfig.QUEUE)
    public void receive(List<ItemDto> films) {
        for (ItemDto dto : films) {
            if (!filmRepository.existsByFilmId(dto.getKinopoiskId())) {
                Film film = new Film(
                        dto.getKinopoiskId(),
                        dto.getNameRu(),
                        Integer.parseInt(dto.getYear()),
                        dto.getRatingKinopoisk(),
                        "Описание отсутствует"
                );
                filmRepository.save(film);
            }
        }
    }
}

