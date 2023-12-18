package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MPAController {
    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<Collection<MPA>> getAllMPA() {
        return ResponseEntity.ok(filmService.getAllMPA());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MPA> getMPAById(@PathVariable int id) {
        return ResponseEntity.ok(filmService.getMPAById(id));
    }
}