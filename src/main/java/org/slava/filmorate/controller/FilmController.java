package org.slava.filmorate.controller;

import org.slava.filmorate.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        checkFilm(film);
        id++;
        film.setId(id);
        films.put(film.getId(),film);
        log.info("Фильм успешно добавлен: " + film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        checkFilm(film);
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

    private void checkFilm(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Ошибка валидации: название пустое");
            throw new ValidationException();
        }
        if (film.getDescription() == null || film.getDescription().isBlank() || film.getDescription().length() > 200) {
            log.error("Ошибка валидации: описание превысило лимит в 200 символов или пустое");
            throw new ValidationException();
        }
        if (film.getReleaseDate() == null
                || film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Ошибка валидации: дата релиза ранее 28.12.1895 или пустая");
            throw new ValidationException();
        }
        if (film.getDuration() <= 0) {
            log.error("Ошибка валидации: длительность фильма равна нулю или отрицательная");
            throw new ValidationException();
        }
    }
}
