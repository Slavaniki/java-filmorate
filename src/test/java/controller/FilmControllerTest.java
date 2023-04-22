package controller;

import org.slava.filmorate.controller.FilmController;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slava.filmorate.service.FilmService;
import org.slava.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private FilmController filmController;
    private Film film;
    private Film film2;
    private Film film3;
    private FilmService filmService;
    private InMemoryFilmStorage filmStorage;

    @BeforeEach
    void setUp() {
        filmStorage = new InMemoryFilmStorage();
        filmService = new FilmService(filmStorage);
        filmController = new FilmController(filmService);
        film = Film.builder()
                .name("jj")
                .description("варпиашгимщргрии")
                .releaseDate(LocalDate.of(1895,12,29))
                .duration(1)
                .build();
        film2 = Film.builder()
                .name("Гарри Поттер")
                .description("варпиашмпршариироамигимргаргиагригриурсисаруисраиисрсаиимрмикассиавщирцщаргисщаргиусщг"
                        + "раицщгмаищгсицкщагцимщгркаисщгрцкигвниысщгцирущгсцщугнищсгуниущгрмицкщгрисщнгкищгцркимуг"
                        + "ицщгкрсищцкгцумиущгимщргрии")
                .releaseDate(LocalDate.of(1895,12,27))
                .duration(0)
                .build();
        film3 = Film.builder()
                .name("Звёздные войны")
                .description("Вселенная «Звёздных войн» расширилась с выходом на Disney+ сериала «Книга Бобы Фетта» — "
                        + "спин-оффа суперуспешного «Мандалорца». Но события нового шоу не просто разворачиваются после"
                        + " «Мандалорца», а тож")
                .releaseDate(LocalDate.of(1895,12,28))
                .duration(1)
                .build();
    }

    @Test
    void create() throws ValidationException {
        assertThrows(ValidationException.class,() -> filmController.create(film2));
        assertThrows(NullPointerException.class,() -> filmController.create(null));
        assertEquals(film3,filmController.create(film3));
    }

    @Test
    void update() throws ValidationException {
        Film film2 = filmController.create(film);
        film2.setDuration(0);
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setReleaseDate(LocalDate.of(1895,12,27));
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setDescription("Гарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри ПоттерГарри Поттер");
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setDescription(" ");
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        film2.setName("");
        assertThrows(ValidationException.class,() -> filmController.update(film2));
        assertThrows(NullPointerException.class,() -> filmController.update(null));
        Film film33 = filmController.create(film3);
        film33.setName("hjhj jhjh jj jj");
        assertEquals("hjhj jhjh jj jj",filmController.update(film33).getName());
    }
}