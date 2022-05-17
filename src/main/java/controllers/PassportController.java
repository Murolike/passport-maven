package controllers;

import db.ConnectionManager;
import models.Passport;
import services.PassportFileService;
import services.PassportService;

import java.sql.SQLException;
import java.util.ArrayList;

public class PassportController implements Controller {
    private final PassportService passportService;

    public PassportController(ConnectionManager connectionManager) {
        this.passportService = new PassportService(connectionManager);
    }

    public String all() throws SQLException {
        ArrayList<Passport> passports = passportService.all();
        StringBuilder builder = new StringBuilder();
        for (Passport passport : passports) {
            builder.append(passport.getSerial()).append(" ").append(passport.getNumber()).append(" ").append(passport.getCreatedAt()).append(" \r\n");
        }
        return builder.toString();
    }

    public String search(String serial, String number) throws SQLException {
        Passport passport = passportService.search(serial, number);
        if (null == passport) {
            return "Паспорт валидный";
        }
        return "Паспорт не валидный. Детализация: " + passport.getSerial() + " " + passport.getNumber() + " " + passport.getCreatedAt();
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
            return "Проблемы: " + exception.getMessage();
        }
    }
}
