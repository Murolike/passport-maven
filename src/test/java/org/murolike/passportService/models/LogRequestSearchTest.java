package org.murolike.passportService.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogRequestSearchTest {

    String series = "0011";
    String number = "112233";
    String requestIp = "127.0.0.1";
    Integer isSuccessful = 1;
    String tableName = "slave";

    @Test
    void getId() {
        LogRequestSearch model = new LogRequestSearch();
        assertNull(model.getId());
    }

    @Test
    void getSeries() {
        LogRequestSearch model = new LogRequestSearch(series, number, requestIp);
        assertEquals(series, model.getSeries());
    }

    @Test
    void getNumber() {
        LogRequestSearch model = new LogRequestSearch(series, number, requestIp);
        assertEquals(number, model.getNumber());
    }

    @Test
    void getRequestIp() {
        LogRequestSearch model = new LogRequestSearch(series, number, requestIp);
        assertEquals(requestIp, model.getRequestIp());
    }

    @Test
    void getCreatedAt() {
        LogRequestSearch model = new LogRequestSearch();
        assertNull(model.getCreatedAt());
    }

    @Test
    void getSearchResult() {
        LogRequestSearch model = new LogRequestSearch();
        assertNull(model.getSearchResult());
    }

    @Test
    void getIsSuccessful() {
        LogRequestSearch model = new LogRequestSearch();
        assertNull(model.getIsSuccessful());
    }

    @Test
    void setSearchResult() {
        String searchResult = "{}";
        LogRequestSearch model = new LogRequestSearch();
        model.setSearchResult(searchResult);
        assertEquals(searchResult, model.getSearchResult());
    }

    @Test
    void setIsSuccessful() {
        LogRequestSearch model = new LogRequestSearch();
        model.setIsSuccessful(isSuccessful);
        assertEquals(isSuccessful, model.getIsSuccessful());
    }

    @Test
    void setSeries() {
        LogRequestSearch model = new LogRequestSearch();
        model.setSeries(series);
        assertEquals(series, model.getSeries());
    }

    @Test
    void setNumber() {
        LogRequestSearch model = new LogRequestSearch();
        model.setNumber(number);
        assertEquals(number, model.getNumber());
    }

    @Test
    void setRequestIp() {
        LogRequestSearch model = new LogRequestSearch();
        model.setRequestIp(requestIp);
        assertEquals(requestIp, model.getRequestIp());
    }

    @Test
    void setSearchInTable() {
        LogRequestSearch model = new LogRequestSearch();
        model.setSearchInTable(tableName);
        assertEquals(tableName, model.getSearchInTable());
    }
}