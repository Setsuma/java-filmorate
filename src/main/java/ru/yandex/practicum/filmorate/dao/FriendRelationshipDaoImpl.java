package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FriendRelationshipDaoImpl implements FriendRelationshipDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(int id, int friendId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("friend_relationship");
        insert.execute(Map.of("user_id", id, "friend_id", friendId));
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        jdbcTemplate.update("DELETE FROM friend_relationship WHERE user_id = ? AND friend_id = ?", id, friendId);
    }

    @Override
    public Collection<User> getFriends(int id) {
        return jdbcTemplate.query("SELECT * FROM users, friend_relationship WHERE users.user_id = friend_relationship.friend_id AND friend_relationship.user_id = ?", UserDbStorage.getUserMapper(), id);
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        return jdbcTemplate.query("SELECT * FROM users u, friend_relationship f, friend_relationship o WHERE u.user_id = f.friend_id AND u.user_id = o.friend_id AND f.user_id = ? AND o.user_id = ?", UserDbStorage.getUserMapper(), id, otherId);
    }
}