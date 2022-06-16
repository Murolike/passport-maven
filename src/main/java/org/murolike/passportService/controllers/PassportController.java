package org.murolike.passportService.controllers;

import org.murolike.passportService.db.ConnectionManager;
import org.murolike.passportService.models.Passport;
import org.murolike.passportService.models.PassportData;
import org.murolike.passportService.services.PassportFileService;
import org.murolike.passportService.services.PassportService;

import java.util.List;

public class PassportController implements Controller {
    private final PassportService passportService;
    private final PassportFileService passportFileService;

    public PassportController(ConnectionManager connectionManager) {
        this.passportService = new PassportService(connectionManager);
        this.passportFileService = new PassportFileService(connectionManager);
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
            int counter = passportFileService.update(filePath);
            return "База данных обновлена. Обновленое следующие кол-во записей " + counter;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "Проблемы: " + exception.getMessage();
        }
    }
}
