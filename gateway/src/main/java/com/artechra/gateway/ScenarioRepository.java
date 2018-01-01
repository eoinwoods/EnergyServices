package com.artechra.gateway;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

@Component
public class ScenarioRepository {

    private final String fileName;
    private final TypeReference<List<Scenario>> tr = new TypeReference<List<Scenario>>(){} ;
    private final ObjectMapper mapper = new ObjectMapper() ;
    private final InputStream scenarioStream ;

    public ScenarioRepository(String fileName) {
        this.fileName = fileName;
        this.scenarioStream = ScenarioRepository.class.getResourceAsStream("/"+fileName) ;
        if (this.scenarioStream == null) {
            throw new IllegalArgumentException("Could not open data file '" + fileName + "'") ;
        }
    }
    public List<Scenario> getScenarios() {
        try {
            List<Scenario> scenarios = this.mapper.readValue(this.scenarioStream, this.tr);
            return scenarios ;
        } catch(java.io.IOException ioe) {
            throw new IllegalArgumentException("Could not read scenarios from '" + this.fileName + "'", ioe) ;
        }
    }

    public Scenario getScenario(String scenarioName) {
        Scenario ret = null ;
        for (Scenario s : this.getScenarios()) {
            if (s.getName().equals(scenarioName)) {
                ret = s ;
                break ;
            }
        }
        return ret ;
    }
}
