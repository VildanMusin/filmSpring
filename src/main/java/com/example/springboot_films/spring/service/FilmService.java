package com.example.springboot_films.spring.service;

import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FilmService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(RestTemplate restTemplate, HttpHeaders httpHeaders, FilmRepository filmRepository){
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.filmRepository = filmRepository;
    }

    public FilmDto fetchFilms(String token) {
        String apiUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<FilmDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, FilmDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Ошибка при запросе к API: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Неожиданная ошибка: " + e.getMessage(), e);
        }
    }



    public List<ItemDto> getFilms() {
        String URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films";
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<FilmDto> response = restTemplate.exchange(URL, HttpMethod.GET, entity, FilmDto.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getItems();
        } else {
            throw new RuntimeException("Ошибка при получении данных с API: " + response.getStatusCode());
        }
    }

    public List<Film> getFromDb(){
        return filmRepository.findAll();
    }


}
