package de.zettsystems.netzfilm.rent.domain;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RentTest {
    @Test
    void shouldProvideToWithSameData() {
        Movie movie = mock(Movie.class);
        when(movie.getTitle()).thenReturn("The company");
        Customer customer = mock(Customer.class);
        Rent testee = new Rent(movie, customer, BigDecimal.TWO, LocalDate.now(), LocalDate.now().plusDays(3L));
        final RentListTo rentTo = testee.toFullTo();

        assertThat(rentTo).usingRecursiveComparison().ignoringFields("title").isEqualTo(testee);
        assertThat(rentTo.title()).isEqualTo(movie.getTitle());
    }
}