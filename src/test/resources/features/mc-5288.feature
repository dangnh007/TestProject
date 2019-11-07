@feature-mc-5288 @capacity @pmt @smoke @wip
Feature: Post-Enrollment Communication
  As a user
  I want to be able
  modify and validate Post-Enrollment Communication features

  Background:
    Given I create user with "ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER" and "All of Us", "", "", ""

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
    And I wait 2 seconds
    When I create a new campaign with "Email" channel
    Then Campaign is "<button>"

    Examples:
      | button         | org           | site                          |
      | created        | Banner Health | Banner Baywood Medical Center |
      | saved as draft | Banner Health | Banner Baywood Medical Center |

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
  Scenario Outline: As a user I want to create segmentation for particular organization and site
    Given I set up my credentials via API
    When I login as user
    Then I created segmentation with "<org>" and "<site>" on Email channel
    Examples:
      | org                   | site                          |
      | Banner Health         | Banner Baywood Medical Center |
      | Boston Medical Center | Boston Medical Center         |

  @mc-5664 @api
  Scenario: As a user I want to create new segmentation via API
    When I login as user via API
    Then I create new segmentation with Email channel via API
