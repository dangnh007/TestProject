@feature-mc-4245 @login @pmt
Feature: Login and Logout
  As a user
  I want to be login
  So that I can access the site

  @mc-4246 @acceptance @smoke
  Scenario: Admin login
    When I login as System Administrator
    Then I am logged in

  @mc-4309 @smoke
  Scenario Outline: Create a user as a System Administrator
    When I login as System Administrator
    Then I am logged in
    When I create user with "<role>"
    Then I see created user
    Examples:
      | role         |
      | NIH          |
      | Site manager |

  @mc-4448 @smoke
  Scenario: Admin login via API
    When I login as System Administrator via API

  @mc-createUser @smoke
  Scenario Outline: Create a user as a System Administrator via API
    When I login as System Administrator via API
    Then I create user with "<role>" and "<group>" via API
    Examples:
      | role                 | group |
      | ROLE_MC_NIH          | 17    |
      | ROLE_MC_SITE_MANAGER | 423   |