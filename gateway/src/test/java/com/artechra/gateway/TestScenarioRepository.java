package com.artechra.gateway;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class TestScenarioRepository {

    @Test
    public void testSingleScenarioIsReadCorrectly() {
        ScenarioRepository sr = new ScenarioRepository("single_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        assertEquals("Expected single scenario", 1, scenarios.size());
        assertEquals("Wrong name for scenario", "scenario1", scenarios.get(0).getName()) ;
    }

    @Test
    public void testMultiStepScenarioIsReadCorrectly() {
        ScenarioRepository sr = new ScenarioRepository("multi_step_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        assertEquals("Expected single scenario", 1, scenarios.size());
        assertEquals("Wrong name for scenario", "multi-step-scenario", scenarios.get(0).getName()) ;
        assertEquals("Wrong number of steps", 4, scenarios.get(0).getInvocations().size()) ;
    }
    @Test
    public void testMultiStepScenarioHasCorrectInvocations() {
        ScenarioRepository sr = new ScenarioRepository("multi_step_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        Scenario s = scenarios.get(0) ;
        assertEquals("Wrong name for scenario", "multi-step-scenario", s.getName()) ;
        List<Invocation> invocations = s.getInvocations() ;
        Invocation i0 = invocations.get(0) ;
        assertEquals("Wrong service for invocation 0", "service1", i0.getServiceName());
        assertEquals("Wrong value for parameter p1 in invocation 0", "v1", i0.getParams().get("p1")) ;
        assertEquals("Wrong value for parameter p2 in invocation 0", "v2", i0.getParams().get("p2")) ;

        assertEquals("Wrong invocation i1",
                new Invocation("service2",
                        new HashMap<String,String>(){{
                            put("p3", "v3");
                            put("p4", "v4");
                        }}), invocations.get(1)) ;
        Invocation i2 = invocations.get(2) ;
        assertEquals("Wrong invocation i2 serviceName", "service3", i2.getServiceName()) ;
        assertEquals("Unexpected invocation i2 params", 0, i2.getParams().size());

        assertNull("Unexpected invocation i3 params", invocations.get(3).getParams());
    }
}
