package cz.fi.muni.pa165.secretagency;

import cz.fi.muni.pa165.secretagency.security.CorsConfigurationImpl;
import cz.muni.fi.pa165.secretagency.InitDataConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({InitDataConfiguration.class, CorsConfigurationImpl.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
