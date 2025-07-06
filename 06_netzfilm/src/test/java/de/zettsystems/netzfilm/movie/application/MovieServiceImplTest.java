package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieDataTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.movie.values.NoSuchMovieException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {
    public static final String THE_SMURFS = "The smurfs";
    public static final LocalDate SMURFS_DATE = LocalDate.of(2011, 8, 4);
    public static final Fsk SMURFS_FSK = Fsk.FSK_0;
    @Mock
    MovieRepository movieRepository;
    @Mock
    EntityManager entityManager;

    @Captor
    ArgumentCaptor<Movie> movieCaptor;

    @InjectMocks
    MovieServiceImpl testee;

    @Test
    void shouldGetAllMovies() {
        Movie movie = new Movie(THE_SMURFS, SMURFS_DATE, SMURFS_FSK);
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        final List<MovieTo> allMovies = testee.getAllMovies();

        assertThat(allMovies).usingRecursiveComparison().isEqualTo(List.of(movie));
    }

    @Test
    void shouldAddMovie() {
        when(movieRepository.save(any(Movie.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final UUID uuid = testee.addMovie(new MovieDataTo(THE_SMURFS, SMURFS_DATE, SMURFS_FSK));

        verify(movieRepository).save(movieCaptor.capture());
        final Movie result = movieCaptor.getValue();

        assertThat(result.getTitle()).isEqualTo(THE_SMURFS);
        assertThat(result.getReleaseDate()).isEqualTo(SMURFS_DATE);
        assertThat(result.getFsk()).isEqualTo(SMURFS_FSK);
        assertThat(result.getVersion()).isZero();
        assertThat(result.getUuid()).isEqualTo(uuid);
    }

    @Test
    void shouldGetMovie() {
        Movie movie = new Movie(THE_SMURFS, SMURFS_DATE, SMURFS_FSK);
        when(movieRepository.findByUuid(movie.getUuid())).thenReturn(Optional.of(movie));

        final MovieTo result = testee.getMovie(movie.getUuid());

        assertThat(result).usingRecursiveComparison().isEqualTo(movie);
    }

    @Test
    void shouldThrowExceptionsForMissingUuid() {
        when(movieRepository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testee.getMovie(UUID.randomUUID())).isInstanceOf(NoSuchMovieException.class);
    }

    @Test
    void updateMovie() {
        Movie movie = new Movie(THE_SMURFS, SMURFS_DATE.minusDays(1L), SMURFS_FSK);
        when(movieRepository.findByUuid(movie.getUuid())).thenReturn(Optional.of(movie));

        testee.updateMovie(new MovieTo(movie.getUuid(), movie.getTitle(), SMURFS_DATE, SMURFS_FSK, 0L));

        verify(entityManager).detach(movie);
        verify(movieRepository).save(movieCaptor.capture());
        final Movie result = movieCaptor.getValue();
        assertThat(result.getReleaseDate()).isEqualTo(SMURFS_DATE);
    }

    @Test
    void deleteMovie() {
        final UUID uuid = UUID.randomUUID();

        testee.deleteMovie(uuid);

        verify(movieRepository).deleteByUuid(uuid);
    }
}