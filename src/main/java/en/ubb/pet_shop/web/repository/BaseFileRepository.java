package en.ubb.pet_shop.web.repository;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.Validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

/**
 * Base Repository for File Repositories using the InMemoryRepository class for storing objects of a specific type.
 */
public abstract class BaseFileRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {
    protected String fileName;
    protected Class<T> classReference;

    /**
     * @param validator Generic validator to validate the objects that this en.ubb.pet_shop.core.repository will store.
     * @param fileName The file that will be used to store the data.
     * @param classReference A class reference to the generic class held by this en.ubb.pet_shop.core.repository.
     * @throws BaseFileRepositoryException exception caused by working with different kind of files, or caused by the class reference.
     */
    public BaseFileRepository(Validator<T> validator, String fileName, Class <T> classReference) throws BaseFileRepositoryException, SQLException {
        super(validator);
        this.fileName = fileName;
        this.classReference = classReference;
        loadData();
    }

    /**
     * Method which transforms a HashMap of attributes into the corresponding entity
     * @param attributes the attributes of the entity that is to be created.
     * @return returns the newly created entity
     * @throws BaseFileRepositoryException exception caused by the class reference.
     */
    public T loadEntity(Map<String, String> attributes) throws BaseFileRepositoryException {
        Method loadEntity = Arrays.stream(classReference.getMethods())
                .filter(p -> p.getName().equals("loadEntity"))
                .findFirst()
                .orElseThrow(() -> {throw new BaseFileRepositoryException("The method loadEntity couldn't be found.");});
        try {
            return Optional.ofNullable(loadEntity.invoke(null, attributes))
                    .filter(entity -> classReference.isInstance(entity))
                    .map(entity -> classReference.cast(entity))
                    .orElseThrow(() -> {
                        throw new BaseFileRepositoryException("Invalid class. The object e is not a child of class " + classReference.getName());
                    });

        } catch (IllegalAccessException illegalAccessException) {
            throw new BaseFileRepositoryException("No access to call the method loadEntity: " + illegalAccessException.getMessage(), illegalAccessException.getCause());
        } catch (InvocationTargetException invocationTargetException) {
            throw new BaseFileRepositoryException("There was an error when invoking the loadEntity method: " + invocationTargetException.getMessage(), invocationTargetException.getCause());
        }
    }

    /**
     * Method which will load data from a file of a specific type.
     * @throws BaseFileRepositoryException caused by failed file operations/processing
     */
    public abstract void loadData() throws BaseFileRepositoryException, SQLException;

    /**
     * Method which will save data in a file of a specific type.
     * @throws BaseFileRepositoryException caused by failed file operations/processing
     */
    public abstract void saveData() throws BaseFileRepositoryException, SQLException;
}
