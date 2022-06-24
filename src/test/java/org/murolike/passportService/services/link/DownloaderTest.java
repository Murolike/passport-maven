package org.murolike.passportService.services.link;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DownloaderTest {
    @Test
    void download() throws IOException {
        String resourceName = getClass().getClassLoader().getResource("list_of_expired_passports.csv.bz2").toString();
        Downloader downloader = new Downloader(resourceName, System.getProperty("java.io.tmpdir") + "/");
        File archive = downloader.download();

        assertTrue(archive.exists());
        assertTrue(archive.length() > 0);
        archive.delete();
    }
}