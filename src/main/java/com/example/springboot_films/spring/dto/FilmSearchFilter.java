package com.example.springboot_films.spring.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class FilmSearchFilter {
    private List<String> countries;
    private List<String> genres;
    private String type;
    private Double ratingFrom;
    private Double ratingTo;
    private Integer yearFrom;
    private Integer yearTo;
    private String imdbId;
    private String keyword;
    private String order; // название поля для сортировки
    private int page = 0;
    private int size = 10;

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getRatingFrom() {
        return ratingFrom;
    }

    public void setRatingFrom(Double ratingFrom) {
        this.ratingFrom = ratingFrom;
    }

    public Double getRatingTo() {
        return ratingTo;
    }

    public void setRatingTo(Double ratingTo) {
        this.ratingTo = ratingTo;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}