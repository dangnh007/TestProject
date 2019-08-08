@feature-mc-5208 @capacity @pmt
Feature: Campaign Management
  As a user
  I want to be able
  modify and validate Capacity Management features

  Background:
    Given I create user with "ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER" and "All of Us", "", "", ""
    And I set up my credentials via API

  @mc-5306 @smoke
  Scenario: As user I have permissions to access Communications
    When I login as user
    Then I see tabs Audience Segmentation, Campaigns, Templates

  @mc-5391 @smoke
  Scenario: As user I want to verify values of Audience Segmentation tab
    When I login as user
    Then I verify all values in the Audience Segmentation section

  @mc-5392 @smoke
  Scenario: As a user I want to verify New Filter section, its groups and categories
    When I login as user
    Then I verify "Program Milestones" and Demographic Segmentation

  @mc-5428 @smoke
  Scenario Outline: As user I want to create new campaign
    When I login as user
    Then I create a new campaign with "<channel>" channel
    And I finished campaign as "<button>"

    Examples:
      | channel | button  |
      | SMS     | created |
      | SMS     | draft   |
      | Email   | created |
      | Email   | draft   |
