package de.zettsystems.netzfilm.customer.domain;

import de.zettsystems.netzfilm.customer.values.CustomerTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {
    Customer testee;

    @BeforeEach
    void setup() {
        testee = new Customer("test", "password", "Test", "Tester", LocalDate.now(), Collections.emptySet());
    }

    @Test
    void shouldProvideToWithSameData() {
        final CustomerTo newCustomerTo = testee.toTo();

        assertThat(newCustomerTo).usingRecursiveComparison().isEqualTo(testee);
    }

    @Test
    void shouldUpdate() {
        final LocalDate birthdate = LocalDate.of(2003, 9, 8);
        testee.update(new CustomerTo(testee.getUuid(), testee.getUsername(), testee.getName(), testee.getLastName(),
                birthdate, 0));

        assertThat(testee.getBirthdate()).isEqualTo(birthdate);
    }
}