package en.ubb.pet_shop.client;

import en.ubb.pet_shop.client.ui.ConsoleUI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("en.ubb.pet_shop.client.config");

        ConsoleUI consoleUI = context.getBean(ConsoleUI.class);
        consoleUI.run();

        System.out.println("bye");

    }
}
