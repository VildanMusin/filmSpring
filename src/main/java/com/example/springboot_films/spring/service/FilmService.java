package com.example.springboot_films.spring.service;

import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class FilmService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(RestTemplate restTemplate, HttpHeaders httpHeaders, FilmRepository filmRepository) {
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

    public List<Film> getFromDb() {
        return filmRepository.findAll();
    }


    public void addFilm(ItemDto itemDto) {
    }


    public FilmDto fetchFilmsByGenre(String token, String genre) {
        String apiUrl = "https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=" + genre + "&page=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<FilmDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, FilmDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке фильмов по жанру " + genre, e);
        }
    }

    public Integer getGenreIdByName(String genreName, String token) {
        String apiUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films/filters";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, JsonNode.class);
            JsonNode genresArray = response.getBody().get("genres");

            for (JsonNode genre : genresArray) {
                if (genre.get("genre").asText().equalsIgnoreCase(genreName)) {
                    return genre.get("id").asInt();
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении жанров: " + e.getMessage(), e);
        }
    }

    public List<ItemDto> fetchTop50FilmsByGenreId(String token, int genreId) {
        String apiUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films?genres=" + genreId + "&page=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<FilmDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, FilmDto.class);
            FilmDto filmDto = response.getBody();
            if (filmDto == null || filmDto.getItems() == null) {
                return Collections.emptyList();
            }
            List<ItemDto> items = filmDto.getItems();
            return items.size() > 50 ? items.subList(0, 50) : items;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке фильмов по жанру ID " + genreId, e);
        }
    }


}






