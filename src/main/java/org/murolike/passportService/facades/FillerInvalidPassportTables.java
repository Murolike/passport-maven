package org.murolike.passportService.facades;

import org.murolike.passportService.services.MasterPassportService;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FillerInvalidPassportTables {

    private final MasterPassportService masterPassportService;
    private final SlavePassportService slavePassportService;

    public FillerInvalidPassportTables(MasterPassportService masterPassportService, SlavePassportService slavePassportService) {
        this.masterPassportService = masterPassportService;
        this.slavePassportService = slavePassportService;
    }

    @Transactional
    public void updateMasterPassports() {
        this.masterPassportService.deleteNotExistingInLoadingTable();
        this.masterPassportService.insertNotExistingDataFromLoadingTable();
        this.masterPassportService.updateLastDateOnFileExistDataFromLoadingTable();
    }

    public void vacuumMasterTable() {
        this.masterPassportService.vacuumTable();
    }

    @Transactional
    public void updateSlavePassports() {
        this.slavePassportService.deleteNotExistingInLoadingTable();
        this.slavePassportService.insertNotExistingDataFromLoadingTable();
        this.slavePassportService.updateLastDateOnFileExistDataFromLoadingTable();

    }

    public void vacuumSlaveTable() {
        this.slavePassportService.vacuumTable();
    }
}
