package org.murolike.passportService.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.murolike.passportService.models.LogRequestSearch;
import org.murolike.passportService.services.LogRequestSearchService;
import org.murolike.passportService.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
abstract public class LogRequestSearchBehavior {

    @Autowired
    protected LogRequestSearchService service;

    @Autowired
    protected RequestService requestService;

    abstract public void search(String series, String number);

    abstract protected String getSearchInTable();

    @Around("search(series, number)")
    public Object log(ProceedingJoinPoint joinPoint, String series, String number) {
        LogRequestSearch model = new LogRequestSearch(series, number, requestService.getClientIp(), getSearchInTable());
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
