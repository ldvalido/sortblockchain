package com.rooftop.challenge.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public interface CircuitBreakerRegistryProviderService {
    void createRegistry();
    CircuitBreakerRegistry getRegistry();
}
