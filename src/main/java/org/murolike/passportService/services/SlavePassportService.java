package org.murolike.passportService.services;

import org.murolike.passportService.dao.SlavePassportRepository;
import org.murolike.passportService.models.SlavePassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlavePassportService {
    @Autowired
    private final SlavePassportRepository repository;

    public SlavePassportService(SlavePassportRepository repository) {
        this.repository = repository;
    }

    public Iterable<SlavePassport> findAll() {
        return this.repository.findAll();
    }

    public Iterable<SlavePassport> findAllBySeriesAndNumber(String series, String number) {
        return this.repository.findAllBySeriesAndNumber(series, number);
    }

    public void save(SlavePassport model) {
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
        this.repository.vacuumTable();
    }
}
