package de.zettsystems.netzfilm.movie.values;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class NoSuchMovieException extends ResponseStatusException {
    public NoSuchMovieException(UUID uuid) {
        super(HttpStatus.NOT_FOUND, "No movie exists with uuid " + uuid);
    }
}
