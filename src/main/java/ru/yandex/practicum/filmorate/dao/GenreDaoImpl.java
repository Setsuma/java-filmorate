package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * FROM genres", getGenreMapper());
    }

    @Override
    public Genre getGenreById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM genres WHERE genre_id = ?", getGenreMapper(), id);
    }

    @Override
    public Collection<Genre> getFilmGenres(int id) {
        return jdbcTemplate.query("SELECT * FROM film_genre WHERE film_id = ?", (rs, rowNum) -> new Genre(rs.getInt("genre_id"), getGenreById(rs.getInt("genre_id")).getName()), id);
    }

    @Override
    public void saveFilmGenres(int filmId, Collection<Genre> genres) {
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ?", filmId);
        for (Genre genre : genres) {
            jdbcTemplate.update("INSERT INTO film_genre VALUES (?, ?)", filmId, genre.getId());
        }
    }

    private static RowMapper<Genre> getGenreMapper() {
        return (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }
}