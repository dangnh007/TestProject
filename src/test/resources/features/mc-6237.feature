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
  Scenario: Create prospects and verify My appointment from Search result
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    When I am on Search page and search appointment by email
    Then I see appointment displays in Search Result

  @mc-6626
  Scenario: Create prospects and verify My appointment from Hamburger menu
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

  @mc-6909
  Scenario: Cancel future appointment then verify
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I create new appointment for prospect via API
    And I login as User then I can search prospect by email and view future appointment details
    When I cancel that appointment then confirm
    Then I should see the appointment cancel message

  @mc-6875
  Scenario: Verify current day on Availability page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I set up my credentials via API
    And I login as user
    And I am logged in as Site Manager
    And I am on Availability page
    When I see current month
    Then I see current day highlighted

  @mc-6876
  Scenario: Add hours of operation on Availability page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And Site custom hours of operation are not set
    And I login as user
    And I am on Availability page
    When I select a day and add hours of operation
    Then I verify new hours of operation and delete

  @mc-6877
  Scenario: Verify custom hours of operation added via API on Availability page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    When I am on Availability page
    Then I see custom hours of operation

  @mc-6878
  Scenario: Edit custom hours of operation added via API on Availability page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I am on Availability page
    When I select a day with hours of operation and edit hours of operation
    Then I verify hours of operation after editing

  @mc-6879
  Scenario: Delete custom hours of operation on Availability page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I set up my credentials via API
    And I login as user
    And I am on Availability page
    And I select a day and add hours of operation
    When I select a day with hours of operation and delete
    Then I verify site is closed

  @mc-6763
  Scenario: Edit Prospect's details
    Given I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create new appointment for prospect via API
    When I am on Search page and search prospect by email
    And I can view full appointment details
    Then I can edit information of prospect

  @mc-6810
  Scenario: User see current month view and current day highlighted on Calendar/Month view page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I am logged in as Site Manager
    When I am on Calendar "Month" view and Scheduler site is selected as "Site/hpo-test-automation"
    Then I can see current month view and current day is highlighted

  @mc-6812
  Scenario: User can see the appointment of prospects on Calendar/Month view page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I am logged in as Site Manager
    And I create new appointment for prospect via API
    When I am on Calendar "Month" view and Scheduler site is selected as "Site/hpo-test-automation"
    Then I can see the appointment on Calendar "Month" view

  @mc-6813
  Scenario: User can see the appointment Scheduler from any future day with hours of operation on Calendar/Month view page
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I login as user
    And I am logged in as Site Manager
    And I create new appointment for prospect via API
    When I am on Calendar "Month" view and Scheduler site is selected as "Site/hpo-test-automation"
    And I access any future day with hours of operation
    Then I can see the appointment Scheduler

  @mc-6946
  Scenario: Search prospect by First name in Appointment Scheduler
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account for searching
    And I login as user
    And I am logged in as Site Manager
    When I am on Appointment Scheduler Search page and search prospect by first name
    Then I see prospect displays in Search Result

  @mc-6947
  Scenario: Search prospect by Last name in Appointment Scheduler
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account for searching
    And I login as user
    And I am logged in as Site Manager
    When I am on Appointment Scheduler Search page and search prospect by last name
    Then I see prospect displays in Search Result

  @mc-6948
  Scenario: Search prospect by email in Appointment Scheduler
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account for searching
    And I login as user
    And I am logged in as Site Manager
    When I am on Appointment Scheduler Search page and search prospect by email
    Then I see prospect displays in Search Result

  @mc-6949
  Scenario: Search prospect by phone in Appointment Scheduler
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account have phone number is "5555666677" for searching
    And I login as user
    And I am logged in as Site Manager
    When I am on Appointment Scheduler Search page and search prospect by phone number "5555666677"
    Then I see prospect displays in Search Result

  @mc-6950
  Scenario: Search prospect by DOB in Appointment Scheduler
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a prospect account for searching
    And I login as user
    And I am logged in as Site Manager
    When I am on Appointment Scheduler Search page and search prospect by DOB
    Then I see prospect displays in Search Result

  @mc-6951
  Scenario: Search prospect by ParticipantID in Appointment Scheduler
    Given I create user with "ROLE_MC_SITE_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", "SITE"
    And I login as user via API
    And I set new Site Settings with toggle "on", target "15", goal "5", days "1" via API
    And I create a default prospect and a special one with first name "Partial Automation API", last name "user API"
    And I login as user
    And I am logged in as Site Manager
    When I am on Appointment Scheduler Search page and search prospect by partial first name "Automation"
    Then I see that prospects displays in Search Result
