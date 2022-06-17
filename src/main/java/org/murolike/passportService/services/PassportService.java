package org.murolike.passportService.services;

import org.murolike.passportService.dao.PassportRepository;
import org.murolike.passportService.dao.SlavePassportRepository;
import org.murolike.passportService.models.Passport;
import org.murolike.passportService.models.SlavePassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportService {
    @Autowired
    private final PassportRepository repository;

    public PassportService(PassportRepository repository) {
        this.repository = repository;
    }

    public Iterable<Passport> findAll() {
        return this.repository.findAll();
    }

    public Iterable<Passport> findAllBySeriesAndNumber(String series, String number) {
        return this.repository.findAllBySeriesAndNumber(series, number);
    }
}
