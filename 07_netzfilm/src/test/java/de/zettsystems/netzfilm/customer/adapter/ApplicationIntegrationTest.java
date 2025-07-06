package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import de.zettsystems.netzfilm.rent.values.RentListTo;
import de.zettsystems.netzfilm.rent.values.RentTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(TestContainersConfiguration.class)
@ActiveProfiles("test")
class ApplicationIntegrationTest {

    public static final String UUID_A_NEW_HOPE = "984008c7-afee-4067-955e-0b55a21de59f";
    public static final String UUID_ANKE = "77c4f5dd-b29c-4976-9c30-47cc00f0f7d2";
    public static final String UUID_ERIK = "43131897-34be-4ab6-9c6a-f7280690fb2a";
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    private String base() {
        return "http://localhost:" + port + "/api/customers";
    }

    private String username;
    private String password;

    @BeforeEach
    void setup() {
        username = "anke";
        password = "anke";
    }

    @Test
    void rentMovieAndFetchRentsShouldWork() {
        UUID customerId = UUID.fromString(UUID_ANKE);

        // 1) Film leihen
        RentTo rentReq = new RentTo(
                UUID.fromString(UUID_A_NEW_HOPE),
                LocalDate.now(),
                7
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RentTo> post = new HttpEntity<>(rentReq, headers);

        ResponseEntity<Void> rentResp = rest
                .withBasicAuth(username, password)
                .exchange(
                        base() + "/" + customerId + "/rents",
                        HttpMethod.POST,
                        post,
                        Void.class
                );
        assertThat(rentResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String location = Objects.requireNonNull(rentResp.getHeaders().getLocation()).toString();
        assertThat(location).contains("/api/customers/" + customerId + "/rents/");

        // 2) Leihen abfragen
        ResponseEntity<RentListTo[]> listResp = rest
                .withBasicAuth(username, password)
                .getForEntity(
                        base() + "/" + customerId + "/rents",
                        RentListTo[].class
                );
        assertThat(listResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(listResp.getBody());
        List<RentListTo> rents = List.of(listResp.getBody());
        assertThat(rents).isNotEmpty();
    }

    @Test
    void rentMovieFailsForOtherUserThanPrincipal() {
        UUID customerId = UUID.fromString(UUID_ERIK);

        // 1) Film leihen
        RentTo rentReq = new RentTo(
                UUID.fromString(UUID_A_NEW_HOPE),
                LocalDate.now(),
                7
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RentTo> post = new HttpEntity<>(rentReq, headers);

        ResponseEntity<Void> rentResp = rest
                .withBasicAuth(username, password)
                .exchange(
                        base() + "/" + customerId + "/rents",
                        HttpMethod.POST,
                        post,
                        Void.class
                );
        assertThat(rentResp.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }
}
