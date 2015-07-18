package com.jinloes.impl;

import com.jinloes.api.Direction;
import com.jinloes.api.Elevator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * Step definitions for testing a {@link Elevator} object.
 */
public class ElevatorStepDefs {
    private Elevator elevator;

    @Given("^an elevator starting at floor (\\d+)$")
    public void createElevator(int floor) throws Throwable {
        elevator = new ElevatorImpl(floor);
    }

    @Given("^destination (\\d+)$")
    public void addDestination(Integer floor) throws Throwable {
        if (floor != null) {
            elevator.addDestination(floor);
        }
    }

    @When("^move the elevator up a floor$")
    public void moveElevatorUp() throws Throwable {
        elevator.moveUp();
    }

    @When("^move the elevator down a floor$")
    public void moveElevatorDown() throws Throwable {
        elevator.moveDown();
    }

    @Then("^the elevator's floor should be (\\d+)$")
    public void checkElevatorFloor(int expectedFloor) throws Throwable {
        assertEquals(expectedFloor, elevator.getCurrentFloor());
    }

    @Then("^the direction should be (.+)$")
    public void the_direction_should_be_direction(String direction) throws Throwable {
        Direction expected = Direction.fromString(direction);
        assertEquals(expected, elevator.getDirection());
    }
}
