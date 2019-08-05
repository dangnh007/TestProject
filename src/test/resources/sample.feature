@feature-mc-25111 @dashboard @educational @educational-module @pmi @subscriber
Feature: Educational Module
  As a user
  I want to have access to the educational module
  So that I can see and complete the survey

  @mc-25116 @complete-education-multiple @automates-mc-78567
  Scenario: User has the ability to complete education survey multiple times
  A one line description
    Given I have completed ehr consent
    When I login
    When I select the education survey button
    When I complete the education survey
    When I see the dashboard
    When I select the education survey button
    Then I see the education survey welcome page

  @mc-25114 @tests-mc-20000 @complete-education @smoke @automates-mc-78567
  Scenario: User completes education survey
    Given I have completed ehr consent
    When I login
    When I select the education survey button
    When I complete the education survey
    Then I see the dashboard

  @mc-25112 @access @access-education @smoke @automates-mc-78567
  Scenario: User accesses education survey
  A multi-line
  description
    Given I have completed ehr consent
    When I login
    #some comment, which is totally fine
    When I select the education survey button
    Then I see the education survey welcome page

  @mc-25787 @tests-mc-98764 @scheduling
  Scenario Outline: Requesting jobs with unavailable userId or ruleId
    Given I am an administrator
    When I make a GET request to the "/api/schedulers/jobs" endpoint with parameters:
      | userId | <userId> |
      | ruleId | <ruleId> |
    Then I receive an empty JSON array response
    And I receive a "200" response

    Examples:
      | userId      | ruleId |
      | -1          | 1      |
      # this is an important line
      | currentUser | -2     |

  @mc-30434 @yellow-scheduling-banner-not-present @dv-light
  Scenario Outline: Verify that yellow scheduling banner is not present for non HPO states
    Given the user state is set as "<state>"
    Given the user medical care state is set to "<state>"
    Given the user ehr sharing is set as "YES"
    Given I have completed consent
    When I login
    When I select the ehr consent button on the dashboard
    When I complete the ehr consent short survey
    When I select the basic survey button
    When I complete the basic survey
    Then I verify that yellow consolidated banner is not present

  @smoke
    Examples:
      | state  |
      | Alaska |

  @regression @banner-states
    Examples:
      | state                          |
      | American_Samoa                 |
      | Arkansas                       |
      | Colorado                       |
      | Delaware                       |
      | District_of_Columbia           |
      | Federated_States_of_Micronesia |
      | Guam                           |
      | Hawaii                         |
      | Idaho                          |
      | Indiana                        |
      | Iowa                           |
