package pet_shop.controller;

import en.ubb.pet_shop.web.service.TransactionController;
import en.ubb.pet_shop.web.domain.Person;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.domain.Transaction;
import en.ubb.pet_shop.web.domain.validators.PersonValidator;
import en.ubb.pet_shop.web.domain.validators.PetValidator;
import en.ubb.pet_shop.web.domain.validators.TransactionValidator;
import en.ubb.pet_shop.web.repository.InMemoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionControllerTest {
    private final Person TEST_PERSON = new Person(1,"a", 100);
    private final Pet TEST_PET = new Pet(1,"b", "c", 15);
    private final Person TEST_PERSON2 = new Person(2,"a", 100);
    private final Pet TEST_PET2 = new Pet(2,"b", "c", 15);
    private TransactionController transactionController;
    private InMemoryRepository<Integer,Person> personInMemoryRepository = new InMemoryRepository<Integer,Person>(new PersonValidator());
    private InMemoryRepository<Integer,Pet> petInMemoryRepository = new InMemoryRepository<Integer, Pet>(new PetValidator());


    @BeforeEach
    public void setUp() {
        transactionController = new TransactionController(new InMemoryRepository<Integer, Transaction>(new TransactionValidator()),petInMemoryRepository,personInMemoryRepository);
    }

    @AfterAll
    public void tearDown() {
        transactionController = null;
    }

    @Test
    public void TransactionController_testGetAllTransactions_shouldGetAllTransactions() {
        personInMemoryRepository.save(TEST_PERSON);
        personInMemoryRepository.save(TEST_PERSON2);
        petInMemoryRepository.save(TEST_PET);
        petInMemoryRepository.save(TEST_PET2);
        transactionController.BuyPet(TEST_PET.getId(), TEST_PERSON.getId());
        transactionController.BuyPet(TEST_PET2.getId(), TEST_PERSON2.getId());
        var transactions = transactionController.GetAllTransactions();
        assertTrue(transactions.stream().map(Transaction::getPet).collect(Collectors.toSet()).containsAll(Arrays.asList(1, 2)), "Wrong pet id returned.");
    }

    @Test
    public void TransactionController_testRemovePerson_shouldRemoveAddedPerson()
    {
        personInMemoryRepository.save(TEST_PERSON);
        personInMemoryRepository.save(TEST_PERSON2);
        petInMemoryRepository.save(TEST_PET);
        petInMemoryRepository.save(TEST_PET2);
        transactionController.BuyPet(1, 2);
        var transaction = transactionController.GetAllTransactions().get(0);
        transactionController.RefundPet(transaction.getId());
        assertEquals(transactionController.GetAllTransactions().size(), 0, "Pet was not refunded.");
    }

    @Test
    public void TransactionController_testBuyPet_shouldBuyAPet(){
        personInMemoryRepository.save(TEST_PERSON);
        personInMemoryRepository.save(TEST_PERSON2);
        petInMemoryRepository.save(TEST_PET);
        petInMemoryRepository.save(TEST_PET2);
        transactionController.BuyPet(1, 2);
        var length = transactionController.GetAllTransactions().size();
        assertEquals(length, 1, "The pet hasn't been bought successfully!");
    }

    @Test
    public void TransactionController_testGetOwner_shouldGetTheOwner()
    {
        personInMemoryRepository.save(TEST_PERSON);
        personInMemoryRepository.save(TEST_PERSON2);
        petInMemoryRepository.save(TEST_PET);
        petInMemoryRepository.save(TEST_PET2);
        transactionController.BuyPet(1,2);
        var Owner = transactionController.GetOwner(java.util.Optional.of(TEST_PET));
        assertEquals(TEST_PERSON2, Owner, "Correct Owner.");
    }
    public void TransactionController_testGetAllPetsOfAnOwner_shouldGetListOfPets()
    {
        personInMemoryRepository.save(TEST_PERSON);
        personInMemoryRepository.save(TEST_PERSON2);
        petInMemoryRepository.save(TEST_PET);
        petInMemoryRepository.save(TEST_PET2);
        transactionController.BuyPet(1,2);
        var list = transactionController.GetAllPetsOfAnOwner(Optional.of(TEST_PERSON));
        assertTrue(list.contains(TEST_PET));
    }

}
