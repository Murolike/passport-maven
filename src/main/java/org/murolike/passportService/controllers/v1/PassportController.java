package org.murolike.passportService.controllers.v1;

import org.murolike.passportService.models.Passport;
import org.murolike.passportService.services.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/passport")
public class PassportController {

    @Autowired
    private final PassportService service;

    public PassportController(PassportService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public Iterable<Passport> index() {
        return this.service.findAll();
    }

    @GetMapping("/search/{series}/{number}")
    public Iterable<Passport> search(@PathVariable String series, @PathVariable String number) {
        return this.service.findAllBySeriesAndNumber(series, number);
    }
}
