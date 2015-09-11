package com.jinloes.impl

import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static spock.util.matcher.HamcrestSupport.expect

class ElevatorSpec extends Specification {
    def "The system should have support for a default elevator"() {
        given: "An elevator"
        def elevator = new ElevatorImpl()
        expect: "the elevator should start at floor 0"
        expect elevator.getCurrentFloor(), is(0)
    }

    def "An elevator should be able to move up a floor"() {
        given: "An elevator"
        def elevator = new ElevatorImpl()
        when: "move the elevator up"
        elevator.moveUp()
        then: "the elevator should move a floor up"
        expect elevator.getCurrentFloor(), is(1)
    }
}