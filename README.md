# Elevator Control System

    The elevator system is split primarily into two main classes, ElevatorControlSystem and  
    Elevator. ElevatorControlSystem is responsible for handling input and making decisions about 
    what the elevators should do next. Elevator is just a model class that processes state change 
    requests sent by the control system. The Elevator class is meant to be a "dumb" class that only
    what it is told to do by the control system. This makes the control system the engine of the 
    overall system.
    
Requirements
============

1. Java 8+
2. Gradle 2.3 or later (for building)
    * https://gradle.org/downloads/
    
Run Tests
=========

    - cd to project root directory
    - gradle cucumber
    - see src/test/sources/*.feature for unit test cases for the system that describe the behavior
    
Output from the cucumber tests will be in the console.
    
    
Simulation
==========

Build using 

    gradle build
    java -jar build/libs/elevator-control-system-1.0-SNAPSHOT.jar
    
An example of how the system can be simulated is found in ElevatorSystemTester.
The tester has commands scheduled to be added by background threads at different intervals. 
The class outputs it's state at each tick. 

The elevator executes the following steps:

1. receive a pickup call for floor 5 to move up
2. move to floor 5 and open the doors
3. receive a pick up request for floor 6 to move down
4. receive a pick up request for floor 7
5. move to floor 7 and open the doors
6. continue to floor 8 and open the doors
7. move to floor 6 and open the doors
8. move to floor 1 and open the doors

Algorithm
=========

The algorithm is as follows:

- If the doors are open, check for any door commands and execute them. 
- If there are none, close the doors and try and move the elevator.
- To move the elevator, the direction needs to be determined.
    - An elevator will move to it's destination picking up anyone that wants to travel in the same 
        direction along the way or lets people off the elevator if they pressed a floor in between 
        the current and destination.
    - Once the elevator has reached the destination, it lets the people off and waits until a pick 
    up call is assigned to it or moves to a new destination if it still has other destinations 
    to move to.
    - The elevator will remain idle if there are no pick up commands to service.