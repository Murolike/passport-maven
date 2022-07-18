package org.murolike.passportService.dao;

import org.murolike.passportService.models.SlavePassport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlavePassportRepository extends CrudRepository<SlavePassport, Long> {
    Iterable<SlavePassport> findAllBySeriesAndNumber(String series, String number);

    /**
     * Удалить все записи из таблицы invalid_passports_master, что не существуют в tmp_passports
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from invalid_passports_slave where id in (select  ip.id from invalid_passports_slave ip\n" +
            "            left join tmp_passports tp on tp.series = ip.series and tp.number = ip.number\n" +
            "            where tp.series is null and tp.number is null)")
    void deleteNotExistingInLoadingTable();

    /**
     * Вставить не существующие записи в таблице invalid_passports_master из таблицы tmp_passports
     */
    @Modifying
    @Query(nativeQuery = true, value =
            "insert into invalid_passports_slave (series,number,created_at,was_in_file_from_date) select  tp.series,tp.number,now(),now() from invalid_passports_slave ip\n" +
                    "            right join tmp_passports tp on tp.series = ip.series and tp.number = ip.number\n" +
                    "            where ip.id is null")
    void insertNotExistingDataFromLoadingTable();

    /**
     * Обновить дату существующих записей в таблице invalid_passports_master и tmp_passports
     */
    @Modifying
    @Query(nativeQuery = true, value = "update invalid_passports_slave set was_in_file_from_date = now() ")
    void updateLastDateOnFileExistDataFromLoadingTable();

    @Modifying
    @Query(nativeQuery = true, value = "vacuum (analyze) invalid_passports_slave")
    void vacuumTable();
}
