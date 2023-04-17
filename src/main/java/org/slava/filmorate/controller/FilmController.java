package org.slava.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;
    Validator validator = new Validator();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        validator.checkFilm(film);
        id++;
        film.setId(id);
        films.put(film.getId(),film);
        log.info("Фильм успешно добавлен: " + film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
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
            throw new ValidationException();
        }
        log.info("Фильм успешно обновлён: " + film);
        return film;
    }
}
