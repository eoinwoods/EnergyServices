package com.artechra.gateway;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;

public class Gateway {


    RestTemplate restTemplate = new RestTemplate();
    public Gateway(long id, String scenario) {



    }

    public String getContent() {
        callCpuHog(1000) ;
        callCpuHog(2000) ;
        return "Called Cpuhog twice (1000, 2000)";
    }

    class Request {
        public String name ;
        public Map<String,String> params ;

    }

    private final String CPUHOG_URL = "http://localhost:9001/burncpu?time_msec={time_msec}" ;
    private String callCpuHog(long timeMsec) {
        String result = this.restTemplate
                .getForObject(CPUHOG_URL, String.class, timeMsec + "");
        return result;
    }

}
