package com.artechra.burner;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BurnerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/burncpu")
    public Burner greeting(@RequestParam(value="time_msec", defaultValue="1000") int time_msec) {
        return new Burner(counter.incrementAndGet(), time_msec) ;
    }
}

