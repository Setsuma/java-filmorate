package ru.yandex.practicum.filmorate.dao;

import java.util.Collection;

public interface FilmLikesDao {

    public void addLike(int filmId, int userId);

    public void removeLike(int filmId, int userId);

    public Collection<Integer> getPopularFilmsIds(int count);
}