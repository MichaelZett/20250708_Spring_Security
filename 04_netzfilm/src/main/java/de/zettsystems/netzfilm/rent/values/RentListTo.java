package de.zettsystems.netzfilm.rent.values;


import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentListTo(@NotNull String title, @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate startDate,
                         @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate endDate,
                         @NotNull @NumberFormat(style = NumberFormat.Style.CURRENCY) BigDecimal amount) {
}
