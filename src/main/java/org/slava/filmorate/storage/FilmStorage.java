package org.slava.filmorate.storage;

import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    public Film create(Film film) throws ValidationException;

    public Film update(Film film) throws ValidationException;

    public Collection<Film> findAll();

    public Film findById(Integer id);
}
