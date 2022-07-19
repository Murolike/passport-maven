package org.murolike.passportService.components.pg;

import java.util.Set;

/**
 * Класс для генерации PgConfiguration
 */
public class PgConfigurationBuilder {

    protected PgConfiguration configuration;

    public PgConfigurationBuilder create() {
        configuration = new PgConfiguration();
        return this;
    }

    public PgConfigurationBuilder host(String host) {
        configuration.setHost(host);
        return this;
    }

    public PgConfigurationBuilder port(String port) {
        configuration.setPort(port);
        return this;
    }

    public PgConfigurationBuilder username(String username) {
        configuration.setUsername(username);
        return this;
    }

    public PgConfigurationBuilder password(String password) {
        configuration.setPassword(password);
        return this;
    }

    public PgConfigurationBuilder dbName(String dbName) {
        configuration.setDbName(dbName);
        return this;
    }

    public PgConfigurationBuilder tableName(String tableName) {
        configuration.setTableName(tableName);
        return this;
    }

    public PgConfigurationBuilder columns(Set<String> columns) {
        configuration.setColumns(columns);
        return this;
    }

    public PgConfiguration build() {
        return configuration;
    }
}
