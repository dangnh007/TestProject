@feature-mc-5208 @capacity @pmt
Feature: Campaign Management
  As a user
  I want to be able
  modify and validate Capacity Management features

  @mc-5306 @smoke
  Scenario Outline: As user I have permissions to access Communications
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    And I set up my credentials via API
    When I login as user
    Then I see tabs Audience Segmentation, Campaigns, Templates

    Examples:
      | role                                      | program   | awardee | org | site |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      |

  @mc-5391 @smoke
  Scenario Outline: As user I want to verify values of Audience Segmentation tab
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    And I set up my credentials via API
    When I login as user
    Then I verify all values in the Audience Segmentation section

    Examples:
      | role                                      | program   | awardee | org | site |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      |

  @mc-5392 @smoke
  Scenario Outline: As a user I want to verify New Filter section, its groups and categories
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    And I set up my credentials via API
    When I login as user
    Then I verify "Program Milestones" and Demographic Segmentation

    Examples:
      | role                                      | program   | awardee | org | site |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      |

  @mc-5428 @smoke
  Scenario Outline: As user I want to create new campaign
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    And I set up my credentials via API
    And I login as user
    When I create a new campaign with "<channel>" channel
    Then I finished campaign as "<button>"

    Examples:
      | role                                      | program   | awardee | org | site | channel | button  |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | SMS     | created |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | SMS     | draft   |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | Email   | created |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | Email   | draft   |
