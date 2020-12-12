Feature: Example feature

  @ExampleFeature
  Scenario: Count cards and houses of deck
    Given list of decks
    When I pick a random deck
    Then it should contain 36 cards
    And the cards should be from 3 houses
