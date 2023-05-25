package org.slava.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.service.FilmService;
import org.slava.filmorate.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;
    private Validator validator = new Validator();
    private final Integer defaultCountOfMostLikedFilms = 10;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @RequestMapping("/{id}")
    @GetMapping
    public Film findById(@PathVariable Integer id) {
        return filmService.findById(id);
    }

    @RequestMapping("/popular")
    @GetMapping
    public List<Film> findMostPopularFilms(@RequestParam(required = false) Integer count) {
        if (count == null) {
            return filmService.getMostLikedFilms(defaultCountOfMostLikedFilms);
        } else {
            return filmService.getMostLikedFilms(count);
        }
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        validator.checkFilm(film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        validator.checkFilm(film);
        return filmService.update(film);
    }

    @RequestMapping(value = "/{id}/like/{userId}", method = RequestMethod.PUT)
    public void likeFilm(@PathVariable Integer id, @PathVariable Integer userId) throws ValidationException {
        filmService.addLike(id, userId);
    }

    @RequestMapping(value = "/{id}/like/{userId}", method = RequestMethod.DELETE)
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) throws ValidationException {
        filmService.deleteLike(id, userId);
    }
}