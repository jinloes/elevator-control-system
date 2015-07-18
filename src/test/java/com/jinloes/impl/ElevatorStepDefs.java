package com.jinloes.impl;

import com.jinloes.api.Elevator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * Created by jinloes on 7/18/15.
 */
public class ElevatorStepDefs {
    private Elevator elevator;

    @Given("^An elevator starting at floor (\\d+)$")
    public void createElevator(int floor) throws Throwable {
        elevator = new ElevatorImpl(floor);
    }

    @When("^move the elevator up$")
    public void moveElevatorUp() throws Throwable {
        elevator.moveUp();
    }

    @Then("^the elevator's floor should be (\\d+)$")
    public void checkElevatorFloor(int expectedFloor) throws Throwable {
        assertEquals(elevator.getCurrentFloor(), expectedFloor);
    }
}
