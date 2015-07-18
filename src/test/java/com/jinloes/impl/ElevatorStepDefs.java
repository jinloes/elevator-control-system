package com.jinloes.impl;

import com.jinloes.api.Elevator;
import com.jinloes.model.Direction;
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

    @Given("^a default elevator$")
    public void a_default_elevator() throws Throwable {
        elevator = new ElevatorImpl();
    }

    @When("^move the elevator up a floor$")
    public void moveElevatorUp() throws Throwable {
        elevator.moveUp();
    }

    @When("^move the elevator down a floor$")
    public void moveElevatorDown() throws Throwable {
        elevator.moveDown();
    }

    @When("^add destination to floor (\\d+)$")
    public void addDestinationFloor(int floor) throws Throwable {
        elevator.addDestination(floor);
    }

    @Then("^the elevator's floor should be (\\d+)$")
    public void checkElevatorFloor(int expectedFloor) throws Throwable {
        assertEquals(expectedFloor, elevator.getCurrentFloor());
    }

    @Then("^the direction should be (.+)$")
    public void checkDirection(String direction) throws Throwable {
        Direction expected = Direction.fromString(direction);
        assertEquals(expected, elevator.getDirection());
    }

    @Then("^the elevator should be waiting$")
    public void checkDirection() throws Throwable {
        assertEquals(Direction.WAIT, elevator.getDirection());
    }

    @Then("^it's current floor should be (\\d+)$")
    public void checkCurrentFloor(int expectedFloor) throws Throwable {
        assertEquals(expectedFloor, elevator.getCurrentFloor());
    }
}
