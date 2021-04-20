package en.ubb.pet_shop.web.domain.validators;

import en.ubb.pet_shop.web.domain.Person;

import java.util.Optional;

public class PersonValidator implements Validator<Person>{

    @Override
    /**
     * validates my entity
     * @param object must be of type person
     * @return none
     * @throws ValidatorException
     */
    public void validate(Person object) throws ValidatorException {
        Optional.of(object).filter(person -> person.getId()>=0).orElseThrow(()-> {throw new ValidatorException("id must be positive");});
        Optional.of(object).filter(person -> person.getName().matches("^[a-zA-Z]+$")).orElseThrow(()-> {throw new ValidatorException("name must contain only letters");});
        Optional.of(object).filter(person -> person.getBudget()>=0).orElseThrow(()-> {throw new ValidatorException("budget must be positive");});
    }
}
