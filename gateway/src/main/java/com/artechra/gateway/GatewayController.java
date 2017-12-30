package com.artechra.gateway;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/invoke")
    public Gateway invoke() {
        return new Gateway(counter.incrementAndGet()) ;
    }
}