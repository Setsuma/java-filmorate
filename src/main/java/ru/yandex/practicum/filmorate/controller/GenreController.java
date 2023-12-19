package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<Collection<Genre>> getAllGenres() {
        return ResponseEntity.ok(filmService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        return ResponseEntity.ok(filmService.getGenreById(id));
    }
}