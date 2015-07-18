package com.jinloes.impl;

import com.jinloes.api.Direction;
import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.api.Status;
import com.jinloes.model.Call;

import java.util.*;

/**
 * Created by jinloes on 7/17/15.
 */
public final class ElevatorControlSystemImpl implements ElevatorControlSystem {
    private final int topFloor;
    private final Queue<Call> calls;
    private final Elevator elevator;


    public ElevatorControlSystemImpl(int topFloor, Elevator elevator) {
        this.topFloor = topFloor;
        this.calls = new ArrayDeque<>();
        this.elevator = elevator;
    }

    @Override
    public void call(Call call) {
        int callFloor = call.getFloor();
        if (callFloor > topFloor) {
            throw new IllegalArgumentException(String.format("Floor: %s is greater than the top floor: %s", callFloor,
                    topFloor));
        }
        if (callFloor < 0) {
            throw new IllegalArgumentException("Floor cannot be less than 0.");
        }
        if(!calls.contains(call)) {
            calls.add(call);
        }
    }

    @Override
    public void addDestination(int floor) {
        elevator.addDestination(floor);
    }

    public void step() {
        switch (elevator.getStatus()) {
            case IDLE:
                if (!calls.isEmpty()) {
                    elevator.addDestination(calls.poll().getFloor());
                }
                break;
            case IN_USE:
                switch (elevator.getDirection()) {
                    case UP:
                        elevator.moveUp();
                        Call currentFloorCall = Call.of(Direction.UP, elevator.getCurrentFloor());
                        if(calls.contains(currentFloorCall)) {
                            calls.remove(currentFloorCall);
                        }
                        break;
                    case DOWN:
                        elevator.moveDown();
                        currentFloorCall = Call.of(Direction.DOWN, elevator.getCurrentFloor());
                        if(calls.contains(currentFloorCall)) {
                            calls.remove(currentFloorCall);
                        }
                        break;
                }
                break;
        }
        if(!elevator.hasDestination()) {
            elevator.setStatus(Status.IDLE);
        }
    }
}