package de.zettsystems.netzfilm.customer.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.CustomerDataTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private CustomerServiceImpl testee;

    @Captor
    ArgumentCaptor<Customer> captor;

    @Test
    void shouldAddCustomer() {
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encrypted_password");
        when(customerRepository.save(any(Customer.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final CustomerDataTo customerTo = new CustomerDataTo("test", "Test", "Tester", LocalDate.now().minusYears(20L));

        testee.addCustomer(customerTo);

        verify(customerRepository).save(captor.capture());

        final Customer result = captor.getValue();
        assertThat(result.getBirthdate()).isEqualTo(LocalDate.now().minusYears(20L));
        assertThat(result.getUsername()).isEqualTo("test");
        assertThat(result.getName()).isEqualTo("Test");
        assertThat(result.getLastName()).isEqualTo("Tester");
        assertThat(result.getPassword()).isEqualTo("encrypted_password");
        assertThat(result.getUuid()).isNotNull();
    }

    @Test
    void shouldThrowExceptions() {
        when(customerRepository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testee.getCustomer(UUID.randomUUID())).isInstanceOf(NoSuchCustomerException.class);
    }

}