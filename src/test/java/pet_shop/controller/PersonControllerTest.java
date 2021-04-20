package pet_shop.controller;

import en.ubb.pet_shop.web.service.PersonController;
import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.domain.validators.PersonValidator;
import en.ubb.pet_shop.web.repository.InMemoryRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTest {

    private PersonController personController;
    @BeforeEach
    public void setUp() {
        personController = new PersonController(new InMemoryRepository<>(new PersonValidator()));
    }

    @AfterAll
    public void tearDown() {
        personController = null;
    }

    @Test
    public void PersonController_testGetAllPeople_shouldGetAllPeople() {
        personController.AddPerson("a", 1);
        personController.AddPerson("b", 2);
        var People = personController.GetAllPeople();
        assertTrue(People.stream().map(Person::getName).collect(Collectors.toSet()).containsAll(Arrays.asList("a", "b")), "Wrong person entities returned.");
    }

    @Test
    public void PersonController_testRemovePerson_shouldRemoveAddedPerson()
    {
        personController.AddPerson("a", 1);
        var Person = personController.GetAllPeople().get(0);
        personController.RemovePerson(Person.getId());
        assertEquals(personController.GetAllPeople().size(), 0, "Person was not removed.");
    }

    @Test
    public void PersonController_testAddPerson_shouldAddOnePerson(){
        personController.AddPerson("b",2);
        var length = personController.GetAllPeople().size();
        assertEquals(length, 1, "Person was added !");
    }

    @Test
    public void PersonController_testGetAllPeopleWithABudgetGreaterOrEqual_shouldGetAllPeopleWithABudgetGreaterOrEqual(){
        personController.AddPerson("b",100);
        var length = personController.GetAllPeopleWithABudgetGreaterOrEqual(50).size();
        assertEquals(length, 1, "Correct answer !");
    }

    @Test
    public void PersonController_testUpdatePerson_shouldUpdatePerson(){
        personController.AddPerson("b",100);
        var id = personController.GetAllPeople().get(0).getId();
        personController.UpdatePerson(id,"c",400);
        Person person = personController.GetAllPeople().get(0);
        assertEquals(400,person.getBudget(),"Correct answer !");
        assertEquals("c",person.getName(),"Correct answer !");
    }

}
