package org.slava.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.slava.filmorate.exceptions.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes;
    private List<Genre> genres;
    private Rating mpa;

    public void setLike(Integer id) {
        if (likes == null) {
           likes = new HashSet<>();
        }
        likes.add(id);
    }

    public void deleteLike(Integer id) {
        if (likes.contains(id)) {
            likes.remove(id);
        } else {
            throw new ResourceNotFoundException("Пользователя с таким id нет в лайках");
        }
    }
}