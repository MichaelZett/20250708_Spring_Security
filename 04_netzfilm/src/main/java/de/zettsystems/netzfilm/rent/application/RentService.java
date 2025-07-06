package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import de.zettsystems.netzfilm.rent.values.RentTo;

import java.util.Collection;

public interface RentService {
    void rentAMovie(RentTo rentTo, Customer customer);

    Collection<RentListTo> findAllRents(Customer customer);
}
