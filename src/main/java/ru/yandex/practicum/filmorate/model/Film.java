package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.validator.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotEmpty(message = "имя не может быть пустым")
    private String name;
    @Size(max = 200, message = "размер описания не может превышать 200 символов")
    private String description;
    @MinimumDate(message = "дата релиза фильма не может быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "длительность фильма должна быть положительной")
    private int duration;
    private MPA mpa;
    private Set<Genre> genres = new HashSet<>();
}