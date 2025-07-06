package de.zettsystems.netzfilm.customer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUuid(UUID uuid);

    int deleteByUuid(UUID uuid);

    Optional<Customer> findByUsername(String name);
}
