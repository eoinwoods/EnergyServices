package com.artechra.fiohog;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FiohogController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hogdata")
    public Fiohog hogdata(@RequestParam(value = "data_mb", defaultValue = "1") int mem_mb) {
        return new Fiohog(counter.incrementAndGet(), mem_mb);
    }
}

