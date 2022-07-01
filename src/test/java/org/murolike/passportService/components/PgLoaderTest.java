package org.murolike.passportService.components;

import org.junit.jupiter.api.Test;
import org.murolike.passportService.configurations.PgConfiguration;
import org.murolike.passportService.configurations.PgConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PgLoaderTest {

    @Autowired
    public DatabaseEnvironment environment;

    @Autowired
    public PgLoader pgLoader;

    public String tempTable = "tmp_passports";

    @Test
    void run() throws IOException, InterruptedException {
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

        File file = new File(this.getClass().getClassLoader().getResource("list_of_expired_passports.csv").getFile());

        int actual = pgLoader.run(file, configuration);

        assertEquals(0, actual);
    }
}