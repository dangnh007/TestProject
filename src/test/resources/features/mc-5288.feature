@feature-mc-5288 @capacity @pmt @smoke
Feature: Campaign Management
  As a user
  I want to be able
  modify and validate Capacity Management features

  Background:
    Given I create user with "ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER" and "All of Us", "", "", ""

  @mc-5306
  Scenario: As user I have permissions to access Communications
    And I set up my credentials via API
    When I login as user
    Then I see tabs Audience Segmentation, Campaigns, Templates

  @mc-5391
  Scenario: As user I want to verify values of Audience Segmentation tab
    And I set up my credentials via API
    When I login as user
    Then I verify all values in the Audience Segmentation section

  @mc-5392
  Scenario: As a user I want to verify New Filter section, its groups and categories
    And I set up my credentials via API
    When I login as user
    Then I verify "Program Milestones" and Demographic Segmentation

  @mc-5428
  Scenario Outline: As user I want to create new campaign
    And I set up my credentials via API
    And I login as user
    And I created segmentation with "<org>" and "<site>" on "<channel>"
    When I create a new campaign with "<channel>" channel
    Then Campaign is "<button>"

    Examples:
      | channel | button         | org           | site                          |
      | SMS     | created        | Banner Health | Banner Baywood Medical Center |
      | SMS     | saved as draft | Banner Health | Banner Baywood Medical Center |
      | Email   | created        | Banner Health | Banner Baywood Medical Center |
      | Email   | saved as draft | Banner Health | Banner Baywood Medical Center |

  @mc-5494 @api
  Scenario Outline: As a user I create or draft campaign via API
    When I login as user via API
    Then I create "<status>" campaign with "<channel>" via API

    Examples:
      | status | channel |
      | DRAFT  | SMS     |
      | ACTIVE | SMS     |
      | DRAFT  | Email   |
      | ACTIVE | Email   |

  @mc-5506
  Scenario: As a user I want to verify values on Templates tab
    And I set up my credentials via API
    When I login as user
    Then I verify values on Templates tab

  @mc-5558
  Scenario: As a user I want to verify organizations on Audience Segmentation
    And I set up my credentials via API
    When I login as user
    Then I verify organizations

  @mc-5559
  Scenario Outline: As a user I want to create segmentation for particular organization and site
    And I set up my credentials via API
    When I login as user
    Then I created segmentation with "<org>" and "<site>" on "<channel>"
    Examples:
      | org                   | site                          | channel |
      | Banner Health         | Banner Baywood Medical Center | Email   |
      | Banner Health         | Banner Baywood Medical Center | SMS     |
      | Boston Medical Center | Boston Medical Center         | Email   |
      | Boston Medical Center | Boston Medical Center         | SMS     |


