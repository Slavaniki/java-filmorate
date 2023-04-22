package org.slava.filmorate.service;

import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.model.User;
import org.slava.filmorate.storage.FilmStorage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(User user, Film film) throws ValidationException {
        film.setLike(user.getId());
        filmStorage.update(film);
    }

    public void deleteLike(User user, Film film) throws ValidationException {
        film.deleteLike(user.getId());
        filmStorage.update(film);
    }

    public List<Film> getMostLikedFilms() {
        return filmStorage.findAll()
                .stream()
                .sorted(this::compare)
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Film> findAll() {
        return new ArrayList<>(filmStorage.findAll());
    }

    public Film create(Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException {
        return filmStorage.update(film);
    }

    private int compare(Film film1, Film film2) {
        return Integer.compare(film2.getLikes().size(), film1.getLikes().size());
    }
}
