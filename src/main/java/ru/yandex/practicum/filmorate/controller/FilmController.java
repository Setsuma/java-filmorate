package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.controller.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film, BindingResult errors) {
        if (errors.hasErrors()) {
            FieldError error = errors.getFieldError();
            throw new ValidationException(error.getDefaultMessage());
        }

        film.setId(id);
        films.put(id++, film);
        log.info("Добавлен новый фильм: " + film);
        return ResponseEntity.ok(film);
    }

    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film, BindingResult errors) {
        if (errors.hasErrors()) {
            FieldError error = errors.getFieldError();
            throw new ValidationException(error.getDefaultMessage());
        }

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм обновлен: " + film);
            return ResponseEntity.ok(film);
        } else {
            throw new IdNotFoundException("Фильм с данным id не найден");
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAllFilms() {
        log.info("Был выполнен запрос на получение всех фильмов");
        return ResponseEntity.ok(films.values());
    }
}
