package com.jinloes.api;

/**
 * Created by jinloes on 7/17/15.
 */
public interface Elevator {
    int getCurrentFloor();

    Status getStatus();

    void setStatus(Status status);

    Direction getDirection();

    void moveUp();

    void moveDown();

    void addDestination(int floor);

    boolean hasDestination();
}
