package de.zettsystems.netzfilm.customer.values;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Customer", description = "Alle Kundendaten")
public record CustomerTo(@Schema(description = "Eindeutige ID") UUID uuid,
                         @Schema(description = "Benutzername",
                                 example = "username") @NotBlank String username,
                         @Schema(description = "Vorname",
                                 example = "Frank") @NotBlank String name,
                         @Schema(description = "Nachname",
                                 example = "Oz") @NotBlank String lastName,
                         @Schema(description = "Geburtsdatum",
                                 type = "string",
                                 format = "date",
                                 pattern = "dd.MM.yyyy",
                                 example = "09.11.1984") @Past @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                 fallbackPatterns = {"dd.MM.yyyy"}) @JsonFormat(pattern = "dd.MM.yyyy") LocalDate birthdate,
                         @Schema(description = "VIP Kennzeichen", requiredMode = Schema.RequiredMode.NOT_REQUIRED) boolean vip,
                         @Schema(description = "Interne Version") long version) {
}
