package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.configuration.SecurityConfig;
import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
@Import(SecurityConfig.class)
class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private MovieService movieService;

    @Test
    @WithMockUser
    void shouldFindAll() throws Exception {
        MovieTo bernd = new MovieTo(UUID.randomUUID(), "Bernd", LocalDate.now(), Fsk.FSK_12, 6);

        List<MovieTo> movies = List.of(bernd);

        given(movieService.getAllMovies()).willReturn(movies);

        mvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("movies", movies))
                .andExpect(content().string(containsString("Bernd")));
    }
}