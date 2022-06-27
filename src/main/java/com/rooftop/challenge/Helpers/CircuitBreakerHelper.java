package com.rooftop.challenge.Helpers;

import com.rooftop.challenge.DTO.BlockDTO;
import com.rooftop.challenge.Service.CircuitBreakerRegistryProviderService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class CircuitBreakerHelper {
    final static Logger LOG = LoggerFactory.getLogger(CircuitBreakerHelper.class);

    public static <T> T apply (String cbKey, Supplier<T> fnc, CircuitBreakerRegistryProviderService service){

        //TODO: Remove coupling with CircuitBreaker
        Supplier<T> decorateSupplier = CircuitBreaker.decorateSupplier(
                service.getRegistry().circuitBreaker(cbKey),
                fnc
        );

        T returnValue = Try.ofSupplier(decorateSupplier).recover(throwable -> {
            LOG.error(throwable.getMessage());
            return null;
        }).get();

        return returnValue;
    }
}
