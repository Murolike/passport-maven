package org.murolike.passportService.dao;

import org.murolike.passportService.models.TmpPassport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmpPassportRepository extends CrudRepository<TmpPassport, Long> {
}
