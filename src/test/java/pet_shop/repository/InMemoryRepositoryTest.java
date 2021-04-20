package pet_shop.repository;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.BaseValidator;
import en.ubb.pet_shop.web.repository.InMemoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InMemoryRepositoryTest {
    private final BaseEntity<Integer> TEST_ENTITY = new BaseEntity<>();
    private final int TEST_ENTITY_ID = 0;
    private final BaseEntity<Integer> TEST_ENTITY2 = new BaseEntity<>();
    private final int TEST_ENTITY_ID2 = 1;

    private InMemoryRepository<Integer, BaseEntity<Integer>> repo;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() {
        repo = new InMemoryRepository<Integer, BaseEntity<Integer>>(new BaseValidator());

        TEST_ENTITY.setId(TEST_ENTITY_ID);
        TEST_ENTITY2.setId(TEST_ENTITY_ID2);
    }

    @AfterAll
    public void tearDown() {
        repo = null;
    }

    @Test
    public void inMemoryRepository_testFindOne_shouldFindAddedEntity() {
        repo.save(TEST_ENTITY);
        Optional<BaseEntity<Integer>> o1 = repo.findOne(0);
        o1.ifPresentOrElse(integerBaseEntity -> assertEquals(integerBaseEntity, TEST_ENTITY, "Wrong entity found"), () -> fail("No entity found."));
    }

    @Test
    public void inMemoryRepository_testSave_shouldSaveEntity() {
        repo.save(TEST_ENTITY);
        Optional<BaseEntity<Integer>> optional = repo.findOne(TEST_ENTITY_ID);
        optional.ifPresentOrElse(integerBaseEntity -> assertEquals(integerBaseEntity, TEST_ENTITY, "The entity should have been saved correctly."), () -> fail("The entity should have been saved."));
    }

    @Test
    public void inMemoryRepository_testFindAll_shouldFindAllEntities() {
        List<BaseEntity<Integer>> baseEntities = Arrays.asList(TEST_ENTITY, TEST_ENTITY2);
        repo.save(TEST_ENTITY);
        repo.save(TEST_ENTITY2);
        Iterable<BaseEntity<Integer>> it = repo.findAll();
        assertTrue(StreamSupport.stream(it.spliterator(), false).collect(Collectors.toSet()).containsAll(baseEntities), "Incorrect entities returned.");

    }

    @Test
    public void inMemoryRepository_testSave_shouldThrowValidatorException() {
        // TODO: Write this once we have implemented the validator classes and remove @Ignore.
        TEST_ENTITY.setId(-1);
        try {
            repo.save(TEST_ENTITY);
        }
        catch (RuntimeException ex) {
            assertEquals("id must be positive",ex.getMessage());
        }
    }

    @Test
    public void inMemoryRepository_testDelete_shouldDeleteAddedEntity() {
        repo.save(TEST_ENTITY);
        var o =  repo.delete(TEST_ENTITY.getId());
        o.ifPresentOrElse(entity -> assertEquals(entity, TEST_ENTITY, "Wrong entity returned by delete."), () -> fail("Nothing was deleted"));
    }

    @Test
    public void inMemoryRepository_testUpdate_shouldUpdateAddedEntity() {
        repo.save(TEST_ENTITY);
        TEST_ENTITY2.setId(TEST_ENTITY_ID);
        var o =  repo.update(TEST_ENTITY2);
        o.ifPresentOrElse(entity -> assertEquals(entity, TEST_ENTITY2, "Wrong entity returned by update."), () -> fail("Nothing was updated."));
        o = repo.findOne(TEST_ENTITY_ID);
        o.ifPresentOrElse(entity -> assertEquals(entity, TEST_ENTITY2, "The entity was not updated correctly."), () -> fail("The entity hasn't been found."));
    }

    @Test
    public void inMemoryRepository_testUpdate_shouldThrowValidatorException() {
        // TODO: Write this once we have implemented the validator classes and remove @Ignore.
        TEST_ENTITY.setId(-1);
        try {
            repo.update(TEST_ENTITY);
        }
        catch (RuntimeException ex) {
            assertEquals("id must be positive",ex.getMessage());
        }
    }
}
