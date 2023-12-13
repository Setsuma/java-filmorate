package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;

    public void addFriend(int id, int friendId) {
        checkUserId(id);
        checkUserId(friendId);
        userStorage.getById(id).getFriendsId().add(friendId);
        userStorage.getById(friendId).getFriendsId().add(id);
        log.info("Пользователь с id: " + id + "добавил в друзья пользователя с id: " + friendId);
    }

    public void deleteFriend(int id, int friendId) {
        checkUserId(id);
        checkUserId(friendId);
        userStorage.getById(id).getFriendsId().remove(friendId);
        userStorage.getById(friendId).getFriendsId().remove(id);
        log.info("Пользователь с id: " + id + "удалил из друзей пользователя с id: " + friendId);
    }

    public Collection<User> getFriends(int id) {
        checkUserId(id);
        log.info("Получен запрос на получение списка друзей");

        List<Integer> friendIds = new ArrayList<>(userStorage.getById(id).getFriendsId());
        if (friendIds.size() == 0) return Collections.emptyList();

        List<User> friends = new ArrayList<>();
        for (int i = 0; i < friendIds.size(); i++) friends.add(userStorage.getById(friendIds.get(i)));

        return friends;
    }

    public Collection<User> getCommonFriends(int id, int otherId) {
        checkUserId(id);
        checkUserId(otherId);
        log.info("Получен запрос на получение списка общих друзей");

        Set<Integer> commonFriendIds = new TreeSet<>(userStorage.getById(id).getFriendsId());
        commonFriendIds.retainAll(userStorage.getById(otherId).getFriendsId());
        if (commonFriendIds.size() == 0) return Collections.emptyList();

        List<User> commonFriends = new ArrayList<>();
        for (int i : commonFriendIds) {
            commonFriends.add(userStorage.getById(i));
        }

        return commonFriends;
    }

    private void checkUserId(int userId) {
        if (!userStorage.getAllWithIds().containsKey(userId))
            throw new IdNotFoundException("Пользователь с данным id не найден");
    }
}