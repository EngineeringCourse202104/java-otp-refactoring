package com.odde.birthday;

import com.odde.securetoken.Birthday;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BirthdayTest {

    @Test
    public void is_birthday() {

        Birthday birthday = new Birthday();

        birthday.today = LocalDate.of(2021, 4, 9);

        assertTrue(birthday.isBirthday());
    }

    @Test
    public void is_not_birthday() {
        Birthday birthday = new Birthday();

        birthday.today = LocalDate.MAX;

        assertFalse(birthday.isBirthday());
    }

}
