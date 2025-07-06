package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.NoSuchMovieException;
import de.zettsystems.netzfilm.rent.domain.Rent;
import de.zettsystems.netzfilm.rent.domain.RentRepository;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import de.zettsystems.netzfilm.rent.values.RentTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {
    private static final BigDecimal TWO_EURO = new BigDecimal("2.0");
    private final RentRepository rentRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public UUID rentAMovie(RentTo rentTo, Customer customer) {
        Movie movie = movieRepository.findByUuid(rentTo.movieUuid()).orElseThrow(() -> new NoSuchMovieException(rentTo.movieUuid()));

        BigDecimal amount = calculateAmount(rentTo.numberOfDays());

        Rent newRent = new Rent(movie, customer, amount, rentTo.startDate(), rentTo.startDate().plusDays(rentTo.numberOfDays()));
        return rentRepository.save(newRent).getUuid();
    }

    @Override
    @Transactional
    public UUID rentAMovie(RentTo rentTo, UUID uuid) {
        Optional<Customer> customerOpt = customerRepository.findByUuid(uuid);
        if (customerOpt.isEmpty()) {
            throw new NoSuchCustomerException(uuid);
        }
        return rentAMovie(rentTo, customerOpt.get());
    }

    @Override
    public Collection<RentListTo> findAllRents(Customer customer) {
        return rentRepository.findAllByCustomer(customer).stream().map(Rent::toFullTo).collect(Collectors.toList());
    }

    @Override
    public Collection<RentListTo> findAllRents(UUID uuid) {
        Optional<Customer> customerOpt = customerRepository.findByUuid(uuid);
        if (customerOpt.isEmpty()) {
            throw new NoSuchCustomerException(uuid);
        }
        return findAllRents(customerOpt.get());
    }

    private static BigDecimal calculateAmount(long numberOfDays) {
        return TWO_EURO.multiply(BigDecimal.valueOf(numberOfDays));
    }
}
