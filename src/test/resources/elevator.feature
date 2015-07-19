Feature: An elevator

  Scenario: Create a default elevator
    Given a default elevator
    Then it's current floor should be 0

  Scenario: it should be able to move up a floor
    Given an elevator starting at floor 0
    When move the elevator up a floor
    Then the elevator's floor should be 1

  Scenario: it should be able to move down a floor
    Given an elevator starting at floor 5
    When move the elevator down a floor
    Then the elevator's floor should be 4

  Scenario Outline: it should return it's current direction
    Given an elevator starting at floor <floor>
    And destination <destination floor>
    Then the direction should be <direction>

    Examples:
      | floor | destination floor | direction |
      | 0     | 10                | UP        |
      | 10    | 0                 | DOWN      |

  Scenario: it should wait if it does not have a destination
    Given an elevator starting at floor 0
    Then the direction should be idle

  Scenario: it should remove all destinations for a given floor
    Given a default elevator
    When add destination to floor 1
    When add destination to floor 1
    When remove floor 1 from the destination queue
    Then the elevator should not have floor 1 as a destination