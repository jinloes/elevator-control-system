package com.jinloes.impl;

import com.google.common.base.Preconditions;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.model.DoorState;
import com.jinloes.model.PickUpCall;
import com.jinloes.model.PickUpDirection;
import com.jinloes.model.State;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Logger;

import static com.jinloes.model.State.IDLE;

/**
 * Implementation of {@link ElevatorControlSystem}.
 */
public class ElevatorControlSystemImpl implements ElevatorControlSystem {
    private static final Logger LOGGER = Logger.getLogger(ElevatorControlSystemImpl.class.toString());
    private Elevator elevator;
    private Queue<PickUpCall> pickUpCallQueue;
    private static final int TOP_FLOOR = 10;

    public ElevatorControlSystemImpl() {
        this(new ElevatorImpl());
    }

    public ElevatorControlSystemImpl(Elevator elevator) {
        this.elevator = elevator;
        this.pickUpCallQueue = new ArrayDeque<>();
    }

    @Override
    public void processPickUpCall(PickUpCall pickUpCall) {
        Preconditions.checkArgument(pickUpCall.getFloor() <= TOP_FLOOR);
        switch (elevator.getState()) {
            case IDLE:
                elevator.addDestination(pickUpCall.getFloor());
            case MOVING_UP:
                if (elevator.getCurrentFloor() < pickUpCall.getFloor()
                        && elevator.getDestinationFloor() > pickUpCall.getFloor()
                        && Objects.equals(pickUpCall.getDirection(), PickUpDirection.UP)) {
                    elevator.addDestination(pickUpCall.getFloor());
                } else {
                    pickUpCallQueue.add(pickUpCall);
                }
                break;
            case MOVING_DOWN:
                if (elevator.getCurrentFloor() > pickUpCall.getFloor()
                        && elevator.getDestinationFloor() < pickUpCall.getFloor()
                        && Objects.equals(pickUpCall.getDirection(), PickUpDirection.DOWN)) {
                    elevator.addDestination(pickUpCall.getFloor());
                } else {
                    pickUpCallQueue.add(pickUpCall);
                }
            default:
                pickUpCallQueue.add(pickUpCall);
        }
    }

    @Override
    public void addDestination(int floor) {
        Preconditions.checkArgument(floor <= TOP_FLOOR);
        LOGGER.info("Adding new destination " + floor);
        elevator.addDestination(floor);
    }

    @Override
    public void openDoors() {
        elevator.setDoorState(DoorState.OPEN);
    }

    @Override
    public void step() {
        if (Objects.equals(elevator.getDoorState(), DoorState.CLOSED)) {
            State state = elevator.getState();
            if (Objects.equals(state, IDLE) && !pickUpCallQueue.isEmpty()) {
                elevator.addDestination(pickUpCallQueue.poll().getFloor());
            }
            elevator.step();
        } else {
            elevator.setDoorState(DoorState.CLOSED);
        }
    }
}
