package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.utilities.LocatorType;
import org.testng.log4testng.Logger;

import java.util.Arrays;
import java.util.List;

//SuppressWarning added to remove highlighted fields by InteliJ, which has been thrown when List<WebbElement> was added.
@SuppressWarnings("FieldCanBeLocal")
public class CapacityManagementPage {

    private final App app;
    private User user;
    Logger log = Logger.getLogger(CapacityManagementPage.class);

    private final String campaignNameRandom = "Test Automation #" + UserUtility.generateUUID(5);
    private final WebbElement communicationsButton;
    private final WebbElement headingCommunications;
    private final WebbElement tabAudienceSegmentation;
    private final WebbElement tabCampaigns;
    private final WebbElement tabTemplates;
    private final WebbElement nameLabel;
    private final WebbElement communicationPreferenceLabel;
    private final WebbElement organizationLabel;
    private final WebbElement siteLabel;
    private final WebbElement descriptionLabel;
    private final WebbElement createButton;
    private final WebbElement segmentationName;
    private final WebbElement defaultCommunicationPreference;
    private final WebbElement defaultOrganization;
    private final WebbElement defaultSite;
    private final WebbElement communicationPreferenceDropDown;
    private final WebbElement emailPreference;
    private final WebbElement organizationDropDown;
    private final WebbElement bannerHealth;
    private final WebbElement siteDisable;
    private final WebbElement segmentationGroupDropDown;
    private final WebbElement programSegmentation;
    private final WebbElement demographicSegmentation;
    private final WebbElement addCategoryButton;
    private final WebbElement consentDropDown;
    private final WebbElement primaryConsent;
    private final WebbElement ehrConsent;
    private final WebbElement ehrConsentResponse;
    private final WebbElement basicPpi;
    private final WebbElement lifestylePpi;
    private final WebbElement overallHealthPpi;
    private final WebbElement healthCareAccessPpi;
    private final WebbElement familyHealthPpi;
    private final WebbElement personalMedicalPpi;
    private final WebbElement physicalMeasurement;
    private final WebbElement biosample;
    private final WebbElement equalDropDown;
    private final WebbElement isEqualTo;
    private final WebbElement isNotEqualTo;
    private final WebbElement isAnyOf;
    private final WebbElement isNotAnyOf;
    private final WebbElement statusDropDown;
    private final WebbElement eligible;
    private final WebbElement inProgress;
    private final WebbElement completed;
    private final WebbElement dateDropDown;
    private final WebbElement onAnyDate;
    private final WebbElement onExactDate;
    private final WebbElement before;
    private final WebbElement after;
    private final WebbElement onOrBefore;
    private final WebbElement onOrAfter;
    private final WebbElement isWithin;
    private final WebbElement isNotWithin;
    private final WebbElement ageRaceGenderDropDown;
    private final WebbElement age;
    private final WebbElement race;
    private final WebbElement gender;
    private final WebbElement campaignActivityDropDown;
    private final WebbElement emailSent;
    private final WebbElement emailOpened;
    private final WebbElement emailClicked;
    private final WebbElement equalDropDownSecond;
    private final WebbElement addLink;
    private final WebbElement ageRaceGenderValuesDropDown;
    private final WebbElement americanIndian;
    private final WebbElement asian;
    private final WebbElement africanAmerican;
    private final WebbElement hispanic;
    private final WebbElement middleEastern;
    private final WebbElement nativeHawaiin;
    private final WebbElement white;
    private final WebbElement noneOfTheseRace;
    private final WebbElement preferNotToAnswerRace;
    private final WebbElement age18;
    private final WebbElement age25;
    private final WebbElement age35;
    private final WebbElement age45;
    private final WebbElement age55;
    private final WebbElement age65;
    private final WebbElement age75;
    private final WebbElement age85;
    private final WebbElement man;
    private final WebbElement woman;
    private final WebbElement nonBinary;
    private final WebbElement transgender;
    private final WebbElement noneOfTheseGender;
    private final WebbElement preferNotToAnswerGender;
    private final WebbElement campaignsTitle;
    private final WebbElement newCampaignsTitle;
    private final WebbElement campaignName;
    private final WebbElement descriptionOptional;
    private final WebbElement capmaignGoalDropdown;
    private final WebbElement healthCareAccessGoal;
    private final WebbElement overallHealthGoal;
    private final WebbElement theBasicsGoal;
    private final WebbElement lifestyleGoal;
    private final WebbElement familyHistoryGoal;
    private final WebbElement primaryConsentGoal;
    private final WebbElement ehrGoal;
    private final WebbElement personalMedicalGoal;
    private final WebbElement sharingYourEhrGoal;
    private final WebbElement biospecimenGoal;
    private final WebbElement pmBGoal;
    private final WebbElement samplesToIzolateDnaGoal;
    private final WebbElement nextButton;
    private final WebbElement cancelButton;
    private final WebbElement saveButton;
    private final WebbElement saveAsDraftButton;
    private final WebbElement campaignNameTitle;
    private final WebbElement firstRadioButton;
    private final WebbElement selectTemplateTitle;
    private final WebbElement reviewTitle;
    private final WebbElement sendButton;
    private final WebbElement campaignNameStarMark;
    private final WebbElement idOrder;
    private final WebbElement nameOrder;
    private final WebbElement channelOrder;
    private final WebbElement createDateOrder;
    private final WebbElement modifiedDateOrder;
    private final WebbElement templatesTitle;
    private final WebbElement descriptionTextForm;
    private final WebbElement siteDropDown;
    private final WebbElement bannerHealthOrg;
    private final WebbElement baylorScottAndWhiteOrg;
    private final WebbElement bostonMedicalOrg;
    private final WebbElement ceadarsSinaiCenterOrg;
    private final WebbElement centerForCommunityOrg;
    private final WebbElement cherokeeHealthOrg;
    private final WebbElement communityHealthCenterOrg;
    private final WebbElement cooperGreenMercyOrg;
    private final WebbElement dvSanDiegoOrg;
    private final WebbElement emsiOrg;
    private final WebbElement eauClaireCenterOrg;
    private final WebbElement emoryUniversityOrg;
    private final WebbElement erieFamilyHealthOrg;
    private final WebbElement essentiaHealthOrg;
    private final WebbElement genomicsTestOrg;
    private final WebbElement hpoSanDiegoBloodBankOrg;
    private final WebbElement hrhCareOrg;
    private final WebbElement harlemHospitalOrg;
    private final WebbElement henryFordHealthOrg;
    private final WebbElement huntsvilleHospitalOrg;
    private final WebbElement jacksonHindsOrg;
    private final WebbElement loisianaStateUnversityOrg;
    private final WebbElement mcwSixteenStreetOrg;
    private final WebbElement mariposaCommunityOrg;
    private final WebbElement marshfieldClinicOrg;
    private final WebbElement medicalCollegeOfWisconsinOrg;
    private final WebbElement mobileAssetOrg;
    private final WebbElement morehouseSchoolOrg;
    private final WebbElement nearNorthHealthOrg;
    private final WebbElement newYorkPresbyterianOrg;
    private final WebbElement northsnoreUniversityOrg;
    private final WebbElement northwesternUniversityOrg;
    private final WebbElement partnersHealthcareOrg;
    private final WebbElement reliantMedicalOrg;
    private final WebbElement rushUniversityOrg;
    private final WebbElement sanYsidroOrg;
    private final WebbElement spectrumHealthOrg;
    private final WebbElement swedishAmericanCenterOrg;
    private final WebbElement templeUniversityOrg;
    private final WebbElement testProdOrg;
    private final WebbElement tuluneUniversityOrg;
    private final WebbElement universityMedicalCenterTuscaloosaOrg;
    private final WebbElement uoaBirmingamOrg;
    private final WebbElement uoaHuntsvilleOrg;
    private final WebbElement uoaMontgomeryOrg;
    private final WebbElement uoaSelmaOrg;
    private final WebbElement uocIrvineSchoolOrg;
    private final WebbElement uocDavisOrg;
    private final WebbElement uocSanDiegoOrg;
    private final WebbElement uocSanFranciscoOrg;
    private final WebbElement universityOfChicagoOrg;
    private final WebbElement universityOfFloridaOrg;
    private final WebbElement universityOfIllinoisOrg;
    private final WebbElement universityOfMiamiOrg;
    private final WebbElement universityOfMississippiOrg;
    private final WebbElement universityOfPittsburghOrg;
    private final WebbElement universityOfSouthAlabamaOrg;
    private final WebbElement universityOfSouthernCaliforniaOrg;
    private final WebbElement universityOfWisconsinOrg;
    private final WebbElement vaAtlantaOrg;
    private final WebbElement vaBostonOrg;
    private final WebbElement vaClementOrg;
    private final WebbElement vaEasternKansasOrg;
    private final WebbElement vaEventOrg;
    private final WebbElement vaMichaelEOrg;
    private final WebbElement vaMInneapolisOrg;
    private final WebbElement vaNewYorkHarborOrg;
    private final WebbElement vaPaloAltoOrg;
    private final WebbElement vaPhoenixHealthcareOrg;
    private final WebbElement vaSanDiegoOrg;
    private final WebbElement vhTestOrg;
    private final WebbElement walgreensHoustonOrg;
    private final WebbElement walgreensMemphisOrg;
    private final WebbElement walgreensPhoenixOrg;
    private final WebbElement weillCornellOrg;
    private final WebbElement warning;
    private final WebbElement sendNowButton;
    private final WebbElement successMessage;
    private final List<WebbElement> emailList;
    private final List<WebbElement> orgList;
    private final List<WebbElement> campaignsGoalList;
    private final List<WebbElement> genderList;
    private final List<WebbElement> ageList;
    private final List<WebbElement> raceList;
    private final List<WebbElement> ageRaceGenderList;
    private final List<WebbElement> dateList;
    private final List<WebbElement> equalList;
    private final List<WebbElement> consentList;
    private final List<WebbElement> communicationList;
    private final List<WebbElement> segmentationList;
    private final List<WebbElement> statusList;

