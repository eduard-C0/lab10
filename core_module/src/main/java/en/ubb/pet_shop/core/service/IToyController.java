package en.ubb.pet_shop.core.service;

import en.ubb.pet_shop.core.domain.Food;
import en.ubb.pet_shop.core.domain.Toy;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IToyController {
    Toy addToy(Toy toy) throws IOException;

    void removeToy(int id) throws IOException;

    List<Toy> getAllToys() throws IOException, ExecutionException, InterruptedException;

    List<Toy> findAllByOrderByPriceAsc();
}
