package com.jinloes.impl;

import com.jinloes.api.Direction;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.model.PickUpCall;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.Assert.fail;

/**
 * Step definitions for testing a {@link ElevatorControlSystemImpl}.
 */
public class ElevatorControlSystemStepDefs {
    @Rule
    private ExpectedException expectedException = ExpectedException.none();
    private ElevatorControlSystem elevatorControlSystem;
    private Elevator elevator = Mockito.mock(Elevator.class);
    private PickUpCall pickUpPickUpCall;

    @After
    public void tearDown() {
        expectedException = ExpectedException.none();
    }

    @Given("^an elevator control system$")
    public void createControlSystem() throws Throwable {
        elevatorControlSystem = new ElevatorControlSystemImpl(elevator);
    }

    @When("^a user calls for a pick up from floor (\\d+) for direction (.+)$")
    public void callForPickup(int floor, String direction) throws Throwable {
        pickUpPickUpCall = PickUpCall.of(Direction.fromString(direction), floor);
    }

    @Then("^the system should be able to handle the call$")
    public void checkNoException() throws Throwable {
        elevatorControlSystem.callForPickup(pickUpPickUpCall);
    }

    @Then("^the control system should throw an Illegal Argument Exception$")
    public void checkIllegalArgumentException() throws Throwable {
        try {
            elevatorControlSystem.callForPickup(pickUpPickUpCall);
            fail("An IllegalArgumentException should have been thrown");
        } catch(IllegalArgumentException e) {
            // We expect an illegal argument exception
        }
    }
}
