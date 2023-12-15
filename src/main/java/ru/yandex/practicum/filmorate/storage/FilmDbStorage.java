package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        int id = insert.executeAndReturnKey(filmToMap(film)).intValue();
        film.setId(id);
        return film;
    }

    @Override
    public void remove(Film film) {
        jdbcTemplate.update("DELETE FROM films WHERE id = ?", film.getId());
    }

    @Override
    public Film update(Film film) {
        if (jdbcTemplate.update("UPDATE films SET name = ?, description = ?, release_date = ?, duration = ? WHERE film_id = ?",
               film.getName(), film.getDescription(), film.getReleaseDate(), film.getDescription()) == 0)
            throw new EmptyResultDataAccessException(1);
        return film;
    }

    @Override
    public Film getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM films WHERE film_id = ?", getFilmMapper(), id);
    }

    @Override
    public Collection<Film> getAll() {
        return jdbcTemplate.query("SELECT * FROM films", getFilmMapper());
    }

    private static RowMapper<Film> getFilmMapper() {
        return (rs, rowNum) -> new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date"),
                rs.getInt("duration")
        );
    }

    private static Map<String, Object> filmToMap(Film film) {
        return Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration()
        );
    }
}