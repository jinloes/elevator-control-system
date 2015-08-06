package com.jinloes.impl;

import com.jinloes.api.Elevator;
import com.jinloes.model.State;
import com.jinloes.model.DoorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 * Implementation of {@link Elevator}.
 */
public class ElevatorImpl implements Elevator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElevatorImpl.class);
    private int currentFloor;
    private final Queue<Integer> destinationQueue;
    private int destinationFloor;
    private DoorState doorState;

    public ElevatorImpl() {
        this(0);
    }

    public ElevatorImpl(int currentFloor) {
        this.currentFloor = currentFloor;
        this.destinationFloor = currentFloor;
        destinationQueue = new ArrayDeque<>();
        this.doorState = DoorState.CLOSED;
    }

    @Override
    public DoorState getDoorState() {
        return doorState;
    }

    @Override
    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public State getState() {
        if (destinationFloor == currentFloor) {
            return State.IDLE;
        }
        return State.calculate(currentFloor, destinationFloor);
    }

    @Override
    public void moveUp() {
        currentFloor++;
        LOGGER.info("Elevator moved up to floor " + currentFloor);
    }

    @Override
    public void moveDown() {
        currentFloor--;
        LOGGER.info("Elevator moved down to floor " + currentFloor);
    }

    @Override
    public void addDestination(int floor) {
        destinationQueue.add(floor);
    }

    @Override
    public boolean containsDestination(int floor) {
        return destinationQueue.contains(floor);
    }

    @Override
    public int removeDestination(int floor) {
        final int[] numRemoved = {0};
        destinationQueue.removeIf(integer -> {
            if (integer == floor) {
                numRemoved[0]++;
                return true;
            } else {
                return false;
            }
        });
        return numRemoved[0];
    }

    @Override
    public void step() {
        switch (getState()) {
            case MOVING_UP:
                LOGGER.info("Moving elevator up");
                moveUp();
                break;
            case MOVING_DOWN:
                LOGGER.info("Moving elevator down");
                moveDown();
                break;
            case IDLE:
                LOGGER.info("Elevator is idle.");
                break;
        }
        if (Objects.equals(currentFloor, destinationFloor)) {
            if (!destinationQueue.isEmpty()) {
                destinationFloor = destinationQueue.poll();
            }
        }
        if (destinationFloor == currentFloor && !destinationQueue.isEmpty()) {
            destinationFloor = destinationQueue.poll();
        }
    }

    @Override
    public int getDestinationFloor() {
        return destinationFloor;
    }
}
