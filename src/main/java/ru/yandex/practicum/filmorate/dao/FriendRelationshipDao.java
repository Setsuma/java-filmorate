package ru.yandex.practicum.filmorate.dao;

import java.util.Collection;

public interface FriendRelationshipDao {
    public void addFriend(int id, int friendId);

    public void deleteFriend(int id, int friendId);

    public Collection<Integer> getFriendsIds(int id);

    public Collection<Integer> getCommonFriendsIds(int id, int otherId);
}