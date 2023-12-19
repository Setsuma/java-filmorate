package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Mpa> getAllMpa() {
        return jdbcTemplate.query("SELECT * FROM mpa", getMpaMapper());
    }

    @Override
    public Mpa getMpaById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE mpa_id = ?", getMpaMapper(), id);
    }

    private static RowMapper<Mpa> getMpaMapper() {
        return (rs, rowNum) -> new Mpa(rs.getInt("mpa_id"), rs.getString("rating"));
    }
}
