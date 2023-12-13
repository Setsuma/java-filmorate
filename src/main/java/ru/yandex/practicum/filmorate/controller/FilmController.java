package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok(filmStorage.add(film));
    }

    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok(filmStorage.update(film));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        return ResponseEntity.ok(filmStorage.getById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAllFilms() {
        return ResponseEntity.ok(filmStorage.getAll());
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<String> likeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.likeFilm(id, userId);
        return ResponseEntity.ok("Лайк поставлен");
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<String> unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.unlikeFilm(id, userId);
        return ResponseEntity.ok("Лайк убран");
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return ResponseEntity.ok(filmService.getPopularFilms(count));
    }
}