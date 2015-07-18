package com.jinloes.api;

import com.jinloes.model.PickUpCall;

/**
 * Interface for a system that manages an elevator system.
 */
public interface ElevatorControlSystem {

    /**
     * Submits a call for pick up to the control system.
     *
     * @param pickUpCall {@link PickUpCall} request
     */
    void callForPickup(PickUpCall pickUpCall);

    /**
     * Add a destination to the elevator subsystem.
     *
     * @param floor destination floor
     */
    void addDestination(int floor);

    /**
     * Iterates through one step of the elevator system.
     */
    void step();
}
