package org.slava.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ResourceNotFoundException;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;
    Validator validator = new Validator();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findById(Integer id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ResourceNotFoundException("Фильма с таким id не существует");
        }
    }

    @Override
    public Film create(Film film) throws ValidationException {
        validator.checkFilm(film);
        id++;
        film.setId(id);
        films.put(film.getId(),film);
        log.info("Фильм успешно добавлен: " + film);
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        validator.checkFilm(film);
        if (films.containsKey(film.getId())) {
            Film tmpFilm = films.get(film.getId());
            tmpFilm.setName(film.getName());
            tmpFilm.setDescription(film.getDescription());
            tmpFilm.setReleaseDate(film.getReleaseDate());
            tmpFilm.setDuration(film.getDuration());
            films.replace(tmpFilm.getId(),tmpFilm);
            film = tmpFilm;
        } else {
            throw new ResourceNotFoundException("Фильма с таким id не существует");
        }
        log.info("Фильм успешно обновлён: " + film);
        return film;
    }
}