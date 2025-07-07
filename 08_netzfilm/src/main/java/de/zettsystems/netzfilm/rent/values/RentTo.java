package de.zettsystems.netzfilm.rent.values;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public record RentTo(UUID movieUuid,
                     @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate startDate,
                     @Min(1) int numberOfDays) {
}
