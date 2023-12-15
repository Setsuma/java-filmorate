package ru.yandex.practicum.filmorate.controller.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
@Slf4j
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getFieldError().getDefaultMessage();
        log.warn("Ошибка валидации: " + defaultMessage);
        return new ErrorResponse("Validation error", defaultMessage);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ErrorResponse handleIdNotFoundException(IdNotFoundException e) {
        log.warn("Ошибка id: " + e.getMessage());
        return new ErrorResponse("IdNotFound error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException e){
        log.warn("Ошибка id: " + e.getMessage());
        return new ErrorResponse("IdNotFound error", e.getMessage());
    }
}
