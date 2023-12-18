package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

public interface MPADao {
    public Collection<MPA> getAllMPA();

    public MPA getMPAById(int id);
}