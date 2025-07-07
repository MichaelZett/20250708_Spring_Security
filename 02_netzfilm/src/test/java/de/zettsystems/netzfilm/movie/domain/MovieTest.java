package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MovieTest {

    Movie testee;

    @BeforeEach
    void setup() {
        testee = new Movie("The company", LocalDate.of(2001, 9, 8), Fsk.FSK_6);
    }

    @Test
    void shouldProvideToWithSameData() {
        final MovieTo newMovieTo = testee.toTo();

        // the method can be used for different types, it seems not to be possible to get rid of the warning
        assertThat(newMovieTo).usingRecursiveComparison().isEqualTo(testee);
    }

    @Test
    void shouldUpdate() {
        final LocalDate newReleaseDate = LocalDate.of(2003, 9, 8);
        final Fsk newFsk = Fsk.FSK_0;
        testee.update(new MovieTo(testee.getUuid(), testee.getTitle(), newReleaseDate, newFsk, 0));

        assertThat(testee.getReleaseDate()).isEqualTo(newReleaseDate);
        assertThat(testee.getFsk()).isEqualTo(newFsk);
    }
}