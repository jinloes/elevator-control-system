package com.jinloes.impl;

import com.jinloes.AppConfig;
import com.jinloes.api.ElevatorControlSystem;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A verticle bridge to an {@link ElevatorControlSystem}.
 */
@Component
@SpringVerticle(springConfig = AppConfig.class, autoremoveOtherSpringVerticles = false)
public class ElevatorControlSystemVerticle extends AbstractVerticle {
    public static final String ELEVATOR_SYSTEM_ADDRESS = "vertx.elevator_system";
    private final ElevatorControlSystem elevatorControlSystem;

    @Autowired
    public ElevatorControlSystemVerticle(ElevatorControlSystem elevatorControlSystem) {
        this.elevatorControlSystem = elevatorControlSystem;
    }

    @Override
    public void start() {
        ProxyHelper.registerService(ElevatorControlSystem.class, vertx, elevatorControlSystem, ELEVATOR_SYSTEM_ADDRESS);
    }
}
