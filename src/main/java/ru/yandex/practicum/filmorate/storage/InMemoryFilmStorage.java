package ru.yandex.practicum.filmorate.storage;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
@NoArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int generatedId = 1;

    @Override
    public Film add(Film film) {
        film.setId(generatedId);
        films.put(generatedId++, film);
        log.info("Добавлен новый фильм: " + film);
        return film;
    }

    @Override
    public void remove(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
            log.info("Фильм удален: " + film);
        } else {
            throw new IdNotFoundException("Фильм с данным id не найден");
        }
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм обновлен: " + film);
            return film;
        } else {
            throw new IdNotFoundException("Фильм с данным id не найден");
        }
    }

    @Override
    public Film getById(int id) {
        if (films.containsKey(id)) {
            log.info("Получен фильм: " + films.get(id));
            return films.get(id);
        } else {
            throw new IdNotFoundException("Фильм с данным id не найден");
        }
    }

    @Override
    public Collection<Film> getAll() {
        log.info("Был выполнен запрос на получение всех фильмов");
        return films.values();
    }
}