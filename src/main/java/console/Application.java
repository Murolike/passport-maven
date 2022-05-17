package console;

import controllers.Controller;
import db.ConnectionManager;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class Application {
    private final String[] args;
    private final PrintStream printStream;

    protected Request request;
    protected ConnectionManager connectionManager;

    public Application(String[] args, PrintStream printStream) throws SQLException {
        this.args = args;
        this.printStream = printStream;

        this.loadComponents();
    }

    protected void loadComponents() throws SQLException {
        this.loadRequest();
        this.loadConnectionManager();
    }

    protected void loadRequest() {
        this.request = new Request(args);
    }

    protected void loadConnectionManager() throws SQLException {
        connectionManager = new ConnectionManager("jdbc:postgresql://localhost:5432/fms", "postgres", "postgres");
    }

    public void run() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Controller controller = this.createController();
        this.runAction(controller);
    }

    /**
     * @return Controller
     * @throws ClassNotFoundException    Если контроллер не найден
     * @throws InvocationTargetException Если не удалось создать объект
     * @throws InstantiationException    Если не удалось создать объект
     * @throws IllegalAccessException    Нарушение прав доступа при создании объекта
     * @todo убрать строгую привязку к packages
     */
    public Controller createController() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String controllerName = this.request.getControllerName();
        String symbol = controllerName.substring(0, 1);

        controllerName = "controllers." + symbol.toUpperCase() + controllerName.substring(1) + "Controller";

        Class<?> classController = Class.forName(controllerName);

        return (Controller) classController.getDeclaredConstructors()[0].newInstance(this.connectionManager);
    }

    public void runAction(Controller controller) throws InvocationTargetException, IllegalAccessException {
        String action = this.request.getActionName();
        Object[] parameters = this.request.getParameters();
        Method[] methods = controller.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(action)) {
                Object output = method.invoke(controller, parameters);
                printStream.println(output);
            }
        }
    }

}
