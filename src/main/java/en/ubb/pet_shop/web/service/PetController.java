package en.ubb.pet_shop.web.service;

import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.repository.IRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PetController {
    private IRepository<Integer, Pet> petsRepository;

    public PetController(IRepository<Integer,Pet> petsRepository)
    {
        this.petsRepository = petsRepository;
    }

    /**
     * Adds the pet with the given {name,species, sellingPrice}.
     *
     * @param name, species, sellingPrice
     *            must be not null.
     */
    public void AddPet(String name, String species, double sellingPrice)
    {
        Pet newPet = new Pet(name,species,sellingPrice);
        petsRepository.save(newPet);
    }

    /**
     * Removes the pet with the given {@code id}.
     *
     * @param id
     *            must be not null.
     */
    public void RemovePet(int id)
    {
        petsRepository.delete(id);
    }

    /**
        Returns a list with all the pets
        @return all pets
     */
    public List<Pet> GetAllPets()
    {
        return (List<Pet>) petsRepository.findAll();
    }

    /**
     * Updates a pet with the given {@code id,name,species,sellingPrice}.
     * @param id,newName,newSpecies,newSellingPrice the id must be not null ,
     *                                  the newName should be a valid one,
     *                                   the newSpecies should be not null,
     *                                   the newSellingPrice should be greater the 0
     *
     * @return none
     */

    public void UpdatePet(int id,String name, String species, double sellingPrice)
    {
        Pet pet = new Pet(id,name,species,sellingPrice);
        this.petsRepository.update(pet);
    }

    /**
     *
     * @param species
     * @return the list of pets from that species
     */
    public List<Pet> FilterPetBySpecies(String species)
    {
        return StreamSupport.stream(petsRepository.findAll().spliterator(),false).filter(pet -> pet.getSpecies().equals(species)).collect(Collectors.toList());
    }


}
