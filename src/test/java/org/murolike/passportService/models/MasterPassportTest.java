package org.murolike.passportService.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasterPassportTest {

    @Test
    void constructorWithArguments() {
        String series = "0011";
        String number = "001122";

        MasterPassport model = new MasterPassport(series, number);
        assertEquals(series, model.getSeries());
        assertEquals(number, model.getNumber());

    }

    @Test
    void getId() {
        MasterPassport model = new MasterPassport();
        assertNull(model.getId());
    }

    @Test
    void getSeries() {
        MasterPassport model = new MasterPassport();
        String series = "0011";
        model.setSeries(series);
        assertEquals(series, model.getSeries());
    }

    @Test
    void setSeries() {
        MasterPassport model = new MasterPassport();
        String series = "0011";
        model.setSeries(series);
        assertEquals(series, model.getSeries());
    }

    @Test
    void getNumber() {
        MasterPassport model = new MasterPassport();
        String number = "001122";
        model.setNumber(number);
        assertEquals(number, model.getNumber());
    }

    @Test
    void setNumber() {
        MasterPassport model = new MasterPassport();
        String number = "001122";
        model.setNumber(number);
        assertEquals(number, model.getNumber());
    }

    @Test
    void getCreatedAt() {
        MasterPassport model = new MasterPassport();
        assertNull(model.getCreatedAt());
    }
}