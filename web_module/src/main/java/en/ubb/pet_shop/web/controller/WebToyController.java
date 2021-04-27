package en.ubb.pet_shop.web.controller;

import en.ubb.pet_shop.core.domain.Pet;
import en.ubb.pet_shop.core.domain.Toy;
import en.ubb.pet_shop.core.service.IToyController;
import en.ubb.pet_shop.web.converter.ToyConverter;
import en.ubb.pet_shop.web.dto.MultiPetDto;
import en.ubb.pet_shop.web.dto.PetDto;
import en.ubb.pet_shop.web.dto.ToyDto;
import en.ubb.pet_shop.web.dto.MultiToyDto;
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
public class WebToyController {

    public static final Logger log = LoggerFactory.getLogger(Toy.class);

    @Autowired
    private IToyController toyController;

    @Autowired
    private ToyConverter toyConverter;

    @RequestMapping(value = "/toys",method = RequestMethod.GET)
    List<ToyDto> getAllToys() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllToys: -method entered");
        List<Toy> l = toyController.getAllToys();
        MultiToyDto multiToyDto = new MultiToyDto(toyConverter.convertModelsToDtos(l));
        log.trace("getAllToys: -method finished with result = {}", multiToyDto);

        return new ArrayList<>(toyConverter.convertModelsToDtos(l));
    }

    @RequestMapping(value = "/toys", method = RequestMethod.POST)
    ToyDto addToy(@RequestBody ToyDto toyDto) throws IOException {
        log.trace("addToy: -method entered");
        ToyDto dto = toyConverter.convertModelToDto(toyController.addToy(toyConverter.convertDtoToModel(toyDto)));
        log.trace("addToy: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/toys/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteToy(@PathVariable Integer id) throws IOException {
        log.trace("deleteToy: -method entered");
        toyController.removeToy(id);
        log.trace("deleteToy: -method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/toys/order-by-price/" ,method = RequestMethod.GET)
    List<ToyDto> findAllByOrderByPriceAsc(){
        log.trace("findAllByOrderByPriceAsc: -method entered");
        List<Toy> l = toyController.findAllByOrderByPriceAsc();
        MultiToyDto multiToyDto = new MultiToyDto(toyConverter.convertModelsToDtos(l));
        log.trace("findAllByOrderByPriceAsc: -method finished with result = {}", multiToyDto);
        return new ArrayList<>(toyConverter.convertModelsToDtos(l));
    }
}
