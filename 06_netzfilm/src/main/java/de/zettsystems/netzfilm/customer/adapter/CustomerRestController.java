package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerDataTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import de.zettsystems.netzfilm.rent.application.RentService;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import de.zettsystems.netzfilm.rent.values.RentTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "the customers API to do CRUD")
class CustomerRestController {
    private final CustomerService customerService;
    private final RentService rentService;

    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all customers", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerTo.class))})})
    @GetMapping("")
    public Collection<CustomerTo> findAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Get a customer by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the customer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerTo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerTo> getCustomer(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.of(Optional.ofNullable(customerService.getCustomer(uuid)));
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a customer by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated the customer", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @Valid @RequestBody CustomerDataTo customerDataTo) {
        try {
            customerService.updateCustomer(new CustomerTo(uuid, customerDataTo.username(), customerDataTo.name(),
                    customerDataTo.lastName(), customerDataTo.birthdate(), customerDataTo.vip(), -1));
            return ResponseEntity.ok().build();
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a customer by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer with given uuid deleted", content = @Content),
            @ApiResponse(responseCode = "204", description = "customer with given uuid did not exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content)})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        final boolean success = customerService.deleteCustomer(uuid);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created the customer", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)})
    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody CustomerDataTo customerTo) {
        final UUID uuid = customerService.addCustomer(customerTo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Rent movie for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "rented a movie", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @PostMapping("/{uuid}/rents")
    @PreAuthorize("#uuid == authentication.principal.uuid")
    public ResponseEntity<Void> rentMovie(@PathVariable UUID uuid, @Valid @RequestBody RentTo rentTo) {
        final UUID rentUuid = rentService.rentAMovie(rentTo, uuid);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rentUuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get all Rents for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all rents", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @GetMapping("/{uuid}/rents")
    @PreAuthorize("#uuid == authentication.principal.uuid")
    public Collection<RentListTo> getAllRents(@PathVariable UUID uuid) {
        return rentService.findAllRents(uuid);
    }

    @GetMapping("/whoami")
    @ResponseBody
    public Authentication whoami(Authentication auth) {
        return auth;
    }
}
