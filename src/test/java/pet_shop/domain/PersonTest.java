package pet_shop.domain;

import en.ubb.pet_shop.web.domain.Person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonTest {

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person("jack",200);
    }

    @AfterAll
    public void tearDown() {
        person = null;
    }

    @Test
    public void Person_testGetName_shouldGetTheName() {
        assertEquals("jack", person.getName(), "The name is correct!");
    }

    @Test
    public void Person_testGetBudget_shouldGetTheBudget() {
        assertEquals(200, person.getBudget(),1e-15, "The budget is correct!");
    }
    @Test
    public void Person_testSetName_shouldSetTheName() {
        person.setName("abc");
        assertEquals("abc", person.getName(), "The name is correct!");
    }

    @Test
    public void Person_testSetBudget_shouldSetTheBudget() {
        person.setBudget(100);
        assertEquals(100, person.getBudget(),1e-15, "The budget is correct!");
    }

}

