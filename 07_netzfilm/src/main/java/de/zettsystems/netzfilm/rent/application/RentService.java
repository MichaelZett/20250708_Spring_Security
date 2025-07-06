package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import de.zettsystems.netzfilm.rent.values.RentTo;

import java.util.Collection;
import java.util.UUID;

public interface RentService {
    UUID rentAMovie(RentTo rentTo, Customer customer);

    Collection<RentListTo> findAllRents(Customer customer);

    UUID rentAMovie(RentTo rentTo, UUID uuid);

    Collection<RentListTo> findAllRents(UUID uuid);
}
