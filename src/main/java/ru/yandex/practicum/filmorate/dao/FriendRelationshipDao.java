package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendRelationshipDao {
    public void addFriend(int id, int friendId);

    public void deleteFriend(int id, int friendId);

    public Collection<User> getFriends(int id);

    public Collection<User> getCommonFriends(int id, int otherId);
}