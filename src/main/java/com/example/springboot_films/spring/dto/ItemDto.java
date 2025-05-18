package com.example.springboot_films.spring.dto;

import java.io.Serializable;


public class ItemDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int kinopoiskId;
    private String nameRu;
    private String year;
    private Double ratingKinopoisk;

    private String description;

    private String title;

    private double rating;

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

    public long getTitle() {
        this.title = title;
        return 0;
    }

    public void setTitle() {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }
}

