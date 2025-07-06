package de.zettsystems.netzfilm.rent.adapter;


import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.rent.application.RentService;
import de.zettsystems.netzfilm.rent.values.RentTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RentController {
    private final MovieService movieService;
    private final RentService rentService;
    private final CustomerRepository customerRepository;

    @GetMapping("/rent")
    public String showRentForm(Model model, HttpServletRequest request) {
        final Customer customer = getCustomer(request.getUserPrincipal());
        final List<MovieTo> allMovies = movieService.getAllMovies();
        model.addAttribute("movies", allMovies);
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap == null) {
            model.addAttribute("message", "Hallo, " + customer.getName() + ". Was möchtest Du leihen?");
        }
        model.addAttribute("rent", new RentTo(null, LocalDate.now(), 1));
        model.addAttribute("rents", rentService.findAllRents(customer));

        return "rent/rent";
    }

    @PostMapping("/addrent")
    public String rent(@Valid @ModelAttribute("rent") RentTo rent, BindingResult result, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        final Customer customer = getCustomer(principal);
        if (result.hasErrors()) {
            final List<MovieTo> allMovies = movieService.getAllMovies();
            model.addAttribute("movies", allMovies);
            model.addAttribute("rents", rentService.findAllRents(customer));
            return "rent/rent";
        }
        rentService.rentAMovie(rent, customer);
        redirectAttributes.addFlashAttribute("message", "Hallo, " + customer.getName() + ". Du hast '" + movieService.getTitle(rent.movieUuid()) + "' geliehen. Danke!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/rent";
    }

    private Customer getCustomer(Principal principal) {
        return customerRepository.findByUsername(principal.getName()).orElseThrow(() -> new NoSuchCustomerException(principal.getName()));
    }

}