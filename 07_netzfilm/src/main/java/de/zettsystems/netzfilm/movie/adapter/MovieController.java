package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieDataTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public String movies(Model model) {
        final List<MovieTo> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("fsks", Fsk.values());
        model.addAttribute("movie", new MovieDataTo(null, null, null));
        return "movies/movies";
    }

    @PostMapping("/addmovie")
    public String addMovie(@Valid @ModelAttribute("movie") MovieDataTo movie, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            final List<MovieTo> movies = movieService.getAllMovies();
            model.addAttribute("movies", movies);
            model.addAttribute("fsks", Fsk.values());
            return "movies/movies";
        }
        movieService.addMovie(movie);
        redirectAttributes.addFlashAttribute("message", "Successfully added " + movie.title());
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/movies";
    }

    @GetMapping("editmovie/{uuid}")
    public String showMovieUpdateForm(@PathVariable("uuid") UUID uuid, Model model) {
        MovieTo movie = movieService.getMovie(uuid);
        model.addAttribute("movie", movie);
        model.addAttribute("fsks", Fsk.values());
        return "movies/update";
    }

    @PostMapping("updatemovie/{uuid}")
    public String updateMovie(@Valid @ModelAttribute("movie") MovieTo movie, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "movies/update";
        }
        movieService.updateMovie(movie);
        redirectAttributes.addFlashAttribute("message", "Successfully updated " + movie.title());
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/movies";
    }

    @GetMapping("deletemovie/{uuid}")
    public String deleteMovie(@PathVariable("uuid") UUID uuid, RedirectAttributes redirectAttributes) {
        boolean deleted = movieService.deleteMovie(uuid);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Successfully deleted movie %s".formatted(uuid));
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to delete movie %s".formatted(uuid));
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/movies";
    }


}