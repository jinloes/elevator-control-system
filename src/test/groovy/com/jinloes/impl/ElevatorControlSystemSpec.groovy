package com.jinloes.impl

import com.jinloes.api.Elevator
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
    @Issue("http://my.issues.org/FOO-2")
    def "A control system should be able to handle a call for pickup"() {
        given: "An elevator moving up"
            def elevator = Mock(Elevator)
            elevator.getState() >> State.MOVING_UP
        and: "an elevator control system"
            def controlSystem = new ElevatorControlSystemImpl(elevator)
        when: "a user calls for a pick up from floor 5 in up direction"
            controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 5))
        then: "the call should be processed without error"
            notThrown(Throwable)
    }

    def "A control system should pick up a person that wants to go travel to a lower floor"() {
        given: "An elevator moving down from floor 11"
            def elevator = Mock(Elevator)
            elevator.getState() >> State.MOVING_DOWN
            elevator.getCurrentFloor() >> 11
        and: "an elevator control system"
            def controlSystem = new ElevatorControlSystemImpl(elevator)
        when: "a user calls for a pick up from floor 5 in down direction"
            controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.DOWN, 5))
        then: "the call should be processed without error"
            notThrown(Throwable)
    }

    def "A system should keep an elevator waiting if there are no pickup calls"() {
        given: "An elevator waiting"
            def elevator = Mock(Elevator)
            elevator.getState() >> State.IDLE
        and: "closed doors"
            elevator.getDoorState() >> DoorState.CLOSED
        and: "an elevator control system with the elevator"
            def controlSystem = new ElevatorControlSystemImpl(elevator)
        when: "one step of the system is executed"
            controlSystem.step()
        then: "no destination should be added to the elevator"
            0 * elevator.addDestination(_ as int)
    }
}
