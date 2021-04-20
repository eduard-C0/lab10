package en.ubb.pet_shop.core.service;


import en.ubb.pet_shop.core.repository.PersonRepository;
import en.ubb.pet_shop.core.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class PersonController implements IPersonController {
    public static final Logger log = LoggerFactory.getLogger(Person.class);

    @Autowired
    private PersonRepository personRepository;

    /**
     * Adds the person with the given {name, budget}.
     * @param newPerson must be not null.
     * @return newPerson
     */
    @Override
    public Person addPerson(Person newPerson)
    {
        log.trace("AddPerson - method entered: person={}", newPerson);
        personRepository.save(newPerson);
        log.trace("AddPerson - method finished");
        return newPerson;
    }

    /**
     * Removes the person with the given {@code id}.
     * @param id must be not null
     */

    public void removePerson(int id)
    {
        log.trace("RemovePerson - method entered: personId={}", id);
        personRepository.deleteById(id);
        log.trace("RemovePerson - method finished");
    }



    /**
     * @return all entities from the personRepository
     */
    public List<Person> getAllPeople()
    {
        log.trace("getAllPeople ---method entered");
        var result =  StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.trace("getAllStudents: result={}", result);

        return result;
    }

    /**
     *  @param budget must be greater than 0
     *  @return all entities from the personRepository with a budget greater than the given budget
     */

    public List<Person> getAllPeopleWithABudgetGreaterOrEqual(double budget)
    {
        log.trace("GetAllPeopleWithABudgetGreaterOrEqual ---method entered");
        /*var result = StreamSupport.stream(personRepository.findAll().spliterator(),false)
                .filter(p -> p.getBudget() >= budget)
                .collect(Collectors.toList());*/

        var result = personRepository.findByBudgetGreaterThanEqual(budget);

        log.trace("GetAllPeopleWithABudgetGreaterOrEqual: result={}", result);

        return result;
    }

    /**
     * Updates the person with a given {@code id}.
     * @param updatedPerson must be not null,
     * @return
     */
    @Override
    @Transactional
    public Person updatePerson(Person updatedPerson)
    {
        log.trace("updateStudent - method entered: student={}", updatedPerson);

        personRepository.findById(updatedPerson.getId())
                .ifPresent(s -> {
                    s.setName(updatedPerson.getName());
                    s.setBudget(updatedPerson.getBudget());
                    log.debug("updatePerson - updated: s={}", s);
                });

        log.trace("updatePerson - method finished");
        return updatedPerson;
    }


}
