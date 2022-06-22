package org.murolike.passportService.controllers.v1;

import org.murolike.passportService.models.SlavePassport;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/slave-passport")
public class SlavePassportController {

    @Autowired
    private final SlavePassportService service;

    public SlavePassportController(SlavePassportService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public Iterable<SlavePassport> index() {
        return this.service.findAll();
    }

    @GetMapping("/search/{series}/{number}")
    public Iterable<SlavePassport> search(@PathVariable String series, @PathVariable String number) {
        return this.service.findAllBySeriesAndNumber(series, number);
    }
}
