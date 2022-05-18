package services;

import db.ConnectionManager;
import models.InvalidPassportMaster;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassportService {
    private final ConnectionManager connectionManager;
    private final Session session;

    public PassportService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.session = connectionManager.getSession();
    }

    public List<InvalidPassportMaster> all() {
        return session.createQuery("select m from InvalidPassportMaster m", InvalidPassportMaster.class).getResultList();
    }

    public InvalidPassportMaster search(String series, String number) {
        Query<InvalidPassportMaster> query = session.createQuery("select m from InvalidPassportMaster m where series = :series and number = :number", InvalidPassportMaster.class);
        query.setParameter("series", series);
        query.setParameter("number", number);

        query.setMaxResults(1);
        List<InvalidPassportMaster> passports = query.getResultList();
        if (passports.size() == 1) {
            return passports.get(0);
        }
        return null;
    }

    public void insert(InvalidPassportMaster model) {
        session.persist(model);
    }

    public int insert(ArrayList<InvalidPassportMaster> models) {
        Transaction transaction = session.beginTransaction();
        int counter = 0;
        try {
            for (InvalidPassportMaster passport : models) {
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
        try {
            Query query = session.createQuery("delete from InvalidPassportMaster");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        }
    }
}
