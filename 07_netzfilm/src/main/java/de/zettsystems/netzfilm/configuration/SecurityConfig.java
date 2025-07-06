package de.zettsystems.netzfilm.configuration;


import de.zettsystems.netzfilm.user.application.UserDetailsServiceImpl;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import de.zettsystems.netzfilm.user.values.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        repository.setCookiePath("/");
        return repository;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF aktiv, Session-Token im JS-cookie, fuer Api wegen Swagger ausgeschaltet
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository()).ignoringRequestMatchers(
                                "/swagger-ui/**", "/v3/api-docs/**", "/api/**")
                )
                .headers(headers -> headers
                        .referrerPolicy(ref -> ref.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                        .contentSecurityPolicy(Customizer.withDefaults())
                        .permissionsPolicyHeader(Customizer.withDefaults())
                )
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/customers", "/movies").hasRole(Role.STAFF.name());
                    authConfig.requestMatchers("/actuator").hasRole(Role.ADMIN.name());
                    authConfig.requestMatchers("/api/customers/whoami").hasAnyRole(Role.CUSTOMER.name(), Role.STAFF.name(), Role.ADMIN.name());
                    authConfig.requestMatchers("/api/customers/*/rents", "/api/customers/*/rents/**", "/swagger-ui", "/v3/api-docs").hasAnyRole(Role.CUSTOMER.name(), Role.STAFF.name(), Role.ADMIN.name());
                    authConfig.requestMatchers("/api/**").hasAnyRole(Role.STAFF.name(), Role.ADMIN.name());
                    authConfig.requestMatchers("/rents").hasRole(Role.CUSTOMER.name());
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

}
