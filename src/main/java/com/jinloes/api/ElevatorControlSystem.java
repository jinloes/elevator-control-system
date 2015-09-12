package com.jinloes.api;

import com.jinloes.model.PickUpCall;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;

/**
 * Interface for a system that manages an elevator system.
 */
@ProxyGen
public interface ElevatorControlSystem {

    static ElevatorControlSystem createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(ElevatorControlSystem.class, vertx, address);
        // Alternatively, you can create the proxy directly using:
        // return new ProcessorServiceVertxEBProxy(vertx, address);
        // The name of the class to instantiate is the service interface + `VertxEBProxy`.
        // This class is generated during the compilation
    }

    /**
     * Submits a call for pick up to the control system.
     *
     * @param pickUpCall {@link PickUpCall} request
     */
    void processPickUpCall(PickUpCall pickUpCall);

    /**
     * Add a destination to the elevator subsystem.
     *
     * @param floor destination floor
     */
    void addDestination(int floor);

    void openDoors();

    /**
     * Iterates through one step of the elevator system.
     */
    void step();
}
