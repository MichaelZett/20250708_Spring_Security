package de.zettsystems.netzfilm.movie.values;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(name = "Filmdaten", description = "Filmdaten zum Anlegen und Anpassen")
public record MovieDataTo(@Schema(description = "Filmtitel",
        example = "Der Titel") @NotBlank String title,
                          @Schema(
                                  description = "Erscheinungsdatum",
                                  type = "string",
                                  format = "date",
                                  pattern = "dd.MM.yyyy",
                                  example = "31.12.2022"
                          ) @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                  fallbackPatterns = {"dd.MM.yyyy"})
                          @JsonFormat(pattern = "dd.MM.yyyy") LocalDate releaseDate,
                          @Schema(description = "Altersfreigabe", example = "FSK_12") @NotNull Fsk fsk) {
}
