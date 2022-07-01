package org.murolike.passportService.dao;

import org.murolike.passportService.models.MasterPassport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterPassportRepository extends CrudRepository<MasterPassport, Long> {
    Iterable<MasterPassport> findAllBySeriesAndNumber(String series, String number);

    /**
     * Удалить все записи из таблицы invalid_passports_master, что не существуют в tmp_passports
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from invalid_passports_master m_ips \n" +
            "where m_ips.id in  (\n" +
            "select ips.id from invalid_passports_slave ips\n" +
            "full join tmp_passports tp \n" +
            "    on tp.series = ips.series \n" +
            "    and tp.number = ips.number\n" +
            "where\n" +
            "tp.series is null and tp.number is null)")
    void deleteNotExistingInLoadingTable();

    /**
     * Вставить не существующие записи в таблице invalid_passports_master из таблицы tmp_passports
     */
    @Modifying
    @Query(nativeQuery = true, value = "insert into invalid_passports_master (series,\"number\")  \n" +
            "select tp.series,tp.number from invalid_passports_slave ips\n" +
            "full join tmp_passports tp on tp.series = ips.series and tp.number = ips.number\n" +
            "where\n" +
            "ips.id is null")
    void insertNotExistingDataFromLoadingTable();

    /**
     * Обновить дату существующих записей в таблице invalid_passports_master и tmp_passports
     */
    @Modifying
    @Query(nativeQuery = true, value = "update invalid_passports_master m_ips set was_in_file_from_date = now() where m_ips.id in (select ips.id from invalid_passports_slave ips\n" +
            "full join tmp_passports tp on tp.series = ips.series and tp.number = ips.number\n" +
            "where\n" +
            "ips.id is not null and tp.series is not null and tp.number is not null)")
    void updateLastDateOnFileExistDataFromLoadingTable();
}
