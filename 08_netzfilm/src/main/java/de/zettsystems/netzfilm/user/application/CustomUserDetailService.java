package de.zettsystems.netzfilm.user.application;

public interface CustomUserDetailService {
    void failedAttempt(String name);
}
