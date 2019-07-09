@feature-mc-4842 @pmt @settings
Feature: PMT Site Settings
  As a user
  I want to modify Site Settings

  @mc-4851 @smoke
  Scenario Outline: I login as Site manager and set up Site Settings
    Given I create user with "<role>" and "<group>"
    When I set up my credentials via API
    And I login as user
    And I go to Settings Page
    And I toggle "<toggle on>" accepting appointments
    And I set target to "<new target>"
    And I set appointment notice "<new days>"
    And I set custom hrs of operations
    And I save changes
    And I logout
    And I login as user
    And I go to Settings Page
    And I toggle "<toggle off>" accepting appointments
    And I set target to "<default target>"
    And I set appointment notice "<default days>"
    And I delete custom hrs of operations
    And I save changes
    Examples:
      | role                 | group | toggle on | toggle off | new target | default target | new days | default days |
      | ROLE_MC_SITE_MANAGER | 509   | true      | false      | 15         | 0              | + 1 Day  | + 3 Days     |

  @mc-4852 @smoke @api
  Scenario Outline: Set up Site Settings via API
    Given I create user with "<role>" and "<group>"
    Then I login as user via API
    Then I toggle "<toggle on>" accepting appointments via API
    Then I set daily "<new target>" and "<new goal>" via API
    Then I set "<new days>" of minimum appointment notice via API
    Then I set custom hours of operations via API
    Then I toggle "<toggle off>" accepting appointments via API
    Then I set daily "<default target>" and "<default goal>" via API
    Then I set "<default days>" of minimum appointment notice via API
    Then I set default hours of operations via API
    Examples:
      | role                 | group | toggle on | toggle off | new target | default target | new goal | default goal | new days | default days |
      | ROLE_MC_SITE_MANAGER | 509   | true      | false      | 15         | 0              | 5        | 0            | 1        | 3            |