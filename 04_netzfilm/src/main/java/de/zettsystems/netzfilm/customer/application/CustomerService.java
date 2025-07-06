package de.zettsystems.netzfilm.customer.application;

import de.zettsystems.netzfilm.customer.values.CustomerDataTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerTo> getAllCustomers();

    void addCustomer(CustomerDataTo customer);

    boolean deleteCustomer(UUID uuid);

    CustomerTo getCustomer(UUID uuid);

    void updateCustomer(CustomerTo customer);
}
