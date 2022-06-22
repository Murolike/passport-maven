package org.murolike.passportService.services;

import org.murolike.passportService.models.MasterPassport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;

/**
 * TODO: Попробовать объединить c SlavePassportFileUpdateService
 */
@Service
public class MasterPassportFileUpdateService {
    @Autowired
    protected MasterPassportService service;
    @Value("${passport.storage.filePath}")
    protected String resourcePath;

    public MasterPassportFileUpdateService(MasterPassportService service) {
        this.service = service;
    }

    @Transactional
    public void run() throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource(resourcePath);
        File file = new File(fileUrl.getFile());
        service.deleteAll();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        boolean isFirstLine = true;
        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            String series = line.substring(0, line.indexOf(","));
            String number = line.substring(line.indexOf(",") + 1);
            MasterPassport passport = new MasterPassport(series, number);
            service.save(passport);
        }
    }
}
