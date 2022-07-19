package org.murolike.passportService.components.downloaders;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.components.downloaders.ChannelDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ChannelDownloaderTest {

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
    void download() throws IOException {
        ChannelDownloader downloader = new ChannelDownloader(listOfExpiredPassportsPath.toString(), System.getProperty("java.io.tmpdir") + "/");
        File archive = downloader.download();

        assertTrue(archive.exists());
        assertTrue(archive.length() > 0);

        if (!archive.delete()) {
            archive.deleteOnExit();
        }
    }
}