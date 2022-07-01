package org.murolike.passportService.components;

import org.murolike.passportService.configurations.PgConfiguration;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PgLoader {

    public static String DEFAULT_CMD_IMPORT =
            " LOAD CSV FROM '{fileName}' WITH ENCODING UTF-8\n" +
            " INTO postgresql://{user}:{password}@{host}:{port}/{dbname}?tablename={tableName}\n" +
            " TARGET COLUMNS ({columns})\n" +
            " WITH TRUNCATE, SKIP HEADER = 1, fields terminated by ',', workers = 8, concurrency = 2;";

    protected String pgLoaderCommand = "pgloader";

    public PgLoader() {
    }

    public int run(File file, PgConfiguration configuration) throws InterruptedException, IOException {
        String pgLoadConfig = generateCommand(file, configuration);
        File pgLoadFile = createPgLoadConfigFile(pgLoadConfig);
        pgLoadFile.deleteOnExit();

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(pgLoaderCommand + " " + pgLoadFile.toPath());
        return process.waitFor();
    }

    protected String generateCommand(File file, PgConfiguration configuration) {
        String columns = String.join(",", configuration.getColumns());

        return DEFAULT_CMD_IMPORT
                .replace("{user}", configuration.getUsername())
                .replace("{password}", configuration.getPassword())
                .replace("{host}", configuration.getHost())
                .replace("{port}", configuration.getPort())
                .replace("{dbname}", configuration.getDbName())
                .replace("{tableName}", configuration.getTableName())
                .replace("{columns}", columns)
                .replace("{fileName}", file.toPath().toString());
    }

    protected File createPgLoadConfigFile(String pgLoadConfig) throws IOException {
        File pgLoadFile = File.createTempFile("pg_loader", ".load");
        OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(pgLoadFile));
        stream.write(pgLoadConfig);
        stream.close();

        return pgLoadFile;
    }
}
