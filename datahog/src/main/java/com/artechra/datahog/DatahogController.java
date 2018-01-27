package com.artechra.datahog;

import java.util.concurrent.atomic.AtomicLong;

import com.mongodb.MongoClient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatahogController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hogdata")
    public Datahog hogdata(@RequestParam(value="data_mb", defaultValue="1") int mem_mb) {
        MongoClient mongoClient = new MongoClient() ;
        String databaseName = "database" ;
        return new Datahog(counter.incrementAndGet(), mem_mb, mongoClient) ;
    }
}

