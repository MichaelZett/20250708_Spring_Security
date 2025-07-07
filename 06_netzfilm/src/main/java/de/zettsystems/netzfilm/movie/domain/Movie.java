package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(name = "movie_title_uq_idx", columnList = "title", unique = true),
        @Index(name = "movie_uuid_uq_idx", columnList = "uuid", unique = true),
})
public class Movie {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_seq"
    )
    @SequenceGenerator(
            name = "movie_seq",
            allocationSize = 1 // default ist 50 in hibernate 6
    )
    private long id;
    @Version
    private long version;
    @Column(updatable = false, nullable = false)
    private final UUID uuid;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private LocalDate releaseDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Fsk fsk;

    //for jpa
    protected Movie() {
        super();
        this.uuid = UUID.randomUUID();
    }

    public Movie(String title, LocalDate releaseDate, Fsk fsk) {
        this();
        this.title = title;
        this.releaseDate = releaseDate;
        this.fsk = fsk;
    }

    public MovieTo toTo() {
        return new MovieTo(this.getUuid(), this.getTitle(), this.getReleaseDate(), this.fsk, this.version);
    }

    public void update(MovieTo movie) {
        this.title = movie.title();
        this.releaseDate = movie.releaseDate();
        this.fsk = movie.fsk();
        if (movie.version() >= 0) {
            this.version = movie.version();
        }

    }
}
