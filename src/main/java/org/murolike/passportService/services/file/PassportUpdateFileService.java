package org.murolike.passportService.services.file;

import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

abstract public class PassportUpdateFileService {
    @Transactional
    public void run(File passportUpdateFile) throws IOException {
        deleteAll();
        Stream<String> stream = Files.lines(passportUpdateFile.toPath());
        stream.skip(1).forEach(line -> {
            String[] data = line.split(",");
            save(data[0], data[1]);
        });
    }

    abstract protected void deleteAll();

    abstract protected void save(String serial, String number);
}
