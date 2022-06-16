import console.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring.xml");
        try {
            Application app = new Application(context, System.out, args);
            app.run();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Не удалось запустить приложение: " + exception.getMessage());
        }
    }
}
