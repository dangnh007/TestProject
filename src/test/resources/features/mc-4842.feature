@feature-mc-4842 @pmt @settings @smoke
Feature: PMT Site Settings
  As a user
  I want to modify Site Settings

  Background:
    Given I create user with "ROLE_MC_SITE_MANAGER" and "PMI", "Wisconsin Consortium", "University of Wisconsin, Madison", "University of WI Madison School of Nursing"

  @mc-4851 @unsafe
  Scenario: I login as Site manager and set up Site Settings
    Given I set up my credentials via API
    And I login as user
    And I set new Site Settings with toggle "on", target "15", days "+ 1 Day"
    And I logout
    When I login as user
    Then I set default Site Settings with toggle "off", target "0", days "+ 3 Days"

  @mc-4852 @api
  Scenario: Set up Site Settings via API
    Given I login as user via API
    When I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    Then I set default Site Settings with toggle "off", target "0", goal "0", days "3" via API

  @mc-5143
  Scenario: Create a new appointment for prospect as Site Manager
    Given I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    When I create new appointment for prospect
    Then I set default Site Settings with toggle "off", target "0", goal "0", days "3" via API

  @mc-5144 @api
  Scenario: Create a new appointment for prospect as Site Manager via API
    Given I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    When I create new appointment for prospect via API
    Then I set default Site Settings with toggle "off", target "0", goal "0", days "3" via API
