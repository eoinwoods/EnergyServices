package com.artechra.gateway;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/invoke/{scenario}")
    public Gateway invoke(@PathVariable(value="scenario") String scenario) {
        if (scenario == null || scenario.length() == 0) {
            throw new IllegalArgumentException("/invoke must be called with a scenario name (e.g. /invoke/s1)");
        }
        return new Gateway(counter.incrementAndGet(), scenario) ;
    }
}