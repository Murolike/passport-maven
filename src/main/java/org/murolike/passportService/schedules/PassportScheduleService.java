package org.murolike.passportService.schedules;

import org.murolike.passportService.services.MasterPassportUpdateFileService;
import org.murolike.passportService.services.SlavePassportUpdateFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PassportScheduleService {

    private final Logger logger = LoggerFactory.getLogger(PassportScheduleService.class);

    @Autowired
    private final MasterPassportUpdateFileService masterPassportFileUpdateService;

    @Autowired
    private final SlavePassportUpdateFileService slavePassportFileUpdateService;

    public PassportScheduleService(MasterPassportUpdateFileService masterPassportFileUpdateService, SlavePassportUpdateFileService slavePassportFileUpdateService) {
        this.masterPassportFileUpdateService = masterPassportFileUpdateService;
        this.slavePassportFileUpdateService = slavePassportFileUpdateService;
    }

    @Scheduled(cron = "0 5 * * * *")
    public void runMasterPassportUpdater() {
        try {
            logger.info("Запущена процедура обновления основных паспортов");
            masterPassportFileUpdateService.run();
            logger.info("Процедура обновления основных паспортов завершена");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void runSlavePassportUpdater() {
        try {
            logger.info("Запущена процедура обновления дополнительных паспортов");
            slavePassportFileUpdateService.run();
            logger.info("Процедура обновления дополнительных паспортов завершена");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
