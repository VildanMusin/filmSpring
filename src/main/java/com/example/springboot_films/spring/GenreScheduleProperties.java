package com.example.springboot_films.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@ConfigurationProperties(prefix = "genre-schedule")
public class GenreScheduleProperties {
    private Map<String, String> map;
    public Map<String, String> getMap() { return map; }
    public void setMap(Map<String, String> map) { this.map = map; }
}