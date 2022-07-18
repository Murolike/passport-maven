package org.murolike.passportService.components.pg;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.murolike.passportService.configurations.PgConfiguration;
import org.murolike.passportService.configurations.PgConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PgLoaderTest {

    @Autowired
    public DatabaseEnvironment environment;

    @Autowired
    public PgLoader pgLoader;

    public String tempTable = "tmp_passports";

    private final static String archiveFileName = "list_of_expired_passports.csv.bz2";
    private final static String listOfExpiredPassportsFileName = "short-list_of_expired_passports.csv";

    private URL archiveListOfExpiredPassportsPath;
    private URL csvListOfExpiredPassportsPath;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        archiveListOfExpiredPassportsPath = this.getClass().getClassLoader().getResource(archiveFileName);
        if (null == archiveListOfExpiredPassportsPath) {
            throw new FileNotFoundException("Не удалось найти файл в папке с ресурсами для тестов: " + archiveFileName);
        }
        csvListOfExpiredPassportsPath = this.getClass().getClassLoader().getResource(listOfExpiredPassportsFileName);
        if (null == csvListOfExpiredPassportsPath) {
            throw new FileNotFoundException("Не удалось найти файл в папке с ресурсами для тестов: " + listOfExpiredPassportsFileName);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenCompleteLoadFileThenSuccess() {
        assertDoesNotThrow(() -> {
                PgConfigurationBuilder builder = new PgConfigurationBuilder();
                LinkedHashSet<String> columns = new LinkedHashSet<>();
                columns.add("series");
                columns.add("number");
                PgConfiguration configuration = builder.create()
                        .host(environment.getHost())
                        .port(environment.getPort())
                        .username(environment.getUserName())
                        .password(environment.getPassword())
                        .dbName(environment.getDbName())
                        .tableName(tempTable)
                        .columns(columns)
                        .build();

                File file = new File(csvListOfExpiredPassportsPath.getFile());

                pgLoader.run(file, configuration);
        });
    }

    @Test
    void whenWrongFileExtensionThrowException() {
        assertThrows(PgLoaderException.class, () -> {
            PgConfigurationBuilder builder = new PgConfigurationBuilder();
            LinkedHashSet<String> columns = new LinkedHashSet<>();
            columns.add("series");
            columns.add("number");
            PgConfiguration configuration = builder.create()
                    .host(environment.getHost())
                    .port(environment.getPort())
                    .username(environment.getUserName())
                    .password(environment.getPassword())
                    .dbName(environment.getDbName())
                    .tableName(tempTable)
                    .columns(columns)
                    .build();

            File file = new File(archiveListOfExpiredPassportsPath.getFile());

            pgLoader.run(file, configuration);
        });
    }
}