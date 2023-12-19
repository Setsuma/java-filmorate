package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {
    T add(T obj);

    void remove(T obj);

    T update(T obj);

    T getById(int id);

    Collection<T> getAll();
}