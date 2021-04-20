package en.ubb.pet_shop.core.repository;

import en.ubb.pet_shop.core.domain.Transaction;

import java.util.List;

public interface TransactionRepository extends CatalogRepository<Transaction,Integer> {
    List<Transaction> findByPerson(int personId);
    List<Transaction> findByPet(int petId);
}
