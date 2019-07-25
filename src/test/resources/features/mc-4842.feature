@feature-mc-4842 @pmt @settings
Feature: PMT Site Settings
  As a user
  I want to modify Site Settings

  @mc-4851 @smoke @unsafe
  Scenario Outline: I login as Site manager and set up Site Settings
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I set up my credentials via API
    And I login as user
    And I go to Settings Page
    And I toggle "<on>" accepting appointments
    And I set target to "<new target>"
    And I set custom hrs of operations
    And I set appointment notice "<new days>"
    And I save changes
    And I logout
    And I login as user
    And I go to Settings Page
    And I toggle "<off>" accepting appointments
    And I set target to "<default target>"
    And I set appointment notice "<default days>"
    And I delete custom hrs of operations
    And I save changes

    Examples:
      | role                 | program | awardee              | org                              | site                                       | on | off | new target | default target | new days | default days |
      | ROLE_MC_SITE_MANAGER | PMI     | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing | on | off | 15         | 0              | + 1 Day  | + 3 Days     |


  @mc-4852 @smoke @api
  Scenario Outline: Set up Site Settings via API
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login as user via API
    Then I toggle "<on>" accepting appointments via API
    Then I set daily "<new target>" and "<new goal>" via API
    Then I set "<new days>" of minimum appointment notice via API
    Then I set custom hours of operations via API
    Then I toggle "<off>" accepting appointments via API
    Then I set daily "<default target>" and "<default goal>" via API
    Then I set "<default days>" of minimum appointment notice via API
    Then I set default hours of operations via API

    Examples:
      | role                 | program | awardee              | org                              | site                                       | on | off | new target | default target | new goal | default goal | new days | default days |
      | ROLE_MC_SITE_MANAGER | PMI     | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing | on | off | 15         | 0              | 5        | 0            | 1        | 3            |

  @mc-5143 @smoke
  Scenario Outline: Create a new appointment for prospect as Site Manager
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login as user via API
    Then I toggle "<on>" accepting appointments via API
    Then I set daily "<new target>" and "<new goal>" via API
    Then I set "<new days>" of minimum appointment notice via API
    Then I set custom hours of operations via API
    And I login as user
    And I create new appointment for prospect
    And I provide participant information
    And I provide appointment details
    And I schedule an appointment
    Then I toggle "<off>" accepting appointments via API
    Then I set daily "<default target>" and "<default goal>" via API
    Then I set "<default days>" of minimum appointment notice via API
    Then I set default hours of operations via API

    Examples:
      | role                 | program | awardee              | org                              | site                                       | on | off | new target | default target | new goal | default goal | new days | default days |
      | ROLE_MC_SITE_MANAGER | PMI     | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing | on | off | 15         | 0              | 5        | 0            | 1        | 3            |

  @mc-5144 @smoke @api
  Scenario Outline: Create a new appointment for prospect as Site Manager via API
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login as user via API
    Then I toggle "<on>" accepting appointments via API
    Then I set daily "<new target>" and "<new goal>" via API
    Then I set "<new days>" of minimum appointment notice via API
    Then I set custom hours of operations via API
    When I create new appointment for prospect via API
    Then I toggle "<off>" accepting appointments via API
    Then I set daily "<default target>" and "<default goal>" via API
    Then I set "<default days>" of minimum appointment notice via API
    Then I set default hours of operations via API

    Examples:
      | role                 | program | awardee              | org                              | site                                       | on | off | new target | default target | new goal | default goal | new days | default days |
      | ROLE_MC_SITE_MANAGER | PMI     | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing | on | off | 15         | 0              | 5        | 0            | 1        | 3            |