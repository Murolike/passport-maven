package org.murolike.passportService.schedules;

import org.murolike.passportService.services.MasterPassportService;
import org.murolike.passportService.services.SlavePassportService;
import org.murolike.passportService.services.file.MasterPassportUpdateFileService;
import org.murolike.passportService.services.file.SlavePassportUpdateFileService;
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

@Component
public class PassportScheduleService {

    private final Logger logger = LoggerFactory.getLogger(PassportScheduleService.class);

    @Autowired
    private final MasterPassportUpdateFileService masterPassportFileUpdateService;

    @Autowired
    private final SlavePassportUpdateFileService slavePassportFileUpdateService;

    @Value("${passport.storage.filePath}")
    protected String passportUpdateFilePath;

    @Value("${passport.storage.link}")
    protected String passportUpdateFileLink;

    public PassportScheduleService(MasterPassportUpdateFileService masterPassportFileUpdateService, SlavePassportUpdateFileService slavePassportFileUpdateService) {
        this.masterPassportFileUpdateService = masterPassportFileUpdateService;
        this.slavePassportFileUpdateService = slavePassportFileUpdateService;
    }

    public void runMasterPassportUpdater() {
        try {
            logger.info("Запущена процедура обновления основных паспортов");
            File passportUpdateFile = new File(getClass().getClassLoader().getResource(passportUpdateFilePath).getFile());
            masterPassportFileUpdateService.run(passportUpdateFile);
            logger.info("Процедура обновления основных паспортов завершена");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void runSlavePassportUpdater() {
        try {
            logger.info("Запущена процедура обновления дополнительных паспортов");
            File passportUpdateFile = new File(getClass().getClassLoader().getResource(passportUpdateFilePath).getFile());
            slavePassportFileUpdateService.run(passportUpdateFile);
            logger.info("Процедура обновления дополнительных паспортов завершена");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 5 * * * *")
    public void runMasterPassportUpdaterInternet() {
        try {
            logger.info("Запущена процедура обновления основных паспортов");
            Downloader downloader = new Downloader(passportUpdateFileLink, System.getProperty("java.io.tmpdir") + "/");
            File archive = downloader.download();
            Bzip2 bzip2 = new Bzip2(archive);
            File passportUpdateFile = bzip2.unzip();
            masterPassportFileUpdateService.run(passportUpdateFile);
            archive.delete();
            passportUpdateFile.delete();
            logger.info("Процедура обновления основных паспортов завершена");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 5 * * * *")
    public void runSlavePassportUpdaterInternet() {
        try {
            logger.info("Запущена процедура обновления дополнительных паспортов");
            Downloader downloader = new Downloader(passportUpdateFileLink, System.getProperty("java.io.tmpdir") + "/");
            File archive = downloader.download();
            Bzip2 bzip2 = new Bzip2(archive);
            File passportUpdateFile = bzip2.unzip();
            slavePassportFileUpdateService.run(passportUpdateFile);
            archive.delete();
            passportUpdateFile.delete();
            logger.info("Процедура обновления дополнительных паспортов завершена");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
