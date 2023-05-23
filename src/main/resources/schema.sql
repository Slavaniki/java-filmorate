CREATE TABLE IF NOT EXISTS PUBLIC.FILM (
	FILM_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING NOT NULL,
	DESCRIPTION CHARACTER VARYING(200),
	RELEASE_DATE DATE NOT NULL,
	DURATION INTEGER NOT NULL,
	RATING_ID INTEGER NOT NULL,
	CONSTRAINT FILM_PK PRIMARY KEY (FILM_ID)
	CONSTRAINT FILM_FK FOREIGN KEY (RATING_ID) REFERENCES PUBLIC.RATING(RATING_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC."USER" (
	USER_ID INTEGER NOT NULL AUTO_INCREMENT,
	EMAIL CHARACTER VARYING NOT NULL,
	LOGIN CHARACTER VARYING,
	NAME CHARACTER VARYING NOT NULL,
	BIRTHDAY DATE NOT NULL,
	CONSTRAINT USER_PK PRIMARY KEY (USER_ID)
);


CREATE TABLE IF NOT EXISTS PUBLIC.FRIENDS (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	USER_ID INTEGER NOT NULL,
	FRIEND_ID INTEGER NOT NULL,
	STATUS_ID CHARACTER VARYING NOT NULL,
	CONSTRAINT FRIENDS_PK PRIMARY KEY (ID),
	CONSTRAINT USER_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC."USER"(USER_ID),
	CONSTRAINT FRIEND_FK FOREIGN KEY (FRIEND_ID) REFERENCES PUBLIC."USER"(USER_ID),
	CONSTRAINT STATUS_FK_2 FOREIGN KEY (STATUS_ID) REFERENCES PUBLIC.STATUS(STATUS_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.STATUS (
	STATUS_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING NOT NULL,
	CONSTRAINT STATUS_PK PRIMARY KEY (STATUS_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.RATING (
	RATING_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING NOT NULL,
	CONSTRAINT RATING_PK PRIMARY KEY (RATING_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.FILM_GENRE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	FILM_ID INTEGER NOT NULL,
	GENRE_ID INTEGER NOT NULL,
	CONSTRAINT FILM_GENRE_PK PRIMARY KEY (ID),
	CONSTRAINT FILM_GENRE_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILM(FILM_ID),
	CONSTRAINT FILM_GENRE_FK_1 FOREIGN KEY (GENRE_ID) REFERENCES PUBLIC.GENRE(GENRE_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.LIKES (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	FILM_ID INTEGER NOT NULL,
	USER_ID INTEGER NOT NULL,
	CONSTRAINT LIKES_PK PRIMARY KEY (ID),
	CONSTRAINT LIKES_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILM(FILM_ID),
	CONSTRAINT LIKES_FK_1 FOREIGN KEY (USER_ID) REFERENCES PUBLIC."USER"(USER_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.GENRE (
	GENRE_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING NOT NULL,
	CONSTRAINT GENRE_PK PRIMARY KEY (GENRE_ID)
);
