@feature-mc-5288 @communication @pmt @smoke
Feature: Post-Enrollment Communication
  As a user
  I want to be able
  modify and validate Post-Enrollment Communication features

  Background:
    Given I create test groups via API
    When I create user with "ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER" and "All of Us", "", "", ""
    Then I toggle communication feature via API

  @mc-5306
  Scenario: As user I have permissions to access Communications
    Given I set up my credentials via API
    When I login as user
    Then I see tabs Audience Segmentation, Campaigns, Templates

  @mc-5391
  Scenario: As user I want to verify values of Audience Segmentation tab
    Given I set up my credentials via API
    When I login as user
    Then I verify all values in the Audience Segmentation section

  @mc-5392
  Scenario: As a user I want to verify New Filter section, its groups and categories
    Given I set up my credentials via API
    When I login as user
    Then I verify "Program Milestones", "Demographic Segmentation" and "Campaign Activity"

  @mc-5428
  Scenario Outline: As user I want to create new campaign
    Given I set up my credentials via API
    And I login as user
    And I created segmentation with "<org>" and "<site>" on Email channel
    When I create a new campaign with "Email" channel
    Then Campaign is "<button>"

    Examples:
      | button         | org                          | site                 |
      | created        | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |
      | saved as draft | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-5494 @api
  Scenario Outline: As a user I create or draft campaign via API
    When I login as user via API
    Then I create "<status>" campaign with "Email" via API

    Examples:
      | status |
      | DRAFT  |
      | ACTIVE |

  @mc-5506
  Scenario: As a user I want to verify values on Templates tab
    Given I set up my credentials via API
    When I login as user
    Then I verify values on Templates tab

  @mc-5558
  Scenario: As a user I want to verify organizations on Audience Segmentation
    Given I set up my credentials via API
    When I login as user
    Then I verify organizations

  @mc-5559
  Scenario: As a user I want to create segmentation for particular organization and site
    Given I set up my credentials via API
    When I login as user
    Then I created segmentation with "TEST AUTOMATION ORGANIZATION" and "TEST AUTOMATION SITE" on Email channel

  @mc-5664 @api
  Scenario: As a user I want to create new segmentation via API
    When I login as user via API
    Then I create new segmentation with Email channel via API

  @mc-7020
  Scenario: As a user I want to clone an existing segmentation
    Given I login as user via API
    And I create new segmentation named "MC-7020" with Email channel via API
    And I login as user
    And I am on Audience Segmentation page
    And I search segmentation by name "MC-7020" successfully
    When I clone this segmentation successfully
    Then Clone segmentation named "Copy of MC-7020" is displayed on grid

  @mc-7060
  Scenario: As a user I want to create new segmentation via API
    Given I login as user via API
    And I create new segmentation "TEST SEGMENT EMAIL MC7060 API" with Email channel via API
    And I create new segmentation "TEST SEGMENT SMS MC7060 API" with SMS channel via API
    When I login as user
    And I search segmentation by part of name "MC7060"
    Then I can search segmentation "TEST SEGMENT EMAIL MC7060 API", "TEST SEGMENT SMS MC7060 API" are listed on grid

  @mc-7061
  Scenario: As a user I want to create new segmentation via API
    Given I login as user via API
    And I create new segmentation "TEST SEGMENT EMAIL MC7061 API" with Email channel via API
    When I login as user
    Then I can search segmentation by full name "TEST SEGMENT EMAIL MC7061 API" successfully