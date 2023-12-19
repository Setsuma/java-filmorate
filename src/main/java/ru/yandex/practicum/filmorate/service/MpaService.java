package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDaoImpl;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaService {
    private final MpaDaoImpl mpaDaoImpl;

    public Collection<Mpa> getAllMpa() {
        log.info("получен запрос на получение всех Mpa");
        return mpaDaoImpl.getAllMpa();
    }

    public Mpa getMpaById(int id) {
        log.info("получен запрос на получение Mpa по id");
        return mpaDaoImpl.getMpaById(id);
    }
}