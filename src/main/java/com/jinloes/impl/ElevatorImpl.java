package com.jinloes.impl;

import com.jinloes.api.Direction;
import com.jinloes.api.Elevator;
import com.jinloes.api.Status;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of {@link Elevator}.
 */
public class ElevatorImpl implements Elevator {
    private int currentFloor;
    private final Queue<Integer> destinationQueue;
    private Status status;

    public ElevatorImpl(int currentFloor) {
        this.currentFloor = currentFloor;
        this.status = Status.IDLE;
        destinationQueue = new ArrayDeque<>();
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
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
    }

    @Override
    public void moveDown() {
        currentFloor--;
    }

    @Override
    public void addDestination(int floor) {
        destinationQueue.add(floor);
    }

    @Override
    public boolean hasDestination() {
        return !destinationQueue.isEmpty();
    }
}
