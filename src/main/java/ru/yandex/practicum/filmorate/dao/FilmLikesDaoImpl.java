package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FilmLikesDaoImpl implements FilmLikesDao {
    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmStorage;

    @Override
    public void addLike(int filmId, int userId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film_likes");
        insert.execute(Map.of("film_id", filmId, "user_id", userId));
    }

    @Override
    public void removeLike(int filmId, int userId) {
        if (jdbcTemplate.update("DELETE FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId) == 0)
            throw new IdNotFoundException("ошибка введенных id");
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return jdbcTemplate.query("SELECT * FROM films WHERE film_id IN (SELECT film_id FROM film_likes GROUP BY film_id ORDER BY COUNT(*) LIMIT ?)", filmStorage.getFilmMapper(), count);
    }
}
