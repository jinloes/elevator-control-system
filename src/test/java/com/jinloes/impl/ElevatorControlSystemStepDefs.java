package com.jinloes.impl;

import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.model.DoorState;
import com.jinloes.model.PickUpCall;
import com.jinloes.model.PickUpDirection;
import com.jinloes.model.State;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.mockito.Mockito;

import static org.junit.Assert.fail;

/**
 * Step definitions for testing a {@link AsyncElevatorControlSystem}.
 */
public class ElevatorControlSystemStepDefs {
    private ElevatorControlSystem elevatorControlSystem;
    private Elevator elevator = Mockito.mock(Elevator.class);
    private PickUpCall pickUpPickUpCall;
    private int destinationFloor;

    @After
    public void tearDown() {
        Mockito.reset(elevator);
    }

    @Given("^an elevator control system$")
    public void createControlSystem() {
        elevatorControlSystem = new AsyncElevatorControlSystem(elevator);
    }

    @Given("^elevator that is waiting$")
    public void setWaitingExpectation() {
        Mockito.when(elevator.getState()).thenReturn(State.IDLE);
    }

    @Given("^an elevator moving (.+)")
    public void setDirectionExpectation(String direction) {
        Mockito.when(elevator.getState()).thenReturn(State.fromString(direction));
    }

    @Given("^the elevator's expected floor should be (\\d+)$")
    public void setExpectedFloor(int floor) throws Throwable {
        Mockito.when(elevator.getCurrentFloor()).thenReturn(floor);
    }

    @Given("^elevator doors are closed$")
    public void setClosedDoorsExpectation() throws Throwable {
        Mockito.when(elevator.getDoorState()).thenReturn(DoorState.CLOSED);
    }

    @Given("^elevator doors are open$")
    public void setOpenDoorsExpectation() throws Throwable {
        Mockito.when(elevator.getDoorState()).thenReturn(DoorState.OPEN);
    }

    @When("^a user calls for a pick up from floor (\\d+) for direction (.+)$")
    public void callForPickup(int floor, String direction) {
        pickUpPickUpCall = PickUpCall.of(PickUpDirection.fromString(direction), floor);
    }

    @When("^add floor (\\d+) as a destination for the elevator$")
    public void addDestination(int floor) {
        destinationFloor = floor;
    }

    @When("^execute one step in the system$")
    public void executeStep() {
        elevatorControlSystem.step();
    }

    @Then("^the call should be processed without error$")
    public void checkPickUp() {
        elevatorControlSystem.processPickUpCall(pickUpPickUpCall);
    }

    @Then("^an Illegal Argument Exception should be thrown when adding the call$")
    public void checkPickUpException() {
        try {
            elevatorControlSystem.processPickUpCall(pickUpPickUpCall);
            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // We expect an illegal argument exception
        }
    }

    @Then("^the destination should be added to the elevator")
    public void addDestination() {
        elevatorControlSystem.addDestination(destinationFloor);
        Mockito.verify(elevator, Mockito.times(1)).addDestination(destinationFloor);
    }

    @Then("^an Illegal Argument Exception should be thrown when adding the destination$")
    public void checkDestinationException() {
        try {
            elevatorControlSystem.addDestination(destinationFloor);
            fail("An IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // We expect an illegal argument exception
        }
        Mockito.verifyZeroInteractions(elevator);
    }

    @Then("^the elevator should be moved up$")
    public void verifyElevatorMovedUp() {
        Mockito.verify(elevator).step();
    }

    @Then("^the elevator should be moved down$")
    public void verifyElevatorMovedDown() {
        Mockito.verify(elevator).step();
    }

    @Then("^no destination should be added for the elevator$")
    public void verifyNoDestinationsAdded() {
        Mockito.verify(elevator, Mockito.never()).addDestination(Mockito.anyInt());
    }

    @Then("^the user should be picked up$")
    public void checkUserPickedUp() {
    }

    @When("^send request to open the doors$")
    public void openDoorsRequest() throws Throwable {
        elevatorControlSystem.openDoors();
    }

    @Then("^the doors should stay open$")
    public void verifyDoorsOpen() throws Throwable {
        Mockito.verify(elevator).setDoorState(DoorState.OPEN);
    }

    @Then("^the doors should be closed$")
    public void verifyDoorsClosed() throws Throwable {
        Mockito.verify(elevator).setDoorState(DoorState.CLOSED);
    }

    @Given("^the elevator is moving up$")
    public void setElevatorMovingUp() throws Throwable {
        Mockito.when(elevator.getState()).thenReturn(State.MOVING_UP);
    }

    @Given("^the elevator is moving down$")
    public void setElevatorMovingDown() throws Throwable {
        Mockito.when(elevator.getState()).thenReturn(State.MOVING_DOWN);
    }

    @Given("^the elevator is idle$")
    public void setElevatorIdle() throws Throwable {
        Mockito.when(elevator.getState()).thenReturn(State.IDLE);
    }
}
