package controllers;

import db.ConnectionManager;
import models.Passport;
import models.PassportData;
import services.PassportFileService;
import services.PassportService;

import java.util.List;

public class PassportController implements Controller {
    private final PassportService passportService;

    public PassportController(ConnectionManager connectionManager) {
        this.passportService = new PassportService(connectionManager);
    }

    public String all() {
        List<Passport> passports = passportService.all();
        StringBuilder builder = new StringBuilder();
        for (Passport passport : passports) {
            PassportData passportData = passport.getPassportData();
            builder.append(passportData.getSeries()).append(" ").append(passportData.getNumber()).append(" ").append(passportData.getCreatedAt()).append(" \r\n");
        }
        return builder.toString();
    }

    public String search(String serial, String number) {
        Passport passport = passportService.search(serial, number);
        if (null == passport) {
            return "Паспорт валидный";
        }
        PassportData passportData = passport.getPassportData();
        return "Паспорт не валидный. Детализация: " + passportData.getSeries() + " " + passportData.getNumber() + " " + passportData.getCreatedAt();
    }

    /**
     * @param filePath Путь до файла
     * @return Возвращает результат обработки команды
     * @todo Переименовать, чтобы было точное восприятие
     */
    public String update(String filePath) {
        try {
            PassportFileService service = new PassportFileService(filePath, passportService);
            int counter = service.update();
            return "База данных обновлена. Обновленое следующие кол-во записей " + counter;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "Проблемы: " + exception.getMessage();
        }
    }
}
