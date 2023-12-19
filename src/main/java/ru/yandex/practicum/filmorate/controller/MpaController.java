package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<Collection<Mpa>> getAllMpa() {
        return ResponseEntity.ok(filmService.getAllMpa());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpa> getMpaById(@PathVariable int id) {
        return ResponseEntity.ok(filmService.getMpaById(id));
    }
}