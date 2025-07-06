package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.configuration.SecurityConfig;
import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.rent.application.RentService;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerRestController.class)
@Import(SecurityConfig.class)
class CustomerRestControllerTest {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CustomerService customerService;

    // needed for config
    @MockitoBean
    private UserRepository userRepository;
    // needed for controller
    @MockitoBean
    private RentService rentService;

    @Test
    @WithMockUser(roles = "STAFF")
    void shouldFindAll() throws Exception {
        CustomerTo customerTo = new CustomerTo(UUID.randomUUID(), "bernd", "Bernd", "das Brot",
                LocalDate.now(), 0);

        List<CustomerTo> customerTos = List.of(customerTo);

        given(customerService.getAllCustomers()).willReturn(customerTos);

        mvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].uuid").value(customerTo.uuid().toString()))
                .andExpect(jsonPath("$.[0].username").value("bernd"))
                .andExpect(jsonPath("$.[0].name").value("Bernd"))
                .andExpect(jsonPath("$.[0].lastName").value("das Brot"))
                .andExpect(jsonPath("$.[0].version").value(0))
                .andExpect(jsonPath("$.[0].birthdate").value(LocalDate.now().format(FORMATTER)));
    }

}