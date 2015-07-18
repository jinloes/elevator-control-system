Feature: An elevator

  Scenario: An elevator should be able to move up
    Given An elevator starting at floor 0
    When move the elevator up
    Then the elevator's floor should be 1