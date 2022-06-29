package org.murolike.passportService.services.file;

import org.junit.jupiter.api.Test;
import org.murolike.passportService.models.MasterPassport;
import org.murolike.passportService.models.SlavePassport;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SlavePassportUpdateFileServiceTest {

    @Value("${passport.storage.filePath}")
    protected String passportUpdateFilePath;

    @Autowired
    private SlavePassportUpdateFileService fileService;

    @Autowired
    private SlavePassportService passportService;

    @Test
    void run() throws IOException {
        File passportUpdateFile = new File(getClass().getClassLoader().getResource(passportUpdateFilePath).getFile());
        fileService.run(passportUpdateFile);

        Iterable<SlavePassport> iterable = passportService.findAll();

        assertEquals(19, StreamSupport.stream(iterable.spliterator(), false).count());
    }
}