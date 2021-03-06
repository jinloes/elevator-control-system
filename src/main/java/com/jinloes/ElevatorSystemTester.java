package com.jinloes;

import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.impl.ElevatorControlSystemImpl;
import com.jinloes.impl.ElevatorImpl;
import com.jinloes.model.PickUpDirection;
import com.jinloes.model.PickUpCall;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Demonstration of how the elevator system can be used.
 */
public class ElevatorSystemTester {
    private static final ScheduledExecutorService EXECUTOR_SERVICE =
            Executors.newSingleThreadScheduledExecutor();

    /*public static void main(String[] args) throws InterruptedException {
        try {
            Elevator elevator = new ElevatorImpl();
            ElevatorControlSystem controlSystem = new ElevatorControlSystemImpl(elevator);
            scheduleCalls(controlSystem);
            runSimulation(controlSystem);
        } finally {
            EXECUTOR_SERVICE.shutdown();
        }
    }*/

    private static void scheduleCalls(ElevatorControlSystem controlSystem) {
        // Make duplicate calls and add a destination
        EXECUTOR_SERVICE.schedule((Runnable) () -> {
            controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 5));
            controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 5));
            controlSystem.addDestination(8);
        }, 2, TimeUnit.SECONDS);
        /// Add another duplicate call and a destination that's on the way of the previous one.
        EXECUTOR_SERVICE.schedule((Runnable) () -> {
            controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.DOWN, 5));
            controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 6));
        }, 3, TimeUnit.SECONDS);
        // Switch direction of the elevator.
        EXECUTOR_SERVICE.schedule((Runnable) () ->
                controlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 1)), 4, TimeUnit.SECONDS);
        EXECUTOR_SERVICE.schedule((Runnable) () -> controlSystem.addDestination(7),
                4, TimeUnit.SECONDS);
    }

    private static void runSimulation(ElevatorControlSystem controlSystem)
            throws InterruptedException {
        long start = System.currentTimeMillis();
        long end = start + TimeUnit.SECONDS.toMillis(30);
        while (System.currentTimeMillis() < end) {
            controlSystem.step();
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            System.out.println();
        }
    }
}
