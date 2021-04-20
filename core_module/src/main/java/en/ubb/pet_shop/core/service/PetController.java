package en.ubb.pet_shop.core.service;


import en.ubb.pet_shop.core.repository.PetRepository;
import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.domain.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class PetController implements IPetController {
    public static final Logger log = LoggerFactory.getLogger(Person.class);

    @Autowired
    private PetRepository petsRepository;
    /**
     * Adds the pet with the given {name,species, sellingPrice}.
     * @param newPet must be not null.
     * @return
     */
    @Override
    public Pet addPet(Pet newPet)
    {
        log.trace("AddPet - method entered: pet={}", newPet);
        petsRepository.save(newPet);
        log.trace("AddPet - method finished");
        return newPet;
    }

    /**
     * Removes the pet with the given {@code id}.
     * @param id must be not null.
     */
    public void removePet(int id)
    {
        log.trace("RemovePet - method entered: personId={}", id);
        petsRepository.deleteById(id);
        log.trace("RemovePet - method finished");
    }

    /**
        Returns a list with all the pets
        @return all pets
     */
    @Override
    public List<Pet> getAllPets()
    {
        log.trace("GetAllPets ---method entered");
        var result = new ArrayList<>(petsRepository.findAll());

        log.trace("GetAllPets: result={}", result);
        return result;
    }

    /**
     * Updates a pet with the given {@code id,name,species,sellingPrice}.
     * @param updatedPet must be not null
     * @return
     */
    @Override
    @Transactional
    public Pet updatePet(Pet updatedPet)
    {
        log.trace("updatedPet - method entered: pet={}", updatedPet);
        petsRepository.findById(updatedPet.getId())
                .ifPresent(s -> {
                    s.setName(updatedPet.getName());
                    s.setSpecies(updatedPet.getSpecies());
                    s.setSellingPrice(updatedPet.getSellingPrice());
                    log.debug("updatedPet - updated: s={}", s);
                });
        log.trace("updatedPet - method finished");
        return updatedPet;
    }

    /**
     * @param species the name of the species to look for
     * @return the list of pets from that species
     */
    @Override
    public List<Pet> filterPetBySpecies(String species)
    {
        log.trace("FilterPetBySpecies ---method entered");
        /*var result = StreamSupport.stream(petsRepository.findAll().spliterator(),false)
                .filter(pet -> pet.getSpecies().equals(species))
                .collect(Collectors.toList());

         */
        var result = petsRepository.findBySpecies(species);
        log.trace("FilterPetBySpecies: result={}", result);
        return result;
    }


}
