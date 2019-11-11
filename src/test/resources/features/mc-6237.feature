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
    And I am on Calendar "Day" view and Scheduler site is selected as "Site/hpo-test-automation"
    When I create new appointment for prospect from first available future time block
    Then I can search created appointment by email that was started from selected time

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
    And I am on Calendar "Week" view and Scheduler site is selected as "Site/hpo-test-automation"
    When I create new appointment for prospect from first available future time block
    Then I can search created appointment by email that was started from selected time

  @mc-6730
  Scenario: Reschedule Prospect's future appointment
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    And I am on Search page and search appointment by email
    When I re-scheduled appointment at "08:15 AM"
    Then Details information of re-scheduled appointment is displayed and placed at "08:15 AM"

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
    When I edit by increasing duration, assign to "mc-6542", select "No Show" outcome and add notes
    Then I should see a message

  @mc-6665
  Scenario: Cancel appointment
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    When I cancel that appointment via API
    Then Appointment change status to cancel successfully
