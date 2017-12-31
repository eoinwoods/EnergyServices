package com.artechra.gateway;

import java.util.List;

public class Scenario {

    private String name ;
    private List<Invocation> invocations ;

    // Note that Jackson needs the default constructor unless annotations are used
    // to name the ctor parameters as the parameter names disappear at compile time
    // so by default Jackson uses the no-args ctor and then reflection on bean
    // properties to construct the object from the JSON
    public Scenario() {}
    public Scenario(final String name, List<Invocation> invocations) {
        this.name = name;
        this.invocations = invocations;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Invocation> getInvocations() {
        return invocations;
    }

    public void setInvocations(final List<Invocation> invocations) {
        this.invocations = invocations;
    }


}
