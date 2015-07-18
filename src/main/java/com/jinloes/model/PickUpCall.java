package com.jinloes.model;

import com.jinloes.api.Direction;

/**
 * Models a pick up call in the elevator control system.
 */
public final class PickUpCall {
    private final Direction direction;
    private final int floor;

    private PickUpCall(Direction direction, int floor) {
        this.direction = direction;
        this.floor = floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    /**
     * Static factory for creating a pick up call request.
     *
     * @param direction direction that the user wishes to travel in
     * @param floor     floor the user call for a pick up from
     * @return {@link PickUpCall} object
     */
    public static final PickUpCall of(Direction direction, int floor) {
        return new PickUpCall(direction, floor);
    }
}
