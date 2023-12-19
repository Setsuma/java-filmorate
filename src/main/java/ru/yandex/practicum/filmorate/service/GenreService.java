package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {
    private final GenreDaoImpl genreDaoImpl;

    public Collection<Genre> getAllGenres() {
        log.info("получен запрос на получение всех жанров");
        return genreDaoImpl.getAllGenres();
    }

    public Genre getGenreById(int id) {
        log.info("получен запрос на получение жанра по id");
        return genreDaoImpl.getGenreById(id);
    }
}