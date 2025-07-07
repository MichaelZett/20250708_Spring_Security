package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import de.zettsystems.netzfilm.movie.values.Fsk;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // we don't want in memory DB
@Import(TestContainersConfiguration.class)
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    void shouldGetByUuid() {
        Movie save = movieRepository.save(new Movie("Film", LocalDate.now(), Fsk.FSK_6));

        Movie retrieved = movieRepository.findByUuid(save.getUuid()).orElseThrow();

        assertThat(retrieved).isEqualTo(save);
    }

}