package org.murolike.passportService.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PassportLogRequestSearchBehavior extends LogRequestSearchBehavior {

    public static String SEARCH_IN_TABLE = "vw_passport";

    @Pointcut("execution(* org.murolike.passportService.services.PassportService.findAllBySeriesAndNumber(String,String)) && args(series, number)")
    public void search(String series, String number) {
    }

    @Override
    protected String getSearchInTable() {
        return SEARCH_IN_TABLE;
    }
}
