package com.example.springboot_films.spring.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "films")
public class Film implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "filmId")
    private Integer filmId;

    @Column(name = "filmName")
    private String filmName;

    @Column(name = "year")
    private Integer year;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "description")
    private String description;


    private long title;
    public Film() {
    }

    public Film(Integer filmId, String filmName, Integer year, Double rating, String description) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.year = year;
        this.rating = rating;
        this.description = description != null ? description : ""; // Если описание отсутствует, устанавливаем пустую строку
    }

    public Film(int kinopoiskId, String nameRu, String year, Double ratingKinopoisk, String description) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = Math.toIntExact(filmId);
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                '}';
    }


    public void setTitle(long title) {
        this.title = title;
    }

    public void setYear(String year) {
    }



    public String getTitle() {
        return null;
    }
}
