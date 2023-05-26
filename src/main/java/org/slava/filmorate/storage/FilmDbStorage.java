package org.slava.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.slava.filmorate.exceptions.ResourceNotFoundException;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.Film;
import org.slava.filmorate.model.Genres;
import org.slava.filmorate.model.MPA;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@Primary
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) throws ValidationException {
        String sqlQuery = "insert into film(NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) " +
                "values (?, ?, ?, ?, ?)";
        int rez = jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());

        String sqlFilm = "select * from film where NAME='" + film.getName() + "'";
        int id = jdbcTemplate.queryForObject(sqlFilm, (rs, rowNum) -> rs.getInt("FILM_ID"));
        film.setId(id);

        if (film.getGenres() != null) {
            film.getGenres()
                    .stream().distinct()
                    .forEach(genre -> {
                        String sqlQueryGenre = "insert into FILM_GENRE(FILM_ID, GENRE_ID) " +
                                "values (?, ?)";
                        jdbcTemplate.update(sqlQueryGenre,
                                film.getId(),
                                genre.getId()
                        );
                    });
        }

        if (film.getLikes() != null) {
            film.getLikes().stream().forEach(like -> {
                String sqlQueryGenre = "insert into LIKES(FILM_ID, USER_ID) " +
                        "values (?, ?)";
                jdbcTemplate.update(sqlQueryGenre,
                        film.getId(),
                        like
                );
            });
        }
        return findById(id);
    }

    @Override
    public Film update(Film film) throws ValidationException {
        String sql = "select * from film where FILM_ID=" + film.getId();
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        if (films.size() == 0) {
            throw new ResourceNotFoundException("Фильма с таким id не существует");
        }
        String sqlQuery = "update film SET NAME='" + film.getName() + "', DESCRIPTION='" + film.getDescription()
                + "', RELEASE_DATE='" + film.getReleaseDate() + "', DURATION=" + film.getDuration() + ", RATING_ID="
                + film.getMpa().getId() + " where FILM_ID=" + film.getId();
        jdbcTemplate.update(sqlQuery);

        String sqlDel = "delete from FILM_GENRE where FILM_ID=" + film.getId();
        jdbcTemplate.execute(sqlDel);

        if (film.getGenres() != null) {
            film.getGenres()
                    .stream().distinct()
                    .forEach(genre -> {
                        String sqlQueryGenre = "insert into FILM_GENRE(FILM_ID, GENRE_ID) " +
                                "values (?, ?)";
                        jdbcTemplate.update(sqlQueryGenre,
                                film.getId(),
                                genre.getId()
                        );
                    });
        }

        String sqlDel2 = "delete from LIKES where FILM_ID=" + film.getId();
        jdbcTemplate.execute(sqlDel2);

        if (film.getLikes() != null) {
            film.getLikes().stream().forEach(like -> {
                String sqlQueryGenre = "insert into LIKES(FILM_ID, USER_ID) " +
                        "values (?, ?)";
                jdbcTemplate.update(sqlQueryGenre,
                        film.getId(),
                        like
                );
            });
        }
        return findById(film.getId());
    }

    @Override
    public Collection<Film> findAll() {
        String sql = "select * from film";
        Collection<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        return films;
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("FILM_ID");
        String name = rs.getString("NAME");
        String desc =  rs.getString("DESCRIPTION");;
        LocalDate rd = rs.getDate("RELEASE_DATE").toLocalDate();
        int dur = rs.getInt("DURATION");
        MPA mpa = makeMPA(rs.getInt("RATING_ID"));

        String sqlLikes = "select * from likes where FILM_ID=" + id;
        Collection<Integer> likess = jdbcTemplate.query(sqlLikes, (rs2, rowNum) -> makeLikes(rs2));
        Set<Integer> likes = likess.stream().collect(Collectors.toSet());

        String sqlGenres = "select * from FILM_GENRE where FILM_ID=" + id;
        List<Genres> genres = jdbcTemplate.query(sqlGenres, (rs3, rowNum) -> makeGenres(rs3));

        Film film = Film.builder()
                .id(id).name(name).description(desc).duration(dur).releaseDate(rd)
                .likes(likes).genres(genres).mpa(mpa)
                .build();

        return film;
    }

    private MPA makeMPA(int ratingId) throws SQLException {
        String sqlRating = "select * from RATING where RATING_ID=" + ratingId;
        String name = jdbcTemplate.queryForObject(sqlRating, (rs, rowNum) -> rs.getString("NAME"));
        return MPA.builder().id(ratingId).name(name).build();
    }

    private Genres makeGenres(ResultSet rs) throws SQLException {
        int id = rs.getInt("GENRE_ID");
        String sqlGenre = "select * from GENRE where GENRE_ID=" + id;
        String name = jdbcTemplate.queryForObject(sqlGenre, (rs2, rowNum) -> rs2.getString("NAME"));
        return Genres.builder().id(id).name(name).build();
    }

    private Integer makeLikes(ResultSet rs) throws SQLException {
        int id = rs.getInt("FILM_ID");
        return id;
    }

    @Override
    public Film findById(Integer id) {
        String sqlFilm = "select * from film where FILM_ID=" + id;
        List<Film> films = jdbcTemplate.query(sqlFilm, (rs, rowNum) -> makeFilm(rs));
        if (films.size() > 0) {
            return films.get(0);
        } else {
            throw new ResourceNotFoundException("Фильма с таким id не существует");
        }
    }

    public boolean checkFilmExist(Integer id) {
        String sql = "select * from film where FILM_ID=" + id;
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        if (films.isEmpty()) {
            return false;
        }
        return true;
    }
}
