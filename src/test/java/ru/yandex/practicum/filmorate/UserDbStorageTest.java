package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private UserDbStorage userStorage;

    @BeforeEach
    public void setUP() {
        userStorage = new UserDbStorage(jdbcTemplate);
    }

    @Test
    public void testAddUser() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        User savedUser = userStorage.getById(1);

        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @Test
    public void testAddUserWithoutName() {
        User newUser = new User(1, "vanya123", "", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        User savedUser = userStorage.getById(1);
        newUser.setName(newUser.getLogin());

        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @Test
    public void testRemoveUser() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);
        userStorage.remove(newUser);

        Collection<User> savedUsers = userStorage.getAll();

        assertThat(savedUsers)
                .asList().isEmpty();
    }

    @Test
    public void testRemoveUserWithInvalidId() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        newUser.setId(100);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            userStorage.remove(newUser);
        });
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        newUser.setName("vanyaSuper");
        userStorage.update(newUser);
        User savedUser = userStorage.getById(1);

        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @Test
    public void testUpdateUserWithoutName() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        newUser.setName("");
        userStorage.update(newUser);
        newUser.setName(newUser.getLogin());
        User savedUser = userStorage.getById(1);

        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @Test
    public void testUpdateUserWithInvalidId() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        newUser.setId(100);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            userStorage.update(newUser);
        });
    }

    @Test
    public void testFindUserById() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        User savedUser = userStorage.getById(1);

        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @Test
    public void testFindUserByIdWithInvalidId() {
        User newUser = new User(1, "vanya123", "Ivan Petrov", "user@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(newUser);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            User savedUser = userStorage.getById(100);
        });
    }

    @Test
    public void testGetAllUsers() {
        User firstUser = new User(1, "vanya123", "Ivan Petrov", "vanya@email.ru", LocalDate.of(1992, 11, 18));
        User secondUser = new User(2, "slava123", "Slava Rol", "slava@email.ru", LocalDate.of(1990, 1, 1));
        userStorage.add(firstUser);
        userStorage.add(secondUser);

        Collection<User> savedUsers = userStorage.getAll();
        Collection<User> newUsers = List.of(firstUser, secondUser);

        assertThat(savedUsers)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUsers);
    }

    @Test
    public void testGetAllUsersWithEmptyStorage() {
        Collection<User> savedUsers = userStorage.getAll();

        assertThat(savedUsers)
                .asList().isEmpty();
    }
}