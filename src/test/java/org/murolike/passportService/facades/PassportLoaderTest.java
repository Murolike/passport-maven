package org.murolike.passportService.facades;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class PassportLoaderTest {

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
    void load() throws IOException, InterruptedException {
        PassportLoader passportLoader = new PassportLoader(listOfExpiredPassportsPath.toString(), System.getProperty("java.io.tmpdir") + "/");
        File file = passportLoader.load();
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}