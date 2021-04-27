package en.ubb.pet_shop.core.service;

import en.ubb.pet_shop.core.domain.Food;
import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.repository.FoodRepository;
import en.ubb.pet_shop.core.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FoodController implements IFoodController{

    public static final Logger log = LoggerFactory.getLogger(Food.class);

    @Autowired
    private FoodRepository foodRepository;
    @Override
    public Food addFood(Food food) throws IOException {
        log.trace("AddFood - method entered: food={}", food);
        foodRepository.save(food);
        log.trace("AddPet - method finished");
        return food;
    }

    @Override
    public void removeFood(int id) throws IOException {
        log.trace("removeFood - method entered: foodId={}", id);
        foodRepository.deleteById(id);
        log.trace("removeFood - method finished");
    }

    @Override
    public List<Food> getAllFoods() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllFoods ---method entered");

        var result =  StreamSupport.stream(foodRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.trace("getAllFoods: result={}", result);
        return result;
    }
}
