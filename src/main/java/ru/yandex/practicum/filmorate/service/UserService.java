package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendRelationshipDaoImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDbStorage userStorage;
    private final FriendRelationshipDaoImpl friendRelationshipDaoImpl;

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
        friendRelationshipDaoImpl.addFriend(id, friendId);
        log.info("Пользователь с id: " + id + "добавил в друзья пользователя с id: " + friendId);
    }

    public void deleteFriend(int id, int friendId) {
        friendRelationshipDaoImpl.deleteFriend(id, friendId);
        log.info("Пользователь с id: " + id + "удалил из друзей пользователя с id: " + friendId);
    }

    public Collection<User> getFriends(int id) {
        log.info("Получен запрос на получение списка друзей");
        return friendRelationshipDaoImpl.getFriendsIds(id).stream().map(userStorage::getById).collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(int id, int otherId) {
        log.info("Получен запрос на получение списка общих друзей");
        return friendRelationshipDaoImpl.getCommonFriendsIds(id, otherId).stream().map(userStorage::getById).collect(Collectors.toList());
    }
}