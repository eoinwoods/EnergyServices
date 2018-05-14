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

    @Value("${cpuhog.url}")
    private String cpuhogUrl ;

    @Value("${memhog.url}")
    private String memhogUrl ;

    @Value("${datahog.url}")
    private String datahogUrl ;

    @Value("${fiohog.url}")
    private String fiohogUrl ;

    public String getScenarioFileName() {
        return this.scenarioFileName ;
    }

    public long getNextId() {
        return counter.incrementAndGet() ;
    }

    public String getCpuhogUrl() {
        return this.cpuhogUrl;
    }

    public String getMemhogUrl() {
        return this.memhogUrl;
    }

    public String getDatahogUrl() {
        return this.datahogUrl ;
    }

    public String getFiohogUrl() {
        return this.fiohogUrl ;
    }

    @Bean
    public ScenarioRepository getScenarioRepository() {
        assert this.scenarioFileName != null ;
        return new ScenarioRepository(this.scenarioFileName) ;
    }
}
