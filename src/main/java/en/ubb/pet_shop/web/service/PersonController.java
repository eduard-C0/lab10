package en.ubb.pet_shop.web.service;

import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.repository.IRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PersonController {
    private IRepository<Integer, Person> personRepository;


    public PersonController(IRepository<Integer,Person> personRepository)
    {
        this.personRepository = personRepository;
    }
    /**
     * Adds the person with the given {name, budget}.
     *
     * @param name,budget
     *            must be not null.
     * @return none
     */
    public void AddPerson(String name, double budget)
    {
        Person newPerson = new Person(name,budget);
        personRepository.save(newPerson);
    }

    /**
     * Removes the person with the given {@code id}.
     *
     * @param id
     */
    public void RemovePerson(int id)
    {
        personRepository.delete(id);
    }



    /**
     *
     * @return all entities from the personRepository
     */
    public List<Person> GetAllPeople()
    {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * @param budget must be greater than 0
     *
     *  @return all entities from the personRepository with a budget greater than the given budget
     */

    public List<Person> GetAllPeopleWithABudgetGreaterOrEqual(double budget)
    {
        return StreamSupport.stream(personRepository.findAll().spliterator(),false).filter(p -> p.getBudget() >= budget).collect(Collectors.toList());
    }

    /**
     * Updates the person with a given {@code id}.
     * @param id,newName,newBudget the id must be not null ,
     *                             the newName should be a valid one,
     *                             the budget should be greater the 0
     */

    public void UpdatePerson(int id, String newName, double newBudget)
    {
        Person person = new Person(id,newName,newBudget);
        personRepository.update(person);
    }
}
