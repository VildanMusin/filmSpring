package com.example.springboot_films.spring.repository;

import com.example.springboot_films.spring.dto.FilmSearchFilter;
import com.example.springboot_films.spring.entity.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmCustomRepositoryImpl implements FilmCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Film> findByFilter(FilmSearchFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);
        Root<Film> film = cq.from(Film.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getCountries() != null && !filter.getCountries().isEmpty()) {
            predicates.add(film.get("country").in(filter.getCountries()));
        }

        if (filter.getGenres() != null && !filter.getGenres().isEmpty()) {
            predicates.add(film.get("genre").in(filter.getGenres()));
        }

        if (filter.getType() != null) {
            predicates.add(cb.equal(film.get("type"), filter.getType()));
        }

        if (filter.getRatingFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(film.get("rating"), filter.getRatingFrom()));
        }

        if (filter.getRatingTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(film.get("rating"), filter.getRatingTo()));
        }

        if (filter.getYearFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(film.get("year"), filter.getYearFrom()));
        }

        if (filter.getYearTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(film.get("year"), filter.getYearTo()));
        }

        if (filter.getImdbId() != null) {
            predicates.add(cb.equal(film.get("imdbId"), filter.getImdbId()));
        }

        if (filter.getKeyword() != null) {
            predicates.add(cb.like(cb.lower(film.get("filmName")), "%" + filter.getKeyword().toLowerCase() + "%"));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Сортировка
        if (filter.getOrder() != null) {
            cq.orderBy(cb.asc(film.get(filter.getOrder())));
        }

        TypedQuery<Film> query = entityManager.createQuery(cq);

        // Пагинация
        int page = filter.getPage();
        int size = filter.getSize();

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Film> resultList = query.getResultList();

        // Подсчёт общего количества записей
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Film> countRoot = countQuery.from(Film.class);
        countQuery.select(cb.count(countRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, PageRequest.of(page, size), total);
    }
}
