package en.ubb.pet_shop.core.repository;

import en.ubb.pet_shop.core.domain.Toy;

import java.util.List;

public interface ToyRepository extends CatalogRepository<Toy,Integer>{
    List<Toy> findAllByOrderByPriceAsc();
}
