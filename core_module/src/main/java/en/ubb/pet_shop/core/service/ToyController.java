package en.ubb.pet_shop.core.service;


import en.ubb.pet_shop.core.domain.Food;
import en.ubb.pet_shop.core.domain.Toy;
import en.ubb.pet_shop.core.repository.ToyRepository;
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
public class ToyController implements IToyController{

    public static final Logger log = LoggerFactory.getLogger(Toy.class);

    @Autowired
    private ToyRepository toyRepository;

    @Override
    public Toy addToy(Toy toy) throws IOException
    {
        log.trace("AddToy - method entered: toy={}", toy);
        toyRepository.save(toy);
        log.trace("AddToy - method finished");
        return toy;
    }

    @Override
    public void removeToy(int id) throws IOException {
        log.trace("removeToy - method entered: toyId={}", id);
        toyRepository.deleteById(id);
        log.trace("removeToy - method finished");
    }

    @Override
    public List<Toy> getAllToys() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllToys ---method entered");

        var result =  StreamSupport.stream(toyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.trace("getAllToys: result={}", result);
        return result;
    }

    @Override
    public List<Toy> findAllByOrderByPriceAsc() {
        log.trace("findAllByOrderByPriceAsc ---method entered");
        var result = toyRepository.findAllByOrderByPriceAsc();
        log.trace("findAllByOrderByPriceAsc: result={}", result);
        return result;
    }



}
