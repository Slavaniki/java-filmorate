package controller;

import exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer,Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Ошибка валидации: название пустое");
            throw new ValidationException();
        }
        if (film.getDescription().length() > 200) {
            log.error("Ошибка валидации: описание превысило лимит в 200 символов");
            throw new ValidationException();
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28)) {
            log.error("Ошибка валидации: дата релиза ранее 28.12.1895");
            throw new ValidationException();
        }
        if (film.getDuration() <= 0) {
            log.error("Ошибка валидации: длительность фильма равна нулю или отрицательная");
            throw new ValidationException();
        }
        films.put(film.getId(),film);
        log.info("Фильм успешно добавлен: " + film.toString());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            if (!(film.getName() == null || film.getName().isBlank())) {
                films.get(film.getId()).setName(film.getName());
            }
            if (!(film.getDescription() == null || film.getDescription().isBlank())) {
                if (film.getDescription().length() > 200) {
                    log.error("Ошибка валидации: описание превысило лимит в 200 символов");
                    throw new ValidationException();
                } else {
                    films.get(film.getId()).setDescription(film.getDescription());
                }
            }
            if (!(film.getReleaseDate() == null)) {
                if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
                    log.error("Ошибка валидации: дата релиза ранее 28.12.1895");
                    throw new ValidationException();
                } else {
                    films.get(film.getId()).setReleaseDate(film.getReleaseDate());
                }
            }
            if (film.getDuration() <= 0) {
                log.error("Ошибка валидации: длительность фильма равна нулю или отрицательная");
                throw new ValidationException();
            } else {
                films.get(film.getId()).setDuration(film.getDuration());
            }
        } else {
            films.put(film.getId(), film);
        }
        return film;
    }
}
