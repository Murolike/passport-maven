package org.murolike.passportService.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlavePassportTest {
    @Test
    void constructorWithArguments() {
        String series = "0011";
        String number = "001122";

        SlavePassport model = new SlavePassport(series, number);
        assertEquals(series, model.getSeries());
        assertEquals(number, model.getNumber());

    }

    @Test
    void getId() {
        SlavePassport model = new SlavePassport();
        assertNull(model.getId());
    }

    @Test
    void getSeries() {
        SlavePassport model = new SlavePassport();
        String series = "0011";
        model.setSeries(series);
        assertEquals(series, model.getSeries());
    }

    @Test
    void setSeries() {
        SlavePassport model = new SlavePassport();
        String series = "0011";
        model.setSeries(series);
        assertEquals(series, model.getSeries());
    }

    @Test
    void getNumber() {
        SlavePassport model = new SlavePassport();
        String number = "001122";
        model.setNumber(number);
        assertEquals(number, model.getNumber());
    }

    @Test
    void setNumber() {
        SlavePassport model = new SlavePassport();
        String number = "001122";
        model.setNumber(number);
        assertEquals(number, model.getNumber());
    }

    @Test
    void getCreatedAt() {
        SlavePassport model = new SlavePassport();
        assertNull(model.getCreatedAt());
    }
}