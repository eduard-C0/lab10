package en.ubb.pet_shop.web.controller;

import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.domain.Pet;
import en.ubb.pet_shop.core.domain.Transaction;
import en.ubb.pet_shop.core.service.ITransactionController;
import en.ubb.pet_shop.web.converter.PersonConverter;
import en.ubb.pet_shop.web.converter.PetConverter;
import en.ubb.pet_shop.web.converter.TransactionConverter;
import en.ubb.pet_shop.web.dto.*;
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
public class WebTransactionController {
    public static final Logger log = LoggerFactory.getLogger(Person.class);

    @Autowired
    private ITransactionController transactionController;

    @Autowired
    private TransactionConverter transactionConverter;

    @Autowired
    private PetConverter petConverter;

    @Autowired
    private PersonConverter personConverter;

    @RequestMapping(value = "/transactions",method = RequestMethod.GET)
    List<TransactionDto>  getAllTransactions() throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllTransactions: -method entered");
        List<Transaction> l = transactionController.getAllTransactions();
        MultiTransactionDto multiTransactionDto = new MultiTransactionDto(transactionConverter.convertModelsToDtos(l));
        log.trace("getAllTransactions: -method finished with result = {}", multiTransactionDto);
        return new ArrayList<>(transactionConverter.convertModelsToDtos(l));    }

    @RequestMapping(value = "/transactions/owner-filter/{personId}")
    MultiPetDto  getAllPetsOfAnOwner(@PathVariable Integer personId) throws IOException, ExecutionException, InterruptedException {
        log.trace("getAllPetsOfAnOwner: -method entered");
        MultiPetDto multiPetDto = new MultiPetDto(petConverter.convertModelsToDtos(transactionController.getAllPetsOfAnOwner(personId)));
        log.trace("getAllPetsOfAnOwner: -method finished with result = {}", multiPetDto);
        return multiPetDto;
    }

    @RequestMapping(value = "/transactions/owner/{petId}")
    PersonDto getOwner(@PathVariable Integer petId) throws IOException, ExecutionException, InterruptedException {
        log.trace("getOwner: -method entered");
        PersonDto dto = personConverter.convertModelToDto(transactionController.getOwner(petId));
        log.trace("getOwner: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    TransactionDto buyPet(@RequestBody TransactionDto transactionDto) throws IOException {
        log.trace("buyPet: -method entered");
        TransactionDto dto = transactionConverter.convertModelToDto(transactionController.buyPet(transactionConverter.convertDtoToModel(transactionDto)));
        log.trace("buyPet: -method finished with result = {}", dto);
        return dto;
    }

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> refundPet(@PathVariable Integer id) throws IOException {
        log.trace("refundPet: -method entered");
        transactionController.refundPet(id);
        log.trace("refundPet: -method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
