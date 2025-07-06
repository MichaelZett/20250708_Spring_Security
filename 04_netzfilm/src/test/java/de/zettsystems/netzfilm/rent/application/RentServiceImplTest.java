package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.rent.domain.Rent;
import de.zettsystems.netzfilm.rent.domain.RentRepository;
import de.zettsystems.netzfilm.rent.values.RentTo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentServiceImplTest {
    @Mock
    private RentRepository rentRepository;
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private RentServiceImpl testee;

    @Captor
    ArgumentCaptor<Rent> captor;

    @Test
    void shouldAddRent() {
        final UUID uuid = UUID.randomUUID();
        when(movieRepository.findByUuid(uuid)).thenReturn(Optional.of(mock(Movie.class)));

        Customer customer = mock(Customer.class);

        LocalDate today = LocalDate.now();
        testee.rentAMovie(new RentTo(uuid, today, 3), customer);

        verify(rentRepository).save(captor.capture());

        final Rent value = captor.getValue();

        assertThat(value.getAmount()).isEqualTo(new BigDecimal("6.0"));
        assertThat(value.getEndDate()).isEqualTo(today.plusDays(3L));
    }
}