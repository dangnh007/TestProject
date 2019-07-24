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
    And I set awardee level "<org>"
    Then I see created user

    Examples:
      | role         | org                                    |
      | NIH          | PMI                                    |
      | Site manager | Site/hpo-site-wimadisonschoolofnursing |

  @mc-4448 @smoke @api
  Scenario: Admin login via API
    When I login as System Administrator via API

  @mc-4592 @smoke @api
  Scenario Outline: Create a user as a System Administrator via API
    When I login as System Administrator via API
    Then I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API

    Examples:
      | role                 | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH          | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |

  @mc-4607 @smoke
  Scenario Outline: Login with created user
    When I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login for the first time and set up my credentials
    And I login as user

    Examples:
      | role                 | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH          | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |

  @mc-4718 @smoke @api
  Scenario Outline: Verify email for created user
    When I login as System Administrator via API
    Then I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API
    Then I check email inbox
    And I verify email and get its id

    Examples:
      | role                 | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH          | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |

  @mc-4723 @smoke @api
  Scenario Outline: Login with created user via API
    When I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login as user via API

    Examples:
      | role                 | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH          | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |
