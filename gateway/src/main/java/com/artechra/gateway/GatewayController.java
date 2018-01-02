package com.artechra.gateway;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @Autowired
    private Gateway gateway;

    @Value("${scenario.file}")
    private String scenarioFileName ;

    @RequestMapping("/invoke/{scenario}")
    public String invoke(@PathVariable(value="scenario") String scenario) {
        if (scenario == null || scenario.length() == 0) {
            throw new IllegalArgumentException("/invoke must be called with a scenario name (e.g. /invoke/s1)");
        }
        assert this.gateway != null ;

        return this.gateway.invokeServices(scenario) ;
    }
}