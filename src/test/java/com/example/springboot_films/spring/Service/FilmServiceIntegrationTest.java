package com.example.springboot_films.spring.Service;
import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.entity.Film;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.dto.ItemDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import com.example.springboot_films.spring.service.FilmService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FilmServiceIntegrationTest {

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmRepository filmRepository;  // Реальный репозиторий с H2


    @MockBean
    private RestTemplate restTemplate;  // Мокируем вызовы API

//    @MockBean
//    private HttpHeaders httpHeaders;  // Мокируем заголовки

    @Test
    public void testGetFilmsFromApi_Success() {
        // Подготовка мок-ответа от API
        FilmDto mockFilmDto = new FilmDto();
        ItemDto item1 = new ItemDto();
        item1.setNameRu("Inception");
        ItemDto item2 = new ItemDto();
        item2.setNameRu("Interstellar");
        mockFilmDto.setItems(List.of(item1, item2));

        ResponseEntity<FilmDto> mockResponse = new ResponseEntity<>(mockFilmDto, HttpStatus.OK);

        // Мокируем вызов RestTemplate
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(FilmDto.class)
        )).thenReturn(mockResponse);

        // Вызываем метод сервиса
        List<ItemDto> result = filmService.getFilms();

        // Проверяем результат
        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getNameRu());
        assertEquals("Interstellar", result.get(1).getNameRu());

    }

    @Test
    public void testGetFromDb_Success() {
        // Подготовка данных в H2
        Film film1 = new Film();
        film1.setFilmName("The Matrix");
        filmRepository.save(film1);

        Film film2 = new Film();
        film2.setFilmName("The Shawshank Redemption");
        filmRepository.save(film2);

        // Вызываем метод сервиса
        List<Film> result = filmService.getFromDb();

        // Проверяем результат
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getFilmName().equals("The Matrix")));
        assertTrue(result.stream().anyMatch(f -> f.getFilmName().equals("The Shawshank Redemption")));
    }

    @Test
    public void testFetchFilms_InvalidToken_ThrowsException() {
        // Мокируем ошибку от API
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(FilmDto.class)
        )).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid API key"));

        // Проверяем, что метод выбрасывает исключение
        assertThrows(RuntimeException.class, () -> filmService.fetchFilms("invalid-token"));
    }
}