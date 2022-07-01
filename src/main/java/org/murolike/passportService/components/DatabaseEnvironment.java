package org.murolike.passportService.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseEnvironment {
    @Autowired
    private Environment environment;

    public DatabaseEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getHost() {
        String url = getUrl();
        int firstIndex = url.indexOf("/") + 2;
        int lastIndex = url.lastIndexOf(":");

        return url.substring(firstIndex, lastIndex);

    }

    public String getPort() {
        String url = getUrl();
        int firstIndex = url.lastIndexOf(":") + 1;
        int lastIndex = url.lastIndexOf("/");

        return url.substring(firstIndex, lastIndex);
    }

    public String getUserName() {
        return this.environment.getProperty("spring.datasource.username");
    }

    public String getPassword() {
        return this.environment.getProperty("spring.datasource.password");
    }

    public String getDbName() {
        String url = getUrl();
        int firstIndex = url.lastIndexOf("/") + 1;

        return url.substring(firstIndex);
    }

    private String getUrl() {
        return this.environment.getProperty("spring.datasource.url");
    }
}
