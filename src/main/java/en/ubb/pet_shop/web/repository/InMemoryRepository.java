package en.ubb.pet_shop.web.repository;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.Validator;
import en.ubb.pet_shop.web.domain.validators.ValidatorException;

import java.util.*;

/**
 * In memory en.ubb.pet_shop.core.repository using the IRepository interface for storing objects of a specific type.
 */
public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements IRepository<ID, T>{
    protected final Map<ID,T> elements;
    protected final Validator<T> validator;
    public InMemoryRepository(Validator<T> validator)
    {
        elements = new HashMap<>();
        this.validator = validator;
    }
    /*
        Returns a wrapper containing the sought element or null.
        Input: the element's id
        Output: Optional object containing either the element or null
    */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null ) {
            throw new IllegalArgumentException("The given id is null!");
        }
        return Optional.ofNullable(elements.get(id));
    }
    /*
        Returns a List with all the values from the Map
        Input:
        Output: ArrayList with all the values
    */
    @Override
    public Iterable<T> findAll() {
        return new ArrayList<>(elements.values());
    }
    /*
        Adds a new element to the map of elements after checking if it is null or valid
        Input: a new element
        Output: null if the entity was saved otherwise returns the entity
    */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if(entity == null) {
            throw new IllegalArgumentException("The given entity is null!");
        }
        // TODO: validate the entity
        validator.validate(entity);
        return Optional.ofNullable(elements.putIfAbsent(entity.getId(),entity));
    }
    /*
        Removes an element (if it exists) and returns the element (or null)
        Input: the element's id
        Output: null if the ID wasn't found or the element if the element was removed successfully from the map
    */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null ) {
            throw new IllegalArgumentException("The given id is null!");
        }
        var entity = Optional.ofNullable(elements.remove(id));
        return entity;

    }
    /*
        Replaces the old entity with the new entity in the map, both having the same id
        Input: the entity
        Output: null if the ID wasn't found or the new element if the element was updated successfully
    */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if(entity == null) {
            throw new IllegalArgumentException("The given entity is null!");
        }
        // TODO: validate the entity
        validator.validate(entity);
        return Optional.ofNullable(elements.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
