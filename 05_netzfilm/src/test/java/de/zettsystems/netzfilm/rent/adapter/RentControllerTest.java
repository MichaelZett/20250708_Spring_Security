package de.zettsystems.netzfilm.rent.adapter;

import de.zettsystems.netzfilm.configuration.SecurityConfig;
import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.rent.application.RentService;
import de.zettsystems.netzfilm.rent.domain.Rent;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import de.zettsystems.netzfilm.rent.values.RentTo;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RentController.class)
@Import(SecurityConfig.class)
class RentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private RentService rentService;
    @MockitoBean
    private CustomerRepository customerRepository;

    // needed for Controller
    @MockitoBean
    private MovieService movieService;

    // needed for config
    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "Petra", roles = "CUSTOMER")
    void shouldGetOverview() throws Exception {
        RentListTo filmchen = new RentListTo("Filmchen", LocalDate.now(), LocalDate.now(), BigDecimal.TEN);

        List<RentListTo> rents = List.of(filmchen);

        Customer petra = mock(Customer.class);

        given(petra.getName()).willReturn("Petra");
        given(customerRepository.findByUsername("Petra")).willReturn(Optional.of(petra));
        given(rentService.findAllRents(petra)).willReturn(rents);

        mvc.perform(get("/rents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("rents", rents))
                .andExpect(content().string(containsString("Petra")))
                .andExpect(content().string(containsString("Filmchen")));
    }

    @Test
    @WithMockUser(username = "Petra", roles = "CUSTOMER")
    void shouldPostForbidden() throws Exception {
        Customer petra = mock(Customer.class);

        given(petra.getName()).willReturn("Petra");
        given(customerRepository.findByUsername("Petra")).willReturn(Optional.of(petra));

        mvc.perform(post("/addrent")
                        .with(csrf())
                        .formField("movieUuid","984008c7-afee-4067-955e-0b55a21de59f")
                        .formField("startDate","2025-07-09")
                        .formField("numberOfDays","3")
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Petra", roles = "CUSTOMER")
    void shouldPostOkWithCsrf() throws Exception {
        Customer petra = mock(Customer.class);

        given(petra.getName()).willReturn("Petra");
        given(customerRepository.findByUsername("Petra")).willReturn(Optional.of(petra));

        mvc.perform(post("/addrent")
                        .with(csrf())
                        .formField("movieUuid","984008c7-afee-4067-955e-0b55a21de59f")
                        .formField("startDate","2025-07-09")
                        .formField("numberOfDays","3")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        verify(rentService).rentAMovie(any(RentTo.class), eq(petra));
    }

}