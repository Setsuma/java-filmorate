package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmLikesDao {

    public void addLike(int filmId, int userId);

    public void removeLike(int filmId, int userId);

    public Collection<Film> getPopularFilms(int count);
}