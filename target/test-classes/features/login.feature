@base @login @pmt
Feature: Login and Logout
  As a user
  I want to be login
  So that I can access the site

  @ac-newtest @acceptance @smoke
  Scenario: Able to login as new user
    When I login
    Then I am logged in