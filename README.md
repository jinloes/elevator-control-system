# Elevator Control System

The elevator system is split primarily into two main classes ElevatorControlSystem and  
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
    
Run Tests (Cucumber)
====================

- cd to project root directory
- gradle cucumber
- see src/test/sources/*.feature for unit test cases for the system that describe the behavior
- report in build/html-cucumber-report
    
Output from the cucumber tests will be in the console.

Run Tests (Spock)
=================

- gradle clean test
- report is in build/spock-reports
    
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

Elevator Management
-------------------

- If the elevator's doors are open, the system will close the elevator's doors
- If the elevator's doors are closed
    - If the elevator is idle, then the elevator will see if there are and pick up calls
    - Then the elevator control system will execute on step for the elevator

Pickup Call Management
----------------------

- If the elevator is idle, assign the call to it
- If the elevator is moving up and the pick up floor is less than the destination floor but greater than the current 
floor and the elevator is moving up, assign the call to it
- If the elevator is moving down and the pickup floor is greater than the destination floor but less than the current 
floor and the elevator is moving down, assign the call to it
