package de.zettsystems.netzfilm.adapter;

import de.zettsystems.netzfilm.configuration.SecurityConfig;
import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PublicRestController.class)
@Import(SecurityConfig.class)
class PublicRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private MovieService movieService;
    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MovieTo thunderbolts = new MovieTo(UUID.randomUUID(), "Thunderbolts*", LocalDate.of(2025, 5, 2), Fsk.FSK_12, 0);

        List<MovieTo> movies = List.of(thunderbolts);

        given(movieService.getAllMovies()).willReturn(movies);
    }

    @Test
    void findAllMovies() throws Exception {

        mvc.perform(get("/public/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("Thunderbolts*"));
    }
}