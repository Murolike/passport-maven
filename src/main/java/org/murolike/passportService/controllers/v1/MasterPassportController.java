package org.murolike.passportService.controllers.v1;

import org.murolike.passportService.models.MasterPassport;
import org.murolike.passportService.services.MasterPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/master-passport")
public class MasterPassportController {

    @Autowired
    private final MasterPassportService service;

    public MasterPassportController(MasterPassportService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public Iterable<MasterPassport> index() {
        return this.service.findAll();
    }

    @GetMapping("/search/{series}/{number}")
    public Iterable<MasterPassport> search(@PathVariable String series, @PathVariable String number) {
        return this.service.findAllBySeriesAndNumber(series, number);
    }
}
