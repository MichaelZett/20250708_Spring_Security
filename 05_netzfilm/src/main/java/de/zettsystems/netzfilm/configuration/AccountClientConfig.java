package de.zettsystems.netzfilm.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AccountClientConfig {
    @Bean
    RestTemplate accountingRestTemplate() {
        // das kommt natürlich eigentlich aus der Umgebungskonfiguration, der ServiceUser und Passwort sind dabei natürlich verschlüsselt
        return new RestTemplateBuilder()
                .rootUri("http://localhost:9000")
                .basicAuthentication("netzfilm-client", "netzfilm-client")
                .build();
    }
}
