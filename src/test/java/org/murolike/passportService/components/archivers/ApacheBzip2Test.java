package org.murolike.passportService.components.archivers;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.models.MasterPassport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ApacheBzip2Test {

    private final static String archiveFileName = "list_of_expired_passports.csv.bz2";
    private URL listOfExpiredPassportsPath;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        listOfExpiredPassportsPath = this.getClass().getClassLoader().getResource(archiveFileName);
        if (null == listOfExpiredPassportsPath) {
            throw new FileNotFoundException("Не удалось найти файл в папке с ресурсами для тестов: " + archiveFileName);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void unzip() throws IOException {
        File archive = new File(listOfExpiredPassportsPath.getFile());
        ApacheBzip2 bzip2 = new ApacheBzip2(archive);
        File listOfExpiredPassports = bzip2.unzip();

        assertTrue(listOfExpiredPassports.exists());
        assertTrue(listOfExpiredPassports.length() > 0);

        if (!listOfExpiredPassports.delete()) {
            listOfExpiredPassports.deleteOnExit();
        }
    }
}