package de.zettsystems.netzfilm.movie.values;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Altersfreigabe", description = "Das Alter ab dem der Film geeignet ist")
public enum Fsk {
    @Schema(description = "Ohne Alterbeschränkung")
    FSK_0(0),
    @Schema(description = "Ab 6 Jahren")
    FSK_6(6),
    @Schema(description = "Ab 12 Jahren")
    FSK_12(12),
    @Schema(description = "Ab 16 Jahren")
    FSK_16(16),
    @Schema(description = "Ab 18 Jahren")
    FSK_18(18);
    public final int age;

    Fsk(int age) {
        this.age = age;
    }
}
