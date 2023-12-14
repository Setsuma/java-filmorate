package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Film getFilmById(int id) {
        return filmStorage.getById(id);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

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
        List<Film> films = new ArrayList<>(filmStorage.getAll());

        List<Film> topFilms = films.stream()
                .sorted((o1, o2) -> o2.getUsersWhoLikedFilmIds().size() - o1.getUsersWhoLikedFilmIds().size())
                .limit(Math.min(count, films.size()))
                .collect(Collectors.toList());

        log.info("получен запрос на получение топ " + count + " фильмов");

        return topFilms;
    }

    private void checkUserId(int userId) throws IdNotFoundException {
        filmStorage.getById(userId);
    }

    private void checkFilmId(int filmId) throws IdNotFoundException {
        filmStorage.getById(filmId);
    }
}