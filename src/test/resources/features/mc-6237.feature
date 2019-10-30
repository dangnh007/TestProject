@feature-mc-6237 @pmt @scheduling @smoke
Feature: Scheduling
  As a user
  I want to operate with Scheduling

  Background:
    Given I create test groups via API

  @mc-6437 @mc-6645
  Scenario: Create prospects and verify My appointment
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    When I am on Search page and search appointment by email
    Then I can see appointment displays in Search Result
    And I set default Site Settings with toggle "off", target "0", goal "0", days "3" via API

  @mc-6438 @mc-6626
  Scenario: Create prospects and verify My appointment
    Given I create user "mc-6438" with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I create new appointment for prospect and assign to "mc-6438"
    When I am on Hamburger menu and I check "Show My Appointment"
    Then I can see Appointment showing up
    And I set default Site Settings with toggle "off", target "0", goal "0", days "3" via API

  @mc-6644 @mc-6439
  Scenario: Initiate and scheduling from Calendar page Week view
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I am logged in as Site Manager
    And I am on Calendar page "Week" view and appointment Scheduler site is selected as "Site/hpo-test-automation"
    When I create new appointment for prospect from first available future time block
    Then I see scheduled appointment message
    And Appointment should be created for prospect started from selected time and I can search prospect by email
    And I set default Site Settings with toggle "off", target "0", goal "0", days "1" via API
