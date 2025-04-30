package com.example.springboot_films.spring.controller;

import com.example.springboot_films.spring.convertor.FilmMapper;
import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.dto.FilmSearchFilter;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import com.example.springboot_films.spring.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@RestController
@RequestMapping("/api/v2")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmRepository filmRepository; // Ваш репозиторий для доступа к данным

    @Autowired
    private FilmMapper filmMapper; // Инъекция FilmMapper

    @PostMapping("/films")
    public ResponseEntity<Void> addFilms(@RequestHeader("X-API-KEY") String token) {
        try {
            FilmDto filmResponse = filmService.fetchFilms(token);
            for (ItemDto film : filmResponse.getItems()) {
                if (!filmRepository.existsByFilmId(film.getKinopoiskId())) {

                    Film filmEntity = new Film(
                            film.getKinopoiskId(),
                            film.getNameRu(),
                            Integer.valueOf(film.getYear()),
                            film.getRatingKinopoisk(),
                            film.getDescription() != null ? film.getDescription() : "Описание отсутствует"
                    );

                    filmRepository.save(filmEntity);
                    logger.info("Фильм {} сохранен.", film.getNameRu());
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (HttpClientErrorException e) {
            logger.error("Ошибка при запросе к API: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Ошибка при добавлении фильмов: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/film")
    public List<ItemDto> getFilms(){
       return filmService.getFilms();
    }


    @GetMapping("/filmAll")
    public List<Film> getFromDb(){
        return filmService.getFromDb();
    }

    @GetMapping("/films/filter")
    public Page<Film> searchFilmsWithFilters(FilmSearchFilter filter) {
        return filmRepository.findByFilter(filter);
    }

}