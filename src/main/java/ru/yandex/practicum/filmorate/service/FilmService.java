package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmLikesDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmDbStorage filmStorage;
    private final FilmLikesDaoImpl likesDao;

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
        likesDao.addLike(filmId, userId);
        log.info("Поставлен лайк фильму с id: " + filmId + " пользователем с id: " + userId);
    }

    public void unlikeFilm(int filmId, int userId) {
        likesDao.removeLike(filmId, userId);
        log.info("Убран лайк фильму с id: " + filmId + " пользователем с id: " + userId);
    }

    public Collection<Film> getPopularFilms(int count) {
        log.info("получен запрос на получение топ " + count + " фильмов");
        Collection<Film> films = likesDao.getPopularFilms(count);
        int id = 1;
        while (films.size() < count && id <= filmStorage.getAll().size()) {
            films.add(filmStorage.getById(id++));
        }
        return films;
    }
}