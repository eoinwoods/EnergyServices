package com.artechra.memhog;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemhogController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hogmemory")
    public Memhog hogmemory(@RequestParam(value="mem_mb", defaultValue="1") int mem_mb,
                            @RequestParam(value="duration_msec", defaultValue="10000") int duration_msec) {
        return new Memhog(counter.incrementAndGet(), mem_mb, duration_msec) ;
    }
}

