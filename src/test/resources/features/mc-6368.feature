@feature-mc-6368
Feature: Capacity Management
  Goal setting
  Set targets at all levels

  Background:
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I login as user
    And I am logged in as user
    And I navigate to Capacity Management

  @mc-6541 @mc-6481
  Scenario: As Program Manager, I can set Target and Goal for Awardee
    When I select Awardee "TEST AUTOMATION AWARDEE" in location selector at the top page
    And I pressed Edit button
    Then User can edit Total Daily Target and Total Daily Goal of Awardee "TEST AUTOMATION AWARDEE"
    And I should see "Your changes have been saved."

  @mc-6541 @mc-6619
  Scenario: As Program Manager, I can set Target and Goal for Organization
    When I select Awardee "TEST AUTOMATION AWARDEE" in location selector at the top page
    And I go into daily goal and target of organization "TEST AUTOMATION ORGANIZATION", pressed Edit button
    Then User can edit Total Daily Target and Total Daily Goal of Organization "TEST AUTOMATION ORGANIZATION" under selected Awardee
    And I should see "Your changes have been saved."

  @mc-6541 @mc-6620
  Scenario: As Program Manager, I can set Target and Goal for Sites
    When I select Awardee "TEST AUTOMATION AWARDEE" in location selector at the top page
    And I go into daily goal and target of organization "TEST AUTOMATION ORGANIZATION", pressed Edit button
    Then User can edit Total Daily Target and Total Daily Goal of Sites under selected Organization "TEST AUTOMATION ORGANIZATION"
    And I should see "Your changes have been saved."
