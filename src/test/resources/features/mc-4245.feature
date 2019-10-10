@feature-mc-4245 @login @pmt @smoke
Feature: Login and Logout
  As a user
  I want to be login
  So that I can access the site

  @mc-4246 @acceptance
  Scenario: Admin login
    When I login as System Administrator
    Then I am logged in

  @mc-4309
  Scenario Outline: Create a user as a System Administrator
    Given I login as System Administrator
    And I create test groups via API
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
    Given I login as System Administrator via API
    When I create test groups via API
    Then I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API

    Examples:
      | role                    | program   | awardee                 | org                          | site                 |
      | ROLE_MC_PROGRAM_MANAGER | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SUPPORT_ADMIN   | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SITE_MANAGER    | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-4607
  Scenario Outline: Login with created user
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I login for the first time and set up my credentials
    Then I login as user

    Examples:
      | role                    | program   | awardee                 | org                          | site                 |
      | ROLE_MC_PROGRAM_MANAGER | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SUPPORT_ADMIN   | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION |                      |
      | ROLE_MC_SITE_MANAGER    | All of Us | TEST AUTOMATION AWARDEE | TEST AUTOMATION ORGANIZATION | TEST AUTOMATION SITE |

  @mc-4718 @api
  Scenario Outline: Verify email for created user
    Given I login as System Administrator via API
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
