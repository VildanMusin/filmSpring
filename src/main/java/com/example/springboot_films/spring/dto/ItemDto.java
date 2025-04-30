package com.example.springboot_films.spring.dto;

import java.util.List;

public class ItemDto {
    private int kinopoiskId;
    private String nameRu;
    private String year;
    private Double ratingKinopoisk;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(int kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getRatingKinopoisk() {
        return ratingKinopoisk;
    }

    public void setRatingKinopoisk(Double ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
    }
}

