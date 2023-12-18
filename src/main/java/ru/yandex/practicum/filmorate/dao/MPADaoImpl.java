package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class MPADaoImpl implements MPADao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<MPA> getAllMPA() {
        return jdbcTemplate.query("SELECT * FROM mpa", getMPAMapper());
    }

    @Override
    public MPA getMPAById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE mpa_id = ?", getMPAMapper(), id);
    }

    private static RowMapper<MPA> getMPAMapper() {
        return (rs, rowNum) -> new MPA(rs.getInt("mpa_id"), rs.getString("rating"));
    }
}
