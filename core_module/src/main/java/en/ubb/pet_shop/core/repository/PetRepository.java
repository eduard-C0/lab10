package en.ubb.pet_shop.core.repository;


import en.ubb.pet_shop.core.domain.Pet;

import java.util.List;

public interface PetRepository extends CatalogRepository<Pet,Integer>{
    List<Pet> findBySpecies(String species);
}
