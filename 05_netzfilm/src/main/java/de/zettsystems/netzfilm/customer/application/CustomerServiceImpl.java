package de.zettsystems.netzfilm.customer.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.CustomerDataTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import de.zettsystems.netzfilm.user.domain.PasswordGenerator;
import de.zettsystems.netzfilm.user.values.Role;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<CustomerTo> getAllCustomers() {
        return customerRepository.findAll().stream().map(Customer::toTo).toList();
    }

    @Override
    public UUID addCustomer(CustomerDataTo customer) {
        String newPwd = PasswordGenerator.generate(16);
        LOG.info("Neues Passwort {} für User {}", newPwd, customer.username());
        Customer saved = customerRepository.save(new Customer(customer.username(), passwordEncoder.encode(newPwd), customer.name(), customer.lastName(), customer.birthdate(),
                Collections.singleton(Role.CUSTOMER)));
        return saved.getUuid();
    }

    @Override
    @Transactional
    public boolean deleteCustomer(UUID uuid) {
        return customerRepository.deleteByUuid(uuid) == 1;
    }

    @Override
    public CustomerTo getCustomer(UUID uuid) {
        return customerRepository.findByUuid(uuid).orElseThrow(() -> new NoSuchCustomerException(uuid)).toTo();
    }

    @Override
    @Transactional
    public void updateCustomer(CustomerTo customer) {
        final Customer customerEntity = customerRepository.findByUuid(customer.uuid()).orElseThrow(() -> new NoSuchCustomerException(customer.uuid()));
        // entity aus Hibernate lösen
        entityManager.detach(customerEntity);
        // ändern
        customerEntity.update(customer);
        // wieder einfügen, andernfalls würde die Version einfach aktualisiert werden
        customerRepository.save(customerEntity);
    }
}
