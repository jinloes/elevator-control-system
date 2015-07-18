package com.jinloes.api;

/**
 * Created by jinloes on 7/17/15.
 */
public enum Direction {
    UP, DOWN, WAIT;

    public static Direction calculate(int currentFloor, int targetFloor) {
        if(currentFloor > targetFloor) {
            return UP;
        } else if (currentFloor < targetFloor) {
            return DOWN;
        } else {
            return WAIT;
        }
    }
}
