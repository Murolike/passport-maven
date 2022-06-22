package org.murolike.passportService.schedules;

import org.murolike.passportService.services.MasterPassportFileUpdateService;
import org.murolike.passportService.services.SlavePassportFileUpdateService;
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
    private final MasterPassportFileUpdateService masterPassportFileUpdateService;

    @Autowired
    private final SlavePassportFileUpdateService slavePassportFileUpdateService;

    public PassportScheduleService(MasterPassportFileUpdateService masterPassportFileUpdateService, SlavePassportFileUpdateService slavePassportFileUpdateService) {
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

    @Scheduled(cron = "0 5 * * * *")
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
