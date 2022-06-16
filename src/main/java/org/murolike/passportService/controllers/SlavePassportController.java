package org.murolike.passportService.controllers;

import org.murolike.passportService.dao.SlavePassportRepository;
import org.murolike.passportService.models.SlavePassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slave-passport")
public class SlavePassportController {
    @Autowired
    private final SlavePassportRepository repository;

    public SlavePassportController(SlavePassportRepository passportRepository) {
        this.repository = passportRepository;
    }

    @GetMapping("/index")
    public Iterable<SlavePassport> index() {
        return this.repository.findAll();
    }

    @GetMapping("/search/{series}/{number}")
    public Iterable<SlavePassport> search(@PathVariable String series, @PathVariable String number) {
        return this.repository.findBySeriesAndNumber(series, number);
    }
}
