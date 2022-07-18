package org.murolike.passportService.components.archivers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleBzipTest {

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
    void unzip() throws IOException, InterruptedException {
        File archive = new File(listOfExpiredPassportsPath.getFile());
        ConsoleBzip bzip2 = new ConsoleBzip(archive);
        File listOfExpiredPassports = bzip2.unzip();

        assertTrue(listOfExpiredPassports.exists());
        assertTrue(listOfExpiredPassports.length() > 0);

        if (!listOfExpiredPassports.delete()) {
            listOfExpiredPassports.deleteOnExit();
        }
    }

    @Test
    void unzipFileNotFound() {
        assertThrows(RuntimeException.class, () -> {
            File archive = File.createTempFile("tmp", ".bz2");
            ConsoleBzip bzip2 = new ConsoleBzip(archive);
            bzip2.unzip();
        });
    }
}