package ru.yandex.practicum.filmorate.controller.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
@Slf4j
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleValidationException(ValidationException e) {
        log.warn("Ошибка валидации: " + e.getMessage());
        return new ErrorResponse("Validation error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ErrorResponse handleIdNotFoundException(IdNotFoundException e) {
        log.warn("Ошибка id: " + e.getMessage());
        return new ErrorResponse("IdNotFound error", e.getMessage());
    }
}