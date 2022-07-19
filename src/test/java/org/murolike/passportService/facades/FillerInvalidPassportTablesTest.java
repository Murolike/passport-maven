package org.murolike.passportService.facades;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FillerInvalidPassportTablesTest {

    @Autowired
    public FillerInvalidPassportTables fillerInvalidPassportTables;

    @Test
    void updateMasterPassports() {
        assertDoesNotThrow(() -> fillerInvalidPassportTables.updateMasterPassports());
    }

    @Test
    void vacuumMasterTable() {
        assertDoesNotThrow(() -> fillerInvalidPassportTables.vacuumMasterTable());
    }

    @Test
    void updateSlavePassports() {
        assertDoesNotThrow(() -> fillerInvalidPassportTables.updateSlavePassports());
    }

    @Test
    void vacuumSlaveTable() {
        assertDoesNotThrow(() -> fillerInvalidPassportTables.vacuumSlaveTable());
    }
}