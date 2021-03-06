package com.artechra.gateway;

import java.util.List;
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

    private long id;

    private ScenarioRepository scenarioRepository;

    @Value("#{appConfig.getCpuhogUrl()}")
    private String cpuhogUrl;
    @Value("#{appConfig.getMemhogUrl()}")
    private String memhogUrl;
    @Value("#{appConfig.getDatahogUrl()}")
    private String datahogUrl;
    @Value("#{appConfig.getFiohogUrl()}")
    private String fiohogUrl;

    public Gateway(@Value("#{appConfig.getNextId()}") long id, ScenarioRepository scenarioRepository) {
        this.id = id;
        this.scenarioRepository = scenarioRepository;
    }

    public String invokeServices(String scenarioName) {
        Scenario s = this.scenarioRepository.getScenario(scenarioName);
        if (s == null) {
            throw new IllegalArgumentException("Scenario " + scenarioName + " not found");
        }
        int repetitions = s.getRepetitions();
        if (repetitions <= 0) {
            LOG.info("Found repetition value of " + repetitions + " -- setting to 1") ;
            repetitions = 1 ;
        }
        int pauseTimeMsec = s.getPauseTimeMsec() ;
        String repetitionClause = (repetitions > 1 ? " " + repetitions + " times with " +
                pauseTimeMsec + "ms pause between" : "") ;
        LOG.info("Gateway running scenario " + scenarioName + repetitionClause) ;

        StringBuilder ret = new StringBuilder("||");
        for (int i=1; i <= repetitions; i++) {
            LOG.info("Scenario " + scenarioName + " iteration " + i) ;
            ret.append(performInvocations((s.getInvocations()))) ;
            ret.append("|| ") ;
            try {
                Thread.sleep(pauseTimeMsec);
            } catch(InterruptedException ie) {
                LOG.info("Interrupted while sleeping: " + ie) ;
            }
        }
        return ret.toString();
    }

    private String performInvocations(final List<Invocation> invocations) {
        StringBuilder ret = new StringBuilder("|");
        for (Invocation i : invocations) {
            LOG.info("--> Calling " + i.getServiceName());
            ret.append(i.toString());
            ret.append("|");
            switch (i.getServiceName()) {
            case "cpuhog":
                callCpuHog(i);
                break;
            case "memhog":
                callMemHog(i);
                break;
            case "datahog":
                callDataHog(i);
                break;
            case "fiohog":
                callFioHog(i);
                break;
            default:
                throw new IllegalStateException(
                        "Unrecognised service " + i.getServiceName() + " for invocation -- aborted");
            }
        }
        return ret.toString();
    }

    private String callCpuHog(Invocation inv) {
        String timeMsec = inv.getParams().get("time_msec");
        String invocationUrl = this.cpuhogUrl + "?time_msec={time_msec}" ;
        String result = this.restTemplate
                .getForObject(invocationUrl, String.class, timeMsec);
        return result;
    }

    private String callMemHog(Invocation inv) {
        throw new IllegalStateException("callMemHog() not yet implemented");
    }

    private String callDataHog(Invocation inv) {
        String dataMb = inv.getParams().get("data_mb");
        String invocationUrl = this.datahogUrl + "?data_mb={data_mb}" ;
        String result = this.restTemplate
                .getForObject(invocationUrl, String.class, dataMb);
        return result;
    }

    private String callFioHog(Invocation inv) {
        String dataMb = inv.getParams().get("data_mb");
        String invocationUrl = this.fiohogUrl + "?data_mb={data_mb}";
        String result = this.restTemplate
                .getForObject(invocationUrl, String.class, dataMb);
        return result;
    }
}
