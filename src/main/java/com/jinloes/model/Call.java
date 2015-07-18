package com.jinloes.model;

import com.jinloes.api.Direction;

/**
 * Created by jinloes on 7/18/15.
 */
public final class Call {
    private final Direction direction;
    private final int floor;

    private Call(Direction direction, int floor) {
        this.direction = direction;
        this.floor = floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    public static final Call of(Direction direction, int floor) {
        return new Call(direction, floor);
    }
}
