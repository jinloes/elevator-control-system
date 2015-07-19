package com.jinloes.impl;

import com.google.common.base.Preconditions;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.model.Direction;
import com.jinloes.model.DoorState;
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
    private final Queue<DoorState> doorCall;
    private final Elevator elevator;

    public ElevatorControlSystemImpl(Elevator elevator) {
        this(10, elevator);
    }

    public ElevatorControlSystemImpl(int topFloor, Elevator elevator) {
        this.topFloor = topFloor;
        this.pickUpCalls = new ArrayDeque<>();
        this.doorCall = new ArrayDeque<>();
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
    public void openDoors() {
        if (Direction.IDLE.equals(elevator.getDirection())) {
            doorCall.add(DoorState.OPEN);
        } else {
            System.out.println("Cannot open doors on a moving elevator.");
        }
    }

    @Override
    public void step() {
        System.out.println("Elevator is at floor " + elevator.getCurrentFloor());
        switch (elevator.getDoorState()) {
            // if the doors are open try and close them this step
            case OPEN:
                if (doorCall.isEmpty()) {
                    elevator.setDoorState(DoorState.CLOSED);
                    System.out.println("Doors are open. Closing them");
                } else {
                    //TODO(jinloes) might want to put a timer here in the future so doors don't
                    // get stuck open forever
                    elevator.setDoorState(doorCall.poll());
                    System.out.println("Leaving doors open.");
                }
                break;
            default:
                processStep();

        }

    }

    private void processStep() {
        switch (elevator.getDirection()) {
            case UP:
                System.out.println("Moving elevator up");
                elevator.moveUp();
                int currentFloor = elevator.getCurrentFloor();
                PickUpCall currentFloorPickUpCall = PickUpCall.of(Direction.UP, currentFloor);
                pickUpPeople(currentFloorPickUpCall, elevator);
                letPeopleOff(currentFloor, elevator);
                break;
            case DOWN:
                System.out.println("Moving elevator down");
                elevator.moveDown();
                currentFloor = elevator.getCurrentFloor();
                currentFloorPickUpCall = PickUpCall.of(Direction.DOWN, currentFloor);
                pickUpPeople(currentFloorPickUpCall, elevator);
                letPeopleOff(currentFloor, elevator);
                break;
            case IDLE:
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
     * Lets people off the elevator. This lets people off that pressed the button for a floor
     * between the current floor and the destination.
     *
     * @param currentFloor current floor
     * @param elevator     elevator
     */
    private void letPeopleOff(int currentFloor, Elevator elevator) {
        int peopleLetOff = elevator.removeDestination(currentFloor);
        if (peopleLetOff > 0) {
            elevator.setDoorState(DoorState.OPEN);
        }
    }

    /**
     * We're checking to see if there were any pick up calls from this floor.
     * Removing the call is seen as picking up that user. The user can add a destination after
     * that point.
     *
     * @param currentCall a pick up call that matches the current floor
     */
    private void pickUpPeople(PickUpCall currentCall, Elevator elevator) {
        pickUpCalls.removeIf(pickUpCall -> {
            if (pickUpCall.equals(currentCall)) {
                elevator.setDoorState(DoorState.OPEN);
                return true;
            }
            return false;
        });
    }
}