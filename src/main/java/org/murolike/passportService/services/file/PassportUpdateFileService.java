package org.murolike.passportService.services.file;

import org.murolike.passportService.components.PgLoader;
import org.murolike.passportService.configurations.PgConfiguration;
import org.murolike.passportService.services.MasterPassportService;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
public class PassportUpdateFileService {

    @Autowired
    private final MasterPassportService masterPassportService;
    @Autowired
    private final SlavePassportService slavePassportService;
    @Autowired
    private final PgLoader pgLoader;

    public PassportUpdateFileService(MasterPassportService masterPassportService, SlavePassportService slavePassportService, PgLoader pgLoader) {
        this.masterPassportService = masterPassportService;
        this.slavePassportService = slavePassportService;
        this.pgLoader = pgLoader;
    }

    public void load(File file, PgConfiguration configuration) throws IOException, InterruptedException {
        this.pgLoader.run(file, configuration);
    }

    @Transactional
    public void updateMasterPassports() {
        this.masterPassportService.deleteNotExistingInLoadingTable();
        this.masterPassportService.insertNotExistingDataFromLoadingTable();
        this.masterPassportService.updateLastDateOnFileExistDataFromLoadingTable();
    }

    @Transactional
    public void updateSlavePassports() {
        this.slavePassportService.deleteNotExistingInLoadingTable();
        this.slavePassportService.insertNotExistingDataFromLoadingTable();
        this.slavePassportService.updateLastDateOnFileExistDataFromLoadingTable();
    }
}
