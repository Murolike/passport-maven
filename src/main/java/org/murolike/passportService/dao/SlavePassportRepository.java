package org.murolike.passportService.dao;

import org.murolike.passportService.models.SlavePassport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlavePassportRepository extends CrudRepository<SlavePassport, Long> {
    Iterable<SlavePassport> findBySeriesAndNumber(String series, String number);
}
