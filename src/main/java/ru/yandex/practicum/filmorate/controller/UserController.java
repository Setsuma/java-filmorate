package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.exceptions.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        createUserNameFromLoginIfNotExists(user);
        user.setId(id);
        users.put(id++, user);
        log.info("Добавлен новый пользователь: " + user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            createUserNameFromLoginIfNotExists(user);
            users.put(user.getId(), user);
            log.info("Пользователь обновлен: " + user);
            return ResponseEntity.ok(user);
        } else {
            throw new IdNotFoundException("Пользователь с данным id не найден");
        }
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers() {
        log.info("Был выполнен запрос на получение всех пользователей");
        return ResponseEntity.ok(users.values());
    }

    private void createUserNameFromLoginIfNotExists(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }
}