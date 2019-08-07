@feature-mc-5208 @campaign @pmt
Feature: Campaign Management
  As a user
  I want to be able
  modify and validate Capacity Management features

  @mc-5306 @smoke
  Scenario Outline: As user I have permissions to access Communications
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I set up my credentials via API
    Then I login as user
    Then I select Communications option and see tabs Audience Segmentation, Campaigns, Templates

    Examples:
      | role                                      | program   | awardee | org | site |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      |

  @mc-5391 @smoke
  Scenario Outline: As user I want to verify values of Audience Segmentation tab
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I set up my credentials via API
    Then I login as user
    Then I select Communications option and see tabs Audience Segmentation, Campaigns, Templates
    Then I verify all values in the Audience Segmentation section

    Examples:
      | role                                      | program   | awardee | org | site |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      |

  @mc-5392 @smoke
  Scenario Outline: As a user I want to verify New Filter section, its groups and categories
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I set up my credentials via API
    Then I login as user
    Then I select Communications option and see tabs Audience Segmentation, Campaigns, Templates
    Then I select "Program Milestones" from Segmentation Group
    Then I verify all values on the Program Milestones category
    Then I select Demographic Segmentation from Segmentation Group
    Then I verify all values on the Demographic Segmentation category

    Examples:
      | role                                      | program   | awardee | org | site |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      |

  @mc-5428 @smoke
  Scenario Outline: As user I want to create new campaign
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I set up my credentials via API
    Then I login as user
    Then I select Communications option and see tabs Audience Segmentation, Campaigns, Templates
    Then I verify Campaign tab and start creating a new campaign
    Then I setup campaign and select "<channel>" channel
    Then I select Audience Segmentation
    Then I select Template
    Then I review campaign and hit "<button>"

    Examples:
      | role                                      | program   | awardee | org | site | channel | button        |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | SMS     | create        |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | SMS     | save as draft |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | Email   | create        |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |         |     |      | Email   | save as draft |