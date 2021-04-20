package en.ubb.pet_shop.core.repository;


import en.ubb.pet_shop.core.domain.Person;

import java.util.List;

public interface PersonRepository extends CatalogRepository<Person,Integer>{
    List<Person> findByBudgetGreaterThanEqual(double budget);
}
