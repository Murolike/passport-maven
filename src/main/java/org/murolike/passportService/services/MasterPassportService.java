package org.murolike.passportService.services;

import org.murolike.passportService.dao.MasterPassportRepository;
import org.murolike.passportService.models.MasterPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MasterPassportService {
    @Autowired
    private final MasterPassportRepository repository;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MasterPassportService(MasterPassportRepository repository, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Iterable<MasterPassport> findAll() {
        return this.repository.findAll();
    }

    public Iterable<MasterPassport> findAllBySeriesAndNumber(String series, String number) {
        return this.repository.findAllBySeriesAndNumber(series, number);
    }

    public void save(MasterPassport model) {
        this.repository.save(model);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }

    public void deleteNotExistingInLoadingTable() {
        this.repository.deleteNotExistingInLoadingTable();
    }

    public void insertNotExistingDataFromLoadingTable() {
        this.repository.insertNotExistingDataFromLoadingTable();
    }

    public void updateLastDateOnFileExistDataFromLoadingTable() {
        this.repository.updateLastDateOnFileExistDataFromLoadingTable();
    }

    public void vacuumTable() {
        jdbcTemplate.execute("VACUUM (analyze) invalid_passports_master");
    }
}
