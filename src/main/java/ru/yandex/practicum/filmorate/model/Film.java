package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.validators.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
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
}
