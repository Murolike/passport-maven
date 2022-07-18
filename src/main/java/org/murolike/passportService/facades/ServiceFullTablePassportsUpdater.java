package org.murolike.passportService.facades;

import org.murolike.passportService.components.pg.DatabaseEnvironment;
import org.murolike.passportService.components.pg.PgLoader;
import org.murolike.passportService.configurations.PgConfiguration;
import org.murolike.passportService.configurations.PgConfigurationBuilder;
import org.murolike.passportService.enums.InvalidPassportTables;
import org.murolike.passportService.components.pg.PgLoaderException;
import org.murolike.passportService.schedules.PassportScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Сервис полного обновления таблиц паспортов
 * <p><b>ВАЖНО:</b> Медленная работа на локальном ПК</p>
 */
public class ServiceFullTablePassportsUpdater {

    private final Logger logger = LoggerFactory.getLogger(PassportScheduleService.class);
    private final DatabaseEnvironment environment;
    private final PgLoader pgLoader;
    private final PassportLoader passportLoader;

    public ServiceFullTablePassportsUpdater(PassportLoader passportLoader, DatabaseEnvironment environment, PgLoader pgLoader) {
        this.environment = environment;
        this.pgLoader = pgLoader;
        this.passportLoader = passportLoader;
    }

    /**
     * Выполняет полное обновление данных в таблицах
     *
     * @throws IOException          Возникает, когда есть проблемы со скачиванием файла
     * @throws InterruptedException Возникает, когда распаковка файла произошла с ошибкой
     * @throws PgLoaderException    Возникает, когда Pgloader завершил свою работу с ошибкой
     */
    public void run() throws IOException, InterruptedException, PgLoaderException {
        logger.info("Запущен процесс полного обновления паспортов...");
        logger.info("Запускаем загрузку и распаковку файла для обновления...");
        File sourceFile = passportLoader.load();
        logger.info("Загрузка и распаковка файла для обновления - завершена");

        logger.info("Выполняем заливку файла в таблицы...");
        for (InvalidPassportTables invalidPassportTable : InvalidPassportTables.values()) {
            logger.info("Выполняем заливку файла в таблицу " + invalidPassportTable.getTitle() + "...");
            loadFileInTable(sourceFile, invalidPassportTable.getTitle());
            logger.info("Заливка в таблицу " + invalidPassportTable.getTitle() + " - завершена");
        }
        if (!sourceFile.delete()) {
            sourceFile.deleteOnExit();
        }
        logger.info("Процесс полного обновления паспортов - завершен");
    }

    /**
     * Загрузка данных файла в таблицу
     *
     * @param sourceFile Исходный файл для загрузки
     * @param tableName  Название таблицы куда будем заливать
     * @throws IOException          Возникает, когда есть проблемы с файлом
     * @throws InterruptedException Возникает, когда есть проблемы с запуском внешнего процесса (pgloader)
     * @throws PgLoaderException    Возникает, когда pgloader отработал некорректно
     */
    protected void loadFileInTable(File sourceFile, String tableName) throws IOException, InterruptedException, PgLoaderException {
        PgConfiguration pgConfiguration = generateConfigurationByTableName(tableName);
        pgLoader.run(sourceFile, pgConfiguration);
    }

    //TODO Метод совпадает с методов из класса ServiceFullTablePassportsUpdater
    /**
     * Генерация конфигурации для последующей генерации файла
     *
     * @param tableName Название таблицы
     * @return Возвращает объект со всеми параметрами для загрузки
     */
    protected PgConfiguration generateConfigurationByTableName(String tableName) {

        PgConfigurationBuilder pgConfigurationBuilder = new PgConfigurationBuilder();
        Set<String> columns = new LinkedHashSet<>();
        columns.add("series");
        columns.add("number");

        return pgConfigurationBuilder.create()
                .host(environment.getHost())
                .port(environment.getPort())
                .username(environment.getUserName())
                .password(environment.getPassword())
                .dbName(environment.getDbName())
                .columns(columns)
                .tableName(tableName)
                .build();
    }
}
