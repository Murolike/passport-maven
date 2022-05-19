package services;

import models.MasterPassport;
import models.Passport;
import models.PassportData;
import models.SlavePassport;

import java.io.*;
import java.util.ArrayList;

public class PassportFileService {
    public static final String EXTENSION_CSV = "csv";

    protected String filePath;
    protected PassportService passportService;

    protected File passportFile;

    public PassportFileService(String filePath, PassportService passportService) {
        this.filePath = filePath;
        this.passportService = passportService;
        this.passportFile = new File(filePath);
    }

    public int update() throws IOException, RuntimeException {
        if (!passportFile.exists()) {
            throw new FileNotFoundException("Файл не существует " + passportFile.toPath());
        }
        if (!passportFile.canRead()) {
            throw new RuntimeException("Не возможно прочитать файл " + passportFile.toPath());
        }

        if (!EXTENSION_CSV.equalsIgnoreCase(getFileExtension())) {
            throw new RuntimeException("Формат файла должен быть CSV");
        }
        passportService.deleteAll();

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
        return passportService.insert(passports);
    }

    protected String getFileExtension() {
        String name = passportFile.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
}
