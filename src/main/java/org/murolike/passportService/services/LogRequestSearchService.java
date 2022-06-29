package org.murolike.passportService.services;

import org.murolike.passportService.dao.LogRequestSearchRepository;
import org.murolike.passportService.models.LogRequestSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogRequestSearchService {
    @Autowired
    protected LogRequestSearchRepository repository;

    public void save(LogRequestSearch model) {
        this.repository.save(model);
    }
}
