package org.murolike.passportService.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.murolike.passportService.enums.InvalidPassportTables;
import org.springframework.stereotype.Component;

/**
 * Класс для логирование запросов к SlavePassportService
 */
@Aspect
@Component
public class SlaveLogRequestSearchBehavior extends LogRequestSearchBehavior {

    @Pointcut("execution(* org.murolike.passportService.services.SlavePassportService.findAllBySeriesAndNumber(String,String)) && args(series, number)")
    public void search(String series, String number) {
    }

    @Override
    protected String getSearchInTable() {
        return InvalidPassportTables.INVALID_PASSPORTS_SLAVE.getTitle();
    }
}
