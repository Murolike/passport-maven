package org.murolike.passportService.aspects;

import org.aspectj.lang.annotation.*;
import org.murolike.passportService.enums.InvalidPassportTables;
import org.springframework.stereotype.Component;

/**
 * Класс для логирование запросов к MasterPassportService
 */
@Aspect
@Component
public class MasterLogRequestSearchBehavior extends LogRequestSearchBehavior {

    @Pointcut("execution(* org.murolike.passportService.services.MasterPassportService.findAllBySeriesAndNumber(String,String)) && args(series, number)")
    public void search(String series, String number) {
    }

    @Override
    protected String getSearchInTable() {
        return InvalidPassportTables.INVALID_PASSPORTS_MASTER.getTitle();
    }
}
