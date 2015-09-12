package com.jinloes.impl;

import com.jinloes.api.ElevatorControlSystem;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;

/**
 * A verticle bridge to an {@link ElevatorControlSystem}.
 */
public class ElevatorControlSystemVerticle extends AbstractVerticle {
    public static final String ELEVATOR_SYSTEM_ADDRESS = "vertx.elevator_system";
    private ElevatorControlSystem elevatorControlSystem;

    @Override
    public void start() {
        elevatorControlSystem = new ElevatorControlSystemImpl();
        ProxyHelper.registerService(ElevatorControlSystem.class, vertx, elevatorControlSystem, ELEVATOR_SYSTEM_ADDRESS);
    }
}
