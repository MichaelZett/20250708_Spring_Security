package de.zettsystems.netzfilm.rent.domain;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(name = "rent_movie_id_fk_idx", columnList = "movie_id"),
        @Index(name = "rent_customer_id_fk_idx", columnList = "customer_id"),
        @Index(name = "rent_uuid_uq_idx", columnList = "uuid", unique = true),
})
public class Rent {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rent_seq"
    )
    @SequenceGenerator(
            name = "rent_seq",
            allocationSize = 1 // default ist 50 in hibernate 6
    )
    private long id;
    @Version
    private long version;
    @Column(updatable = false, nullable = false)
    private final UUID uuid;
    @ManyToOne(optional = false)
    private Movie movie;
    @ManyToOne(optional = false)
    private Customer customer;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    //for jpa
    protected Rent() {
        super();
        this.uuid = UUID.randomUUID();
    }

    public Rent(Movie movie, Customer customer, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        this();
        this.movie = movie;
        this.customer = customer;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RentListTo toFullTo() {
        return new RentListTo(movie.getTitle(), startDate, endDate, amount);
    }
}
