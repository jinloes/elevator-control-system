package com.jinloes.impl;

import com.google.common.base.Preconditions;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.model.Direction;
import com.jinloes.model.PickUpCall;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of {@link ElevatorControlSystem}.
 */
public final class ElevatorControlSystemImpl implements ElevatorControlSystem {
    private static final String REQUEST_FLOOR_TOO_LARGE_MSG = "Floor: %s is greater than the top " +
            "floor: %s";
    private final int topFloor;
    private final Queue<PickUpCall> pickUpCalls;
    private final Elevator elevator;

    public ElevatorControlSystemImpl(Elevator elevator) {
        this(10, elevator);
    }

    public ElevatorControlSystemImpl(int topFloor, Elevator elevator) {
        this.topFloor = topFloor;
        this.pickUpCalls = new ArrayDeque<>();
        this.elevator = elevator;
    }

    @Override
    public void callForPickup(PickUpCall pickUpCall) {
        int callFloor = pickUpCall.getFloor();
        Preconditions.checkArgument(callFloor <= topFloor,
                String.format(REQUEST_FLOOR_TOO_LARGE_MSG, callFloor, topFloor));
        if (!pickUpCalls.contains(pickUpCall)) {
            System.out.println("Pick up call received for floor " + pickUpCall.getFloor() +
                    " and direction " + pickUpCall.getDirection());
            pickUpCalls.add(pickUpCall);
        } else {
            System.out.println("Duplicate pick up call detected, ignoring");
        }
    }

    @Override
    public void addDestination(int floor) {
        Preconditions.checkArgument(floor <= topFloor,
                String.format(REQUEST_FLOOR_TOO_LARGE_MSG, floor, topFloor));
        elevator.addDestination(floor);
        System.out.println("Added destination for floor " + floor);
    }

    @Override
    public void step() {
        System.out.println("Elevator is at floor " + elevator.getCurrentFloor());
        switch (elevator.getDirection()) {
            case UP:
                System.out.println("Moving elevator up");
                elevator.moveUp();
                PickUpCall currentFloorPickUpCall = PickUpCall.of(Direction.UP,
                        elevator.getCurrentFloor());
                pickUpPeople(currentFloorPickUpCall);
                break;
            case DOWN:
                System.out.println("Moving elevator down");
                elevator.moveDown();
                currentFloorPickUpCall = PickUpCall.of(Direction.DOWN,
                        elevator.getCurrentFloor());
                pickUpPeople(currentFloorPickUpCall);
                break;
            case WAIT:
                if (!pickUpCalls.isEmpty()) {
                    PickUpCall call = pickUpCalls.poll();
                    int floor = call.getFloor();
                    elevator.addDestination(floor);
                    System.out.println("Pick up call sent to elevator. Moving elevator to floor "
                            + floor);
                } else {
                    System.out.println("No one to pick up. Elevator will continue waiting.");
                }
        }
    }

    /**
     * We're checking to see if there were any pick up calls from this floor.
     * Removing the call is seen as picking up that user. The user can add a destination after
     * that point.
     *
     * @param currentCall a pick up call that matches the current floor
     */
    private void pickUpPeople(PickUpCall currentCall) {
        int peoplePickedUp = 0;
        while (pickUpCalls.contains(currentCall)) {
            pickUpCalls.remove(currentCall);
            peoplePickedUp++;
        }
        System.out.println("Number of people picked up: " + peoplePickedUp);
    }
}