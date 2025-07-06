package de.zettsystems.netzfilm.staff.domain;

import de.zettsystems.netzfilm.user.domain.BaseUser;
import de.zettsystems.netzfilm.user.values.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorValue("STAFF")
@NoArgsConstructor
public class StaffMember extends BaseUser {
    public StaffMember(LocalDate birthdate, String username, String password, String name, String lastName, Set<Role> roles) {
        super(username, password, name, lastName, birthdate, roles);
    }
}