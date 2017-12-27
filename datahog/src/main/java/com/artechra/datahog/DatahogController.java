package com.artechra.datahog;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatahogController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hogdata")
    public Datahog hogdata(@RequestParam(value="mem_mb", defaultValue="1") int mem_mb) {
        return new Datahog(counter.incrementAndGet(), mem_mb) ;
    }
}

