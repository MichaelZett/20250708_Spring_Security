package de.zettsystems.netzfilm;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfiguration.class)
class NetzfilmApplicationTests {

    @Test
    void contextLoads() {
    }

}
