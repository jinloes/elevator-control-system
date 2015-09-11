package com.jinloes.impl

import com.jinloes.api.Elevator
import com.jinloes.model.DoorState
import com.jinloes.model.PickUpCall
import com.jinloes.model.PickUpDirection
import com.jinloes.model.State
import spock.lang.Specification

/**
 * Unit tests for {@link ElevatorControlSystemImpl}.
 */
class ElevatorControlSystemSpec extends Specification {
    def "A control system should be able to handle a call for pickup"() {
        given: "An elevator moving up"
        def elevator = Mock(Elevator)
        elevator.getState() >> State.MOVING_UP
        and: "an elevator control system"
        def controlSystem = new ElevatorControlSystemImpl(elevator)
        when: "a user calls for a pick up from floor 5"
        controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 5))
        then: "the call should be processed without error"
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
