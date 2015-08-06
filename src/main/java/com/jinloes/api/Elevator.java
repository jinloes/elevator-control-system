package com.jinloes.api;

import com.jinloes.model.State;
import com.jinloes.model.DoorState;

/**
 * An interface for an elevator.
 */
public interface Elevator {
    /**
     * Returns the door state of the elevator.
     *
     * @return door state
     */
    DoorState getDoorState();

    void setDoorState(DoorState doorState);

    /**
     * Returns the current floor the elevator is at.
     *
     * @return
     */
    int getCurrentFloor();

    /**
     * Returns the elevator's state.
     *
     * @return the elevator's state.
     */
    State getState();

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

    /**
     * Checks if the elevator containst he destination.
     *
     * @param floor floor to check
     * @return true if the elevator has the floor as a destination, otherwise false
     */
    boolean containsDestination(int floor);

    /**
     * Removes a destination from the elevator
     *
     * @param floor floor to remove
     */
    int removeDestination(int floor);

    void step();

    int getDestinationFloor();
}
