package org.murolike.passportService.aspects;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MasterLogRequestSearchBehavior extends LogRequestSearchBehavior {

    public static String SEARCH_IN_TABLE = "invalid_passports_master";

    @Pointcut("execution(* org.murolike.passportService.services.MasterPassportService.findAllBySeriesAndNumber(String,String)) && args(series, number)")
    public void search(String series, String number) {
    }

    @Override
    protected String getSearchInTable() {
        return SEARCH_IN_TABLE;
    }
}
