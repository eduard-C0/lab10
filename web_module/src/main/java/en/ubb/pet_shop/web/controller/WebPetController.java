package en.ubb.pet_shop.web.controller;

import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.domain.Pet;
import en.ubb.pet_shop.core.service.IPetController;
import en.ubb.pet_shop.web.converter.PetConverter;
import en.ubb.pet_shop.web.dto.MultiPetDto;
import en.ubb.pet_shop.web.dto.PersonDto;
import en.ubb.pet_shop.web.dto.PetDto;
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
public class WebPetController {
    public static final Logger log = LoggerFactory.getLogger(Person.class);

    @Autowired
    private IPetController petController;

    @Autowired
    private PetConverter petConverter;

    @RequestMapping(value = "/pets" ,method = RequestMethod.GET)
    List<PetDto>  getAllPets() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllPets: -method entered");
        List<Pet> l = petController.getAllPets();
        MultiPetDto multiPetDto = new MultiPetDto(petConverter.convertModelsToDtos(l));
        log.trace("getAllPets: -method finished with result = {}", multiPetDto);
        return new ArrayList<>(petConverter.convertModelsToDtos(l));
    }

    @RequestMapping(value = "/pets/species-filter/{species}",method = RequestMethod.GET)
    List<PetDto> filterPetBySpecies(@PathVariable String species) throws IOException, ExecutionException, InterruptedException {
        log.trace("filterPetBySpecies: -method entered");
        List<Pet> l = petController.filterPetBySpecies(species);
        MultiPetDto multiPetDto = new MultiPetDto(petConverter.convertModelsToDtos(l));
        log.trace("filterPetBySpecies: -method finished with result = {}", multiPetDto);
        return new ArrayList<>(petConverter.convertModelsToDtos(l));
    }

    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    PetDto addPet(@RequestBody PetDto petDto) throws IOException {
        log.trace("addPet: -method entered");
        PetDto dto = petConverter.convertModelToDto(petController.addPet(petConverter.convertDtoToModel(petDto)));
        log.trace("addPet: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/pets/{id}", method = RequestMethod.PUT)
    PetDto updatePet(@PathVariable Integer id, @RequestBody PetDto petDto) throws IOException {
        log.trace("updatePet: -method entered");
        PetDto dto = petConverter.convertModelToDto(petController.updatePet(petConverter.convertDtoToModel(petDto)));
        log.trace("updatePet: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/pets/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePet(@PathVariable Integer id) throws IOException {
        log.trace("deletePet: -method entered");
        petController.removePet(id);
        log.trace("deletePet: -method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
