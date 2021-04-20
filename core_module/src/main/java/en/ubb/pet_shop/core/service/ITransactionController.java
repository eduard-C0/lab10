package en.ubb.pet_shop.core.service;



import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.domain.Pet;
import en.ubb.pet_shop.core.domain.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ITransactionController {
    Transaction buyPet(Transaction transaction) throws IOException;

    void refundPet(int id) throws IOException;

    List<Transaction> getAllTransactions() throws IOException, ExecutionException, InterruptedException;

    Person getOwner(int petId) throws IOException, ExecutionException, InterruptedException;

    List<Pet> getAllPetsOfAnOwner(int personId) throws IOException, ExecutionException, InterruptedException;


}
