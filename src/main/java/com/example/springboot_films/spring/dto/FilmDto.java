package com.example.springboot_films.spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilmDto {
    private int total;
    private int totalPages;
    private List<ItemDto> items; // Так как это массив объектов ItemDto

    public FilmDto() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
