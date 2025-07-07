package de.zettsystems.account.statistics.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Accounting endpoints")
@Slf4j
public class AccountRestController {

    @Operation(summary = "See account statistics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account statistics", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})})
    @GetMapping("")
    public Collection<String> retrieveStatistics(Authentication auth) {
        LOG.info("Statistics requested by service user '{}', roles={}",
                auth.getName(), auth.getAuthorities());
        return List.of("Statistik 1", "Statistik 2", "Statistik 3");
    }
}