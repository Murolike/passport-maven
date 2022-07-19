package org.murolike.passportService.facades;

import org.murolike.passportService.services.MasterPassportService;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Заливщик данных в таблицы с паспортами (master+slave)
 */
@Service
public class FillerInvalidPassportTables {

    @Autowired
    private final MasterPassportService masterPassportService;

    @Autowired
    private final SlavePassportService slavePassportService;


    public FillerInvalidPassportTables(MasterPassportService masterPassportService, SlavePassportService slavePassportService) {
        this.masterPassportService = masterPassportService;
        this.slavePassportService = slavePassportService;
    }

    /**
     * Обновление основной таблицы (master)
     */
    @Transactional
    public void updateMasterPassports() {
        this.masterPassportService.deleteNotExistingInLoadingTable();
        this.masterPassportService.insertNotExistingDataFromLoadingTable();
        this.masterPassportService.updateLastDateOnFileExistDataFromLoadingTable();
    }

    public void vacuumMasterTable() {
        this.masterPassportService.vacuumTable();
    }

    /**
     * Обновление дополнительной таблицы (slave)
     */
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
