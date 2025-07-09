package de.zettsystems.netzfilm.user.application;

import de.zettsystems.netzfilm.user.domain.BaseUser;
import de.zettsystems.netzfilm.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class LoginAttemptService {
    private final UserRepository userRepository;

    @EventListener
    @Transactional
    public void onFail(AuthenticationFailureBadCredentialsEvent e) {
        userRepository.findByUsername(e.getAuthentication().getName()).ifPresent(BaseUser::incrementFailedAttempt);
    }

}
