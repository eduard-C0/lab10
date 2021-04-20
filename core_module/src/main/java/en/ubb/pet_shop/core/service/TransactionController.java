package en.ubb.pet_shop.core.service;


import en.ubb.pet_shop.core.repository.PersonRepository;
import en.ubb.pet_shop.core.repository.PetRepository;
import en.ubb.pet_shop.core.repository.TransactionRepository;
import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.core.domain.Pet;
import en.ubb.pet_shop.core.domain.Transaction;
import en.ubb.pet_shop.core.domain.validators.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionController implements ITransactionController {
    public static final Logger log = LoggerFactory.getLogger(Transaction.class);
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PetRepository petRepo;
    @Autowired
    private PersonRepository personRepo;

    /**
     * Stores a transaction between a person and a pet. The person buys a pet
     * @param newTransaction must be not null.
     * @return newTransaction
     */
    public Transaction buyPet(Transaction newTransaction) {
        log.trace("BuyPet - method entered: transaction={}", newTransaction);
        int pet = newTransaction.getPet();
        int person = newTransaction.getPerson();
        Optional<Pet> petO = petRepo.findAll().stream()
                .filter(p -> p.getId() == pet)
                .reduce((pid, myPet) -> myPet);

        Optional<Person> personO = personRepo.findAll().stream()
                .filter(person1 -> person1.getId() == person)
                .reduce((pid, myPerson) -> myPerson);

        Person myPerson = personO.orElseThrow(() -> {
            throw new IllegalArgumentException("The person id specified in this transaction object is non-existent");
        });

        Pet myPet = petO.orElseThrow(() -> {
            throw new IllegalArgumentException("The pet id specified in this transaction object is non-existent");
        });

        var check = transactionRepository.findAll().stream()
                .filter(trans -> trans.getPet() == myPet.getId())
                .count();

        if (check != 0)
            throw new ValidatorException("Pet already bought");

        Optional.of(myPerson)
                .filter(p -> p.getBudget() >= myPet.getSellingPrice())
                .orElseThrow(() -> {
                    throw new ValidatorException("the budget must be above or equal");
                });

        myPerson.setBudget(myPerson.getBudget() - myPet.getSellingPrice());
        transactionRepository.save(newTransaction);
        log.trace("BuyPet - method finished");
        return newTransaction;
    }

    /**
     * Refunds a pet and removes the stored transaction between that pet and it's ex-owner
     * @param id the id of the transaction that is to be reverted;
     *           must be not null.
     */
    public void refundPet(int id)
    {
        log.trace("RefundPet - method entered: petId={}", id);
        Optional<Transaction> transaction = transactionRepository.findAll().stream()
                .filter(transaction1 -> transaction1.getId() == id)
                .reduce((pid, transaction1) -> transaction1);

        Transaction myTransaction = transaction.orElseThrow(() -> {
            throw new IllegalArgumentException("The transaction id is non-existent");
        });

        int PetId = myTransaction.getPet();
        int PersonId = myTransaction.getPerson();

        Optional<Pet> petO = petRepo.findAll().stream()
                .filter(p -> p.getId() == PetId)
                .reduce((pid, myPet)-> myPet);

        Pet actual_pet = petO.orElseThrow(() -> {
            throw new IllegalArgumentException("The pet id specified in this transaction object is non-existent");
        });

        Optional<Person> personO = personRepo.findAll().stream()
                .filter(person1 -> person1.getId() == PersonId)
                .reduce((pid, myPerson) -> myPerson);

        Person actual_person = personO.orElseThrow(() -> {
            throw new IllegalArgumentException("The person id specified in this transaction object is non-existent");
        });

        actual_person.setBudget(actual_person.getBudget()+actual_pet.getSellingPrice());
        transactionRepository.deleteById(id);
        log.trace("RefundPet - method finished");
    }

    /**
        @return all transactions
     */
    public List<Transaction> getAllTransactions()
    {
        log.trace("GetAllTransactions - method entered");
        var result = new ArrayList<>(transactionRepository.findAll());
        log.trace("GetAllTransactions - method finished, results={}",result);
        return result;
    }

    /**
     *
     * @param petId , must be not null
     * @return the owner of a specific pet
     */
    public Person getOwner(int petId)
    {
        log.trace("GetOwner - method entered: petId={}", petId);
        /*Optional <Pet> oPet = petRepo.findById(petId);
        Pet pet = oPet.orElseThrow(() -> {
            throw new IllegalArgumentException("The pet id is non-existent");
        });
        Optional <Transaction> oTransaction =  transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getPet() == pet.getId())
                .reduce((pid, trans) -> trans);
        Transaction transaction = oTransaction.orElseThrow(() -> {
            throw new IllegalArgumentException("No transaction found with this petId");
        });
        int id = transaction.getPerson();
        Optional<Person> p = personRepo.findAll().stream()
                .filter(person -> person.getId() == id)
                .reduce((pid, person) -> person);
        var res =  p.orElse(null);
         */
        var transaction = transactionRepository.findByPet(petId).stream().findFirst().orElseThrow(() -> {
            throw new IllegalArgumentException("The pet id is non-existent");
        });
        Optional<Person> oPerson = personRepo.findAll().stream().filter(p -> p.getId().equals(transaction.getPerson())).findFirst();
        var res = oPerson.orElseThrow(() -> {
            throw new IllegalArgumentException("The pet id is non-existent");
        });
        log.trace("GetOwner - method finished: person={}", res);
        return res;
    }

    /**
     *
     * @param personId the person whose pets we want, must not be null
     * @return the list of pets of that owner
     */
    public List<Pet> getAllPetsOfAnOwner(int personId)
    {
        log.trace("GetAllPetsOfAnOwner - method entered: personId={}", personId);
        Optional <Person> oPerson = personRepo.findById(personId);
        Person person = oPerson.orElseThrow(() -> {
            throw new IllegalArgumentException("The person id is non-existent");
        });
        List<Transaction> transactions = transactionRepository.findByPerson(personId);
        List<Pet> res = transactions.stream().map(transaction -> petRepo.findById(transaction.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("Non-existent Pet Id");
        })).collect(Collectors.toList());
        /*
        var Pets =  transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getPerson() == person.getId())
                .map(Transaction::getPet)
                .collect(Collectors.toList());
        var res= petRepo.findAll().stream()
                .filter(pet -> Pets.contains(pet.getId()))
                .collect(Collectors.toList());*/
        log.trace("GetAllPetsOfAnOwner - method finished: pets={}", res);
        return res;
    }




}
