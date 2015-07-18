package com.jinloes.impl;

import com.jinloes.api.Elevator;
import com.jinloes.model.Direction;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of {@link Elevator}.
 */
public class ElevatorImpl implements Elevator {
    private int currentFloor;
    private final Queue<Integer> destinationQueue;

    public ElevatorImpl() {
        this(0);
    }

    public ElevatorImpl(int currentFloor) {
        this.currentFloor = currentFloor;
        destinationQueue = new ArrayDeque<>();
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public Direction getDirection() {
        if (destinationQueue.isEmpty()) {
            return Direction.WAIT;
        }
        return Direction.calculate(currentFloor, destinationQueue.peek());
    }

    @Override
    public void moveUp() {
        currentFloor++;
        System.out.println("Elevator moved up to floor " + currentFloor);
        checkDestinationReached();
    }

    @Override
    public void moveDown() {
        currentFloor--;
        System.out.println("Elevator moved down to floor " + currentFloor);
        checkDestinationReached();
    }

    private void checkDestinationReached() {
        if (!destinationQueue.isEmpty() && currentFloor == destinationQueue.peek()) {
            System.out.println("Reached destination floor " + currentFloor);
            destinationQueue.poll();
        }
    }

    @Override
    public void addDestination(int floor) {
        destinationQueue.add(floor);
    }
}
