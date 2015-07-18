package com.jinloes.api;

import com.jinloes.model.Direction;

/**
 * An interface for an elevator.
 */
public interface Elevator {
    /**
     * Returns the current floor the elevator is at.
     *
     * @return
     */
    int getCurrentFloor();

    /**
     * Returns the next direction the elevator will take.
     *
     * @return the elevator's direction.
     */
    Direction getDirection();

    /**
     * Moves the elevator up a floor.
     */
    void moveUp();

    /**
     * Moves the elevator down a floor.
     */
    void moveDown();

    /**
     * Adds a destination to the elevator.
     *
     * @param floor destination floor
     */
    void addDestination(int floor);
}
