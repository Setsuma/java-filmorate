package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.validators.MinimumDate;

import javax.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private MPA mpa;
    private final Set<Integer> usersWhoLikedFilmIds = new HashSet<>();

    public Film(String name, String description, Date release_date, int duration, MPA mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = release_date.toLocalDate();
        this.duration = duration;
        this.mpa = mpa;
    }

    public Film(int film_id, String name, String description, Date release_date, int duration) {
        this.id = film_id;
        this.name = name;
        this.description = description;
        this.releaseDate = release_date.toLocalDate();
        this.duration = duration;
    }
}