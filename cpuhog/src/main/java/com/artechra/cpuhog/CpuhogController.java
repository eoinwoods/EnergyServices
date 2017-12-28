package com.artechra.cpuhog;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CpuhogController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/burncpu")
    public Cpuhog burncpu(@RequestParam(value="time_msec", defaultValue="1000") int time_msec) {
        return new Cpuhog(counter.incrementAndGet(), time_msec) ;
    }
}

