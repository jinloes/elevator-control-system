package com.jinloes;

import com.jinloes.api.Elevator;
import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.impl.ElevatorControlSystemImpl;
import com.jinloes.impl.ElevatorImpl;
import com.jinloes.model.Direction;
import com.jinloes.model.PickUpCall;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Demonstration of how the elevator system can  be used.
 */
public class ElevatorSystemTester {
    private static final ScheduledExecutorService EXECUTOR_SERVICE =
            Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws InterruptedException {
        try {
            Elevator elevator = new ElevatorImpl();
            ElevatorControlSystem controlSystem = new ElevatorControlSystemImpl(elevator);
            EXECUTOR_SERVICE.schedule((Runnable) () -> {
                controlSystem.callForPickup(PickUpCall.of(Direction.UP, 5));
                controlSystem.callForPickup(PickUpCall.of(Direction.UP, 5));
                controlSystem.addDestination(8);
            }, 2, TimeUnit.SECONDS);
            EXECUTOR_SERVICE.schedule((Runnable) () -> {
                controlSystem.callForPickup(PickUpCall.of(Direction.UP, 5));
                controlSystem.callForPickup(PickUpCall.of(Direction.DOWN, 6));
            }, 3, TimeUnit.SECONDS);
            EXECUTOR_SERVICE.schedule((Runnable) () ->
                    controlSystem.callForPickup(PickUpCall.of(Direction.UP, 1)), 4, TimeUnit.SECONDS);

            long start = System.currentTimeMillis();
            long end = start + TimeUnit.SECONDS.toMillis(30);
            while (System.currentTimeMillis() < end) {
                controlSystem.step();
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
        } finally {
            EXECUTOR_SERVICE.shutdown();
        }
    }
}
