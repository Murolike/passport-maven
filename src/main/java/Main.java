import console.Application;

public class Main {
    public static void main(String[] args) {
        try {
            Application app = new Application(args, System.out);
            app.run();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Не удалось запустить приложение:" + exception.getMessage());
        }
    }
}
