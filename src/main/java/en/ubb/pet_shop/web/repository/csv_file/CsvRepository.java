package en.ubb.pet_shop.web.repository.csv_file;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.Validator;
import en.ubb.pet_shop.web.repository.BaseFileRepository;
import en.ubb.pet_shop.web.repository.BaseFileRepositoryException;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class CsvRepository<ID, T extends BaseEntity<ID>> extends BaseFileRepository<ID, T>{
    /**
     * @param validator      Generic validator to validate the objects that this en.ubb.pet_shop.core.repository will store.
     * @param fileName       The file that will be used to store the data.
     * @param classReference A class reference to the generic class held by this en.ubb.pet_shop.core.repository.
     * @throws BaseFileRepositoryException exception caused by working with different kind of files, or caused by the class reference.
     */
    public CsvRepository(Validator<T> validator, String fileName, Class<T> classReference) throws BaseFileRepositoryException, SQLException {
        super(validator, fileName, classReference);
    }

    @Override
    public void loadData() throws BaseFileRepositoryException
    {
        File newFile = new File(this.fileName);
        try
        {
            boolean success = new File(fileName).createNewFile();
            if (success)
            {
                System.out.println("The file " + fileName + " did not exist and was created instead.");
                // prepare the empty content..
                saveData();
                return;
            }
            Scanner myScanner = new Scanner(newFile);
            while(myScanner.hasNextLine())
            {
                Map<String, String> attributes = new HashMap<>();
                String line = myScanner.nextLine();
                var SplitLine = line.split(";");
                Stream.of(SplitLine)
                        .map(s->s.split(":"))
                .forEach(element ->
                {
                    String name = element[0];
                    String attribute = element[1];
                    attributes.put(name,attribute);
                });
                save(loadEntity(attributes));
            }
        } catch (IOException ex)
        {
            throw new BaseFileRepositoryException(ex.getMessage());
        }
    }

    @Override
    public void saveData() throws BaseFileRepositoryException
    {
        try
        {
            FileWriter writer = new FileWriter(this.fileName);
            elements.values()
                    .forEach(element ->
                    {
                       try
                       {
                           writer.write(element.CsvToString());
                       }
                       catch (IOException ex)
                       {
                           throw new BaseFileRepositoryException(ex.getMessage());
                       }

                    });
            writer.close();
        }
        catch (IOException ex)
        {
            throw new BaseFileRepositoryException(ex.getMessage());
        }
    }
}
