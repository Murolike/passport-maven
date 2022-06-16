package services;

import db.ConnectionManager;
import models.MasterPassport;
import models.Passport;
import models.PassportData;
import models.SlavePassport;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

@Component
public class PassportFileService extends PassportService {
    public static final String EXTENSION_CSV = "csv";

    public PassportFileService(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    public int update(String filePath) throws IOException, RuntimeException {
        File passportFile = new File(filePath);
        String name = passportFile.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1);

        if (!passportFile.exists()) {
            throw new FileNotFoundException("Файл не существует " + passportFile.toPath());
        }
        if (!passportFile.canRead()) {
            throw new RuntimeException("Не возможно прочитать файл " + passportFile.toPath());
        }

        if (!EXTENSION_CSV.equalsIgnoreCase(extension)) {
            throw new RuntimeException("Формат файла должен быть CSV");
        }

        deleteAll();

        BufferedReader br = new BufferedReader(new FileReader(passportFile));
        ArrayList<Passport> passports = new ArrayList<>();
        int lineCounter = 0;
        String line;
        while ((line = br.readLine()) != null) {
            if (0 == lineCounter) {
                lineCounter++;
                continue;
            }

            String[] data = line.split(",");
            if (data.length != 2) {
                throw new RuntimeException("Кол-во элементов на строке " + (++lineCounter) + " не равен 2, необходимое для обновления данных");
            }
            MasterPassport masterPassport = new MasterPassport();
            SlavePassport slavePassport = new SlavePassport();
            PassportData passportData = new PassportData(data[0], data[1]);
            masterPassport.setPassportData(passportData);
            slavePassport.setPassportData(passportData);
            passports.add(masterPassport);
            passports.add(slavePassport);
            lineCounter++;
        }
        return insert(passports);
    }
}
