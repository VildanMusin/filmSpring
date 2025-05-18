package com.example.springboot_films.spring.Service;

import com.example.springboot_films.spring.dto.FilmDto;
import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.repository.FilmRepository;
import com.example.springboot_films.spring.service.FilmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpHeaders httpHeaders;

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @Test
    void testGetFromDb_shouldReturnAllFilms() {
        List<Film> mockFilms = List.of(new Film(), new Film());

        Mockito.when(filmRepository.findAll()).thenReturn(mockFilms);

        List<Film> result = filmService.getFromDb();

        Assertions.assertEquals(2, result.size());
        Mockito.verify(filmRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testFetchFilms_shouldReturnFilmDto() {
        String token = "test-token";
        String url = "https://kinopoiskapiunofficial.tech/api/v2.2/films";

        FilmDto mockResponse = new FilmDto();
        ResponseEntity<FilmDto> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Mockito.eq(url),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class),
                Mockito.eq(FilmDto.class))
        ).thenReturn(responseEntity);

        FilmDto result = filmService.fetchFilms(token);

        Assertions.assertNotNull(result);
    }

    @Test
    void testFetchFilms_shouldThrowOnHttpClientError() {
        String token = "bad-token";
        String url = "https://kinopoiskapiunofficial.tech/api/v2.2/films";

        Mockito.when(restTemplate.exchange(
                Mockito.eq(url),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class),
                Mockito.eq(FilmDto.class))
        ).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            filmService.fetchFilms(token);
        });

        Assertions.assertTrue(exception.getMessage().contains("Ошибка при запросе к API"));
    }
}
