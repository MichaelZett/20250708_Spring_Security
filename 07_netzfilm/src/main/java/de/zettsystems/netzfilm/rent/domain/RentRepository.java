package de.zettsystems.netzfilm.rent.domain;

import de.zettsystems.netzfilm.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface RentRepository extends JpaRepository<Rent, Long> {

    Collection<Rent> findAllByCustomer(Customer customer);
}
