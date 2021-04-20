package en.ubb.pet_shop.web.service;

import en.ubb.pet_shop.web.domain.Person;
import en.ubb.pet_shop.web.domain.Pet;
import en.ubb.pet_shop.web.domain.Transaction;
import en.ubb.pet_shop.web.domain.validators.ValidatorException;
import en.ubb.pet_shop.web.repository.IRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TransactionController {
    // TODO: Revise and make sure the order of the parameters in the transaction controller is correct.

    private final IRepository<Integer, Transaction> transactionRepository;
    private final IRepository<Integer, Pet> petRepo;
    private final IRepository<Integer, Person> personRepo;

    public TransactionController(IRepository<Integer,Transaction> transactionRepository,IRepository<Integer,Pet> petrepo,IRepository<Integer,Person> personRepo)
    {
        this.transactionRepository = transactionRepository;
        this.petRepo = petrepo;
        this.personRepo = personRepo;
    }

    /**
     * Stores a transaction between a person and a pet. The person buys a pet
     * @param pet the pet that is being bought;
     *            must be not null.
     * @param person the person that buys the pet;
     *               must be not null
     */
    public void BuyPet(int pet, int person)
    {
        Transaction newTransaction = new Transaction(pet, person);
        Optional<Pet> petO = StreamSupport.stream(petRepo.findAll().spliterator(),false).filter(p->p.getId()==pet).reduce( (pid, mypet)-> mypet);
        Optional<Person> personO = StreamSupport.stream(personRepo.findAll().spliterator(),false).filter(person1 -> person1.getId()==person).reduce((pid,myperson)->myperson);
        Person myPerson = personO.get();
        Pet myPet = petO.get();

        var check = StreamSupport.stream(transactionRepository.findAll().spliterator(),false).filter(trans->trans.getPet()==myPet.getId()).count();
        if(check!=0)
            throw new ValidatorException("Pet already bought");
        Optional.of(myPerson).filter(p->p.getBudget()>=myPet.getSellingPrice()).orElseThrow(()-> {throw new ValidatorException("the budget must be above or equal");});
        myPerson.setBudget(myPerson.getBudget()-myPet.getSellingPrice());
        transactionRepository.save(newTransaction);
    }

    /**
     * Refunds a pet and removes the stored transaction between that pet and it's ex-owner
     * @param id the id of the transaction that is to be reverted;
     *           must be not null.
     */
    public void RefundPet(int id)
    {

        Optional<Transaction> transaction = StreamSupport.stream(transactionRepository.findAll().spliterator(), false).filter(transaction1 -> transaction1.getId()==id).reduce((pid,transaction1)->transaction1);
        Transaction myTransacation = transaction.get();
        int PetId = myTransacation.getPet();
        int PersonId = myTransacation.getPerson();
        Optional<Pet> petO = StreamSupport.stream(petRepo.findAll().spliterator(),false).filter(p->p.getId()==PetId).reduce( (pid, mypet)-> mypet);
        Optional<Person> personO = StreamSupport.stream(personRepo.findAll().spliterator(),false).filter(person1 -> person1.getId()==PersonId).reduce((pid,myperson)->myperson);
        Person actual_person = personO.get();
        Pet actual_pet = petO.get();
        actual_person.setBudget(actual_person.getBudget()+actual_pet.getSellingPrice());
        transactionRepository.delete(id);
    }

    /**
        @return all transactions
     */
    public List<Transaction> GetAllTransactions()
    {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     *
     * @param pet , must be not null
     * @return the owner of a specific pet
     */
    public Person GetOwner(Optional<Pet> pet)
    {
        Optional<Transaction> tr =  StreamSupport.stream(transactionRepository.findAll().spliterator(),false).filter(transaction -> transaction.getPet()==pet.get().getId()).reduce( (pid, trans)-> trans);
        int id = tr.get().getPerson();
        Optional<Person> p = StreamSupport.stream(personRepo.findAll().spliterator(),false).filter(person->person.getId() == id).reduce( (pid, person)-> person);
        return p.get();
    }

    /**
     *
     * @param person the person whose pets we want, must not be null
     * @return the list of pets of that owner
     */
    public List<Pet> GetAllPetsOfAnOwner(Optional<Person> person)
    {
        var Pets =  StreamSupport.stream(transactionRepository.findAll().spliterator(),false).filter(transaction -> transaction.getPerson()==(person.get().getId())).map(transaction -> transaction.getPet()).collect(Collectors.toList());
        return StreamSupport.stream(petRepo.findAll().spliterator(),false).filter(pet->Pets.contains(pet.getId())).collect(Collectors.toList());
    }

}
