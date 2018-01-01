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
        Invocation i1 = invocations.get(1) ;
        assertEquals("Wrong service for invocation 1", "service2", i1.getServiceName());
        Invocation i2 = invocations.get(2) ;
        assertEquals("Wrong service for invocation 1", "service3", i2.getServiceName()) ;
        Invocation i3 = invocations.get(3) ;
        assertEquals("Wrong service for invocation 1", "service4", i3.getServiceName()) ;
    }

    @Test
    public void testMultiStepScenarioAllowsExplicitExtractionOfInvocationData() {
        ScenarioRepository sr = new ScenarioRepository("multi_step_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        Invocation i0 = scenarios.get(0).getInvocations().get(0) ;
        assertEquals("Wrong service for invocation 0", "service1", i0.getServiceName());
        assertEquals("Wrong value for parameter p1 in invocation 0", "v1", i0.getParams().get("p1")) ;
        assertEquals("Wrong value for parameter p2 in invocation 0", "v2", i0.getParams().get("p2")) ;
    }

    @Test
    public void testMultiStepScenarioReturnsCorrectObjectForInvocationItem() {
        ScenarioRepository sr = new ScenarioRepository("multi_step_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        Invocation i1 = scenarios.get(0).getInvocations().get(1) ;
        assertEquals("Wrong invocation i1",
                new Invocation("service2",
                        new HashMap<String,String>(){{
                            put("p3", "v3");
                            put("p4", "v4");
                        }}), i1) ;
    }

    @Test
    public void testThatEmptyParamsArrayResultsInEmptyJavaList() {
        ScenarioRepository sr = new ScenarioRepository("multi_step_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        Invocation i2 = scenarios.get(0).getInvocations().get(2) ;
        assertEquals("Unexpected invocation i2 params", 0, i2.getParams().size());
    }

    @Test
    public void testThatMissingParamsArrayResultsInNullJavaAttribute() {
        ScenarioRepository sr = new ScenarioRepository("multi_step_scenario.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        Invocation i3 = scenarios.get(0).getInvocations().get(3) ;
        assertNull("Unexpected invocation i3 params", i3.getParams());
    }

    @Test
    public void testThatMultipleScenariosInOneFileCanBeExtracted() {
        ScenarioRepository sr = new ScenarioRepository("multi_scenarios.json") ;
        List<Scenario> scenarios = sr.getScenarios() ;
        assertEquals("Wrong number of scenarios", 3, scenarios.size()) ;
        assertEquals("Wrong scenario 1 name", "scenario1", scenarios.get(0).getName()) ;
        assertEquals("Wrong scenario 2 name", "scenario2", scenarios.get(1).getName()) ;
        assertEquals("Wrong scenario 3 name", "scenario3", scenarios.get(2).getName()) ;
        Invocation s2i0 = scenarios.get(1).getInvocations().get(0) ;
        assertEquals("Wrong service name for s2.i0", "s2", s2i0.getServiceName());
    }

    @Test
    public void testThatSingleScenarioCanBeRetrievedByName() {
        ScenarioRepository sr = new ScenarioRepository("multi_scenarios.json") ;
        Scenario s2 = sr.getScenario("scenario2") ;
        assertEquals("Wrong name for s2", "scenario2", s2.getName()) ;
        assertEquals("Wrong invocation list length for s2", 1, s2.getInvocations().size()) ;
    }

    @Test
    public void testThatUnknownScenarioNameReturnsNull() {
        ScenarioRepository sr = new ScenarioRepository("multi_scenarios.json") ;
        Scenario s1 = sr.getScenario("DOESNOTEXIST") ;
        assertNull("Unexpected scenario returned", s1) ;

    }


}
