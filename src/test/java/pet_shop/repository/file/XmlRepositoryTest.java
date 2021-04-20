package pet_shop.repository.file;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.BaseValidator;
import en.ubb.pet_shop.web.repository.file.XmlRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.io.File;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class XmlRepositoryTest {
    private final BaseEntity<Integer> TEST_ENTITY = new BaseEntity<>();
    private final int TEST_ENTITY_ID = 5;
    private final String testLoadDataFile = "data/test/testLoadData.xml";
    private final String testSaveDataFile = "data/test/testSaveData.xml";

    @AfterAll
    public void tearDown() {
        boolean ignored = new File(testSaveDataFile).delete();
    }

    @Test
    public void xmlRepository_testLoadData_successfullyLoaded() throws SQLException {
        @SuppressWarnings("unchecked")
        XmlRepository<Integer, BaseEntity<Integer>> xmlRepository = new XmlRepository<Integer, BaseEntity<Integer>>(new BaseValidator(), testLoadDataFile, (Class<BaseEntity<Integer>>) TEST_ENTITY.getClass());
        assertEquals((int) StreamSupport.stream(xmlRepository.findAll().spliterator(), false).count(), 1, "The xml en.ubb.pet_shop.core.repository doesn't contain exactly one entity.");
        Optional<BaseEntity<Integer>> o = xmlRepository.findOne(0);
        o.ifPresentOrElse(integerBaseEntity -> assertTrue(true), () -> fail("Wrong entity in the xml en.ubb.pet_shop.core.repository"));
    }

    @Test
    public void xmlRepository_testSaveData_successfullySaved() throws SQLException {
        {
            @SuppressWarnings("unchecked")
            XmlRepository<Integer, BaseEntity<Integer>> xmlRepository = new XmlRepository<Integer, BaseEntity<Integer>>(new BaseValidator(), testSaveDataFile, (Class<BaseEntity<Integer>>) TEST_ENTITY.getClass());
            TEST_ENTITY.setId(TEST_ENTITY_ID);
            xmlRepository.save(TEST_ENTITY);
            xmlRepository.saveData();
        }
        {
            @SuppressWarnings("unchecked")
            XmlRepository<Integer, BaseEntity<Integer>> xmlRepository = new XmlRepository<Integer, BaseEntity<Integer>>(new BaseValidator(), testSaveDataFile, (Class<BaseEntity<Integer>>) TEST_ENTITY.getClass());
            assertEquals((int) StreamSupport.stream(xmlRepository.findAll().spliterator(), false).count(), 1, "The xml en.ubb.pet_shop.core.repository doesn't contain exactly one entity.");
            Optional<BaseEntity<Integer>> o = xmlRepository.findOne(5);
            o.ifPresentOrElse(integerBaseEntity -> assertTrue(true), () -> fail("Wrong entity in the xml en.ubb.pet_shop.core.repository"));
        }
    }
}
