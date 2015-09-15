package com.jinloes.impl

import com.jinloes.api.Elevator
import com.jinloes.api.ElevatorControlSystem
import com.jinloes.model.DoorState
import com.jinloes.model.PickUpCall
import com.jinloes.model.PickUpDirection
import com.jinloes.model.State
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

/**
 * Unit tests for {@link ElevatorControlSystemImpl}.
 */
@Issue("http://my.issues.org/FOO-1")
@Title("Elevator Control System Spec")
@Narrative(
        """
As a user
I want to be able to interact with an elevator through a control interface
So that I can ride a elevator
""")
class ElevatorControlSystemSpec extends Specification {
    def Elevator elevator
    def elevatorControlSystem

    def setup() {
        elevator = Mock(Elevator)
        elevatorControlSystem = new ElevatorControlSystemImpl(elevator)
    }
    @Issue("http://my.issues.org/FOO-2")
    def "A control system should be able to handle a call for pickup"() {
        given: "An elevator moving up"
            elevator.getState() >> State.MOVING_UP
        when: "a user calls for a pick up from floor 5 in up direction"
            elevatorControlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 5))
        then: "the call should be processed without error"
            notThrown(Throwable)
    }

    def "A control system should pick up a person that wants to go travel to a lower floor"() {
        given: "An elevator moving down from floor 11"
            elevator.getState() >> State.MOVING_DOWN
            elevator.getCurrentFloor() >> 11
        when: "a user calls for a pick up from floor 5 in down direction"
            elevatorControlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.DOWN, 5))
        then: "the call should be processed without error"
            notThrown(Throwable)
    }

    def "A system should keep an elevator waiting if there are no pickup calls"() {
        given: "An elevator waiting"
            elevator.getState() >> State.IDLE
        and: " with closed doors"
            elevator.getDoorState() >> DoorState.CLOSED
        when: "one step of the system is executed"
            elevatorControlSystem.step()
        then: "no destination should be added to the elevator"
            0 * elevator.addDestination(_ as int)
    }

    def "A system should add a destination to an elevator"() {
        when: "receive a request to add a destination"
            elevatorControlSystem.addDestination(5)
        then: "the destination should be added to the elevator"
            1 * elevator.addDestination(5)
    }

    def "A system should not add a destination greater than the top floor"() {
        when: "receive a request to add a destination"
            elevatorControlSystem.addDestination(11)
        then: "the destination should not be added to the elevator"
            0 * elevator.addDestination(11)
            thrown(IllegalArgumentException)
    }

    def "A system should tell and elevator to open its doors"() {
        when: "receive a message to open an elevator's doors"
            elevatorControlSystem.openDoors();
        then: "the elevator should open it's doors"
            1 * elevator.setDoorState(DoorState.OPEN);
    }
}
