package com.example.springboot_films.spring.service;

import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class FilmReceiverService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmReceiverService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @JmsListener(destination = "filmsQueue")
    @Transactional  // если нужно
    public void receiveFilm(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Object object = objectMessage.getObject();

                if (object instanceof ItemDto) {
                    ItemDto filmDto = (ItemDto) object;

                    Long kpId = (long) filmDto.getKinopoiskId();
                    if (kpId == null) return;

                    // Проверяем, есть ли фильм в БД
                    if (!filmRepository.existsByFilmId(kpId)) {
                        Film film = new Film();
                        film.setFilmId(kpId);
                        film.setTitle(filmDto.getTitle());  // название фильма
                        //film.setYear(filmDto.getYear());
                        //film.setRating(filmDto.getRating());
                        film.setFilmName(filmDto.getNameRu());
                        film.setDescription(filmDto.getDescription());
                        film.setYear(filmDto.getYear());
                        film.setRating(filmDto.getRating());
                        // заполни остальные поля, которые есть в ItemDto

                        Film savedFilm = filmRepository.save(film);
                        System.out.println("Saved film id: " + savedFilm.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

