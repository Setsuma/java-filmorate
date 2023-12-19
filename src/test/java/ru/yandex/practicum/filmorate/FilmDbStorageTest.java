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
import ru.yandex.practicum.filmorate.dao.GenreDaoImpl;
import ru.yandex.practicum.filmorate.dao.MpaDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private FilmDbStorage filmStorage;
    private MpaDaoImpl mpaDao;
    private GenreDaoImpl genreDao;

    @BeforeEach
    public void setUP() {
        mpaDao = new MpaDaoImpl(jdbcTemplate);
        genreDao = new GenreDaoImpl(jdbcTemplate);
        filmStorage = new FilmDbStorage(mpaDao, genreDao, jdbcTemplate);
    }

    @Test
    public void testAddFilm() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);

        Film savedFilm = filmStorage.getById(1);

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testRemoveFilm() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);
        filmStorage.remove(newFilm);

        Collection<Film> savedUsers = filmStorage.getAll();

        assertThat(savedUsers)
                .asList().isEmpty();
    }

    @Test
    public void testRemoveFilmWithInvalidId() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);

        newFilm.setId(100);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            filmStorage.remove(newFilm);
        });
    }

    @Test
    public void testUpdateFilm() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);

        newFilm.setName("vanyaSuper");
        filmStorage.update(newFilm);
        Film savedFilm = filmStorage.getById(1);

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testUpdateFilmWithInvalidId() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);

        newFilm.setId(100);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            filmStorage.update(newFilm);
        });
    }

    @Test
    public void testFindFilmById() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);

        Film savedFilm = filmStorage.getById(1);

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testFindFilmByIdWithInvalidId() {
        Film newFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(newFilm);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            Film savedFilm = filmStorage.getById(100);
        });
    }

    @Test
    public void testGetAllFilms() {
        Film firstFilm = new Film(1, "Interstellar", "A fantastic film", LocalDate.of(1895, 12, 29), 120, new Mpa(1, "G"), new HashSet<>());
        Film secondFilm = new Film(2, "DMB", "Film about army", LocalDate.of(2000, 1, 19), 120, new Mpa(1, "G"), new HashSet<>());
        filmStorage.add(firstFilm);
        filmStorage.add(secondFilm);

        Collection<Film> savedFilms = filmStorage.getAll();
        Collection<Film> newFilms = List.of(firstFilm, secondFilm);

        assertThat(savedFilms)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilms);
    }

    @Test
    public void testGetAllFilmsWithEmptyStorage() {
        Collection<Film> savedFilms = filmStorage.getAll();

        assertThat(savedFilms)
                .asList().isEmpty();
    }
}