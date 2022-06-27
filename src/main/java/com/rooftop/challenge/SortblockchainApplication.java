package com.rooftop.challenge;

import com.rooftop.challenge.Service.OrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SortblockchainApplication
        implements CommandLineRunner {
    final static Logger LOG = LoggerFactory.getLogger(SortblockchainApplication.class);

    public SortblockchainApplication(OrchestratorService orchestrator) {
        this.orchestrator = orchestrator;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SortblockchainApplication.class);
        app.run(args);
    }

    final OrchestratorService orchestrator;
    @Override
    public void run(String... args)  {
        try{
            orchestrator.process();
        }catch(Exception ex){
            LOG.error(ex.getMessage());
        }
    }

}
