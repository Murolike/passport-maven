package org.murolike.passportService.facades;

import org.murolike.passportService.components.pg.DatabaseEnvironment;
import org.murolike.passportService.components.pg.PgLoader;
import org.murolike.passportService.components.pg.PgConfiguration;
import org.murolike.passportService.components.pg.PgConfigurationBuilder;
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
 * Сервис частиного обновления таблиц паспортов
 * <p><b>ВАЖНО:</b> Медленная работа на локальном ПК</p>
 * <p>Алгоритм обновления следующий:</p>
 * <ol>
 *     <li>Выполняется заливка данных из файл во временную таблицу, по умолчанию tmp_passports</li>
 *     <li>Из существующей таблиц данных удаляются данные отсутствующие в таблице tmp_passports</li>
 *     <li>Выполняется поиск новых данных несуществующих в основной, но добавленных в tmp_passports</li>
 *     <li>Выполняется обновление информации о последнем существованение записи (дата) в основной таблице</li>
 * </ul>
 */
public class ServicePartialTablePassportsUpdater {

    private final Logger logger = LoggerFactory.getLogger(PassportScheduleService.class);
    private final DatabaseEnvironment environment;
    private final PgLoader pgLoader;
    private final PassportLoader passportLoader;
    private final FillerInvalidPassportTables fillerInvalidPassportTables;

    public ServicePartialTablePassportsUpdater(PassportLoader passportLoader, DatabaseEnvironment environment, PgLoader pgLoader, FillerInvalidPassportTables fillerInvalidPassportTables) {
        this.passportLoader = passportLoader;
        this.environment = environment;
        this.pgLoader = pgLoader;
        this.fillerInvalidPassportTables = fillerInvalidPassportTables;
    }

    /**
     * Выполняет частичное обновление таблиц
     *
     * @throws IOException          Возникает, когда есть проблемы со скачиванием файла
     * @throws InterruptedException Возникает, когда распаковка файла произошла с ошибкой
     * @throws PgLoaderException    Возникает, когда Pgloader завершил свою работу с ошибкой
     */
    public void run() throws IOException, InterruptedException, PgLoaderException {
        logger.info("Запущен процесс частичного обновления паспортов...");
        logger.info("Запускаем загрузку и распаковку файла для обновления...");
        File sourceFile = passportLoader.load();
        logger.info("Загрузка и распаковка файла для обновления - завершена");

        logger.info("Выполняем заливку файла в таблицы...");
        String tableName = InvalidPassportTables.TMP_PASSPORTS.getTitle();
        logger.info("Выполняем заливку файла в таблицу " + tableName + "...");
        loadFileInTable(sourceFile, tableName);
        logger.info("Заливка в таблицу " + tableName + " - завершена");
        if (!sourceFile.delete()) {
            sourceFile.deleteOnExit();
        }
        logger.info("Выполняем обновление основной таблицы невалидных паспортов...");
        fillerInvalidPassportTables.updateMasterPassports();
        logger.info("Обновление основной таблицы невалидных паспортов - завершено");
        logger.info("Выполняем обновление дополнительной таблицы невалидных паспортов...");
        fillerInvalidPassportTables.updateSlavePassports();
        logger.info("Обновление дополнительной таблицы невалидных паспортов - завершено");
        logger.info("Процесс частичного обновления паспортов - завершен");
    }

    /**
     * Загрузка данных из файла в таблицу
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
     * Ф-ция для генерации конфигурационного файла для pgloader
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
