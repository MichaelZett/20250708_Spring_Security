package de.zettsystems.netzfilm.user.application;

import de.zettsystems.netzfilm.user.domain.BaseUser;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final BaseUser baseUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(
                username,
                baseUser.getPassword(),
                baseUser.getAuthorities() == null ? Collections.emptyList() : baseUser.getAuthorities()
        );
    }
}
