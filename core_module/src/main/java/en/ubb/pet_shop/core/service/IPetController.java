package en.ubb.pet_shop.core.service;



import en.ubb.pet_shop.core.domain.Pet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IPetController {
    Pet addPet(Pet pet) throws IOException;

    void removePet(int id) throws IOException;

    List<Pet> getAllPets() throws IOException, ExecutionException, InterruptedException;

    Pet updatePet(Pet pet) throws IOException;

    List<Pet> filterPetBySpecies(String species) throws IOException, ExecutionException, InterruptedException;

}
