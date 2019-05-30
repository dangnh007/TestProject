@feature-mc-4245 @login @pmt
Feature: Login and Logout
  As a user
  I want to be login
  So that I can access the site

  @mc-4246 @acceptance @smoke
  Scenario: Admin login
    When I login
    Then I am logged in


  @mc-4309 @smoke
  Scenario Outline: Create a user
    When I login
    Then I am logged in
    Then I create user with "<role>"
    Then I set auth level Awardee
    Then I set group as Test Awardee
    Then I see created user
    Examples:
      | role            |
      | Program manager |
      | Site manager    |





