package services;

import db.ConnectionManager;
import models.MasterPassport;
import models.Passport;
import models.SlavePassport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PassportService {
    private final ConnectionManager connectionManager;
    private final Session session;

    public PassportService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.session = connectionManager.getSession();
    }

    public List<Passport> all() {
        List<MasterPassport> masterPassports = session.createQuery("select p from MasterPassport p", MasterPassport.class).getResultList();
        List<SlavePassport> slavePassports = session.createQuery("select p from SlavePassport p", SlavePassport.class).getResultList();
        List<Passport> passports = new ArrayList<>();
        passports.addAll(masterPassports);
        passports.addAll(slavePassports);
        return passports;
    }

    /**
     * @param series серия
     * @param number номер
     * @return Возвращает первый найденный паспорт иначе null
     * @todo убрать дублирование
     */
    public Passport search(String series, String number) {
        Query<MasterPassport> queryMaster = session.createQuery("select m from MasterPassport m where series = :series and number = :number", MasterPassport.class);
        queryMaster.setParameter("series", series);
        queryMaster.setParameter("number", number);

        queryMaster.setMaxResults(1);
        List<MasterPassport> masterPassports = queryMaster.getResultList();
        if (masterPassports.size() == 1) {
            return masterPassports.get(0);
        }
        Query<SlavePassport> querySlave = session.createQuery("select m from SlavePassport m where series = :series and number = :number", SlavePassport.class);
        querySlave.setParameter("series", series);
        querySlave.setParameter("number", number);

        querySlave.setMaxResults(1);
        List<SlavePassport> slavePassports = querySlave.getResultList();
        if (slavePassports.size() == 1) {
            return slavePassports.get(0);
        }

        return null;
    }


    public void insert(Passport model) {
        session.persist(model);
    }

    public int insert(ArrayList<Passport> models) {
        Transaction transaction = session.beginTransaction();
        int counter = 0;
        try {
            for (Passport passport : models) {
                insert(passport);
                counter++;
            }
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
            throw exception;
        }

        return counter;
    }

    public void deleteAll() {
        Transaction transaction = session.beginTransaction();
        Query query;
        try {
            query = session.createQuery("delete from MasterPassport");
            query.executeUpdate();
            query = session.createQuery("delete from SlavePassport");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
            throw exception;
        }
    }
}
