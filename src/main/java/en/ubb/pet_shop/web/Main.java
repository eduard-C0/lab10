package en.ubb.pet_shop.web;

import en.ubb.pet_shop.web.service.PersonController;
import en.ubb.pet_shop.web.service.PetController;
import en.ubb.pet_shop.web.service.TransactionController;
import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.domain.Transaction;
import en.ubb.pet_shop.domain.validators.*;
import en.ubb.pet_shop.web.repository.BaseFileRepository;
import en.ubb.pet_shop.web.repository.IRepository;
import en.ubb.pet_shop.web.repository.InMemoryRepository;
import en.ubb.pet_shop.web.repository.database.DatabaseRepository;
import en.ubb.pet_shop.web.repository.csv_file.CsvRepository;
import en.ubb.pet_shop.web.repository.file.XmlRepository;
import en.ubb.pet_shop.web.ui.ConsoleUI;
import en.ubb.pet_shop.web.domain.validators.PersonValidator;
import en.ubb.pet_shop.web.domain.validators.PetValidator;
import en.ubb.pet_shop.web.domain.validators.TransactionValidator;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner sin = new Scanner(System.in);
        System.out.println("Please select the type of repo you want");
        System.out.println("1.Database Repo");
        System.out.println("2.In memory");
        System.out.println("3.Csv Repo");
        System.out.println("4.Xml Repo");
        IRepository<Integer, Pet> repoPet;
        IRepository<Integer, Person> repoPerson;
        IRepository<Integer, Transaction> repoTransaction;
        BaseFileRepository<Integer,Pet> filePet;
        BaseFileRepository<Integer,Person> filePerson;
        BaseFileRepository<Integer,Transaction> fileTransaction;
        int id = sin.nextInt();
        switch (id)
        {
            case 1:
            {
                filePet = new DatabaseRepository<>(new PetValidator(),"Pet",Pet.class);
                filePerson = new DatabaseRepository<>(new PersonValidator(),"Person",Person.class);
                fileTransaction= new DatabaseRepository<>(new TransactionValidator(),"Transaction",Transaction.class);
                PetController petController = new PetController(filePet);
                PersonController personController = new PersonController(filePerson);
                TransactionController transactionController = new TransactionController(fileTransaction,filePet,filePerson);
                ConsoleUI consoleUI = new ConsoleUI(personController, petController, transactionController);
                consoleUI.run();
                filePet.saveData();
                filePerson.saveData();
                fileTransaction.saveData();
                break;

            }
            case 2:
            {
                repoPet= new InMemoryRepository<Integer,Pet>(new PetValidator());
                repoPerson=  new InMemoryRepository<Integer,Person>(new PersonValidator());
                repoTransaction = new InMemoryRepository<Integer,Transaction>(new TransactionValidator());
                PetController petController = new PetController(repoPet);
                PersonController personController = new PersonController(repoPerson);
                TransactionController transactionController = new TransactionController(repoTransaction,repoPet,repoPerson);
                ConsoleUI consoleUI = new ConsoleUI(personController, petController, transactionController);
                consoleUI.run();

                break;
            }
            case 3:
            {
                filePerson= new CsvRepository<Integer,Person>(new PersonValidator(),"data/test/testCsvPerson.txt",Person.class);
                filePet = new  CsvRepository<Integer,Pet>(new PetValidator(),"data/test/testCsvPet.txt",Pet.class);
                fileTransaction = new CsvRepository<Integer,Transaction>(new TransactionValidator(),"data/test/testCsvTransaction.txt",Transaction.class);
                PetController petController = new PetController(filePet);
                PersonController personController = new PersonController(filePerson);
                TransactionController transactionController = new TransactionController(fileTransaction,filePet,filePerson);
                ConsoleUI consoleUI = new ConsoleUI(personController, petController, transactionController);
                consoleUI.run();
                filePerson.saveData();
                filePet.saveData();
                fileTransaction.saveData();
                break;
            }
            case 4:
            {   filePerson = new XmlRepository<>(new PersonValidator(), "data/test/testPerson.txt", Person.class);
                filePet= new XmlRepository<>(new PetValidator(), "data/test/testPet.txt", Pet.class);
                fileTransaction = new XmlRepository<>(new TransactionValidator(), "data/test/testTransaction.txt", Transaction.class);
                PetController petController = new PetController(filePet);
                PersonController personController = new PersonController(filePerson);
                TransactionController transactionController = new TransactionController(fileTransaction,filePet,filePerson);
                ConsoleUI consoleUI = new ConsoleUI(personController, petController, transactionController);
                consoleUI.run();
                filePet.saveData();
                filePerson.saveData();
                fileTransaction.saveData();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }




//        XmlRepository<Integer, Person> personFileRepository = new XmlRepository<>(new PersonValidator(), "data/test/testPerson.txt", Person.class);
//        XmlRepository<Integer, Pet> petFileRepository = new XmlRepository<>(new PetValidator(), "data/test/testPet.txt", Pet.class);
//        XmlRepository<Integer, Transaction> transactionFileRepository = new XmlRepository<>(new TransactionValidator(), "data/test/testTransaction.txt", Transaction.class);


//       DatabaseRepository<Integer,Pet> petDatabaseRepository = new DatabaseRepository<>(new PetValidator(),"Pet",Pet.class);
//       DatabaseRepository<Integer,Person> personDatabaseRepository = new DatabaseRepository<>(new PersonValidator(),"Person",Person.class);
//       DatabaseRepository<Integer,Transaction> transactionDatabaseRepository = new DatabaseRepository<>(new TransactionValidator(),"Transaction",Transaction.class);

//        CsvRepository<Integer,Person> personCsvRepository = new CsvRepository<>(new PersonValidator(),"data/test/testCsvPerson.txt",Person.class);
//        personCsvRepository.save(new Person("gigi",230));
//        personCsvRepository.save(new Person("luigi",300));
//        personCsvRepository.saveData();
//        CsvRepository<Integer,Pet> petCsvRepository = new CsvRepository<>(new PetValidator(),"data/test/testCsvPet.txt",Pet.class);
//        petCsvRepository.save(new Pet("pufi","dog",100));
//        petCsvRepository.saveData();
//        CsvRepository<Integer,Transaction> transactionCsvRepository = new CsvRepository<>(new TransactionValidator(),"data/test/testCsvTransaction.txt",Transaction.class);


//        InMemoryRepository<Integer, Pet> petInMemoryRepository = new InMemoryRepository<>(new PetValidator());
//        InMemoryRepository<Integer, Person> personInMemoryRepository = new InMemoryRepository<>(new PersonValidator());
//        InMemoryRepository<Integer, Transaction> transactionInMemoryRepository = new InMemoryRepository<>(new TransactionValidator());



//        PetController petController = new PetController(repoPet);
//        PersonController personController = new PersonController(repoPerson);
//        TransactionController transactionController = new TransactionController(repoTransaction,repoPet,repoPerson);
//        ConsoleUI consoleUI = new ConsoleUI(personController, petController, transactionController);
//        consoleUI.run();

//        transactionDatabaseRepository.saveData();
//        personDatabaseRepository.saveData();
//        petDatabaseRepository.saveData();

//        personFileRepository.saveData();
//        petFileRepository.saveData();
//        transactionFileRepository.saveData();

//        personCsvRepository.saveData();
//        petCsvRepository.saveData();
//        transactionCsvRepository.saveData();



    }
}
