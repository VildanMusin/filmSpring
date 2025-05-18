package com.example.springboot_films.spring.Scheduler;

import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.messaging.FilmProducer;
import com.example.springboot_films.spring.service.FilmService;
import com.example.springboot_films.spring.service.GenreScheduleService;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class FilmScheduler {

    private final GenreScheduleService genreService;
    private final FilmService filmService;
    private final FilmProducer producer;

    @Value("${kinopoisk.api-key}")
    private String apiKey;

    @Autowired
    public FilmScheduler(GenreScheduleService genreService, FilmService filmService, FilmProducer producer) {
        this.genreService = genreService;
        this.filmService = filmService;
        this.producer = producer;
    }

    // Для боевого запуска
    @Scheduled(cron = "0 0 7 * * ?") // каждый день в 7:00
    public void scheduleFilmFetch() {
        fetchAndSendFilms();
    }

    // Для теста — вызывается вручную через контроллер
    public void fetchAndSendFilms() {
        String genreId = String.valueOf(genreService.getGenreIdForToday());
        List<ItemDto> films = filmService.fetchFilmsByGenre(apiKey, genreId).getItems();
        if (films != null && !films.isEmpty()) {
            producer.send(films);
        }
    }
}

