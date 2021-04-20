package en.ubb.pet_shop.web.controller;

import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.service.IPersonController;
import en.ubb.pet_shop.web.converter.PersonConverter;
import en.ubb.pet_shop.web.dto.MultiPersonDto;
import en.ubb.pet_shop.web.dto.PersonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class WebPersonController {
    public static final Logger log = LoggerFactory.getLogger(Person.class);

    @Autowired
    private IPersonController personController;

    @Autowired
    private PersonConverter personConverter;

    @RequestMapping(value = "/people",method = RequestMethod.GET)
    List<PersonDto> getAllPeople() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllPeople: -method entered");
        List<Person> l = personController.getAllPeople();
        MultiPersonDto multiPersonDto = new MultiPersonDto(personConverter.convertModelsToDtos(l));
        log.trace("getAllPeople: -method finished with result = {}", multiPersonDto);

        return new ArrayList<>(personConverter.convertModelsToDtos(l));
    }

    @RequestMapping(value = "/people/budget-filter/{budget}")
    MultiPersonDto getAllPeopleWithABudgetGreaterOrEqual(@PathVariable Double budget) throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllPeopleWithABudgetGreaterOrEqual: -method entered");
        MultiPersonDto multiPersonDto = new MultiPersonDto(personConverter.convertModelsToDtos(personController.getAllPeopleWithABudgetGreaterOrEqual(budget)));
        log.trace("getAllPeopleWithABudgetGreaterOrEqual: -method finished with result = {}", multiPersonDto);
        return multiPersonDto;
    }

    @RequestMapping(value = "/people", method = RequestMethod.POST)
    PersonDto addPerson(@RequestBody PersonDto personDto) throws IOException {
        log.trace("addPerson: -method entered");
        PersonDto dto = personConverter.convertModelToDto(personController.addPerson(personConverter.convertDtoToModel(personDto)));
        log.trace("addPerson: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/people/{id}", method = RequestMethod.PUT)
    PersonDto updatePerson(@PathVariable Integer id, @RequestBody PersonDto personDto) throws IOException {
        log.trace("updatePerson: -method entered");
        PersonDto dto = personConverter.convertModelToDto(personController.updatePerson(personConverter.convertDtoToModel(personDto)));
        log.trace("updatePerson: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/people/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePerson(@PathVariable Integer id) throws IOException {
        log.trace("deletePerson: -method entered");
        personController.removePerson(id);
        log.trace("deletePerson: -method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
