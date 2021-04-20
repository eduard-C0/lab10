package en.ubb.pet_shop.web.ui;

import en.ubb.pet_shop.web.service.PersonController;
import en.ubb.pet_shop.web.service.PetController;
import en.ubb.pet_shop.web.service.TransactionController;
import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.domain.Pet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

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
public class ConsoleUI {
    private final PersonController personController;
    private final PetController petController;
    private final TransactionController transactionController;
    private final Scanner sin = new Scanner(System.in);
    private Map<Integer, MenuOption> options = new HashMap<>();

    /**
     *
     * @param personController the controller that this menu is going to manage
     */
    public ConsoleUI(PersonController personController, PetController petController, TransactionController transactionController) {
        this.personController = personController;
        this.petController = petController;
        this.transactionController = transactionController;
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
    public boolean removePerson() {
        System.out.println("Enter a person's id: ");
        int id = sin.nextInt();
        personController.RemovePerson(id);
        return false;
    }

    /**
     * Method which remove a pet from the list
     *  @return false, this command will not stop the execution of the program
     */
    public boolean removePet()
    {
        System.out.println("Enter a pet's id: ");
        int id = sin.nextInt();
        petController.RemovePet(id);
        return false;
    }

    /**
     * Method which handles the refund of a pet from a person
     * @return false, this command will not stop the execution of the program
     */
    public boolean refundTransaction() {
        System.out.println("Enter a transaction id: ");
        int id = sin.nextInt();
        transactionController.RefundPet(id);
        return false;
    }

    /**
     * Method which adds a person to the list
     *  @return false, this command will not stop the execution of the program
     */
    public boolean addPerson()
    {
        System.out.println("Enter the person's name: ");
        String name = sin.next();
        System.out.println("Enter the person's budget: ");
        double price = sin.nextDouble();
        personController.AddPerson(name,price);
        return false;
    }

    /**
     * Method which adds a person to the list
     * @return false, this command will not stop the execution of the program
     */
    public boolean addPet()
    {
        System.out.println("Enter the pet's name: ");
        String name = sin.next();
        System.out.println("Enter the pet's species: ");
        String species = sin.next();
        System.out.println("Enter the pet's price: ");
        double price = sin.nextDouble();
        petController.AddPet(name,species,price);
        return false;
    }

    /**
     *  Method which updates an existing Pet
     * @return false, this command will not stop the execution of the program
     */
    private Boolean updatePet()
    {
        System.out.println("Enter the pet's id: ");
        int petId = Integer.parseInt(sin.next());
        System.out.println("Enter the pet's name: ");
        String name = sin.next();
        System.out.println("Enter the pet's species: ");
        String species = sin.next();
        System.out.println("Enter the pet's price: ");
        double price = sin.nextDouble();
        Optional<Pet> petO = petController.GetAllPets().stream().filter((p) -> p.getId() == petId).findFirst();
        petO.ifPresentOrElse(pet->{petController.UpdatePet(petId,name,species,price);},null);
        return false;
    }

    /**
     * Method which registers the transaction between a pet and a person
     * @return false, this command will not stop the execution of the program
     */
    public boolean buyPet()
    {
        System.out.println("Enter the pet's id: ");
        int petId = Integer.parseInt(sin.next());
        System.out.println("Enter the person's id: ");
        int personId = Integer.parseInt(sin.next());
        Optional<Pet> petO = petController.GetAllPets().stream().filter((p) -> p.getId() == petId).findFirst();
        Optional<Person> personO = personController.GetAllPeople().stream().filter((p) -> p.getId() == personId).findFirst();
        petO.ifPresentOrElse((pet) -> {
            personO.ifPresentOrElse((person) -> {
                transactionController.BuyPet(pet.getId(), person.getId());
            }, null);
        }, null);

        return false;
    }

    /**
     * Method which prints a list with all the person entities stored by the program
     * @return false, this command will not stop the execution of the program
     */
    public boolean getAllPeople() {
        System.out.println(personController.GetAllPeople().stream().reduce("", (str, element) -> str + element, String::concat));
        return false;
    }

    /**
     * Method which prints a list with all the pets entities stored by the program
     * @return false, this command will not stop the execution of the program
     *
     */
    public boolean getAllPets()
    {
        System.out.println(petController.GetAllPets().stream().reduce("", (str, element) -> str + element, String::concat));
        return false;
    }

    /**
     * Method which prints a list with all the transactions stored by the program
     * @return false, this command will not stop the execution of the program
     *
     */
    public boolean getAllTransactions()
    {
        System.out.println(transactionController.GetAllTransactions().stream().reduce("", (str, element) -> str + element, String::concat));
        return false;
    }

    /**
     * Method which gets an Owner of a pet.
     * @return false, this command will not stop the execution of the program
     */
    public boolean getOwner()
    {
        System.out.println("Enter the pet's id: ");
        int petId = Integer.parseInt(sin.next());
        System.out.println(transactionController.GetOwner(petController.GetAllPets().stream().filter((p) -> p.getId() == petId).findFirst()));
        return false;
    }

    /**
     * Method which modifies data about a person.
     * @return false, this command will not stop the execution of the program
     */
    public  boolean updatePerson()
    {
        System.out.println("Enter the person's id: ");
        int personId = Integer.parseInt(sin.next());
        System.out.println("Enter the person's name: ");
        String name = sin.next();
        System.out.println("Enter the person's budget: ");
        double budget = sin.nextDouble();
        personController.UpdatePerson(personId,name,budget);
        return false;
    }
  /**
     * Method which prints a list with all the pets of the owner with the id given by the user
     * @return false, this command will not stop the execution of the program
     */
    private Boolean getAllPetsOfAnOwner()
    {
        System.out.println("Enter the person's id: ");
        int personId = Integer.parseInt(sin.next());
        Optional<Person> person = personController.GetAllPeople().stream().filter((p) -> p.getId() == personId).findFirst();
        person.ifPresentOrElse(pet->{
            System.out.println(transactionController.GetAllPetsOfAnOwner(person).stream().reduce("", (str, element) -> str + element, String::concat));},null);
        return false;
    }

    /**
     * Method which prints a list with all the persons with a given minimum budget.
     * @return false, this command will not stop the execution of the program
     */
    public boolean getAllPeopleWithABudgetGreaterOrEqual()
    {
        System.out.println("Enter the person's minimum budget: ");
        double budget = sin.nextDouble();
        System.out.println(personController.GetAllPeopleWithABudgetGreaterOrEqual(budget));
        return false;
    }

  /**
     * Method which prints a list of all pets of a given species
     * @return false, this command will not stop the execution of the program
     */
    private Boolean getAllPetsOfGivenSpecies()
    {
        System.out.println("Enter the pet's species: ");
        String species = sin.next();
        System.out.println(petController.FilterPetBySpecies(species).stream().reduce("", (str, element) -> str + element, String::concat));
        return false;
    }

    /**
     * Method which fills the options map with the option number and the method to execute
     */
    public void fillOptions() {
        options.put(0, new MenuOption("Exit",() -> true));
        options.put(1, new MenuOption("Get all people", this::getAllPeople));
        options.put(2, new MenuOption("Get all pets",this::getAllPets));
        options.put(3, new MenuOption("Get all transactions",this::getAllTransactions));
        options.put(4, new MenuOption("Add a person",this::addPerson));
        options.put(5, new MenuOption("Add a pet",this::addPet));
        options.put(6, new MenuOption("Buy a pet for a person",this::buyPet));
        options.put(7, new MenuOption("Remove a person", this::removePerson));
        options.put(8, new MenuOption("Remove a pet",this::removePet));
        options.put(9, new MenuOption("Refund a pet for a person",this::refundTransaction));
        options.put(10, new MenuOption("Get owner",this::getOwner));
        options.put(11, new MenuOption("Update person",this::updatePerson));
        options.put(12, new MenuOption("Get All People With A Budget Greater Or Equal",this::getAllPeopleWithABudgetGreaterOrEqual));
        options.put(13,new MenuOption("Update a pet",this::updatePet));
        options.put(14,new MenuOption("Get all pets of an owner",this::getAllPetsOfAnOwner));
        options.put(15,new MenuOption("Get all pets of a given species",this::getAllPetsOfGivenSpecies));
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
