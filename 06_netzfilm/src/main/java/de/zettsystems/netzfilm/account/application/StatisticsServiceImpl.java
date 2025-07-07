package de.zettsystems.netzfilm.account.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final RestTemplate accountingRestTemplate;

    @Override
    public Collection<String> retrieveStatistics() {
        ResponseEntity<List<String>> r = accountingRestTemplate.exchange("/api/statistics", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {
        });
        return r.getBody();
    }
}
