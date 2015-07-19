package com.jinloes.impl;

import com.jinloes.api.Elevator;
import com.jinloes.model.Direction;
import com.jinloes.model.DoorState;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        assertEquals(Direction.IDLE, elevator.getDirection());
    }

    @Then("^it's current floor should be (\\d+)$")
    public void checkCurrentFloor(int expectedFloor) throws Throwable {
        assertEquals(expectedFloor, elevator.getCurrentFloor());
    }

    @And("^the elevator's doors should be open$")
    public void the_elevator_s_doors_should_be_open() throws Throwable {
        assertEquals(DoorState.OPEN, elevator.getDoorState());
    }

    @When("^remove floor (\\d+) from the destination queue$")
    public void removeFloor(int floor) throws Throwable {
        elevator.removeDestination(floor);
    }

    @Then("^the elevator should not have floor (\\d+) as a destination$")
    public void checkNotContainsFloor(int floor) throws Throwable {
        assertFalse(elevator.containsDestination(floor));
    }
}
