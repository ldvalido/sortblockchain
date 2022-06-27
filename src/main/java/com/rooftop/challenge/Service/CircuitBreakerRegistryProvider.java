package com.rooftop.challenge.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
public class CircuitBreakerRegistryProvider
        implements CircuitBreakerRegistryProviderService {

    CircuitBreakerRegistry circuitBreakerRegistry;
    @Override
    public void createRegistry() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();

        this.circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
    }

    public CircuitBreakerRegistry getRegistry() {
        return circuitBreakerRegistry;
    }

}
