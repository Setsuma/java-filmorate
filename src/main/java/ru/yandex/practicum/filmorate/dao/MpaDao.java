package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaDao {
    public Collection<Mpa> getAllMpa();

    public Mpa getMpaById(int id);
}