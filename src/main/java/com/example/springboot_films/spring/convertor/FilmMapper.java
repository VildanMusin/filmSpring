package com.example.springboot_films.spring.convertor;

import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.dto.FilmDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public FilmMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // из entity в dto
    public FilmDto entityToDto(Film film) {
        return modelMapper.map(film, FilmDto.class);
    }

    // из dto в entity
    public Film dtoToEntity(FilmDto filmDto) {
        return modelMapper.map(filmDto, Film.class);
    }
}