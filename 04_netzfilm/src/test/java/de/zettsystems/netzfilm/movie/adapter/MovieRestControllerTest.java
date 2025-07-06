package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.configuration.SecurityConfig;
import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieDataTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.user.application.UserDetailsServiceImpl;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieRestController.class)
@Import(SecurityConfig.class)
class MovieRestControllerTest {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private MovieService movieService;
    @MockitoBean
    private UserRepository userRepository;
    UUID uuid;

    @BeforeEach
    void setup() {
        uuid = UUID.randomUUID();
        MovieTo bernd = new MovieTo(uuid, "Bernd", LocalDate.now(), Fsk.FSK_12, 13);

        List<MovieTo> movies = List.of(bernd);

        given(movieService.getAllMovies()).willReturn(movies);
        given(movieService.getMovie(uuid)).willReturn(bernd);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldFindAllMovies() throws Exception {

        mvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.[0].title").value("Bernd"))
                .andExpect(jsonPath("$.[0].version").value(13))
                .andExpect(jsonPath("$.[0].fsk").value("FSK_12"))
                .andExpect(jsonPath("$.[0].releaseDate").value(LocalDate.now().format(FORMATTER)));

    }

    @Test
    void shouldGetMovie() throws Exception {
        mvc.perform(get("/api/movies/" + uuid.toString()).with(user("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.title").value("Bernd"))
                .andExpect(jsonPath("$.version").value(13))
                .andExpect(jsonPath("$.fsk").value("FSK_12"))
                .andExpect(jsonPath("$.releaseDate").value(LocalDate.now().format(FORMATTER)));
    }

    @Test
    void shouldNotGetMovie() throws Exception {
        mvc.perform(get("/api/movies/" + uuid.toString()))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldBeForbiddenToGetMovie() throws Exception {
        mvc.perform(get("/api/movies/" + uuid.toString()))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "Petra", roles = "ADMIN")
    void shouldUpdate() throws Exception {
        String body = """
                { "uuid": "%s",\
                  "title": "%s",\
                  "releaseDate": "%s",\
                  "fsk": "%s",\
                  "version": "%s"\
                }""".formatted(uuid.toString(), "Bernd", LocalDate.of(2023, 6, 1).format(FORMATTER), "FSK_12", 13);
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/movies/" + uuid.toString())
                .accept("*/*")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());

        ArgumentCaptor<MovieTo> captor = ArgumentCaptor.forClass(MovieTo.class);
        verify(movieService).updateMovie(captor.capture());
        assertThat(captor.getValue().releaseDate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {
        mvc.perform(delete("/api/movies/" + uuid.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {
        String body = """
                { "title": "%s",\
                  "releaseDate": "%s",\
                  "fsk": "%s"\
                }""".formatted("Bernd 2", LocalDate.of(2023, 6, 1).format(FORMATTER), "FSK_12");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/movies")
                .accept("*/*")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        final UUID uuid = UUID.randomUUID();
        when(movieService.addMovie(any(MovieDataTo.class))).thenReturn(uuid);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/movies/%s".formatted(uuid.toString()))));

        ArgumentCaptor<MovieDataTo> captor = ArgumentCaptor.forClass(MovieDataTo.class);
        verify(movieService).addMovie(captor.capture());
        assertThat(captor.getValue().releaseDate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }
}
