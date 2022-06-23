package org.murolike.passportService.services.file;

import org.murolike.passportService.models.MasterPassport;
import org.murolike.passportService.services.MasterPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterPassportUpdateFileService extends PassportUpdateFileService {
    @Autowired
    protected MasterPassportService service;

    public MasterPassportUpdateFileService(MasterPassportService service) {
        this.service = service;
    }

    protected void deleteAll() {
        service.deleteAll();
    }

    protected void save(String serial, String number) {
        MasterPassport passport = new MasterPassport(serial, number);
        service.save(passport);
    }
}
