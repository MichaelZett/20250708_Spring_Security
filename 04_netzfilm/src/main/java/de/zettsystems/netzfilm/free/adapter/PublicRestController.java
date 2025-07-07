package de.zettsystems.netzfilm.free.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(name = "Public", description = "the public space of netzfilm")
class PublicRestController {
    private final MovieService movieService;

    @Operation(summary = "See all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all movies", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieTo.class))})})
    @GetMapping("/movies")
    public Collection<MovieTo> findAllMovies() {
        return movieService.getAllMovies();
    }

}