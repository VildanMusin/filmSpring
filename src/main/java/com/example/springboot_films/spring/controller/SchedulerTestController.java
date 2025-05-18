package com.example.springboot_films.spring.controller;

import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.service.FilmSenderService;
import com.example.springboot_films.spring.service.FilmService;
import com.example.springboot_films.spring.service.GenreScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerTestController {

    private final GenreScheduleService genreScheduleService;
    private final FilmService filmService;
    private final FilmSenderService filmSenderService;

    @Autowired
    public SchedulerTestController(FilmSenderService filmSenderService,
                          GenreScheduleService genreScheduleService,
                          FilmService filmService) {
        this.filmSenderService = filmSenderService;
        this.genreScheduleService = genreScheduleService;
        this.filmService = filmService;
    }

    @GetMapping("/send-today-genre")
    public ResponseEntity<String> sendFilmsForTodayGenre(@RequestHeader("X-API-KEY") String token) {
        int genreId = genreScheduleService.getGenreIdForToday();  // ✔ Уже ID

        List<ItemDto> films = filmService.fetchTop50FilmsByGenreId(token, genreId); // ✔ уже передаём ID

        if (films.isEmpty()) {
            return ResponseEntity.ok("Не найдено фильмов для жанра с ID '" + genreId + "'.");
        }

        filmSenderService.sendFilms(films);

        return ResponseEntity.ok("Sent " + films.size() + " films of genre ID '" + genreId + "' to the queue");
    }
}