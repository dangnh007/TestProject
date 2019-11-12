@feature-mc-4245 @login @pmt @smoke
Feature: User Management
  As a user
  I want to be login
  So that I can access the site

  @mc-4246 @acceptance
  Scenario: Admin login
    When I login as System Administrator
    Then I am logged in

  @mc-4309
  Scenario Outline: Create a user as a System Administrator
    Given I create test groups via API
    And I login as System Administrator
    When I create user with "<role>" and "<org>" level
    Then User has been created

    Examples:
      | role            | org                                       |
      | Site Manager    | Site/hpo-test-automation                  |
      | Support Admin   | Organization/TEST_AUTOMATION_ORGANIZATION |
      | Program Manager | Awardee/TEST_AUTOMATION                   |

  @mc-4448 @api
  Scenario: Admin login via API
    When I login as System Administrator via API

  @mc-4592 @api
  Scenario Outline: Create a user as a System Administrator via API
    Given I create test groups via API
    When I login as System Administrator via API
    Then I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API

    Examples:
      | role                    | program   | awardee                 | org                          | site                 |
      | ROLE_MC_PROGRAM_MANAGER | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SUPPORT_ADMIN   | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SITE_MANAGER    | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-4607
  Scenario Outline: Login with created user
    Given I create test groups via API
    And I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I login for the first time and set up my credentials
    Then I login as user

    Examples:
      | role                    | program   | awardee                 | org                          | site                 |
      | ROLE_MC_PROGRAM_MANAGER | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SUPPORT_ADMIN   | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SITE_MANAGER    | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-4718 @api
  Scenario Outline: Verify email for created user
    Given I create test groups via API
    And I login as System Administrator via API
    When I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API
    Then I verify email and get its id

    Examples:
      | role                    | program   | awardee                 | org                          | site                 |
      | ROLE_MC_PROGRAM_MANAGER | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SUPPORT_ADMIN   | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SITE_MANAGER    | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-4723 @api
  Scenario Outline: Login with created user via API
    Given I create test groups via API
    When I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login as user via API

    Examples:
      | role                    | program   | awardee                 | org                          | site                 |
      | ROLE_MC_PROGRAM_MANAGER | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SUPPORT_ADMIN   | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SITE_MANAGER    | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-6471
  Scenario: Edit user as Program Manager
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    And I am logged in as user
    And I update user to "Site Manager" role and "Site/hpo-test-automation" org
    And I logout
    Then I login as edited user

  @mc-6501
  Scenario: View list user and Search user
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    Then I found created user by searching email

  @mc-6171
  Scenario: Create users with the role belongs to Program Manager
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I login as user
    And I am logged in as user
    When I create user with the role belongs to "Program Manager"
    Then Users have been created

  @mc-6273
  Scenario: Edit user as a System Administrator
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_SYSTEM_ADMINISTRATOR" and "All of Us", "", "", ""
    And I set up my credentials via API
    And I login as user
    And I am logged in as System Administrator
    When I edit that user and validate negative editing cases
    Then User info should be unchanged

  @mc-6220
  Scenario: Lock user and verify email notification by Program Manager
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    Then I lock user and status of this user should be changed to Disabled and locked user can not login

  @mc-6398
  Scenario: As Program Manager I want to reset password for another user
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I reset password for temp user
    And An email notification should be received
    Then I login as temp user again by reset password successfully

  @mc-6216
  Scenario: As Program Manager I want to verify Cancel delete user
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I try to delete that temp user but choose Cancel button
    Then The temp user still be persistent

  @mc-6217
  Scenario: As Program Manager I want to verify Delete user successfully
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I delete that temp user
    Then The temp user should be removed

  @mc-6184
  Scenario: As Program Manager I want to verify delete user message
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I try to delete that temp user
    Then The alert message is displayed with options Delete User and Cancel

  @mc-6431
  Scenario: Program Manager forgot password and reset password successfully
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When User login page, select Forgot Password and submit email address
    And User should receive reset password email
    Then User should reset password and login successfully

  @mc-6398
  Scenario: As Program Manager I want to reset password for another user
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I reset password for temp user
    Then An email notification should be received
    And I login as temp user again by reset password successfully

  @mc-6461
  Scenario: As Program Manager I want to verify MFA reset function
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I reset MFA code for temp user
    Then I login temp user with new secret key

  @mc-6099 @mc-6222 @mc-6223
  Scenario: Unlock User
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    And I am logged in as user
    And Lock user, status of user is Disabled
    And I unlock that user
    Then User is unlock successfully and can log into system successfully

  @mc-6095 @mc-6242
  Scenario: Program Manager can edit first name, last name of user
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    Then I edit First Name and Last Name of created user
    And First Name and Last Name of user should be updated successfully

  @mc-6095 @mc-6613
  Scenario: Program Manager can change role of user successfully
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    Then I change role of user from "ROLE_MC_PROGRAM_MANAGER" to "ROLE_MC_PROGRAM_COORDINATOR" and "TEST AUTOMATION ORGANIZATION" successfully

  @mc-6095 @mc-6614
  Scenario: Program Manager can edit group of user successfully
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    When I login as user
    Then I change role of user from "ROLE_MC_PROGRAM_MANAGER" to "ROLE_MC_RESEARCH_ASSISTANT" and group of user from "TEST AUTOMATION ORGANIZATION" to "TEST AUTOMATION SITE" successfully

  @mc-6776 @api
  Scenario: Communication Toggle API
    Given I create test groups via API
    When I login as System Administrator via API
    Then I toggle communication feature via API

  @mc-6696 @api
  Scenario: Edit User - API
    Given I create test groups via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    And I set up my credentials via API
    And I create user with "ROLE_MC_PROGRAM_MANAGER" and "All of Us", "TEST AUTOMATION AWARDEE", "TEST AUTOMATION ORGANIZATION", ""
    When I login as user via API
    And I edit user and change its role from "ROLE_MC_PROGRAM_MANAGER" to "ROLE_MC_SITE_MANAGER" via API
    Then I login as edited user via API
