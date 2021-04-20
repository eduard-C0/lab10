package pet_shop.controller;

import en.ubb.pet_shop.web.service.PetController;
import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.domain.validators.PetValidator;
import en.ubb.pet_shop.web.repository.InMemoryRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetControllerTest {

    private PetController petController;
    @BeforeEach
    public void setUp() {
        petController = new PetController(new InMemoryRepository<>(new PetValidator()));
    }

    @AfterAll
    public void tearDown() {
        petController = null;
    }

    @Test
    public void PetController_testGetAllPets_shouldGetAllThePets() {
        petController.AddPet("a", "a",150);
        petController.AddPet("b", "b",200);
        var Pets = petController.GetAllPets();
        assertTrue(Pets.stream().map(Pet::getName).collect(Collectors.toSet()).containsAll(Arrays.asList("a", "b")), "Wrong pets entities returned.");
    }

    @Test
    public void PetController_testAddPet_shouldAddOnePet(){
        petController.AddPet("a","dog",400);
        var length = petController.GetAllPets().size();
        assertEquals(length, 1, "Pet was added !");
    }

    @Test
    public void PetController_testRemovePet_shouldRemoveAddedPet(){
        petController.AddPet("a", "cat",200);
        var Pet = petController.GetAllPets().get(0);
        petController.RemovePet(Pet.getId());
        assertEquals(petController.GetAllPets().size(), 0, "Pet was not removed.");
    }

    @Test
    public void PetController_testFilterPetBySpecies_shouldReturnAllCats()
    {
        petController.AddPet("a", "cat",200);
        petController.AddPet("b", "cat",200);
        petController.AddPet("c", "dog",200);
        var Pets = petController.FilterPetBySpecies("cat");
        assertTrue(Pets.stream().map(Pet::getName).collect(Collectors.toSet()).containsAll(Arrays.asList("a", "b")), "Wrong pets entities returned.");
    }

    @Test
    public void PetController_testUpdatePet_shouldUpdatePet()
    {
        petController.AddPet("a", "cat",200);
        var id = petController.GetAllPets().get(0).getId();
        petController.UpdatePet(id,"edi","dog",400);
        var pet= petController.GetAllPets().get(0);
        assertEquals(pet.getName(),"edi");
        assertEquals(pet.getSpecies(),"dog");
        assertEquals(pet.getSellingPrice(),400);


    }
}
