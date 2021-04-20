package pet_shop.domain;

import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.domain.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionTest {

    private Transaction transaction;
    private final Person TEST_ENTITY_PERSON = new Person(1,"a",200);
    private final Pet TEST_ENTITY_PET = new Pet(1,"b","dog",130);

    @BeforeEach
    public void setUp() {
        transaction = new Transaction(1,1);
    }

    @AfterAll
    public void tearDown() {
        transaction = null;
    }

    @Test
    public void Transaction_testGetPet_shouldGetThePet() {
        assertEquals(1, transaction.getPet(), "The pet is correct!");
    }
    @Test
    public void Transaction_testGetPerson_shouldGetThePerson() {
        assertEquals(1 , transaction.getPerson(), "The person is correct!");
    }

    @Test
    public void Transaction_testSetPet_shouldSetThePet() {
        Pet pet = new Pet(2,"ddd","cat",50);
        transaction.setPet(2);
        assertEquals(2, transaction.getPet(), "The pet is correct!");
    }
    @Test
    public void Transaction_testSetPerson_shouldSetThePerson() {
        Person person = new Person(2,"hhh",1000);
        transaction.setPerson(2);
        assertEquals(2, transaction.getPerson(), "The person is correct!");
    }

}