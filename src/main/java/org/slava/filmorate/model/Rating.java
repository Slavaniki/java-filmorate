package org.slava.filmorate.model;

public enum Rating {
    G(1),
    PG(2),
    R(3),
    PG_13(4),
    NC_17(5);
    private Integer id;

    Rating(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
