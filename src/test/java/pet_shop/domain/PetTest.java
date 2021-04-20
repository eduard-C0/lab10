package pet_shop.domain;

import en.ubb.pet_shop.web.domain.Pet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetTest {

    private Pet pet;

    @BeforeEach
    public void setUp() {
        pet = new Pet("Filip","cat",1000);
    }

    @AfterAll
    public void tearDown() {
        pet = null;
    }

    @Test
    public void Pet_testGetName_shouldGetTheName() {
        assertEquals("Filip", pet.getName(), "The name is correct!");
    }
    @Test
    public void Pet_testGetSpecies_shouldGetTheSpecies() {
        assertEquals("cat", pet.getSpecies(), "The species is correct!");
    }

    @Test
    public void Pet_testGetSellingPrice_shouldGetTheSellingPrice() {
        assertEquals(1000, pet.getSellingPrice(),1e-15, "The selling price is correct!");
    }
    @Test
    public void Pet_testSetName_shouldSetTheName() {
        pet.setName("abc");
        assertEquals("abc", pet.getName(), "The name is correct!");
    }
    @Test
    public void Pet_testSetSpecies_shouldSetTheSpecies() {
        pet.setSpecies("aaa");
        assertEquals("aaa", pet.getSpecies(), "The species is correct!");
    }

    @Test
    public void Pet_testSetSellingPrice_shouldSetTheSellingPrice() {
        pet.setSellingPrice(500);
        assertEquals(500, pet.getSellingPrice(),1e-15, "The selling price is correct!");
    }

}