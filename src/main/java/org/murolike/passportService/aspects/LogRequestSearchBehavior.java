package org.murolike.passportService.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.murolike.passportService.models.LogRequestSearch;
import org.murolike.passportService.services.LogRequestSearchService;
import org.murolike.passportService.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Aspect
@Component
public class LogRequestSearchBehavior {

    @Autowired
    private LogRequestSearchService service;

    @Autowired
    private RequestService requestService;

    @Pointcut("execution(* org.murolike.passportService.services.MasterPassportService.findAllBySeriesAndNumber(String,String)) && args(series, number)")
    public void search(String series, String number) {
    }

    @Around("search(series, number)")
    public Object logAround(ProceedingJoinPoint joinPoint, String series, String number) {
        LogRequestSearch model = new LogRequestSearch(series, number, requestService.getClientIp());
        Object result = new Object();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = joinPoint.proceed();
            String json = mapper.writeValueAsString(result);
            model.setSearchResult(json);
            model.setIsSuccessful(1);
        } catch (Throwable e) {
            model.setIsSuccessful(0);
        }
        service.save(model);
        return result;
    }
}
