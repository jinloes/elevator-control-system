package com.jinloes.impl;

import com.google.common.base.Preconditions;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.model.DoorState;
import com.jinloes.model.PickUpCall;
import com.jinloes.model.PickUpDirection;
import com.jinloes.model.State;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Logger;

import static com.jinloes.model.State.IDLE;

/**
 * Created by rr2re on 8/5/2015.
 */
public class ElevatorControlSystemImpl extends AbstractVerticle implements ElevatorControlSystem {
    private static final Logger LOGGER = Logger.getLogger(ElevatorControlSystemImpl.class.toString());
    public static final String ADD_DESTINATION_ADDRESS = "addDestination";
    public static final String PICKUP_ADDRESS = "pickup";
    private Elevator elevator;
    private Queue<PickUpCall> pickUpCallQueue;
    private static final int TOP_FLOOR = 10;

    public ElevatorControlSystemImpl() {
        this(new ElevatorImpl());
    }

    public ElevatorControlSystemImpl(Elevator elevator) {
        this.elevator = elevator;
        this.pickUpCallQueue = new ArrayDeque<>();
    }

    @Override
    public void start() {
        vertx.eventBus().consumer(ADD_DESTINATION_ADDRESS, this::handleDestinationMessage);
        vertx.eventBus().consumer(PICKUP_ADDRESS, this::handlePickUpCall);
    }

    // Message handlers

    private void handleDestinationMessage(Message<Integer> message) {
        addDestination(message.body());
    }

    private void handlePickUpCall(Message<JsonObject> message) {
        JsonObject body = message.body();
        PickUpCall pickUpCall = PickUpCall.of(PickUpDirection.fromString(body.getString("direction")),
                body.getInteger("floor"));
        processPickUpCall(pickUpCall);
    }

    // End message handlers

    @Override
    public void processPickUpCall(PickUpCall pickUpCall) {
        Preconditions.checkArgument(pickUpCall.getFloor() <= TOP_FLOOR);
        switch (elevator.getState()) {
            case IDLE:
                elevator.addDestination(pickUpCall.getFloor());
            case MOVING_UP:
                if (elevator.getCurrentFloor() < pickUpCall.getFloor()
                        && elevator.getDestinationFloor() > pickUpCall.getFloor()
                        && Objects.equals(pickUpCall.getDirection(), PickUpDirection.UP)) {
                    elevator.addDestination(pickUpCall.getFloor());
                } else {
                    pickUpCallQueue.add(pickUpCall);
                }
                break;
            case MOVING_DOWN:
                if (elevator.getCurrentFloor() > pickUpCall.getFloor()
                        && elevator.getDestinationFloor() < pickUpCall.getFloor()
                        && Objects.equals(pickUpCall.getDirection(), PickUpDirection.DOWN)) {
                    elevator.addDestination(pickUpCall.getFloor());
                } else {
                    pickUpCallQueue.add(pickUpCall);
                }
            default:
                pickUpCallQueue.add(pickUpCall);
        }
    }

    @Override
    public void addDestination(int floor) {
        Preconditions.checkArgument(floor <= TOP_FLOOR);
        LOGGER.info("Adding new destination " + floor);
        elevator.addDestination(floor);
    }

    @Override
    public void openDoors() {
        elevator.setDoorState(DoorState.OPEN);
    }

    @Override
    public void step() {
        if (Objects.equals(elevator.getDoorState(), DoorState.CLOSED)) {
            State state = elevator.getState();
            if (Objects.equals(state, IDLE) && !pickUpCallQueue.isEmpty()) {
                elevator.addDestination(pickUpCallQueue.poll().getFloor());
            }
            elevator.step();
        } else {
            elevator.setDoorState(DoorState.CLOSED);
        }
    }
}
