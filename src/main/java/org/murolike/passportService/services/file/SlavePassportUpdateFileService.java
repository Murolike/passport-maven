package org.murolike.passportService.services.file;

import org.murolike.passportService.models.SlavePassport;
import org.murolike.passportService.services.SlavePassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlavePassportUpdateFileService extends PassportUpdateFileService {
    @Autowired
    protected SlavePassportService service;

    public SlavePassportUpdateFileService(SlavePassportService service) {
        this.service = service;
    }

    protected void deleteAll() {
        service.deleteAll();
    }

    protected void save(String serial, String number) {
        SlavePassport passport = new SlavePassport(serial, number);
        service.save(passport);
    }
}
