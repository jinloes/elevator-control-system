Feature: An elevator

  Scenario: An elevator should be able to move up a floor
    Given an elevator starting at floor 0
    When move the elevator up a floor
    Then the elevator's floor should be 1

  Scenario: An elevator should be able to move down a floor
    Given an elevator starting at floor 5
    When move the elevator down a floor
    Then the elevator's floor should be 4

  Scenario Outline: An elevator should return it's current direction
    Given an elevator starting at floor <floor>
    And destination <destination floor>
    Then the direction should be <direction>

    Examples:
      | floor | destination floor | direction |
      | 0     | 10                | UP        |
      | 10    | 0                 | DOWN      |

  Scenario: An elevator should wait if it does not have a destination
    Given an elevator starting at floor 0
    Then the direction should be wait