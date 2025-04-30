package com.example.springboot_films.spring.repository;

import com.example.springboot_films.spring.dto.FilmSearchFilter;
import com.example.springboot_films.spring.entity.Film;
import org.springframework.data.domain.Page;

public interface FilmCustomRepository {
    Page<Film> findByFilter(FilmSearchFilter filter);
}
