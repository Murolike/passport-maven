package org.murolike.passportService.services;

import org.murolike.passportService.dao.TmpPassportRepository;
import org.murolike.passportService.models.TmpPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TmpPassportService {
    @Autowired
    private final TmpPassportRepository repository;

    @Autowired
    private final JdbcTemplate template;

    private final static String SQL_INSERT_IN_TMP_PASSPORT = "insert into tmp_passports(series,number) values (?, ?)";

    public TmpPassportService(TmpPassportRepository repository, JdbcTemplate template) {
        this.repository = repository;
        this.template = template;
    }

    public Iterable<TmpPassport> findAll() {
        return repository.findAll();
    }

    public void save(TmpPassport model) {
        repository.save(model);
    }

    public void saveAll(Iterable<TmpPassport> passports) {
        repository.saveAll(passports);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void insert(String series, String number) {
        template.update(SQL_INSERT_IN_TMP_PASSPORT, series, number);
    }
}
