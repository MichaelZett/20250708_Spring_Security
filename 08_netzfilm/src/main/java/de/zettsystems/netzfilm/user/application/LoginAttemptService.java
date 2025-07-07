package de.zettsystems.netzfilm.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoginAttemptService {

    private final UserDetailsService userDetailsService;

    @EventListener(AuthenticationFailureBadCredentialsEvent.class)
    public void onFail(AuthenticationFailureBadCredentialsEvent e) {
        ((CustomUserDetailService) userDetailsService).failedAttempt(e.getAuthentication().getName());
    }

}
