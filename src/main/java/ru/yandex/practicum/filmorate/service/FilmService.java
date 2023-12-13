package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    public void likeFilm(int filmId, int userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        filmStorage.getById(filmId).getUsersWhoLikedFilmIds().add(userId);
        log.info("Поставлен лайк фильму с id: " + filmId + " пользователем с id: " + userId);
    }

    public void unlikeFilm(int filmId, int userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        filmStorage.getById(filmId).getUsersWhoLikedFilmIds().remove(userId);
        log.info("Убран лайк фильму с id: " + filmId + " пользователем с id: " + userId);
    }

    public Collection<Film> getPopularFilms(int count) {
        if (filmStorage.getAll().size() == 0 || count < 1) return Collections.emptyList();
        if (count > filmStorage.getAll().size()) count = filmStorage.getAll().size();
        List<Film> topFilms = new ArrayList<>(filmStorage.getAll());

        topFilms.sort((o1, o2) -> o2.getUsersWhoLikedFilmIds().size() - o1.getUsersWhoLikedFilmIds().size());

        log.info("получен запрос на получение топ " + count + " фильмов");

        return topFilms.subList(0, count);
    }

    private void checkUserId(int userId) {
        if (!userStorage.getAllWithIds().containsKey(userId))
            throw new IdNotFoundException("Пользователь с данным id не найден");
    }

    private void checkFilmId(int filmId) {
        if (!filmStorage.getAllWithIds().containsKey(filmId))
            throw new IdNotFoundException("Фильм с данным id не найден");
    }
}