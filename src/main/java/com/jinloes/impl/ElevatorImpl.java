package com.jinloes.impl;

import com.jinloes.api.Elevator;
import com.jinloes.model.Direction;
import com.jinloes.model.DoorState;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of {@link Elevator}.
 */
public class ElevatorImpl implements Elevator {
    private int currentFloor;
    private final Queue<Integer> destinationQueue;
    private DoorState doorState;

    public ElevatorImpl() {
        this(0);
    }

    public ElevatorImpl(int currentFloor) {
        this.currentFloor = currentFloor;
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
    public Direction getDirection() {
        if (destinationQueue.isEmpty()) {
            return Direction.IDLE;
        }
        return Direction.calculate(currentFloor, destinationQueue.peek());
    }

    @Override
    public void moveUp() {
        currentFloor++;
        System.out.println("Elevator moved up to floor " + currentFloor);
    }

    @Override
    public void moveDown() {
        currentFloor--;
        System.out.println("Elevator moved down to floor " + currentFloor);
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
}
