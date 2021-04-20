package en.ubb.pet_shop.client.ui;


import en.ubb.pet_shop.core.domain.Transaction;
import en.ubb.pet_shop.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
    Class which stores a menu option
 */
class MenuOption {
    String info;
    Supplier<Boolean> operation;

    /**
     * @param info description of the option
     * @param operation a function with no parameters that will perform the menu option
     */
    public MenuOption(String info, Supplier<Boolean> operation) {
        this.info = info;
        this.operation = operation;
    }

    /**
     * Runs the option.
     * @return exit flag, which tells us whether this command is going to end the program execution or not.
     */
    public boolean run() {
        return operation.get();
    }

    /**
     * @return the description of the menu option
     */
    public String getInfo() {
        return info;
    }
}

/**
 * Class which handles the console user interface
 */
@Component
public class ConsoleUI {

    @Autowired
    private RestTemplate restTemplate;

    String peopleURL = "http://localhost:8080/api/people";
    String petsURL = "http://localhost:8080/api/pets";
    String transactionsURL = "http://localhost:8080/api/transactions";

    private final Scanner sin = new Scanner(System.in);
    private Map<Integer, MenuOption> options = new HashMap<>();

    public ConsoleUI() {
        fillOptions();
    }

    /**
     * @return the menu options as a string
     */
    public String menu() {
        return options.entrySet().stream().reduce("", (str, element) -> str + element.getKey() + ") " + element.getValue().getInfo() + "\n", String::concat);
    }

    /**
     * Method which handles the removal of a person option from the menu
     * @return false, this command will not stop the execution of the program
     */
    public boolean removePerson()  {
        System.out.println("Enter a person's id: ");
        int id = sin.nextInt();
        restTemplate.delete(peopleURL + "/{id}", id);
        return false;
    }

    /**
     * Method which remove a pet from the list
     *  @return false, this command will not stop the execution of the program
     */
    public boolean removePet() {
        System.out.println("Enter a pet's id: ");
        int id = sin.nextInt();
        restTemplate.delete(petsURL + "/{id}", id);
        return false;
    }

    /**
     * Method which handles the refund of a pet from a person
     * @return false, this command will not stop the execution of the program
     */
    public boolean refundTransaction() {
        System.out.println("Enter a transaction id: ");
        int id = sin.nextInt();
        restTemplate.delete(transactionsURL + "/{id}", id);
        return false;
    }

    /**
     * Method which adds a person to the list
     *  @return false, this command will not stop the execution of the program
     */
    public boolean addPerson()  {
        System.out.println("Enter the person's name: ");
        String name = sin.next();
        System.out.println("Enter the person's budget: ");
        double budget = sin.nextDouble();
        PersonDto savedPerson = restTemplate.postForObject(peopleURL,
                new PersonDto(name,budget),
                PersonDto.class);
        System.out.println("saved person:");
        return false;
    }

    /**
     * Method which adds a person to the list
     * @return false, this command will not stop the execution of the program
     */
    public boolean addPet() {
        System.out.println("Enter the pet's name: ");
        String name = sin.next();
        System.out.println("Enter the pet's species: ");
        String species = sin.next();
        System.out.println("Enter the pet's price: ");
        double price = sin.nextDouble();
        PetDto savedPet = restTemplate.postForObject(petsURL,
                new PetDto(name,species,price),
                PetDto.class);
        System.out.println("saved pet:");
        return false;
    }

    /**
     *  Method which updates an existing Pet
     * @return false, this command will not stop the execution of the program
     */
    private Boolean updatePet()  {
        System.out.println("Enter the pet's id: ");
        int petId = Integer.parseInt(sin.next());
        System.out.println("Enter the pet's name: ");
        String name = sin.next();
        System.out.println("Enter the pet's species: ");
        String species = sin.next();
        System.out.println("Enter the pet's price: ");
        double price = sin.nextDouble();
//            Optional<Pet> petO = petController.getAllPets().stream().filter((p) -> p.getId() == petId).findFirst();
//            petO.ifPresentOrElse(pet -> {
//                try {
//                    Pet pet1 = new Pet(petId,name,species,price);
//                    petController.updatePet(pet1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }, null);
        PetDto petDto = new PetDto(name,species,price);
        petDto.setId(petId);
        restTemplate.put(petsURL + "/{id}", petDto, petDto.getId());
        return false;
    }

    /**
     * Method which registers the transaction between a pet and a person
     * @return false, this command will not stop the execution of the program
     */
    public boolean buyPet()  {
        System.out.println("Enter the pet's id: ");
        int petId = Integer.parseInt(sin.next());
        System.out.println("Enter the person's id: ");
        int personId = Integer.parseInt(sin.next());
//            Optional<Pet> petO = petController.getAllPets().stream().filter((p) -> p.getId() == petId).findFirst();
//            Optional<Person> personO = personController.GetAllPeople().stream().filter((p) -> p.getId() == personId).findFirst();
//            petO.ifPresentOrElse((pet) -> {
//                personO.ifPresentOrElse((person) -> {
//                    try {
//                        Transaction transaction = new Transaction(-1, pet.getId(),person.getId());
//                        transactionController.BuyPet(transaction);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }, null);
//            }, null);
        TransactionDto transactionDto = restTemplate.postForObject(transactionsURL, new Transaction(petId,personId), TransactionDto.class);
        System.out.println("saved transaction:");

        return false;
    }

