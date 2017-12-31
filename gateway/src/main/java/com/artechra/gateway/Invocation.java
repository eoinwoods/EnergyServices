package com.artechra.gateway;

import java.util.Map;
import java.util.Objects;

import org.springframework.context.annotation.Bean;

class Invocation {
    private String serviceName ;
    private Map<String, String> params ;

    public Invocation() {}
    public Invocation(final String serviceName, final Map<String, String> params) {
        this.serviceName = serviceName;
        this.params = params;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(final Map<String, String> params) {
        this.params = params;
    }

    @Override public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Invocation that = (Invocation) o;
        return Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(params, that.params);
    }

    @Override public int hashCode() {

        return Objects.hash(serviceName, params);
    }
}
