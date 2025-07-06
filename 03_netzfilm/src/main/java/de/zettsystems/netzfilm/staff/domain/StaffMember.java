package de.zettsystems.netzfilm.staff.domain;

import de.zettsystems.netzfilm.user.domain.BaseUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("STAFF")
@NoArgsConstructor
public class StaffMember extends BaseUser {
    public StaffMember(String username, String password, String name, String lastName, LocalDate birthdate) {
        super(username, password, name, lastName, birthdate);
    }
}