package org.murolike.passportService.dao;

import org.murolike.passportService.models.Passport;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface PassportRepository extends Repository<Passport, Integer> {
    Iterable<Passport> findAll();

    Iterable<Passport> findBySeriesAndNumber(String series, String number);
}
