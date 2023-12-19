package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User add(User user) {
        createUserNameFromLoginIfNotExists(user);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        int id = insert.executeAndReturnKey(userToMap(user)).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public void remove(User user) {
        if (jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", user.getId()) == 0)
            throw new EmptyResultDataAccessException(1);
    }

    @Override
    public User update(User user) {
        createUserNameFromLoginIfNotExists(user);
        if (jdbcTemplate.update("UPDATE users SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?", user.getLogin(), user.getName(), user.getEmail(), user.getBirthday(), user.getId()) == 0)
            throw new EmptyResultDataAccessException(1);
        return user;
    }

    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", getUserMapper(), id);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", getUserMapper());
    }

    private static RowMapper<User> getUserMapper() {
        return (rs, rowNum) -> new User(
                rs.getInt("User_id"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate()
        );
    }

    private static Map<String, Object> userToMap(User user) {
        return Map.of(
                "login", user.getLogin(),
                "name", user.getName(),
                "email", user.getEmail(),
                "birthday", user.getBirthday()
        );
    }

    private void createUserNameFromLoginIfNotExists(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }
}