package org.murolike.passportService.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SlaveLogRequestSearchBehavior extends LogRequestSearchBehavior {

    public static String SEARCH_IN_TABLE = "invalid_passports_slave";

    @Pointcut("execution(* org.murolike.passportService.services.SlavePassportService.findAllBySeriesAndNumber(String,String)) && args(series, number)")
    public void search(String series, String number) {
    }

    @Override
    protected String getSearchInTable() {
        return SEARCH_IN_TABLE;
    }
}
