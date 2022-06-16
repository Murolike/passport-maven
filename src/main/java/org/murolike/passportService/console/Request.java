package org.murolike.passportService.console;

import org.springframework.stereotype.Component;

public class Request {
    private final String route;
    private final String[] args;

    public Request(String[] args) {
        this.route = args[0];
        this.args = args;
    }

    public String getControllerName() {
        return route.substring(0, route.indexOf("/"));
    }

    public String getActionName() {
        return route.substring(route.indexOf("/") + 1);
    }

    public Object[] getParameters() {
        Object[] parameters = new Object[args.length - 1];
        for (int i = 0, j = 1; j < args.length; j++, i++) {
            parameters[i] = args[j];
        }
        return parameters;
    }
}
