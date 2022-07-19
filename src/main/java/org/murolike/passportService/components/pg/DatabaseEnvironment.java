package org.murolike.passportService.components.pg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Компонент для быстрого получения данных по подключению к СУБД
 * Нужен для работы PgLoader
 */
@Component
public class DatabaseEnvironment {
    @Autowired
    private final Environment environment;

    public DatabaseEnvironment(Environment environment) {
        this.environment = environment;
    }


    /**
     * Получить хост
     *
     * @return Возвращает адрес для подключения
     */
    public String getHost() {
        String url = getUrl();
        int firstIndex = url.indexOf("/") + 2;
        int lastIndex = url.lastIndexOf(":");

        return url.substring(firstIndex, lastIndex);

    }

    /**
     * Получить порт
     *
     * @return Возвращает порт
     */
    public String getPort() {
        String url = getUrl();
        int firstIndex = url.lastIndexOf(":") + 1;
        int lastIndex = url.lastIndexOf("/");

        return url.substring(firstIndex, lastIndex);
    }

    /**
     * Получить имя пользователя
     *
     * @return Возвращает имя пользователя
     */
    public String getUserName() {
        return this.environment.getProperty("spring.datasource.username");
    }

    /**
     * Получить пароль от СУБД
     *
     * @return Возвращает пароль для подключения к СУБД
     */
    public String getPassword() {
        return this.environment.getProperty("spring.datasource.password");
    }

    /**
     * Получить имя БД
     *
     * @return Возвращает имя БД
     */
    public String getDbName() {
        String url = getUrl();
        int firstIndex = url.lastIndexOf("/") + 1;

        return url.substring(firstIndex);
    }

    /**
     * Получить url для подключения
     *
     * @return Возвращает url для подключения
     */
    private String getUrl() {
        return this.environment.getProperty("spring.datasource.url");
    }
}
