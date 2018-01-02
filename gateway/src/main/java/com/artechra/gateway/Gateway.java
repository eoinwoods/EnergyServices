package com.artechra.gateway;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component()
public class Gateway {

    private final Logger LOG = Logger.getLogger(Gateway.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    private long id ;

    private ScenarioRepository scenarioRepository;

    public Gateway(@Value("#{appConfig.getNextId()}")long id, ScenarioRepository scenarioRepository) {
        this.id = id ;
        this.scenarioRepository = scenarioRepository ;
    }

    public String invokeServices(String scenarioName) {
        Scenario s = this.scenarioRepository.getScenario(scenarioName) ;
        if (s == null) {
            throw new IllegalArgumentException("Scenario " + scenarioName + " not found") ;
        }
        LOG.info("Gateway running scenario " + scenarioName) ;

        StringBuilder ret = new StringBuilder("|") ;
        for (Invocation i : s.getInvocations()) {
            LOG.info("--> Calling " + i.getServiceName()) ;
            ret.append(i.toString()) ;
            ret.append("|") ;
            switch(i.getServiceName()) {
            case "cpuhog":
                callCpuHog(i) ;
                break ;
            case "memhog":
                callMemHog(i) ;
                break ;
            case "datahog":
                callDataHog(i) ;
                break ;
            default:
                throw new IllegalStateException("Unrecognised service " + i.getServiceName() + " for invocation -- aborted");
            }
        }
        return ret.toString() ;
    }

    private final String CPUHOG_URL = "http://localhost:9001/burncpu?time_msec={time_msec}" ;
    private String callCpuHog(Invocation inv) {
        String timeMsec = inv.getParams().get("time_msec") ;
        String result = this.restTemplate
                .getForObject(CPUHOG_URL, String.class, timeMsec);
        return result;
    }
    private final String MEMHOG_URL = "http://localhost:9002/hogmemory?size_mb={size_mb}" ;
    private String callMemHog(Invocation inv) {
        throw new IllegalStateException("callMemHog() not yet implemented") ;
    }
    private final String DATAHOG_URL = "http://localhost:9002/hogdata?size_mb={size_mb}" ;
    private String callDataHog(Invocation inv) {
        throw new IllegalStateException("callDataHog() not yet implemented") ;
    }
}
