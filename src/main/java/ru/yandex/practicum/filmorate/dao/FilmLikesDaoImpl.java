package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;

import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FilmLikesDaoImpl implements FilmLikesDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int filmId, int userId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film_likes");
        insert.execute(Map.of("film_id", filmId, "user_id", userId));
    }

    @Override
    public void removeLike(int filmId, int userId) {
        if(jdbcTemplate.update("DELETE FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId) == 0) throw new IdNotFoundException("ошибка введенных id");
    }

    @Override
    public Collection<Integer> getPopularFilmsIds(int count) {
        return jdbcTemplate.query("SELECT film_id FROM film_likes GROUP BY film_id ORDER BY COUNT(*) LIMIT ?", (rs, rowNum) -> rs.getInt("film_id"), count);
    }
}