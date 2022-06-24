package org.murolike.passportService.services.link;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class Bzip2Test {

    @Test
    void unzip() throws IOException {
        URL path = this.getClass().getClassLoader().getResource("list_of_expired_passports.csv.bz2");
        File archive = new File(path.getFile());
        Bzip2 bzip2 = new Bzip2(archive);
        File base = bzip2.unzip();
        assertTrue(base.exists());
        assertTrue(base.length() > 0);
    }
}