package controller;

import org.slava.filmorate.controller.UserController;
import org.slava.filmorate.exceptions.ValidationException;
import org.slava.filmorate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void create() throws ValidationException {
        User user2 = User.builder()
                .email("  ")
                .login("  ")
                .name("")
                .birthday(LocalDate.now().plusMonths(10))
                .build();
        User user3 = User.builder()
                .email("KirillFireBeavers@gmail.com")
                .login("9impulse")
                .name("")
                .birthday(LocalDate.of(1999,10,31))
                .build();
        assertThrows(ValidationException.class,() -> userController.create(user2));
        assertThrows(NullPointerException.class,() -> userController.create(null));
        assertEquals(user3,userController.create(user3));
    }

    @Test
    void update() throws ValidationException {
        User user = User.builder()
                .email("KirillFireBeavers@gmail.com")
                .login("9impulse")
                .name("")
                .birthday(LocalDate.now())
                .build();
        User user2 = userController.create(user);
        user2.setBirthday(LocalDate.now().plusMonths(10));
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setLogin("Kos tia noi");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setLogin("");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setEmail("Skeleton");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        user2.setEmail("  ");
        assertThrows(ValidationException.class,() -> userController.update(user2));
        assertThrows(NullPointerException.class,() -> userController.update(null));
        User user3 = User.builder()
                .email("KirillFireBeavers@gmail.com")
                .login("9impulse")
                .name("")
                .birthday(LocalDate.of(1999,10,31))
                .build();
        User user33 = userController.create(user3);
        assertEquals("9impulse",userController.update(user33).getLogin());
    }
}