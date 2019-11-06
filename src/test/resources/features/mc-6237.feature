@feature-mc-6237 @pmt @scheduling @smoke
Feature: Scheduling
  As a user
  I want to operate with Scheduling

  Background:
    Given I create test groups via API

  @mc-6598
  Scenario: Initiate and scheduling from Calendar page Day view
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I am logged in as Site Manager
    And I am on Calendar page "Day" view and appointment Scheduler site is selected as "Site/hpo-test-automation"
    When I create new appointment for prospect from first available future time block
    Then I see scheduled appointment message
    And Appointment should be created for prospect started from selected time and I can search prospect by email
    And I set default Site Settings with toggle "off", target "0", goal "0", days "1" via API

  @mc-6645
  Scenario: Create prospects and verify My appointment
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    When I am on Search page and search appointment by email
    Then I see appointment displays in Search Result

  @mc-6626
  Scenario: Create prospects and verify My appointment
    Given I create user "mc-6438" with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I create new appointment for prospect and assign to "mc-6438"
    When I am on Hamburger menu and I check "Show My Appointment"
    Then I see Appointment showing up

  @mc-6690
  Scenario: Schedule appointment from Search Page
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account
    When I am on Search page and search appointment by email
    Then I create new appointment for prospect from Search result

  @mc-6690
  Scenario: Schedule appointment from Search Page
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account
    When I am on Search page and search appointment by email
    Then I create new appointment for prospect from Search result

  @mc-6644
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

  @mc-6718
  Scenario: Edit Prospect's future appointment details
    Given I create user "mc-6542" with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    And I login as user
    And I am logged in as Program Manager
    And I am on Search page. I can search prospect by email and view future appointment details
    When I edit, I can increase duration, assign to "mc-6542", select "No Show" outcome, add notes and I save changes
    Then I should see a message
    And I set default Site Settings with toggle "off", target "0", goal "0", days "1" via API

  @mc-6661 @mc-6665
  Scenario: Cancel appointment
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    When I cancel that appointment via API
    Then Appointment change status to cancel successfully
    And I set default Site Settings with toggle "off", target "0", goal "0", days "3" via API

