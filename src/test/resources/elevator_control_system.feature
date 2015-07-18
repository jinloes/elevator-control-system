Feature: Elevator control system

  Scenario: An elevator control system should be able to handle a call for a pickup
    Given an elevator control system
    When a user calls for a pick up from floor 5 for direction up
    Then the system should be able to handle the call

  Scenario: An elevator control system should fail when a call for pick up is made on a floor greater than the top floor
    Given  an elevator control system
    When a user calls for a pick up from floor 11 for direction down
    Then the control system should throw an Illegal Argument Exception