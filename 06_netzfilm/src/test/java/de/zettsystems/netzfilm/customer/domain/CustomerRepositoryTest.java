package de.zettsystems.netzfilm.customer.domain;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // we don't want in memory DB
@Import(TestContainersConfiguration.class)
class CustomerRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository testee;

    @Test
    void shouldGetByUuid() {
        Customer c = new Customer("test", "password", "Test", "Tester", LocalDate.now(), Collections.emptySet());

        entityManager.persistAndFlush(c);

        final Optional<Customer> result = testee.findByUuid(c.getUuid());

        assertThat(result).isNotEmpty().contains(c);

    }
}