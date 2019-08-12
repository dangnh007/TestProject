@feature-mc-4842 @pmt @settings
Feature: PMT Site Settings
  As a user
  I want to modify Site Settings

  Background:
    Given I create user with "ROLE_MC_SITE_MANAGER" and "PMI", "Wisconsin Consortium", "University of Wisconsin, Madison", "University of WI Madison School of Nursing"

  @mc-4851 @smoke @unsafe
  Scenario Outline: I login as Site manager and set up Site Settings
    And I set up my credentials via API
    And I login as user
    When I set new Site Settings with toggle "<on>", "<new target>", "<new days>"
    And I logout
    And I login as user
    Then I set default Site Settings with toggle "<off>", "<default target>", "<default days>"

    Examples:
      | on | off | new target | default target | new days | default days |
      | on | off | 15         | 0              | + 1 Day  | + 3 Days     |


  @mc-4852 @smoke @api
  Scenario Outline: Set up Site Settings via API
    And I login as user via API
    When I set new Site Settings with toggle "<on>", "<new target>", "<new goal>", "<new days>" via API
    Then I set default Site Settings with toggle "<off>", "<default target>", "<default goal>", "<default days>" via API

    Examples:
      | on | off | new target | default target | new goal | default goal | new days | default days |
      | on | off | 15         | 0              | 5        | 0            | 1        | 3            |

  @mc-5143 @smoke
  Scenario Outline: Create a new appointment for prospect as Site Manager
    And I login as user via API
    And I set new Site Settings with toggle "<on>", "<new target>", "<new goal>", "<new days>" via API
    And I login as user
    When I create new appointment for prospect
    Then I set default Site Settings with toggle "<off>", "<default target>", "<default goal>", "<default days>" via API

    Examples:
      | on | off | new target | default target | new goal | default goal | new days | default days |
      | on | off | 15         | 0              | 5        | 0            | 1        | 3            |

  @mc-5144 @smoke @api
  Scenario Outline: Create a new appointment for prospect as Site Manager via API
    And I login as user via API
    And I set new Site Settings with toggle "<on>", "<new target>", "<new goal>", "<new days>" via API
    When I create new appointment for prospect via API
    Then I set default Site Settings with toggle "<off>", "<default target>", "<default goal>", "<default days>" via API
    Examples:
      | on | off | new target | default target | new goal | default goal | new days | default days |
      | on | off | 15         | 0              | 5        | 0            | 1        | 3            |