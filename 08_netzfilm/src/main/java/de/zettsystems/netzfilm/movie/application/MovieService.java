package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.values.MovieDataTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    List<MovieTo> getAllMovies();

    UUID addMovie(MovieDataTo movieTo);

    MovieTo getMovie(UUID uuid);

    void updateMovie(MovieTo Movie);

    boolean deleteMovie(UUID uuid);

    String getTitle(UUID uuid);
}
