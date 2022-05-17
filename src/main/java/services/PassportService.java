package services;

import db.ConnectionManager;
import models.Passport;

import java.sql.*;
import java.util.ArrayList;

public class PassportService {
    private final ConnectionManager connectionManager;
    private final Connection connection;

    public PassportService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.connection = connectionManager.getConnection();
    }

    public ArrayList<Passport> all() throws SQLException {
        ArrayList<Passport> passports = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select series, number, to_char(created_at,'dd.mm.YYYY HH24:mi:ss') as created_at from invalid_passports_master limit 10");

        while (resultSet.next()) {
            Passport passport = new Passport(resultSet.getString("series"), resultSet.getString("number"), resultSet.getString("created_at"));
            passports.add(passport);
        }
        return passports;
    }

    public Passport search(String serial, String number) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select series, number, to_char(created_at,'dd.mm.YYYY HH24:mi:ss') as created_at from invalid_passports_master where  series = ? and number = ? LIMIT 1");
        statement.setString(1, serial);
        statement.setString(2, number);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Passport passport = new Passport(resultSet.getString("series"), resultSet.getString("number"), resultSet.getString("created_at"));
            statement.close();
            resultSet.close();

            return passport;
        }
        return null;
    }

    public boolean insert(Passport model) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into invalid_passports_master(series,number) VALUES (?,?)");
        statement.setString(1, model.getSerial());
        statement.setString(2, model.getNumber());

        int result = statement.executeUpdate();
        return result > 0;
    }

    public int insert(ArrayList<Passport> models) throws SQLException {
        connection.setAutoCommit(false);
        int counter = 0;
        try {
            for (Passport passport : models) {
                if (insert(passport)) {
                    counter++;
                    continue;
                }
                throw new SQLException("Не удалось вставить запись");
            }
            connection.commit();
        } catch (Exception exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw exception;
        }

        return counter;
    }

    public void deleteAll() throws SQLException {
        connection.createStatement().execute("delete from invalid_passports_master");
    }
}
