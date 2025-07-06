package de.zettsystems.netzfilm.customer.values;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(UUID uuid) {
        super("No customer exists with uuid " + uuid);
    }

    public NoSuchCustomerException(String lastName) {
        super("No customer exists with lastname " + lastName);
    }
}
