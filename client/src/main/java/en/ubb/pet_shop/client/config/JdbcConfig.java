package en.ubb.pet_shop.client.config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Bean
    JdbcOperations jdbcOperations(){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    private DataSource dataSource() {
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUsername(System.getProperty("username"));
        dataSource.setPassword(System.getProperty("password"));
        dataSource.setUrl("jdbc:postgresql://localhost:5432/PetShop");
        dataSource.setInitialSize(2);
        return dataSource;
    }
}
