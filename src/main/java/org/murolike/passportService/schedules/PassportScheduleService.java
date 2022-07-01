package org.murolike.passportService.schedules;

import org.murolike.passportService.components.DatabaseEnvironment;
import org.murolike.passportService.configurations.PgConfiguration;
import org.murolike.passportService.configurations.PgConfigurationBuilder;
import org.murolike.passportService.services.file.PassportUpdateFileService;
import org.murolike.passportService.services.link.Bzip2;
import org.murolike.passportService.services.link.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class PassportScheduleService {

    private final Logger logger = LoggerFactory.getLogger(PassportScheduleService.class);

    @Autowired
    private PassportUpdateFileService passportUpdateFileService;

    @Value("${passport.storage.link}")
    private String passportUpdateFileLink;

    @Autowired
    private DatabaseEnvironment environment;

    @Scheduled(cron = "0 */1 * * * *")
    public void run() {
        try {
            logger.info("Запущена процедура обновления основных паспортов");
            Downloader downloader = new Downloader(passportUpdateFileLink, System.getProperty("java.io.tmpdir") + "/");
            File archive = downloader.download();
            Bzip2 bzip2 = new Bzip2(archive);
            File passportUpdateFile = bzip2.unzip();
            PgConfigurationBuilder pgConfigurationBuilder = new PgConfigurationBuilder();
            Set<String> columns = new LinkedHashSet<>();
            columns.add("series");
            columns.add("number");
            PgConfiguration pgConfiguration = pgConfigurationBuilder.create()
                    .host(environment.getHost())
                    .port(environment.getPort())
                    .username(environment.getUserName())
                    .password(environment.getPassword())
                    .dbName(environment.getDbName())
                    .columns(columns)
                    .tableName("tmp_passports")
                    .build();

            passportUpdateFileService.load(passportUpdateFile, pgConfiguration);
            passportUpdateFileService.updateMasterPassports();
            passportUpdateFileService.updateSlavePassports();
            archive.deleteOnExit();
            passportUpdateFile.deleteOnExit();

            logger.info("Процедура обновления основных паспортов завершена");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
