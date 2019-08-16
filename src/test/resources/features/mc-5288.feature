@feature-mc-5288 @capacity @pmt @smoke
Feature: Campaign Management
  As a user
  I want to be able
  modify and validate Capacity Management features

  Background:
    Given I create user with "ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER" and "All of Us", "", "", ""

  @mc-5306
  Scenario: As user I have permissions to access Communications
    And I set up my credentials via API
    When I login as user
    Then I see tabs Audience Segmentation, Campaigns, Templates

  @mc-5391
  Scenario: As user I want to verify values of Audience Segmentation tab
    And I set up my credentials via API
    When I login as user
    Then I verify all values in the Audience Segmentation section

  @mc-5392
  Scenario: As a user I want to verify New Filter section, its groups and categories
    When I login as user
    Then I verify "Program Milestones" and Demographic Segmentation

  @mc-5428
  Scenario Outline: As user I want to create new campaign
    And I set up my credentials via API
    And I login as user
    When I create a new campaign with "<channel>" channel
    Then Campaign is "<button>"

    Examples:
      | channel | button         |
      | SMS     | created        |
      | SMS     | saved as draft |
      | Email   | created        |
      | Email   | saved as draft |

  @mc-5494 @api
  Scenario Outline: As a user I create or draft campaign via API
    When I login as user via API
    Then I create "<status>" campaign with "<channel>" via API

    Examples:
      | status | channel |
      | DRAFT  | SMS     |
      | ACTIVE | SMS     |
      | DRAFT  | Email   |
      | ACTIVE | Email   |

  @mc-5506
  Scenario: As a user I want to verify values on Templates tab
    And I set up my credentials via API
    When I login as user
    Then I verify values on Templates tab

  @mc-test @unsafe
  Scenario Outline: As a user I want to set org and site with email preference on Audience Segmentation
    And I set up my credentials via API
    When I login as user
    Then "<org>" and "<site>" should be saved for particular segmentation
    Examples:
      | org                                                        | site                                                                            |
      | Banner Health                                              | Banner Baywood Medical Center                                                   |
      | Baylor Scott and White Health                              | Baylor Hillcrest Family Health Center                                           |
      | Boston Medical Center                                      | Boston Medical Center                                                           |
      | Cedars-Sinai Medical Center                                | Cedars-Sinai Medical Center                                                     |
      | Center for Community Engagement and Health Partnerships    | Center for Community Engagement and Health Partnerships                         |
      | Cherokee Health Systems                                    | Cherokee Health Systems, Knoxville                                              |
      | Community Health Center, Inc                               | Meriden Medical                                                                 |
      | Cooper Green Mercy Hospital                                | Cooper Green Mercy Hospital                                                     |
      | DV San Diego Blood Bank                                    | San Diego Blood Bank Coastal Donor Center (CDC DV)                              |
      | EMSI                                                       | EMSI East Region                                                                |
      | Eau Claire Cooperative Health Center                       | Eau Claire Walk                                                                 |
      | Emory University                                           | Emory University - Cardiology                                                   |
      | Erie Family Health Centers                                 |                                                                                 |
      | Essentia Health                                            | Essentia Health Virginia Clinic                                                 |
      | GenomicsTest Organization                                  | General Genomics Testing                                                        |
      | HPO San Diego Blood Bank                                   | San Diego Blood Bank (SDBB)                                                     |
      | HRHCare, Inc.                                              | HRHCare, Beacon                                                                 |
      | Harlem Hospital                                            | Harlem Hospital                                                                 |
      | Henry Ford Health System                                   | Henry Ford Health System                                                        |
      | Huntsville Hospital                                        | Huntsville Hospital  Physicians Care at Bailey Cove                             |
      | Jackson-Hinds Comprehensive Health Center                  | Dr. James Anderson Health Facility                                              |
      | Louisiana State University                                 | LSUHC Healthcare Network                                                        |
      | MCW - Sixteenth Street Community Health Center (SSHC 16th) | Sixteenth Street Community Health Center Parkway Clinic                         |
      | Mariposa Community Health Center                           |                                                                                 |
      | Marshfield Clinic                                          | Marshfield Clinic Wausau Center                                                 |
      | Medical College of Wisconsin                               | Medical College of Wisconsin, Town Hall Health Center                           |
      | Mobile Engagement Asset (MEA) 2                            | All of Us Mobile Journey, NAHH & El Centro Hispano                              |
      | Morehouse School of Medicine                               | Morehouse School of Medicine - Grady                                            |
      | Near North Health Services Corporation                     | UIC Research Clinic, NN                                                         |
      | New York Presbyterian Columbia University                  | Irving Institute for Clinical and Translational Research                        |
      | Northshore University Health System                        | Northshore Evanston Hospital                                                    |
      | Northwestern University                                    | Northwestern Medicine Vernon Hills                                              |
      | Partners Healthcare                                        | Brookside Community HealthCenter                                                |
      | Reliant Medical Group (Meyers Primary Care)                | Reliant Auburn                                                                  |
      | Rush University                                            | Rush University Medical Center                                                  |
      | San Ysidro Health Center                                   | CHC Ocean View                                                                  |
      | Spectrum Health System                                     | Spectrum Health                                                                 |
      | Swedish American Regional Center, UW Health                | Swedish American Regional Center, UW Health                                     |
      | Temple University                                          | Temple University Heart & Vascular Institute                                    |
      | Test Prod Organization                                     | Prod HPO Site A                                                                 |
      | Tulane University                                          | Tulane Medical Center                                                           |
      | University Medical Center Tuscaloosa                       | University Medical Center Tuscaloosa                                            |
      | University of Alabama Birmingham                           | UAB Volker Hall                                                                 |
      | University of Alabama Birmingham Huntsville                | University of Alabama Birmingham Health Center Huntsville                       |
      | University of Alabama Birmingham Montgomery                | University of Alabama Birmigham - Montgomery (UAB Montgomery)                   |
      | University of Alabama Birmingham Selma                     | UAB Selma                                                                       |
      | University of California Irvine School of Medicine         | University of California, Irvine (Anaheim)                                      |
      | University of California, Davis                            | University of California Davis                                                  |
      | University of California, San Diego                        | University California San Diego Hillcrest North                                 |
      | University of California, San Francisco                    | University of California San Francisco Mission Bay Inpatient                    |
      | University of Chicago                                      | University of Chicago Medical Center                                            |
      | University of Florida                                      | University of Florida JAX ASCENT                                                |
      | University of Illinois at Chicago                          | UIC Research Clinic                                                             |
      | University of Miami                                        | University of Miami Sylvester Cancer Center                                     |
      | University of Mississippi Medical Center                   | University of Mississippi Medical Center, Jackson                               |
      | University of Pittsburgh Medical Center                    | UPMC North Hills, Hampton                                                       |
      | University of South Alabama                                | University of South Alabama Mastin Clinic                                       |
      | University of Southern California                          | University of Southern California, Diabetes & Obesity Research Institute (DORI) |
      | University of Wisconsin, Madison                           | University of WI Madison School of Nursing                                      |
      | VA Atlanta Medical Center                                  | VA Atlanta Medical Center                                                       |
      | VA Boston Healthcare System                                | VA Boston Healthcare System                                                     |
      | VA Clement J. Zablocki Medical Center                      | VA Clement J. Zablocki Medical Center                                           |
      | VA Eastern Kansas Health Care System                       | VA Eastern Kansas Health Care System                                            |
      | VA Event                                                   | VA Event                                                                        |
      | VA Michael E. DeBakey Medical Center                       | VA Michael E. DeBakey Medical Center                                            |
      | VA Minneapolis Healthcare System                           | VA Minneapolis Healthcare System                                                |
      | VA New York Harbor Healthcare System                       | VA New York Harbor Healthcare System                                            |
      | VA Palo Alto Health Care System                            | VA Palo Alto Health Care System                                                 |
      | VA Phoenix Healthcare System                               | VA Phoenix Healthcare System                                                    |
      | VA San Diego Healthcare System                             | VA San Diego Healthcare System                                                  |
      | VH Test Organization                                       | VH Test Site                                                                    |
      | Walgreens Houston                                          | Walgreens Houston Gray St.                                                      |
      | Walgreens Memphis                                          | Walgreens Memphis                                                               |
      | Walgreens Phoenix                                          | Walgreens Phoenix                                                               |
      | Weill Cornell Medicine                                     | Weill Cornell Medicine                                                          |
