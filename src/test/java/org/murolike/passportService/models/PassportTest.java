package org.murolike.passportService.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassportTest {

    @Test
    void getId() {
        Passport model = new Passport();
        assertNull(model.getId());
    }

    @Test
    void getSeries() {
        String series = "0011";
        String number = "001122";
        Passport model = new Passport(series, number);
        assertEquals(series, model.getSeries());
    }

    @Test
    void getNumber() {
        String series = "0011";
        String number = "001122";
        Passport model = new Passport(series, number);
        assertEquals(number, model.getNumber());
    }
}