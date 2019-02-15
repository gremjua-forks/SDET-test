Feature: Curiosity NASA API

  Background:
    Given a NASA API session for Curiosity

  @TEST-1
  Scenario: Pictures can be retrieved per sol and earth date
    Given I get the first "10" photos made on sol "1000"
    And I get the earth date for sol "1000"
    When I get the first "10" pictures made on that earth date
    Then the photos should be the same


  @TEST-2
  Scenario: A camera did not take more than 10 photos compared to the others
    Given I get the all photos made on sol "1000"
    Then no camera made more than "10" photos