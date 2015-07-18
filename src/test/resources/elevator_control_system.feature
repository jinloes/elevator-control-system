Feature: Elevator control system

  Background:
    Given an elevator control system

  Scenario: it should be able to handle a call for a pickup
    When a user calls for a pick up from floor 5 for direction up
    Then the call should be processed without error

  Scenario: it should fail when a call for pick up is made on a floor greater than the top floor
    When a user calls for a pick up from floor 11 for direction down
    Then an Illegal Argument Exception should be thrown when adding the call

  Scenario: it should be able to add a new destination to the elevator
    When add destination to floor 5 for the elevator
    Then the destination should be added to the elevator

  Scenario: it should be able to add a new destination to the elevator
    When add destination to floor 11 for the elevator
    Then an Illegal Argument Exception should be thrown when adding the destination

  Scenario: it should be keep a waiting elevator waiting if there are no pick up calls
    Given elevator that is waiting
    When execute one step in the system
    Then no destination should be added for the elevator

  Scenario: it should be able to tell an waiting elevator to pick up a user
    Given elevator that is waiting
    And a user calls for a pick up from floor 5 for direction up
    Then the call should be processed without error
    When execute one step in the system
    Then the destination should be added to the elevator

  Scenario: it should move an elevator up a floor towards its target destination
    Given an elevator moving up
    When execute one step in the system
    Then the elevator should be moved up

  Scenario: it should move an elevator down a floor towards its target destination
    Given an elevator moving down
    When execute one step in the system
    Then the elevator should be moved down

  Scenario: it should ignore duplicate calls for a pick up
    Given elevator that is waiting
    When a user calls for a pick up from floor 1 for direction up
    Then the call should be processed without error
    And a user calls for a pick up from floor 1 for direction up
    Then the call should be processed without error
    When execute one step in the system
    Then the destination should be added to the elevator

  Scenario: in should pick up people on the way to a destination
    Given an elevator moving up
    And the elevator's expected floor should be 1
    When a user calls for a pick up from floor 1 for direction UP
    Then the call should be processed without error
    When execute one step in the system
    Then the user should be picked up