package com.jinloes.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Models the direction an elevator is moving in.
 */
public enum Direction {
    /**
     * Indicates the elevator is moving up.
     */
    UP,
    /**
     * Indicates the elevator is moving down.
     */
    DOWN,
    /**
     * Indicates the elevator is waiting.
     */
    WAIT;

    /**
     * Calculates a direction based on the current floor and target floor.
     *
     * @param currentFloor elevator's current floor
     * @param targetFloor  elevator's  target floor
     * @return {@link Direction#DOWN} if the current floor is greater than the target floor.
     * {@link Direction#UP} if the current floor is less than the target floor.
     * {@link Direction#WAIT} if the current floor is equal to the target floor.
     */
    public static Direction calculate(int currentFloor, int targetFloor) {
        if (currentFloor > targetFloor) {
            return DOWN;
        } else if (currentFloor < targetFloor) {
            return UP;
        } else {
            return WAIT;
        }
    }

    /**
     * Creates a direction from a string, which is case insensitive.
     *
     * @param direction direction string
     * @return {@link Direction} if input string matches a direction regardless of case. Null, if null is passed in.
     * @throws IllegalArgumentException see {@link Enum#valueOf(Class, String)}
     */
    public static Direction fromString(String direction) {
        return StringUtils.isNotEmpty(direction) ? valueOf(direction.toUpperCase()) : null;
    }
}
