package com.artechra.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class ScenarioRepository {

    private final String fileName;
    private final TypeReference<List<Scenario>> tr = new TypeReference<List<Scenario>>() {
    };
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<Scenario> scenarioList;

    public ScenarioRepository(String fileName) {
        this.fileName = fileName;
        try (InputStream scenarioStream = ScenarioRepository.class.getResourceAsStream("/" + fileName)) {
            if (scenarioStream == null) {
                throw new IllegalArgumentException("Could not open data file '" + fileName + "'");
            }
            this.scenarioList = this.mapper.readValue(scenarioStream, this.tr);
        } catch(IOException ioe) {
            throw new IllegalArgumentException("Could not read scenarios from file " + fileName, ioe) ;
        }
    }

    public List<Scenario> getScenarios() {
        return this.scenarioList ;
    }

    public Scenario getScenario(String scenarioName) {
        Scenario ret = null;
        for (Scenario s : this.getScenarios()) {
            if (s.getName().equals(scenarioName)) {
                ret = s;
                break;
            }
        }
        return ret;
    }
}
