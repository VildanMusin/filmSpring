package com.example.springboot_films.spring.repository;

import com.example.springboot_films.spring.entity.Film;
import com.example.springboot_films.spring.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, FilmCustomRepository {
    boolean existsByFilmId (Integer filmId);
    @Query("SELECT f FROM Film f WHERE " +
            "(:name IS NULL OR LOWER(f.filmName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:year IS NULL OR f.year = :year) AND " +
            "(:rating IS NULL OR f.rating = :rating)")
    Page<Film> findFilmsByFilters(@Param("name") String name,
                                  @Param("year") Integer year,
                                  @Param("rating") Double rating,
                                  Pageable pageable);
}
