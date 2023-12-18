package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
    public Collection<Integer> getFriendsIds(int id) {
        return jdbcTemplate.query("SELECT friend_id FROM friend_relationship WHERE user_id = ?", (rs, rowNum) -> rs.getInt("friend_id"), id);
    }

    @Override
    public Collection<Integer> getCommonFriendsIds(int id, int otherId) {
        return jdbcTemplate.query("SELECT friend_id as my_friends FROM friend_relationship WHERE user_id = ? INTERSECT SELECT friend_id as other_friends FROM friend_relationship WHERE user_id = ?", (rs, rowNum) -> rs.getInt("my_friends"), id, otherId);
    }
}