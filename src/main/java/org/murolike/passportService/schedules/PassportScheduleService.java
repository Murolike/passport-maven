package org.murolike.passportService.schedules;

import org.murolike.passportService.components.pg.DatabaseEnvironment;
import org.murolike.passportService.components.pg.PgLoader;
import org.murolike.passportService.facades.PassportLoader;
import org.murolike.passportService.facades.ServiceFullTablePassportsUpdater;
import org.murolike.passportService.facades.ServicePartialTablePassportsUpdater;
import org.murolike.passportService.facades.FillerInvalidPassportTables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PassportScheduleService {

    private final Logger logger = LoggerFactory.getLogger(PassportScheduleService.class);

    @Value("${passport.storage.link}")
    private String passportUpdateFileLink;

    @Autowired
    private DatabaseEnvironment environment;

    @Autowired
    private PgLoader pgLoader;

    @Autowired
    private FillerInvalidPassportTables fillerInvalidPassportTables;

    /**
     * Полное обновление таблиц с невалидными паспортами
     * Должна быть запущена 1 раз при запуске проекта, после необходимо переключиться на частичное обновление
     * Продолжительность зависит от сервера, на локальной машине это время может занять более 1 часа.
     * Необходимо более 100ГБ свободного места для выполнения процедуры (с учетом индексов)
     * В случае настройки крона - запуск должен быть 1 раз в день в 01:00 и до 05:00
     */
    public void fullTablePassportsUpdate() {
        try {
            PassportLoader loader = new PassportLoader(passportUpdateFileLink, System.getProperty("java.io.tmpdir") + "/");
            ServiceFullTablePassportsUpdater service = new ServiceFullTablePassportsUpdater(loader, environment, pgLoader);

            service.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Частичное обновление таблиц с невалидными паспортами
     * Запускать каждый день с 01:00 до 05:00 или в период наименьшей нагрузки
     * Продолжительность зависит от сервера, на локальной машине это время может занять более 1 часа.
     * Необходимо более 100ГБ свободного места для выполнения процедуры (с учетом индексов)
     */
    public void partialTablePassportsUpdate() {
        try {
            PassportLoader loader = new PassportLoader(passportUpdateFileLink, System.getProperty("java.io.tmpdir") + "/");

            ServicePartialTablePassportsUpdater service = new ServicePartialTablePassportsUpdater(loader, environment, pgLoader, fillerInvalidPassportTables);

            service.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
