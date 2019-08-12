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
    And I am logged in
    When I create user with "<role>" and "<org>" level
    Then User has been created

    Examples:
      | role                                | org                                    |
      | NIH                                 | PMI                                    |
      | Site manager                        | Site/hpo-site-wimadisonschoolofnursing |
      | Communications & Engagement Manager | PMI                                    |

  @mc-4448 @api
  Scenario: Admin login via API
    When I login as System Administrator via API

  @mc-4592 @api
  Scenario Outline: Create a user as a System Administrator via API
    When I login as System Administrator via API
    Then I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API

    Examples:
      | role                                      | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH                               | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER                      | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |                      |                                  |                                            |

  @mc-4607 @smoke
  Scenario Outline: Login with created user
    Given I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    When I login for the first time and set up my credentials
    Then I login as user

    Examples:
      | role                                      | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH                               | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER                      | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |                      |                                  |                                            |

  @mc-4718 @api
  Scenario Outline: Verify email for created user
    Given I login as System Administrator via API
    When I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>" via API
    Then I verify email and get its id

    Examples:
      | role                                      | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH                               | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER                      | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |                      |                                  |                                            |

  @mc-4723 @api
  Scenario Outline: Login with created user via API
    When I create user with "<role>" and "<program>", "<awardee>", "<org>", "<site>"
    Then I login as user via API

    Examples:
      | role                                      | program   | awardee              | org                              | site                                       |
      | ROLE_MC_NIH                               | All of Us |                      |                                  |                                            |
      | ROLE_MC_SITE_MANAGER                      | All of Us | Wisconsin Consortium | University of Wisconsin, Madison | University of WI Madison School of Nursing |
      | ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER | All of Us |                      |                                  |                                            |
