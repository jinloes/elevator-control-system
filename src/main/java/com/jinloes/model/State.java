package com.jinloes.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Models the state of an elevator.
 */
public enum State {
    /**
     * Indicates the elevator is moving up.
     */
    MOVING_UP,
    /**
     * Indicates the elevator is moving down.
     */
    MOVING_DOWN,
    /**
     * Indicates the elevator is waiting.
     */
    IDLE;

    /**
     * Calculates a the state based on the elevator's current floor and target floor.
     *
     * @param currentFloor elevator's current floor
     * @param targetFloor  elevator's  target floor
     * @return {@link State#MOVING_DOWN} if the current floor is greater than the target floor.
     * {@link State#MOVING_UP} if the current floor is less than the target floor.
     * {@link State#IDLE} if the current floor is equal to the target floor.
     */
    public static State calculate(int currentFloor, int targetFloor) {
        if (currentFloor > targetFloor) {
            return MOVING_DOWN;
        } else if (currentFloor < targetFloor) {
            return MOVING_UP;
        } else {
            return IDLE;
        }
    }

    /**
     * Creates a state from a string, which is case insensitive.
     *
     * @param direction direction string
     * @return {@link State} if input string matches a direction regardless of case. Null, if null is passed in.
     * @throws IllegalArgumentException see {@link Enum#valueOf(Class, String)}
     */
    public static State fromString(String direction) {
        return StringUtils.isNotEmpty(direction) ? valueOf(direction.toUpperCase()) : null;
    }
}
