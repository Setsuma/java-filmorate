package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.management.ConstructorParameters;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;
    @NotEmpty(message = "логин не может быть пустым")
    @Pattern(regexp = "^\\S*$", message = "в логине не может быть пробелов")
    private String login;
    @Nullable
    private String name;
    @NotEmpty(message = "электронная почта не может быть пустой")
    @Email(message = "адрес электронный почты не соответствует формату Email")
    private String email;
    @PastOrPresent(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;
    private final Set<Integer> friendsId = new HashSet<>();

    public User(int id, String login, String name, String email, Date birthday) {
this.id = id;
this.login= login;
this.name = name;
this.email = email;
this.birthday = birthday.toLocalDate();
    }
}
