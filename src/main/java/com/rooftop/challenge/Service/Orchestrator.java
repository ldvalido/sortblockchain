package com.rooftop.challenge.Service;

import com.rooftop.challenge.DTO.BlockDTO;
import com.rooftop.challenge.DTO.TokenDTO;
import com.rooftop.challenge.Helpers.CircuitBreakerHelper;
import com.rooftop.challenge.Helpers.HttpHelper;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public  class Orchestrator
        implements OrchestratorService {
    final static Logger LOG = LoggerFactory.getLogger(Orchestrator.class);

    @Value("${rooftop_tokenUrl}")
    String tokenUrl;

    @Value("${rooftop_blockUrl}")
     String blockUrl;

    final static HttpHelper httpHelper = new HttpHelper();
    final SortService sortService;
    final CircuitBreakerRegistryProviderService cbService;

    public Orchestrator(SortService sortService, CircuitBreakerRegistryProviderService cbService) {
        this.sortService = sortService;
        this.cbService = cbService;
    }

    @Override
    public void process() {
        LOG.info("Starting");
        cbService.createRegistry();

        TokenDTO t = CircuitBreakerHelper.apply("getToken",
                () -> httpHelper.get(tokenUrl, TokenDTO.class),
                cbService);
        LOG.info(t.getToken());

        BlockDTO block = CircuitBreakerHelper.apply("blockDTO",
                () ->  httpHelper.get(String.format(blockUrl, t.getToken()), BlockDTO.class),
                        cbService);

        LOG.info(String.join(",", block.getData()));

        String[] blocksSorted = sortService.check(block.getData(), t.getToken());
        LOG.info(String.join(",", blocksSorted));

        boolean verify = sortService.verify(blocksSorted, t.getToken());
        LOG.info("Verify: " + verify);
        LOG.info("Finishing");
    }
}
