package com.jinloes.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

/**
 * Models a pick up call in the elevator control system.
 */
@DataObject
public final class PickUpCall {
    private final PickUpDirection direction;
    private final int floor;

    public PickUpCall() {
        direction = PickUpDirection.UP;
        floor = 0;
    }

    /**
     * A copy constructor.
     *
     * @param pickUpCall pickup call to copy
     */
    public PickUpCall(PickUpCall pickUpCall) {
        this.direction = pickUpCall.direction;
        this.floor = pickUpCall.floor;
    }

    /**
     * Constructor from json object
     *
     * @param jsonObject
     */
    public PickUpCall(JsonObject jsonObject) {
        this.direction = PickUpDirection.fromString(jsonObject.getString("direction"));
        this.floor = jsonObject.getInteger("floor");
    }

    private PickUpCall(PickUpDirection direction, int floor) {
        this.direction = direction;
        this.floor = floor;
    }

    public PickUpDirection getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    /**
     * Static factory for creating a pick up call request.
     *
     * @param direction direction the user wishes to move in
     * @param floor     floor the user call for a pick up from
     * @return {@link PickUpCall} object
     */
    public static PickUpCall of(PickUpDirection direction, int floor) {
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

    public JsonObject toJson() {
        return new JsonObject()
                .put("direction", direction.toString())
                .put("floor", floor);
    }
}
