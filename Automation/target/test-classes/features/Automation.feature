@test
Feature: Automation at home

  Scenario: Test
    When I navigate to Home page
    And I click on element MMTHome.from_city
    And I enter text Kolkata to element MMTHome.from_txt
    And I click on element MMTHome.select_cty
    And I click on element MMTHome.to_city
    And I enter text Bangalore to element MMTHome.to_txt
    And I click on element MMTHome.select_cty

