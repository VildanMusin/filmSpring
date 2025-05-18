package com.example.springboot_films.spring.service;

import com.example.springboot_films.spring.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmSenderService {

    private final JmsTemplate jmsTemplate;
    private final String queueName = "filmsQueue";
    private final FilmService filmService;
    private final GenreScheduleService genreScheduleService;

    @Autowired
    public FilmSenderService(JmsTemplate jmsTemplate,
                             FilmService filmService,
                             GenreScheduleService genreScheduleService) {
        this.jmsTemplate = jmsTemplate;
        this.filmService = filmService;
        this.genreScheduleService = genreScheduleService;
    }

    public void sendFilms(List<ItemDto> films) {
        for (ItemDto film : films) {
            jmsTemplate.convertAndSend(queueName, film);
        }
    }

    @Scheduled(cron = "0 0 7 * * *") // каждый день в 7 утра
    public void sendDailyFilmsByGenre() {
        String token = "ТВОЙ_API_КЛЮЧ"; // можешь вынести в application.yml
        int genre = genreScheduleService.getGenreIdForToday();
        Integer genreId = filmService.getGenreIdByName(String.valueOf(genre), token);

        if (genreId == null) {
            System.out.println("Жанр '" + genre + "' не найден.");
            return;
        }

        List<ItemDto> films = filmService.fetchTop50FilmsByGenreId(token, genreId);
        sendFilms(films);
        System.out.println("Sent " + films.size() + " films of genre '" + genre + "' to the queue");
    }

}
