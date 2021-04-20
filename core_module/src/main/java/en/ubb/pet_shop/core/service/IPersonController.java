package en.ubb.pet_shop.core.service;

import en.ubb.pet_shop.core.domain.Person;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IPersonController {

    Person addPerson(Person person) throws IOException;

    void removePerson(int id) throws IOException;

    List<Person> getAllPeople() throws IOException, ExecutionException, InterruptedException;

    List<Person> getAllPeopleWithABudgetGreaterOrEqual(double budget) throws IOException, ExecutionException, InterruptedException;

    Person updatePerson(Person person) throws IOException;

}