    public CapacityManagementPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.saveButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary btn-md btn btn-default']");
        this.communicationsButton = app.newElement(LocatorType.CSS, "svg[class='src-components-common-icon-styles__communications-icon--3P03Etk6']");
        this.headingCommunications = app.newElement(LocatorType.XPATH, "//h1[text()='Communications']");
        this.tabAudienceSegmentation = app.newElement(LocatorType.XPATH, "//a[text()='Audience Segmentation']");
        this.tabCampaigns = app.newElement(LocatorType.XPATH, "//a[text()='Campaigns']");
        this.tabTemplates = app.newElement(LocatorType.XPATH, "//a[text()='Templates']");
        this.nameLabel = app.newElement(LocatorType.XPATH, "//label[text()='Name']");
        this.communicationPreferenceLabel = app.newElement(LocatorType.XPATH, "//label[text()='Communication Preference']");
        this.organizationLabel = app.newElement(LocatorType.XPATH, "//label[text()='Organization']");
        this.siteLabel = app.newElement(LocatorType.XPATH, "//label[text()='Site']");
        this.descriptionLabel = app.newElement(LocatorType.XPATH, "//label[text()='Description']");
        this.createButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary btn btn-default']");
        this.segmentationName = app.newElement(LocatorType.CSS, "input[placeholder='Segmentation Title']");
        this.defaultCommunicationPreference = app.newElement(LocatorType.XPATH, "//span[text()='Email']");
        this.defaultOrganization = app.newElement(LocatorType.XPATH, "//div[text()='All Organizations (Default)']");
        this.defaultSite = app.newElement(LocatorType.XPATH, "//div[text()='All Sites (Default)']");
        this.communicationPreferenceDropDown = app.newElement(LocatorType.ID, "comm-preference-select");
        this.emailPreference = app.newElement(LocatorType.ARIALABEL, "Email");
        communicationList = Arrays.asList(emailPreference);
        this.organizationDropDown = app.newElement(LocatorType.XPATH, "(//div[@class=' select-custom-wrapper'])[2]");
        this.bannerHealth = app.newElement(LocatorType.ARIALABEL, "Banner Health");
        this.siteDisable = app.newElement(LocatorType.CSS, "div[class='Select is-clearable is-disabled Select--multi']");
        this.segmentationGroupDropDown = app.newElement(LocatorType.ID, "add-category-select");
        this.programSegmentation = app.newElement(LocatorType.ARIALABEL, "Program Milestones");
        this.demographicSegmentation = app.newElement(LocatorType.ARIALABEL, "Demographic Segmentation");
        segmentationList = Arrays.asList(programSegmentation, demographicSegmentation);
        this.addCategoryButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary button-lg btn btn-default']");
        this.consentDropDown = app.newElement(LocatorType.ID, "primary-select-value");
        this.primaryConsent = app.newElement(LocatorType.ARIALABEL, "Primary Consent");
        this.ehrConsent = app.newElement(LocatorType.ARIALABEL, "EHR Consent");
        this.ehrConsentResponse = app.newElement(LocatorType.ARIALABEL, "EHR Consent Response");
        this.basicPpi = app.newElement(LocatorType.ARIALABEL, "The Basics");
        this.lifestylePpi = app.newElement(LocatorType.ARIALABEL, "Lifestyle");
        this.overallHealthPpi = app.newElement(LocatorType.ARIALABEL, "Overall Health");
        this.healthCareAccessPpi = app.newElement(LocatorType.ARIALABEL, "Health Care Access & Utilization");
        this.familyHealthPpi = app.newElement(LocatorType.ARIALABEL, "Family History");
        this.personalMedicalPpi = app.newElement(LocatorType.ARIALABEL, "Personal Medical History");
        this.physicalMeasurement = app.newElement(LocatorType.ARIALABEL, "Physical Measurement");
        this.biosample = app.newElement(LocatorType.ARIALABEL, "Biosample");
        consentList = Arrays.asList(primaryConsent, ehrConsent, ehrConsentResponse, basicPpi,
                lifestylePpi, overallHealthPpi, healthCareAccessPpi, familyHealthPpi, personalMedicalPpi, physicalMeasurement, biosample);
        this.equalDropDown = app.newElement(LocatorType.ID, "primary-operator-select");
        this.isEqualTo = app.newElement(LocatorType.ARIALABEL, "is equal to");
        this.isNotEqualTo = app.newElement(LocatorType.ARIALABEL, "is not equal to");
        this.isAnyOf = app.newElement(LocatorType.ARIALABEL, "is any of");
        this.isNotAnyOf = app.newElement(LocatorType.ARIALABEL, "is not any of");
        equalList = Arrays.asList(isEqualTo, isNotEqualTo, isAnyOf, isNotAnyOf);
        this.statusDropDown = app.newElement(LocatorType.ID, "modifier-select");
        this.eligible = app.newElement(LocatorType.ARIALABEL, "Eligible, But Not Started");
        this.inProgress = app.newElement(LocatorType.ARIALABEL, "In Progress");
        this.completed = app.newElement(LocatorType.ARIALABEL, "Completed");
        statusList = Arrays.asList(eligible, inProgress, completed);
        this.dateDropDown = app.newElement(LocatorType.ID, "date-operator-select");
        this.onAnyDate = app.newElement(LocatorType.ARIALABEL, "on any date (Default)");
        this.onExactDate = app.newElement(LocatorType.ARIALABEL, "on exact date");
        this.before = app.newElement(LocatorType.ARIALABEL, "before");
        this.after = app.newElement(LocatorType.ARIALABEL, "after");
        this.onOrBefore = app.newElement(LocatorType.ARIALABEL, "on or before");
        this.onOrAfter = app.newElement(LocatorType.ARIALABEL, "on or after");
        this.isWithin = app.newElement(LocatorType.ARIALABEL, "is within");
        this.isNotWithin = app.newElement(LocatorType.ARIALABEL, "is not within");
        dateList = Arrays.asList(onAnyDate, onExactDate, before, after, onOrBefore, onOrAfter, isWithin, isNotWithin);
        this.ageRaceGenderDropDown = app.newElement(LocatorType.XPATH, "(//div[@id='primary-select-value'])[2]");
        this.age = app.newElement(LocatorType.ARIALABEL, "Age");
        this.race = app.newElement(LocatorType.ARIALABEL, "Race");
        this.gender = app.newElement(LocatorType.ARIALABEL, "Gender");
        this.campaignActivityDropDown = app.newElement(LocatorType.XPATH, "(//div[@id='primary-select-value'])[3]");
        this.emailSent = app.newElement(LocatorType.ARIALABEL, "Email Sent");
        this.emailOpened = app.newElement(LocatorType.ARIALABEL, "Email Opened");
        this.emailClicked = app.newElement(LocatorType.ARIALABEL, "Email Clicked");
        emailList = Arrays.asList(emailSent, emailOpened, emailClicked);
        ageRaceGenderList = Arrays.asList(age, race, gender);
        this.equalDropDownSecond = app.newElement(LocatorType.ID, "multi-operator-select");
        this.addLink = app.newElement(LocatorType.CSS, "a[class='action-link add-link']");
        this.ageRaceGenderValuesDropDown = app.newElement(LocatorType.ID, "multi-value-select");
        this.americanIndian = app.newElement(LocatorType.ARIALABEL, "American Indian or Alaska Native");
        this.asian = app.newElement(LocatorType.ARIALABEL, "Asian");
        this.africanAmerican = app.newElement(LocatorType.ARIALABEL, "Black, African American or African");
        this.hispanic = app.newElement(LocatorType.ARIALABEL, "Hispanic, Latino, or Spanish");
        this.middleEastern = app.newElement(LocatorType.ARIALABEL, "Middle Eastern or North African");
        this.nativeHawaiin = app.newElement(LocatorType.ARIALABEL, "Native Hawaiian or other Pacific Islander");
        this.white = app.newElement(LocatorType.ARIALABEL, "White");
        this.noneOfTheseRace = app.newElement(LocatorType.ARIALABEL, "None of these fully describe me");
        this.preferNotToAnswerRace = app.newElement(LocatorType.ARIALABEL, "Prefer not to answer");
        raceList = Arrays.asList(americanIndian, asian, africanAmerican, hispanic, middleEastern, nativeHawaiin, white, noneOfTheseRace, preferNotToAnswerRace);
        this.age18 = app.newElement(LocatorType.ARIALABEL, "18-24");
        this.age25 = app.newElement(LocatorType.ARIALABEL, "25-34");
        this.age35 = app.newElement(LocatorType.ARIALABEL, "35-44");
        this.age45 = app.newElement(LocatorType.ARIALABEL, "45-54");
        this.age55 = app.newElement(LocatorType.ARIALABEL, "55-64");
        this.age65 = app.newElement(LocatorType.ARIALABEL, "65-74");
        this.age75 = app.newElement(LocatorType.ARIALABEL, "75-84");
        this.age85 = app.newElement(LocatorType.ARIALABEL, "85+");
        ageList = Arrays.asList(age18, age25, age35, age45, age55, age65, age75, age85);
        this.man = app.newElement(LocatorType.ARIALABEL, "Man");
        this.woman = app.newElement(LocatorType.ARIALABEL, "Woman");
        this.nonBinary = app.newElement(LocatorType.ARIALABEL, "Non-Binary");
        this.transgender = app.newElement(LocatorType.ARIALABEL, "Transgender");
        this.noneOfTheseGender = app.newElement(LocatorType.ARIALABEL, "None of these describe me, and I want to specify");
        this.preferNotToAnswerGender = app.newElement(LocatorType.ARIALABEL, "Prefer not to answer");
        genderList = Arrays.asList(man, woman, nonBinary, transgender, noneOfTheseGender, preferNotToAnswerGender);
        this.campaignsTitle = app.newElement(LocatorType.CSS, "div[class='campaign-tittle col-xs-6']");
        this.newCampaignsTitle = app.newElement(LocatorType.CSS, "div[class='campaign-name'");
        this.campaignName = app.newElement(LocatorType.CSS, "input[label='Campaign Name']");
        this.descriptionOptional = app.newElement(LocatorType.CSS, "textarea[label='Description (Optional)']");
        this.capmaignGoalDropdown = app.newElement(LocatorType.CSS, "div[class='select-custom-wrapper']");
        this.healthCareAccessGoal = app.newElement(LocatorType.ARIALABEL, "Complete Health Care Access & Utilization PPI Module");
        this.overallHealthGoal = app.newElement(LocatorType.ARIALABEL, "Complete Overall Health PPI Module");
        this.theBasicsGoal = app.newElement(LocatorType.ARIALABEL, "Complete The Basics PPI Module");
        this.lifestyleGoal = app.newElement(LocatorType.ARIALABEL, "Complete Lifestyle PPI Module");
        this.familyHistoryGoal = app.newElement(LocatorType.ARIALABEL, "Complete Family History PPI Module");
        this.primaryConsentGoal = app.newElement(LocatorType.ARIALABEL, "Complete Primary Consent");
        this.ehrGoal = app.newElement(LocatorType.ARIALABEL, "Complete EHR Consent");
        this.personalMedicalGoal = app.newElement(LocatorType.ARIALABEL, "Complete Personal Medical History PPI Module");
        this.sharingYourEhrGoal = app.newElement(LocatorType.ARIALABEL, "Complete Sharing Your Electronic Health Records");
        this.biospecimenGoal = app.newElement(LocatorType.ARIALABEL, "Complete Biospecimen");
        this.pmBGoal = app.newElement(LocatorType.ARIALABEL, "Complete PM/B");
        this.samplesToIzolateDnaGoal = app.newElement(LocatorType.ARIALABEL, "Complete Samples to Isolate DNA");
        campaignsGoalList = Arrays.asList(healthCareAccessGoal, overallHealthGoal, theBasicsGoal, lifestyleGoal, familyHistoryGoal, primaryConsentGoal,
                ehrGoal, personalMedicalGoal, sharingYourEhrGoal, biospecimenGoal, pmBGoal, samplesToIzolateDnaGoal);
        this.nextButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary btn-next btn btn-default']");
        this.cancelButton = app.newElement(LocatorType.CSS, "button[class='btn btn-secondary btn-cancel btn btn-default']");
        this.saveAsDraftButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary btn-save btn btn-default']");
        this.campaignNameTitle = app.newElement(LocatorType.CSS, "div[class='campaign-name']");
        this.firstRadioButton = app.newElement(LocatorType.XPATH, "(//td[@style='text-align: center;'])[1]");
        this.selectTemplateTitle = app.newElement(LocatorType.CSS, "div[class='title']");
        this.reviewTitle = app.newElement(LocatorType.CSS, "div[class=\"default-label medium-label\"]");
        this.sendButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary-2 btn-Send btn btn-default']");
        this.campaignNameStarMark = app.newElement(LocatorType.XPATH, "//label[text()='Campaign Name']//span");
        this.idOrder = app.newElement(LocatorType.CSS, "th[data-field='id']");
        this.nameOrder = app.newElement(LocatorType.CSS, "th[data-field='name']");
        this.channelOrder = app.newElement(LocatorType.CSS, "th[data-field='channel']");
        this.createDateOrder = app.newElement(LocatorType.CSS, "th[data-field='createdDate']");
        this.modifiedDateOrder = app.newElement(LocatorType.CSS, "th[data-field='modifiedDate']");
        this.templatesTitle = app.newElement(LocatorType.CSS, "div[class='templates-count col-xs-12']");
        this.descriptionTextForm = app.newElement(LocatorType.CSS, "textarea[class=form-control]");
        this.siteDropDown = app.newElement(LocatorType.ID, "site-select");
        this.bannerHealthOrg = app.newElement(LocatorType.ARIALABEL, "Banner Health");
        this.baylorScottAndWhiteOrg = app.newElement(LocatorType.ARIALABEL, "Baylor Scott and White Health");
        this.bostonMedicalOrg = app.newElement(LocatorType.ARIALABEL, "Boston Medical Center");
        this.ceadarsSinaiCenterOrg = app.newElement(LocatorType.ARIALABEL, "Cedars-Sinai Medical Center");
        this.centerForCommunityOrg = app.newElement(LocatorType.ARIALABEL, "Center for Community Engagement and Health Partnerships");
        this.cherokeeHealthOrg = app.newElement(LocatorType.ARIALABEL, "Cherokee Health Systems");
        this.communityHealthCenterOrg = app.newElement(LocatorType.ARIALABEL, "Community Health Center, Inc");
        this.cooperGreenMercyOrg = app.newElement(LocatorType.ARIALABEL, "Cooper Green Mercy Hospital");
        this.dvSanDiegoOrg = app.newElement(LocatorType.ARIALABEL, "DV San Diego Blood Bank");
        this.emsiOrg = app.newElement(LocatorType.ARIALABEL, "EMSI");
        this.eauClaireCenterOrg = app.newElement(LocatorType.ARIALABEL, "Eau Claire Cooperative Health Center");
        this.emoryUniversityOrg = app.newElement(LocatorType.ARIALABEL, "Emory University");
        this.erieFamilyHealthOrg = app.newElement(LocatorType.ARIALABEL, "Erie Family Health Centers");
        this.essentiaHealthOrg = app.newElement(LocatorType.ARIALABEL, "Essentia Health");
        this.genomicsTestOrg = app.newElement(LocatorType.ARIALABEL, "GenomicsTest Organization");
        this.hpoSanDiegoBloodBankOrg = app.newElement(LocatorType.ARIALABEL, "HPO San Diego Blood Bank");
        this.hrhCareOrg = app.newElement(LocatorType.ARIALABEL, "HRHCare, Inc.");
        this.harlemHospitalOrg = app.newElement(LocatorType.ARIALABEL, "Harlem Hospital");
        this.henryFordHealthOrg = app.newElement(LocatorType.ARIALABEL, "Henry Ford Health System");
        this.huntsvilleHospitalOrg = app.newElement(LocatorType.ARIALABEL, "Huntsville Hospital");
        this.jacksonHindsOrg = app.newElement(LocatorType.ARIALABEL, "Jackson-Hinds Comprehensive Health Center");
        this.loisianaStateUnversityOrg = app.newElement(LocatorType.ARIALABEL, "Louisiana State University");
        this.mcwSixteenStreetOrg = app.newElement(LocatorType.ARIALABEL, "MCW - Sixteenth Street Community Health Center (SSHC 16th)");
        this.mariposaCommunityOrg = app.newElement(LocatorType.ARIALABEL, "Mariposa Community Health Center");
        this.marshfieldClinicOrg = app.newElement(LocatorType.ARIALABEL, "Marshfield Clinic");
        this.medicalCollegeOfWisconsinOrg = app.newElement(LocatorType.ARIALABEL, "Medical College of Wisconsin");
        this.mobileAssetOrg = app.newElement(LocatorType.ARIALABEL, "Mobile Engagement Asset (MEA) 2");
        this.morehouseSchoolOrg = app.newElement(LocatorType.ARIALABEL, "Morehouse School of Medicine");
        this.nearNorthHealthOrg = app.newElement(LocatorType.ARIALABEL, "Near North Health Services Corporation");
        this.newYorkPresbyterianOrg = app.newElement(LocatorType.ARIALABEL, "New York Presbyterian Columbia University");
        this.northsnoreUniversityOrg = app.newElement(LocatorType.ARIALABEL, "Northshore University Health System");
        this.northwesternUniversityOrg = app.newElement(LocatorType.ARIALABEL, "Northwestern University");
        this.partnersHealthcareOrg = app.newElement(LocatorType.ARIALABEL, "Partners Healthcare");
        this.reliantMedicalOrg = app.newElement(LocatorType.ARIALABEL, "Reliant Medical Group (Meyers Primary Care)");
        this.rushUniversityOrg = app.newElement(LocatorType.ARIALABEL, "Rush University");
        this.sanYsidroOrg = app.newElement(LocatorType.ARIALABEL, "San Ysidro Health Center");
        this.spectrumHealthOrg = app.newElement(LocatorType.ARIALABEL, "Spectrum Health System");
        this.swedishAmericanCenterOrg = app.newElement(LocatorType.ARIALABEL, "Swedish American Regional Center, UW Health");
        this.templeUniversityOrg = app.newElement(LocatorType.ARIALABEL, "Temple University");
        this.testProdOrg = app.newElement(LocatorType.ARIALABEL, "Test Prod Organization");
        this.tuluneUniversityOrg = app.newElement(LocatorType.ARIALABEL, "Tulane University");
        this.universityMedicalCenterTuscaloosaOrg = app.newElement(LocatorType.ARIALABEL, "University Medical Center Tuscaloosa");
        this.uoaBirmingamOrg = app.newElement(LocatorType.ARIALABEL, "University of Alabama Birmingham");
        this.uoaHuntsvilleOrg = app.newElement(LocatorType.ARIALABEL, "University of Alabama Birmingham Huntsville");
        this.uoaMontgomeryOrg = app.newElement(LocatorType.ARIALABEL, "University of Alabama Birmingham Montgomery");
        this.uoaSelmaOrg = app.newElement(LocatorType.ARIALABEL, "University of Alabama Birmingham Selma");
        this.uocIrvineSchoolOrg = app.newElement(LocatorType.ARIALABEL, "University of California Irvine School of Medicine");
        this.uocDavisOrg = app.newElement(LocatorType.ARIALABEL, "University of California, Davis");
        this.uocSanDiegoOrg = app.newElement(LocatorType.ARIALABEL, "University of California, San Diego");
        this.uocSanFranciscoOrg = app.newElement(LocatorType.ARIALABEL, "University of California, San Francisco");
        this.universityOfChicagoOrg = app.newElement(LocatorType.ARIALABEL, "University of Chicago");
        this.universityOfFloridaOrg = app.newElement(LocatorType.ARIALABEL, "University of Florida");
        this.universityOfIllinoisOrg = app.newElement(LocatorType.ARIALABEL, "University of Illinois at Chicago");
        this.universityOfMiamiOrg = app.newElement(LocatorType.ARIALABEL, "University of Miami");
        this.universityOfMississippiOrg = app.newElement(LocatorType.ARIALABEL, "University of Mississippi Medical Center");
        this.universityOfPittsburghOrg = app.newElement(LocatorType.ARIALABEL, "University of Pittsburgh Medical Center");
        this.universityOfSouthAlabamaOrg = app.newElement(LocatorType.ARIALABEL, "University of South Alabama");
        this.universityOfSouthernCaliforniaOrg = app.newElement(LocatorType.ARIALABEL, "University of Southern California");
        this.universityOfWisconsinOrg = app.newElement(LocatorType.ARIALABEL, "University of Wisconsin, Madison");
        this.vaAtlantaOrg = app.newElement(LocatorType.ARIALABEL, "VA Atlanta Medical Center");
        this.vaBostonOrg = app.newElement(LocatorType.ARIALABEL, "VA Boston Healthcare System");
        this.vaClementOrg = app.newElement(LocatorType.ARIALABEL, "VA Clement J. Zablocki Medical Center");
        this.vaEasternKansasOrg = app.newElement(LocatorType.ARIALABEL, "VA Eastern Kansas Health Care System");
        this.vaEventOrg = app.newElement(LocatorType.ARIALABEL, "VA Event");
        this.vaMichaelEOrg = app.newElement(LocatorType.ARIALABEL, "VA Michael E. DeBakey Medical Center");
        this.vaMInneapolisOrg = app.newElement(LocatorType.ARIALABEL, "VA Minneapolis Healthcare System");
        this.vaNewYorkHarborOrg = app.newElement(LocatorType.ARIALABEL, "VA New York Harbor Healthcare System");
        this.vaPaloAltoOrg = app.newElement(LocatorType.ARIALABEL, "VA Palo Alto Health Care System");
        this.vaPhoenixHealthcareOrg = app.newElement(LocatorType.ARIALABEL, "VA Phoenix Healthcare System");
        this.vaSanDiegoOrg = app.newElement(LocatorType.ARIALABEL, "VA San Diego Healthcare System");
        this.vhTestOrg = app.newElement(LocatorType.ARIALABEL, "VH Test Organization");
        this.walgreensHoustonOrg = app.newElement(LocatorType.ARIALABEL, "Walgreens Houston");
        this.walgreensMemphisOrg = app.newElement(LocatorType.ARIALABEL, "Walgreens Memphis");
        this.walgreensPhoenixOrg = app.newElement(LocatorType.ARIALABEL, "Walgreens Phoenix");
        this.weillCornellOrg = app.newElement(LocatorType.ARIALABEL, "Weill Cornell Medicine");
        orgList = Arrays.asList(bannerHealthOrg, baylorScottAndWhiteOrg, bostonMedicalOrg, ceadarsSinaiCenterOrg, centerForCommunityOrg,
                cherokeeHealthOrg, communityHealthCenterOrg, cooperGreenMercyOrg, dvSanDiegoOrg, emsiOrg, eauClaireCenterOrg, emoryUniversityOrg,
                erieFamilyHealthOrg, essentiaHealthOrg, genomicsTestOrg, hpoSanDiegoBloodBankOrg, hrhCareOrg, harlemHospitalOrg, henryFordHealthOrg,
                huntsvilleHospitalOrg, jacksonHindsOrg, loisianaStateUnversityOrg, mcwSixteenStreetOrg, mariposaCommunityOrg, marshfieldClinicOrg,
                medicalCollegeOfWisconsinOrg, mobileAssetOrg, morehouseSchoolOrg, nearNorthHealthOrg, newYorkPresbyterianOrg, northsnoreUniversityOrg,
                northwesternUniversityOrg, partnersHealthcareOrg, reliantMedicalOrg, rushUniversityOrg, sanYsidroOrg, spectrumHealthOrg,
                swedishAmericanCenterOrg, templeUniversityOrg, testProdOrg, tuluneUniversityOrg, universityMedicalCenterTuscaloosaOrg,
                uoaBirmingamOrg, uoaHuntsvilleOrg, uoaMontgomeryOrg, uoaSelmaOrg, uocIrvineSchoolOrg, uocDavisOrg, uocSanDiegoOrg, uocSanFranciscoOrg,
                universityOfChicagoOrg, universityOfFloridaOrg, universityOfIllinoisOrg, universityOfMiamiOrg, universityOfMississippiOrg,
                universityOfPittsburghOrg, universityOfSouthAlabamaOrg, universityOfSouthernCaliforniaOrg, universityOfWisconsinOrg, vaAtlantaOrg,
                vaBostonOrg, vaClementOrg, vaEasternKansasOrg, vaEventOrg, vaMichaelEOrg, vaMInneapolisOrg, vaNewYorkHarborOrg, vaPaloAltoOrg,
                vaPhoenixHealthcareOrg, vaSanDiegoOrg, vhTestOrg, walgreensHoustonOrg, walgreensMemphisOrg, walgreensPhoenixOrg, weillCornellOrg);
        this.warning = app.newElement(LocatorType.CSS, "p[class='title']");
        this.sendNowButton = app.newElement(LocatorType.CSS, "button[class='button-send-now btn btn-primary']");
        this.successMessage = app.newElement(LocatorType.CSS, "div[class='message animated fade success in']");
    }

    public void confirmSend() {
        if (warning.is().present()) {
            sendNowButton.click();
        }
    }

    public void saveSegmentation() {
        saveButton.waitFor().displayed();
        saveButton.click();
    }

    public void verifyOrgList() {
        organizationDropDown.click();
        for (WebbElement each : orgList) {
            each.assertState().displayed();
        }
    }

    public void typeDescription() {
        descriptionTextForm.type("Test Automation");
    }

    public void selectSite(String site) {
        siteDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, site);
        option.click();
    }

    public void selectOrganization(String org) {
        organizationDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, org);
        option.click();
    }

    public void selectCommunicationPreference(String channel) {
        communicationPreferenceDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, channel);
        option.click();
    }

    public void typeSegmentationName() {
        segmentationName.type(campaignNameRandom);
    }

    public void assertTemplatesTitle() {
        templatesTitle.assertState().displayed();
    }

    public void orderByName() {
        nameOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=name]>span[class='order']");
        order.assertState().present();
    }

    public void orderById() {
        idOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=id]>span[class='order']");
        order.assertState().present();
    }

    public void orderByChannel() {
        channelOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=channel]>span[class='order']");
        order.assertState().present();
    }

    public void orderByCreatedDate() {
        createDateOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=createdDate]>span[class='order']");
        order.assertState().present();
    }

    public void orderByModifiedDate() {
        modifiedDateOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=modifiedDate]>span[class='order']");
        order.assertState().present();
    }

    public void verifyCreatedCampaign() {
        successMessage.is().displayed();
    }

    public void saveOrDraft(String button) {
        if (("created").equals(button)) {
            clickOnSendButton();
        } else if (("saved as draft").equals(button)) {
            saveAsDraftButton.waitFor().displayed();
            saveDraft();
        }
    }

    public void clickOnSendButton() {
        sendButton.waitFor().displayed();
        sendButton.click();
    }

    public void assertReviewTitle() {
        reviewTitle.assertState().displayed();
    }

    public void assertSelectTemplateTitle() {
        selectTemplateTitle.assertState().displayed();
    }

    public void selectFirstRadioButton() {
        firstRadioButton.click();
    }

    public void verifyCampaignName() {
        if (campaignNameRandom.equals(campaignNameTitle.get().text())) {
            campaignNameTitle.assertState().displayed();
        }
    }

    public void saveDraft() {
        saveAsDraftButton.click();
    }

    public void clickOnCancelButton() {
        cancelButton.click();
    }

    public void clickOnNextButton() {
        nextButton.waitFor().displayed();
        nextButton.click();
    }

    public void selectChannel(String option) {
        WebbElement radioOption = app.newElement(LocatorType.CSS, "input[label='" + option + "']");
        WebbElement radioSelect = app.newElement(LocatorType.XPATH, "//span[text()='" + option + "']");
        if (!radioOption.is().checked()) {
            radioSelect.click();
        }
    }

    public void selectCampaignGoal() {
        capmaignGoalDropdown.click();
        for (WebbElement each : campaignsGoalList) {
            each.assertState().displayed();
        }
        overallHealthGoal.click();
    }

    public void enterCampaignName() {
        campaignNameStarMark.assertState().displayed();
        campaignName.type(campaignNameRandom);
    }

    public void enterDescriptionOptional() {
        descriptionOptional.type("Test Automation");
    }

    public void assertNewCampaignsTitle() {
        newCampaignsTitle.assertState().displayed();
    }

    public void assertCampaignsTitle() {
        campaignsTitle.assertState().displayed();
    }

    public void selectCampaignsTab() {
        WebbElement nonActive = app.newElement(LocatorType.XPATH, "//li[@class='sub-tab ']/a[@href='/communications/campaigns']");
        if (nonActive.is().present()) {
            tabCampaigns.click();
        }
    }

    public void selectTemplatesTab() {
        WebbElement nonActive = app.newElement(LocatorType.XPATH, "//li[@class='sub-tab ']/a[@href='/communications/templates']");
        if (nonActive.is().present()) {
            tabTemplates.click();
        }
    }

    public void selectAudienceSegmentationTab() {
        WebbElement nonActive = app.newElement(LocatorType.XPATH, "//li[@class='sub-tab ']/a[@href='/communications/segmentation']");
        if (nonActive.is().present()) {
            tabAudienceSegmentation.click();
        }
    }

    public void verifyAgeList() {
        ageRaceGenderValuesDropDown.click();
        for (WebbElement each : ageList) {
            each.assertState().displayed();
        }
    }

    public void selectAge(String age) {
        ageRaceGenderValuesDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, age);
        option.click();
        ageRaceGenderValuesDropDown.click();
    }

    public void verifyGenderList() {
        ageRaceGenderValuesDropDown.click();
        for (WebbElement each : genderList) {
            each.assertState().displayed();
        }
    }

    public void verifyRaceList() {
        ageRaceGenderValuesDropDown.click();
        for (WebbElement each : raceList) {
            each.assertState().displayed();
        }
    }

    public void addNewCategory() {
        addLink.click();
    }

    public void verifyEqualDropDownSecond() {
        equalDropDownSecond.click();
        for (WebbElement each : equalList) {
            each.assertState().displayed();
        }
    }

    public void selectAgeRaceGender(String ageRaceGender) {
        WebbElement option = app.newElement(LocatorType.ARIALABEL, ageRaceGender);
        option.click();
    }

    public void selectEmailFilter(String emailFilter) {
        WebbElement option = app.newElement(LocatorType.ARIALABEL, emailFilter);
        option.click();
    }

    public void argDropdown() {
        ageRaceGenderDropDown.click();
    }

    public void verifyCampaignActivityDropDown() {
        campaignActivityDropDown.click();
        for (WebbElement each : emailList) {
            each.assertState().displayed();
        }
    }

    public void verifyAgeRaceGenderDropdown() {
        for (WebbElement each : ageRaceGenderList) {
            each.assertState().displayed();
        }
    }

    public void verifyDateDropDown() {
        dateDropDown.click();
        for (WebbElement each : dateList) {
            each.assertState().displayed();
        }
    }

    public void verifyStatusDropDown() {
        statusDropDown.click();
        for (WebbElement each : statusList) {
            each.assertState().displayed();
        }
    }

    public void verifyEqualDropdown() {
        equalDropDown.click();
        for (WebbElement each : equalList) {
            each.assertState().displayed();
        }
    }

    public void consentDropDownClick() {
        consentDropDown.click();
    }

    public void verifyConsentDropdown() {
        consentDropDown.click();
        for (WebbElement each : consentList) {
            each.assertState().displayed();
        }
    }

    public void selectConsent(String consent) {
        WebbElement option = app.newElement(LocatorType.ARIALABEL, consent);
        option.click();
    }

    public void selectProgramSegmentationCategory(String group) {
        segmentationGroupDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, group);
        option.click();
        addCategoryButton.click();
    }

    public void verifySegmentationGroup() {
        segmentationGroupDropDown.click();
        for (WebbElement each : segmentationList) {
            each.assertState().displayed();
        }
        addCategoryButton.assertState().displayed();
    }

    public void verifySiteValues() {
        siteDisable.assertState().displayed();
    }

    public void verifyOrganizationValues() {
        organizationDropDown.click();
        bannerHealth.assertState().displayed();
    }

    public void verifyCommunicationPreferenceValues() {
        communicationPreferenceDropDown.click();
        for (WebbElement each : communicationList) {
            each.assertState().displayed();
        }
    }

    /**
     * Waits for default values to be displayed and asserts it.
     */
    public void verifyDefaultValues() {
        segmentationName.assertState().displayed();
        defaultCommunicationPreference.assertState().displayed();
        defaultOrganization.assertState().displayed();
        defaultSite.assertState().displayed();
    }

    /**
     * Waits for labels to be displayed and asserts it.
     */
    public void verifyAudienceSegmentationLabels() {
        nameLabel.assertState().displayed();
        communicationPreferenceLabel.assertState().displayed();
        organizationLabel.assertState().displayed();
        siteLabel.assertState().displayed();
        descriptionLabel.assertState().displayed();
    }

    /**
     * Clicks on the create button
     */
    public void createNew() {
        createButton.click();
    }

    /**
     * Clicks on Communications button.
     */
    public void selectCommunications() {
        communicationsButton.click();
    }

    /**
     * Waits for the header to be displayed.
     */
    public void assertTitle() {
        headingCommunications.assertState().displayed();
    }

    /**
     * Waits for the tab to be displayed.
     */
    public void assertAudienceSegmentation() {
        tabAudienceSegmentation.assertState().displayed();
    }

    /**
     * Waits for the tab to be displayed.
     */
    public void assertCampaigns() {
        tabCampaigns.assertState().displayed();
    }

    /**
     * Waits for the tab to be displayed.
     */
    public void assertTemplates() {
        tabTemplates.assertState().displayed();
    }
}
