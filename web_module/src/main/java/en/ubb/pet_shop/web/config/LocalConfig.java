package en.ubb.pet_shop.web.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import en.ubb.pet_shop.core.config.JpaConfig;

@Configuration
@ComponentScan({"en.ubb.pet_shop.core"})
@Import({JpaConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db.properties"),
})
public class LocalConfig {

    /**
     * Enables placeholders usage with SpEL expressions.
     *
     * @return nothing
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}