package org.murolike.passportService.controllers;

import org.murolike.passportService.dao.PassportRepository;
import org.murolike.passportService.models.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private final PassportRepository repository;

    public PassportController(PassportRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public Iterable<Passport> index() {
        return this.repository.findAll();
    }

    @GetMapping("/search/{series}/{number}")
    public Iterable<Passport> search(@PathVariable String series, @PathVariable String number) {
        return this.repository.findBySeriesAndNumber(series, number);
    }
}