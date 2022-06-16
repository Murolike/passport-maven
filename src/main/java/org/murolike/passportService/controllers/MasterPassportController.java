package org.murolike.passportService.controllers;

import org.murolike.passportService.dao.MasterPassportRepository;
import org.murolike.passportService.models.MasterPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master-passport")
public class MasterPassportController {

    @Autowired
    private final MasterPassportRepository repository;

    public MasterPassportController(MasterPassportRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public Iterable<MasterPassport> index() {
        return this.repository.findAll();
    }

    @GetMapping("/search/{series}/{number}")
    public Iterable<MasterPassport> search(@PathVariable String series, @PathVariable String number) {
        return this.repository.findBySeriesAndNumber(series, number);
    }
}
