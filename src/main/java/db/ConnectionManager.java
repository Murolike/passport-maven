package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private final String url;
    private final String user;
    private final String password;

    private Connection connection;

    public ConnectionManager(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;

        this.initConnection();

    }

    private void initConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", this.user);
        properties.setProperty("password", this.password);
        properties.setProperty("ssl", "false");

        this.connection = DriverManager.getConnection(this.url, properties);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
