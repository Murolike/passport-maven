package org.murolike.passportService.dao;

import org.murolike.passportService.models.MasterPassport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterPassportRepository extends CrudRepository<MasterPassport, Long> {
    Iterable<MasterPassport> findBySeriesAndNumber(String series, String number);
}
