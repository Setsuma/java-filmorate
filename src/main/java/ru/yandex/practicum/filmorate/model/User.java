package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
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
}