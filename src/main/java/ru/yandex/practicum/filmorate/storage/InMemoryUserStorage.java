package ru.yandex.practicum.filmorate.storage;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
@NoArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int generatedId = 1;

    @Override
    public User add(User user) {
        createUserNameFromLoginIfNotExists(user);
        user.setId(generatedId);
        users.put(generatedId++, user);
        log.info("Добавлен новый пользователь: " + user);
        return user;
    }

    @Override
    public void remove(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            log.info("пользователь удален: " + user);
        } else {
            throw new IdNotFoundException("пользователь с данным id не найден");
        }
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            createUserNameFromLoginIfNotExists(user);
            users.put(user.getId(), user);
            log.info("пользователь обновлен: " + user);
            return user;
        } else {
            throw new IdNotFoundException("пользователь с данным id не найден");
        }
    }

    @Override
    public User getById(int id) {
        if (users.containsKey(id)) {
            log.info("Получен пользователь: " + users.get(id));
            return users.get(id);
        } else {
            throw new IdNotFoundException("пользователь с данным id не найден");
        }
    }

    @Override
    public Collection<User> getAll() {
        log.info("Был выполнен запрос на получение всех пользователей");
        return users.values();
    }

    @Override
    public HashMap<Integer, User> getAllWithIds() {
        return new HashMap<>(users);
    }

    private void createUserNameFromLoginIfNotExists(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }
}