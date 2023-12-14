package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public User getUserById(int id) {
        return userStorage.getById(id);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

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

        return friendIds.stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(int id, int otherId) {
        checkUserId(id);
        checkUserId(otherId);
        log.info("Получен запрос на получение списка общих друзей");

        Set<Integer> commonFriendIds = new TreeSet<>(userStorage.getById(id).getFriendsId());
        commonFriendIds.retainAll(userStorage.getById(otherId).getFriendsId());

        return commonFriendIds.stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    private void checkUserId(int userId) throws IdNotFoundException {
        userStorage.getById(userId);
    }
}