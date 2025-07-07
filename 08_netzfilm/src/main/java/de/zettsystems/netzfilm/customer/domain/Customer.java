package de.zettsystems.netzfilm.customer.domain;

import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.user.domain.BaseUser;
import de.zettsystems.netzfilm.user.values.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Getter
@ToString
@Entity
@DiscriminatorValue("CUST")
@NoArgsConstructor
public class Customer extends BaseUser {

    private boolean vip;

    public Customer(String username, String password, String name, String lastName, LocalDate birthdate, Set<Role> roles) {
        super(username, password, name, lastName, birthdate, roles);
    }

    public CustomerTo toTo() {
        return new CustomerTo(this.getUuid(), this.getUsername(), this.getName(), this.getLastName(), this.getBirthdate(), this.vip, this.getVersion());
    }

    public void update(CustomerTo customer) {
        this.setUsername(customer.username());
        this.setName(customer.name());
        this.setLastName(customer.lastName());
        this.setBirthdate(customer.birthdate());
        this.vip = customer.vip();
        if (customer.version() >= 0) {
            this.setVersion(customer.version());
        }
    }
}
