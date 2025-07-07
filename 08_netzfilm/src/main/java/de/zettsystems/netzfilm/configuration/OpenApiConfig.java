package de.zettsystems.netzfilm.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Netzfilm Api",
                version = "0.0.1",
                description = "The API description",
                license = @License(name = "GNU GPL 3.0", url = "https://www.gnu.org/licenses/gpl-3.0.html"),
                contact = @Contact(url = "https://github.com/MichaelZett", name = "Michael", email = "michael2.zoeller@gmail.com")
        )
)
public class OpenApiConfig {

}
