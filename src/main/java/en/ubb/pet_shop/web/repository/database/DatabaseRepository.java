package en.ubb.pet_shop.web.repository.database;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.Validator;
import en.ubb.pet_shop.web.repository.BaseFileRepository;
import en.ubb.pet_shop.web.repository.BaseFileRepositoryException;

import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.sql.DriverManager.getConnection;

public class DatabaseRepository <ID, T extends BaseEntity<ID>> extends BaseFileRepository<ID, T> {
    public DatabaseRepository(Validator<T> validator, String fileName, Class<T> classReference) throws SQLException {
        super(validator, fileName, classReference);
    }


    @Override
    public void loadData() throws BaseFileRepositoryException, SQLException {
        String sql = "SELECT * FROM " + this.fileName;
        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PetShop","postgres","12345");
            var personPrep = connection.prepareStatement(sql);
            var result = personPrep.executeQuery())
        {
            while (result.next()) {
                AtomicInteger i = new AtomicInteger(1);
                Map<String,String> attributes = new HashMap<>();
                ResultSetMetaData metaData = result.getMetaData();
                var numberOfColumns = metaData.getColumnCount();
                for(int j=1; j<= numberOfColumns;j++)
                {
                    attributes.put(metaData.getColumnName(j),result.getString(j));
                }
//                Stream.of(metaData).forEach(element ->{
//                    try {
//                        attributes.put(element.getColumnName(i.get()),result.getString(i.get()));
//                        System.out.println(element.getColumnName(i.get()));
//                        System.out.println(result.getString(i.get()));
//                        i.set(i.get() + 1);
//                    } catch (SQLException throwables) {
//                        throwables.printStackTrace();
//                    }
//                });
                save(loadEntity(attributes));
            }
        }
    }

    @Override
    public void saveData() throws BaseFileRepositoryException, SQLException {
            String sql = "DELETE FROM " + this.fileName;
            String insert = "INSERT INTO " + this.fileName;

        try
        {
            var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PetShop","postgres","12345");
            var personPrep = connection.prepareStatement(sql);
            var result = personPrep.executeUpdate();
            elements.values()
                    .forEach(element ->
                    {
                        try {
                            var insertStatement = connection.prepareStatement(insert + element.InsertDatabase());
                            var r = insertStatement.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
