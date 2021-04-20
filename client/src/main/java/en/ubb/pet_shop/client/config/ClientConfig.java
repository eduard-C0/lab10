package en.ubb.pet_shop.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestTemplate;


@Configuration
@ComponentScan("en.ubb.pet_shop.client.ui")
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
