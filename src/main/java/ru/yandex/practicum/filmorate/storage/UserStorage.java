package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage extends Storage<User> {
    User add(User user);

    void remove(User user);

    User update(User user);

    User getById(int id);

    Collection<User> getAll();
}