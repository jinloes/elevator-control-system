package com.jinloes.api;

import com.jinloes.model.PickUpCall;

/**
 * Created by jinloes on 7/17/15.
 */
public interface ElevatorControlSystem {

    /**
     * Submits a call for pick up to the control system.
     *
     * @param pickUpCall {@link PickUpCall} request
     */
    void callForPickup(PickUpCall pickUpCall);

    void addDestination(int floor);
}
