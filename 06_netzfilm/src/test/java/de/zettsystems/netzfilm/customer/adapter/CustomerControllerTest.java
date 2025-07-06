package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.configuration.SecurityConfig;
import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.movie.adapter.MovieController;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.user.domain.UserRepository;
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

@WebMvcTest(controllers = CustomerController.class)
@Import(SecurityConfig.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CustomerService customerService;

    // needed for config
    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    void shouldFindAll() throws Exception {
        CustomerTo customerTo = new CustomerTo(UUID.randomUUID(), "bernd", "Bernd", "das Brot",
                LocalDate.now(), false, 0);

        List<CustomerTo> movies = List.of(customerTo);

        given(customerService.getAllCustomers()).willReturn(movies);

        mvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("customers", movies))
                .andExpect(content().string(containsString("Bernd")));
    }
}