package org.murolike.passportService.components.pg;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Компонент для работы с pgloader
 */
@Component
public class PgLoader {


    //TODO Можно добавить реализацию через использование файла (шаблонный файл, чтобы его можно было модифицировать)
    /**
     * Шаблонный файл для заливки
     */
    public static String DEFAULT_CMD_IMPORT =
            " LOAD CSV FROM '{fileName}' WITH ENCODING UTF-8\n" +
                    " INTO postgresql://{user}:{password}@{host}:{port}/{dbname}?tablename={tableName}\n" +
                    " TARGET COLUMNS ({columns})\n" +
                    " WITH TRUNCATE, DROP INDEXES, SKIP HEADER = 1, fields terminated by ',', workers = 8, concurrency = 2;";

    /**
     * Команда для выполнения
     */
    protected String pgLoaderCommand = "pgloader";

    /**
     * MIME тип для проверки перед загрузкой
     */
    public static String CSV_MIME_TYPE = "text/csv";

    public PgLoader() {
    }

    /**
     * Выполняет заливку файла с настройками
     *
     * @param file          Файл для заливки
     * @param configuration Конфигурация заливки
     * @throws InterruptedException Выбрасывается, когда при работе с внешней программой произошла проблема
     * @throws IOException          Выбрасывается, когда не удалось создать и/или сохранить данные в файле
     * @throws PgLoaderException    Выбрасывается, когда pgloader завершил работу некорректно (exitValue !=0)
     */
    public void run(File file, PgConfiguration configuration) throws InterruptedException, IOException, PgLoaderException {

        checkFileExtension(file);

        String pgLoadConfig = generateCommand(file, configuration);
        File pgLoadFile = createPgLoadConfigFile(pgLoadConfig);
        pgLoadFile.deleteOnExit();

        List<String> commands = new ArrayList<>();
        commands.add(pgLoaderCommand);
        commands.add("-q");
        commands.add("--on-error-stop");
        commands.add(pgLoadFile.getAbsolutePath());

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);
        Process process = processBuilder.start();

        int resultExecute = process.waitFor();
        if (resultExecute != 0) {
            throw new PgLoaderException("Не удалось загрузить файл, проанализируйте файл по пути /tmp/pgloader");
        }
    }

    /**
     * Проверка MIME типа расширения
     *
     * @param file Файл для заливки
     * @throws IOException       Возникает, когда файла нет или есть проблемы
     * @throws PgLoaderException Возникает, когда файл имеет неверный тип MIME
     */
    protected void checkFileExtension(File file) throws IOException, PgLoaderException {

        String mimeType = Files.probeContentType(file.toPath());

        if (!CSV_MIME_TYPE.equals(mimeType)) {
            throw new PgLoaderException("Не поддерживаем тип файла для заливки");
        }
    }

    /**
     * Генерация команды для запуска
     *
     * @param file          Файл для заливки
     * @param configuration Параметры подключения и заливки
     * @return Возвращает сгенерированную команду для записи в файл
     */
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


    /**
     * Создание файла с настройками для pgloader
     *
     * @param pgLoadConfig Команда для выполнения
     * @return Возращает ссылку на файл с настройками
     * @throws IOException Выбрасывается, когда не удалось создать или записать файл
     */
    protected File createPgLoadConfigFile(String pgLoadConfig) throws IOException {
        File pgLoadFile = File.createTempFile("pg_loader", ".load");
        OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(pgLoadFile));
        stream.write(pgLoadConfig);
        stream.close();

        return pgLoadFile;
    }
}
