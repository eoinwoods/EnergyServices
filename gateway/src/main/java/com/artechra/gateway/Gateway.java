package com.artechra.gateway;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

public class Gateway {

    private final Logger LOG = Logger.getLogger(Gateway.class.getName());

    private RestTemplate restTemplate = new RestTemplate();
    private long id ;
    private String scenarioName ;

    private ScenarioRepository scenarioRepository;

    public Gateway(long id, String scenario, ScenarioRepository scenarioRepository) {
        this.id = id ;
        this.scenarioName = scenario ;
        this.scenarioRepository = scenarioRepository ;
    }

    public String getContent() {
        Scenario s = this.scenarioRepository.getScenario(this.scenarioName) ;
        if (s == null) {
            throw new IllegalArgumentException("Scenario " + this.scenarioName + " not found") ;
        }
        LOG.info("Gateway running scenario " + this.scenarioName) ;

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
