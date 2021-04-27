package en.ubb.pet_shop.web.controller;

import en.ubb.pet_shop.core.domain.Food;
import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.service.IFoodController;
import en.ubb.pet_shop.core.service.IPersonController;
import en.ubb.pet_shop.web.converter.FoodConverter;
import en.ubb.pet_shop.web.converter.PersonConverter;
import en.ubb.pet_shop.web.dto.FoodDto;
import en.ubb.pet_shop.web.dto.MultiFoodDto;
import en.ubb.pet_shop.web.dto.MultiPersonDto;
import en.ubb.pet_shop.web.dto.PersonDto;
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
public class WebFoodController {

    public static final Logger log = LoggerFactory.getLogger(Food.class);

    @Autowired
    private IFoodController foodController;

    @Autowired
    private FoodConverter foodConverter;

    @RequestMapping(value = "/foods",method = RequestMethod.GET)
    List<FoodDto> getAllFood() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllFood: -method entered");
        List<Food> l = foodController.getAllFoods();
        MultiFoodDto multiFoodDto = new MultiFoodDto(foodConverter.convertModelsToDtos(l));
        log.trace("getAllFood: -method finished with result = {}", multiFoodDto);

        return new ArrayList<>(foodConverter.convertModelsToDtos(l));
    }

    @RequestMapping(value = "/foods", method = RequestMethod.POST)
    FoodDto addFood(@RequestBody FoodDto foodDto) throws IOException {
        log.trace("addFood: -method entered");
        FoodDto dto = foodConverter.convertModelToDto(foodController.addFood(foodConverter.convertDtoToModel(foodDto)));
        log.trace("addFood: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/foods/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteFood(@PathVariable Integer id) throws IOException {
        log.trace("deleteFood: -method entered");
        foodController.removeFood(id);
        log.trace("deleteFood: -method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
