package en.ubb.pet_shop.web;

import en.ubb.pet_shop.common.domain.Person;
import en.ubb.pet_shop.common.domain.Pet;
import en.ubb.pet_shop.common.domain.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("unchecked")
public class ServerApp {
    private static final int PORT = 1234;
    public static void main(String[] args) {
        System.out.println("Server starting...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("en.ubb.pet_shop");


    }
}
