package de.zettsystems.netzfilm.configuration;


import de.zettsystems.netzfilm.user.application.UserDetailsServiceImpl;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import de.zettsystems.netzfilm.user.values.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/customers", "/movies").hasRole(Role.STAFF.name());
                    authConfig.requestMatchers("/actuator", "/swagger-ui", "/api/**", "/v3/api-docs").hasRole(Role.ADMIN.name());
                    authConfig.requestMatchers("/rent").hasRole(Role.CUSTOMER.name());
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

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
