package org.murolike.passportService.facades;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.murolike.passportService.components.pg.DatabaseEnvironment;
import org.murolike.passportService.components.pg.PgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ServiceFullTablePassportsUpdaterTest {

    @Autowired
    private DatabaseEnvironment environment;
    @Autowired
    private PgLoader pgLoader;

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
    void run() {
        assertDoesNotThrow(() -> {
            PassportLoader loader = new PassportLoader(listOfExpiredPassportsPath.toString(), System.getProperty("java.io.tmpdir") + "/");
            ServiceFullTablePassportsUpdater service = new ServiceFullTablePassportsUpdater(loader, environment, pgLoader);

            service.run();
        });
    }
}