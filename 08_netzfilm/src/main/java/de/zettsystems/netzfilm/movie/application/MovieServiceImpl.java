package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.MovieDataTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.movie.values.NoSuchMovieException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final EntityManager entityManager;

    @Override
    @PostFilter(
            "filterObject.fsk.age <= T(java.time.LocalDate).now().year - authentication.principal.birthdate.year"
    )
    public List<MovieTo> getAllMovies() {
        return movieRepository.findAll().stream().map(Movie::toTo).toList();
    }

    @Override
    public UUID addMovie(MovieDataTo movie) {
        Movie entity = movieRepository.save(new Movie(movie.title(), movie.releaseDate(), movie.fsk()));
        return entity.getUuid();
    }

    @Override
    public MovieTo getMovie(UUID uuid) {
        return movieRepository.findByUuid(uuid).orElseThrow(() -> new NoSuchMovieException(uuid)).toTo();
    }

    @Override
    @Transactional
    public void updateMovie(MovieTo movie) {
        final Movie entity = movieRepository.findByUuid(movie.uuid()).orElseThrow(() -> new NoSuchMovieException(movie.uuid()));
        // entity aus Hibernate lösen
        entityManager.detach(entity);
        // ändern
        entity.update(movie);
        // wieder einfügen, andernfalls würde die Version einfach aktualisiert werden
        movieRepository.save(entity);
    }

    @Override
    @Transactional
    public boolean deleteMovie(UUID uuid) {
        return movieRepository.deleteByUuid(uuid) == 1;
    }

    @Override
    public String getTitle(UUID uuid) {
        final Movie entity = movieRepository.findByUuid(uuid).orElseThrow(() -> new NoSuchMovieException(uuid));
        return entity.getTitle();
    }

}
