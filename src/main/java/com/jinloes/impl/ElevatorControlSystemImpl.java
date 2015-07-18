package com.jinloes.impl;

import com.jinloes.api.Direction;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.api.Status;
import com.jinloes.model.PickUpCall;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of {@link ElevatorControlSystem}.
 */
public final class ElevatorControlSystemImpl implements ElevatorControlSystem {
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
        if (callFloor > topFloor) {
            throw new IllegalArgumentException(
                    String.format("Floor: %s is greater than the top floor: %s", callFloor,
                            topFloor));
        }
        if (!pickUpCalls.contains(pickUpCall)) {
            pickUpCalls.add(pickUpCall);
        }
    }

    @Override
    public void addDestination(int floor) {
        elevator.addDestination(floor);
    }

    public void step() {
        switch (elevator.getStatus()) {
            case IDLE:
                if (!pickUpCalls.isEmpty()) {
                    elevator.addDestination(pickUpCalls.poll().getFloor());
                }
                break;
            case IN_USE:
                switch (elevator.getDirection()) {
                    case UP:
                        elevator.moveUp();
                        PickUpCall currentFloorPickUpCall = PickUpCall.of(Direction.UP,
                                elevator.getCurrentFloor());
                        if (pickUpCalls.contains(currentFloorPickUpCall)) {
                            pickUpCalls.remove(currentFloorPickUpCall);
                        }
                        break;
                    case DOWN:
                        elevator.moveDown();
                        currentFloorPickUpCall = PickUpCall.of(Direction.DOWN,
                                elevator.getCurrentFloor());
                        if (pickUpCalls.contains(currentFloorPickUpCall)) {
                            pickUpCalls.remove(currentFloorPickUpCall);
                        }
                        break;
                }
                break;
        }
        if (!elevator.hasDestination()) {
            elevator.setStatus(Status.IDLE);
        }
    }
}