    /**
     * Method which prints a list with all the person entities stored by the program
     * @return false, this command will not stop the execution of the program
     */
    public boolean getAllPeople()  {
        MultiPersonDto people = restTemplate.getForObject(peopleURL, MultiPersonDto.class);
        System.out.println(people.toString());
        return false;
    }

    /**
     * Method which prints a list with all the pets entities stored by the program
     * @return false, this command will not stop the execution of the program
     *
     */
    public boolean getAllPets()  {
        MultiPetDto pets = restTemplate.getForObject(petsURL, MultiPetDto.class);

        System.out.println(pets);
        return false;
    }

    /**
     * Method which prints a list with all the transactions stored by the program
     * @return false, this command will not stop the execution of the program
     *
     */
    public boolean getAllTransactions()  {
        MultiTransactionDto transactions = restTemplate.getForObject(transactionsURL, MultiTransactionDto.class);
        System.out.println(transactions);
        return false;
    }

    /**
     * Method which gets an Owner of a pet.
     * @return false, this command will not stop the execution of the program
     */
    public boolean getOwner()  {
        System.out.println("Enter the pet's id: ");
        int petId = Integer.parseInt(sin.next());
        MultiTransactionDto transactions = restTemplate.getForObject(transactionsURL + "/owner/{petId}",MultiTransactionDto.class,petId);
        System.out.println(transactions);
        return false;
    }

    /**
     * Method which modifies data about a person.
     * @return false, this command will not stop the execution of the program
     */
    public  boolean updatePerson()  {
        System.out.println("Enter the person's id: ");
        int personId = Integer.parseInt(sin.next());
        System.out.println("Enter the person's name: ");
        String name = sin.next();
        System.out.println("Enter the person's budget: ");
        double budget = sin.nextDouble();
        PersonDto personDto = new PersonDto(name,budget);
        personDto.setId(personId);
        restTemplate.put(peopleURL + "/{id}", personDto, personDto.getId());
        return false;
    }
  /**
     * Method which prints a list with all the pets of the owner with the id given by the user
     * @return false, this command will not stop the execution of the program
     */
    private Boolean getAllPetsOfAnOwner()  {
        System.out.println("Enter the person's id: ");
        int personId = Integer.parseInt(sin.next());
//            Optional<Person> person = personController.GetAllPeople().stream().filter((p) -> p.getId() == personId).findFirst();
//            person.ifPresentOrElse(pet -> {
//                try {
//                    System.out.println(transactionController.GetAllPetsOfAnOwner(personId).stream().reduce("", (str, element) -> str + element, String::concat));
//                } catch (IOException | ExecutionException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }, null);
        MultiTransactionDto transactions = restTemplate.getForObject(transactionsURL + "/owner-filter/{personId}",MultiTransactionDto.class,personId);
        System.out.println(transactions);
        return false;
    }

    /**
     * Method which prints a list with all the persons with a given minimum budget.
     * @return false, this command will not stop the execution of the program
     */
    public boolean getAllPeopleWithABudgetGreaterOrEqual()  {
        System.out.println("Enter the person's minimum budget: ");
        double budget = sin.nextDouble();
        MultiPersonDto personDto = restTemplate.getForObject(peopleURL + "/budget-filter/{budget}",MultiPersonDto.class,budget);
        System.out.println(personDto);
        return false;
    }

  /**
     * Method which prints a list of all pets of a given species
     * @return false, this command will not stop the execution of the program
     */
    private Boolean getAllPetsOfGivenSpecies()  {
        System.out.println("Enter the pet's species: ");
        String species = sin.next();
        MultiPetDto petDto = restTemplate.getForObject(peopleURL + "species-filter/{species}",MultiPetDto.class,species);
        System.out.println(petDto);
        return false;
    }

    /**
     * Method which fills the options map with the option number and the method to execute
     */
    public void fillOptions() {

        options.put(0, new MenuOption("Exit",() -> true));
        options.put(1, new MenuOption("Get all people", this::getAllPeople));
        options.put(2, new MenuOption("Get all pets", this::getAllPets));
        options.put(3, new MenuOption("Get all transactions", this::getAllTransactions));
        options.put(4, new MenuOption("Add a person", this::addPerson));
        options.put(5, new MenuOption("Add a pet", this::addPet));
        options.put(6, new MenuOption("Buy a pet for a person", this::buyPet));
        options.put(7, new MenuOption("Remove a person", this::removePerson));
        options.put(8, new MenuOption("Remove a pet", this::removePet));
        options.put(9, new MenuOption("Refund a pet for a person", this::refundTransaction));
        options.put(10, new MenuOption("Get owner", this::getOwner));
        options.put(11, new MenuOption("Update person", this::updatePerson));
        options.put(12, new MenuOption("Get All People With A Budget Greater Or Equal", this::getAllPeopleWithABudgetGreaterOrEqual));
        options.put(13, new MenuOption("Update a pet", this::updatePet));
        options.put(14, new MenuOption("Get all pets of an owner", this::getAllPetsOfAnOwner));
        options.put(15, new MenuOption("Get all pets of a given species", this::getAllPetsOfGivenSpecies));

    }


    /**
     * Method which displays the menu and executes the options
     *
     */
    public void run() {
        int option;
        while (true) {
            System.out.println(menu());
            System.out.println("Enter an option: ");
            option = sin.nextInt();
            if (options.get(option) == null) {
                System.out.println("Invalid option!");
            } else {
                try
                {
                    if (options.get(option).run())
                        break;
                }
                catch (RuntimeException ex)
                {
                    System.out.println(ex.getMessage());
                }

            }
        }
    }
}
