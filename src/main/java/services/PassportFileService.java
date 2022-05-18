package services;

import models.InvalidPassportMaster;
import models.Passport;

import java.io.*;
import java.sql.SQLException;
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
        ArrayList<InvalidPassportMaster> passports = new ArrayList<>();
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
            passports.add(new InvalidPassportMaster(data[0], data[1]));
            lineCounter++;
        }
        return passportService.insert(passports);
    }

    protected String getFileExtension() {
        String name = passportFile.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
}
