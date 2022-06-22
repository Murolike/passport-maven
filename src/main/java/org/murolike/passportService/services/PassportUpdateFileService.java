package org.murolike.passportService.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

abstract public class PassportUpdateFileService {

    @Value("${passport.storage.filePath}")
    protected String resourcePath;

    @Transactional
    public void run() throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource(resourcePath);
        deleteAll();
        try (Stream<String> stream = Files.lines(Paths.get(fileUrl.getFile()))) {
            stream.skip(1).forEach(line -> {
                String[] data = line.split(",");
                save(data[0], data[1]);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract protected void deleteAll();

    abstract protected void save(String serial, String number);
}
