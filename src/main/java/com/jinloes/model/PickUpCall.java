package com.jinloes.model;

import com.google.common.base.Preconditions;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Models a pick up call in the elevator control system.
 */
public final class PickUpCall {
    private static final Set<Direction> ALLOWED_PICKUP_OPTIONS = EnumSet.of(Direction.UP, Direction.DOWN);
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
     * @param floor floor the user call for a pick up from
     * @return {@link PickUpCall} object
     */
    public static PickUpCall of(Direction direction, int floor) {
        Preconditions.checkArgument(ALLOWED_PICKUP_OPTIONS.contains(direction), "Direction :" +
                direction.toString() + " is not allowed for pickup");
        return new PickUpCall(direction, floor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PickUpCall that = (PickUpCall) o;
        return Objects.equals(floor, that.floor) &&
                Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, floor);
    }
}
