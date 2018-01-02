package com.artechra.gateway;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("appConfig")
public class AppConfiguration {

    private final AtomicLong counter = new AtomicLong();

    @Value("${scenario.file}")
    private String scenarioFileName ;

    public String getScenarioFileName() {
        return this.scenarioFileName ;
    }

    public long getNextId() {
        return counter.incrementAndGet() ;
    }

    @Bean
    public ScenarioRepository getScenarioRepository() {
        assert this.scenarioFileName != null ;
        return new ScenarioRepository(this.scenarioFileName) ;
    }
}
