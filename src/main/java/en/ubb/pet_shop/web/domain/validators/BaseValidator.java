package en.ubb.pet_shop.web.domain.validators;

import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.domain.Transaction;

public class BaseValidator implements Validator {

    /**
     * validates my entity
     * @param object must be of type baseClass
     * @throws ValidatorException -> thrown by sibling validators
     */
    @Override
    public void validate(Object object) throws ValidatorException
    {
       if(object instanceof Person)
       {
           PersonValidator pv = new PersonValidator();
           pv.validate((Person)object);
       }
       if(object instanceof Pet)
       {
           PetValidator pv = new PetValidator();
           pv.validate((Pet)object);
       }
       if(object instanceof Transaction)
       {
           TransactionValidator tv = new TransactionValidator();
           tv.validate((Transaction)object);
       }
    }
}
