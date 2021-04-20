package en.ubb.pet_shop.web.config;

import en.ubb.pet_shop.common.domain.Person;
import en.ubb.pet_shop.common.domain.Pet;
import en.ubb.pet_shop.common.domain.Transaction;
import en.ubb.pet_shop.common.service.IPersonController;
import en.ubb.pet_shop.common.service.IPetController;
import en.ubb.pet_shop.common.service.ITransactionController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.sql.SQLException;

@Configuration
@DependsOn("Jdbc")
@ComponentScan({"en.ubb.pet_shop.en.ubb.pet_shop.core.service", "en.ubb.pet_shop.web.repository"})
public class ServerConfig {


    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private IPetController petController;

    @Autowired
    private IPersonController personController;

    @Autowired
    private ITransactionController transactionController;

    @DependsOn("Jdbc")
    @Bean("PetService")
    RmiServiceExporter rmiPetServiceExporter() throws SQLException {

        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(IPetController.class);
        // rmiServiceExporter.setService(new PetController(repoPet));
        rmiServiceExporter.setService(petController);
        rmiServiceExporter.setServiceName("IPetController");
        rmiServiceExporter.setRegistryPort(5433);
        return rmiServiceExporter;
    }
    @DependsOn("Jdbc")
    @Bean("PersonService")
    RmiServiceExporter rmiPersonServiceExporter() throws SQLException {

        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(IPersonController.class);
        // rmiServiceExporter.setService(new PersonController(repoPerson));
        rmiServiceExporter.setService(personController);
        rmiServiceExporter.setServiceName("IPersonController");
        rmiServiceExporter.setRegistryPort(5433);
        return rmiServiceExporter;
    }
    @DependsOn({"Jdbc", "PersonService", "PetService"})
    @Bean
    RmiServiceExporter rmiTransactionServiceExporter() throws SQLException {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(ITransactionController.class);
        // rmiServiceExporter.setService(new TransactionController(repoTransaction, repoPet, repoPerson));
        rmiServiceExporter.setService(transactionController);
        rmiServiceExporter.setServiceName("ITransactionController");
        rmiServiceExporter.setRegistryPort(5433);
        return rmiServiceExporter;
    }

}
