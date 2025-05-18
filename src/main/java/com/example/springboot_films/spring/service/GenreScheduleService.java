package com.example.springboot_films.spring.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@Service
public class GenreScheduleService {

    private final Map<DayOfWeek, Integer> schedule;

    public GenreScheduleService() {
        schedule = Map.of(
                DayOfWeek.MONDAY, 13,     // комедия
                DayOfWeek.TUESDAY, 2,     // драма
                DayOfWeek.WEDNESDAY, 11,  // боевик
                DayOfWeek.THURSDAY, 1,    // триллер
                DayOfWeek.FRIDAY, 6,      // фэнтези
                DayOfWeek.SATURDAY, 17,   // ужасы
                DayOfWeek.SUNDAY, 4       // мелодрама (romance)
        );
    }

    public int getGenreIdForToday() {
        return Integer.parseInt(String.valueOf(schedule.get(LocalDate.now().getDayOfWeek())));
    }
}
