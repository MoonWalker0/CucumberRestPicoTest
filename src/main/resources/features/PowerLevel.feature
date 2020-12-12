Feature: Example feature2

  @ExampleFeature2
  Scenario: Positive power level
    Given a list of decks
    When I pick a deck with power level greater than 0
    Then it should have more than 0 wins
