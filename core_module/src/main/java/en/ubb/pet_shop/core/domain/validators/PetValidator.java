package en.ubb.pet_shop.core.domain.validators;


import en.ubb.pet_shop.core.domain.Pet;

import java.util.Optional;

public class PetValidator implements Validator<Pet>{

    @Override
    /**
     * validates my entity
     * @param object must be of type pet
     * @return none
     * @throws ValidatorException
     */
    public void validate(Pet object) throws ValidatorException
    {
        Optional.of(object).filter(pet -> pet.getId()>=0).orElseThrow(()-> {throw new ValidatorException("id must be positive");});
        Optional.of(object).filter(pet -> pet.getName().matches("^[a-zA-Z]+$")).orElseThrow(()-> {throw new ValidatorException("name must contain only letters");});
        Optional.of(object).filter(pet -> pet.getSpecies().matches("^[a-zA-Z]+$")).orElseThrow(()-> {throw new ValidatorException("species name must contain only letters");});
        Optional.of(object).filter(pet -> pet.getSellingPrice()>=0).orElseThrow(()-> {throw new ValidatorException("selling price must be positive");});
    }
}
