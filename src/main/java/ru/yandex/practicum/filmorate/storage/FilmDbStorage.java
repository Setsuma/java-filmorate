package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDaoImpl;
import ru.yandex.practicum.filmorate.dao.MpaDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final MpaDaoImpl mpaDaoImpl;
    private final GenreDaoImpl genreDaoImpl;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        int id = insert.executeAndReturnKey(filmToMap(film)).intValue();
        film.setId(id);
        film.setMpa(mpaDaoImpl.getMpaById(film.getMpa().getId()));
        genreDaoImpl.saveFilmGenres(film.getId(), film.getGenres());
        return film;
    }

    @Override
    public void remove(Film film) {
        if (jdbcTemplate.update("DELETE FROM films WHERE film_id = ?", film.getId()) == 0)
            throw new EmptyResultDataAccessException(1);
    }

    @Override
    public Film update(Film film) {
        if (jdbcTemplate.update("UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE film_id = ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId()) == 0)
            throw new EmptyResultDataAccessException(1);
        genreDaoImpl.saveFilmGenres(film.getId(), film.getGenres());
        film.setMpa(mpaDaoImpl.getMpaById(film.getMpa().getId()));
        film.setGenres(new HashSet<>(genreDaoImpl.getFilmGenres(film.getId())));
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

    public RowMapper<Film> getFilmMapper() {
        return (rs, rowNum) -> new Film(rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                mpaDaoImpl.getMpaById(rs.getInt("mpa_id")),
                new HashSet<>(genreDaoImpl.getFilmGenres(rs.getInt("film_id"))));
    }

    private static Map<String, Object> filmToMap(Film film) {
        return Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_id", film.getMpa().getId()
        );
    }
}