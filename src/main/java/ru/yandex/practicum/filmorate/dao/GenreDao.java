package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDao {
    public Collection<Genre> getAllGenres();

    public Genre getGenreById(int id);

    public Collection<Genre> getFilmGenres(int id);

    public void saveFilmGenres(int filmId, Collection<Genre> genres);
}
