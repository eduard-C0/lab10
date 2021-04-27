package en.ubb.pet_shop.core.service;

import en.ubb.pet_shop.core.domain.Food;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFoodController {

    Food addFood(Food food) throws IOException;

    void removeFood(int id) throws IOException;

    List<Food> getAllFoods() throws IOException, ExecutionException, InterruptedException;
}
