package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface FilmStorage extends Storage<Film> {
    Film add(Film film);

    void remove(Film film);

    Film update(Film film);

    Film getById(int id);

    Collection<Film> getAll();

    Map<Integer, Film> getAllWithIds();
}