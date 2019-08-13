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
    
    private final String campaignNameRandom = "Test Automation #"  + UserUtility.generateUUID(5);
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
    private final WebbElement defaultName;
    private final WebbElement defaultCommunicationPreference;
    private final WebbElement defaultOrganization;
    private final WebbElement defaultSite;
    private final WebbElement communicationPreferenceDropDown;
    private final WebbElement smsPreference;
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
    private final WebbElement inBetween;
    private final WebbElement ageRaceGenderDropDown;
    private final WebbElement age;
    private final WebbElement race;
    private final WebbElement gender;
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
    private final WebbElement goal1;
    private final WebbElement goal2;
    private final WebbElement nextButton;
    private final WebbElement cancelButton;
    private final WebbElement saveAsDraftButton;
    private final WebbElement campaignNameTitle;
    private final WebbElement firstRadioButton;
    private final WebbElement selectTemplateTitle;
    private final WebbElement reviewTitle;
    private final WebbElement sendButton;
    private final WebbElement campaignNameStarMark;
    private final WebbElement createdCampaign;
    private final WebbElement spinner;
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
        this.defaultName = app.newElement(LocatorType.CSS, "input[placeholder='Segmentation Title']");
        this.defaultCommunicationPreference = app.newElement(LocatorType.XPATH, "//div[text()='Select Channel']");
        this.defaultOrganization = app.newElement(LocatorType.XPATH, "//div[text()='Select Organization']");
        this.defaultSite = app.newElement(LocatorType.XPATH, "//div[text()='Select Site']");
        this.communicationPreferenceDropDown = app.newElement(LocatorType.ID, "comm-preference-select");
        this.smsPreference = app.newElement(LocatorType.ARIALABEL, "SMS");
        this.emailPreference = app.newElement(LocatorType.ARIALABEL, "Email");
        communicationList = Arrays.asList(smsPreference, emailPreference);
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
        this.basicPpi = app.newElement(LocatorType.ARIALABEL, "Basic PPI Module");
        this.lifestylePpi = app.newElement(LocatorType.ARIALABEL, "Lifestyle PPI Module");
        this.overallHealthPpi = app.newElement(LocatorType.ARIALABEL, "Overall Health PPI Module");
        this.healthCareAccessPpi = app.newElement(LocatorType.ARIALABEL, "Health Care Access & Utilization PPI Module");
        this.familyHealthPpi = app.newElement(LocatorType.ARIALABEL, "Family Health PPI Module");
        this.personalMedicalPpi = app.newElement(LocatorType.ARIALABEL, "Personal Medical History PPI Module");
        this.physicalMeasurement = app.newElement(LocatorType.ARIALABEL, "Physical Measurement");
        this.biosample = app.newElement(LocatorType.ARIALABEL, "Biosample");
        consentList = Arrays.asList(primaryConsent, ehrConsent, ehrConsentResponse, basicPpi,
                lifestylePpi, overallHealthPpi, healthCareAccessPpi, familyHealthPpi, personalMedicalPpi, physicalMeasurement, biosample);
        this.equalDropDown = app.newElement(LocatorType.ID, "primary-operator-select");
        this.isEqualTo = app.newElement(LocatorType.ARIALABEL, "Is equal to");
        this.isNotEqualTo = app.newElement(LocatorType.ARIALABEL, "Is not equal to");
        this.isAnyOf = app.newElement(LocatorType.ARIALABEL, "Is any of");
        this.isNotAnyOf = app.newElement(LocatorType.ARIALABEL, "Is not any of");
        equalList = Arrays.asList(isEqualTo, isNotEqualTo, isAnyOf, isNotAnyOf);
        this.statusDropDown = app.newElement(LocatorType.ID, "modifier-select");
        this.eligible = app.newElement(LocatorType.ARIALABEL, "Eligible, But Not Started");
        this.inProgress = app.newElement(LocatorType.ARIALABEL, "In Progress");
        this.completed = app.newElement(LocatorType.ARIALABEL, "Completed");
        statusList = Arrays.asList(eligible, inProgress, completed);
        this.dateDropDown = app.newElement(LocatorType.ID, "date-operator-select");
        this.onAnyDate = app.newElement(LocatorType.ARIALABEL, "on any date (default)");
        this.onExactDate = app.newElement(LocatorType.ARIALABEL, "on exact date");
        this.before = app.newElement(LocatorType.ARIALABEL, "before");
        this.after = app.newElement(LocatorType.ARIALABEL, "after");
        this.onOrBefore = app.newElement(LocatorType.ARIALABEL, "on or before");
        this.onOrAfter = app.newElement(LocatorType.ARIALABEL, "on or after");
        this.inBetween = app.newElement(LocatorType.ARIALABEL, "in between");
        dateList = Arrays.asList(onAnyDate, onExactDate, before, after, onOrBefore, onOrAfter, inBetween);
        this.ageRaceGenderDropDown = app.newElement(LocatorType.XPATH, "(//div[@id='primary-select-value'])[2]");
        this.age = app.newElement(LocatorType.ARIALABEL, "Age");
        this.race = app.newElement(LocatorType.ARIALABEL, "Race");
        this.gender = app.newElement(LocatorType.ARIALABEL, "Gender");
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
        this.nonBinary = app.newElement(LocatorType.ARIALABEL, "Non-binary");
        this.transgender = app.newElement(LocatorType.ARIALABEL, "Transgender");
        this.noneOfTheseGender = app.newElement(LocatorType.ARIALABEL, "None of these fully describe me");
        this.preferNotToAnswerGender = app.newElement(LocatorType.ARIALABEL, "Prefer not to answer");
        genderList = Arrays.asList(man, woman, nonBinary, transgender, noneOfTheseGender, preferNotToAnswerGender);
        this.campaignsTitle = app.newElement(LocatorType.CSS, "div[class='campaign-tittle']");
        this.newCampaignsTitle = app.newElement(LocatorType.CSS, "div[class='campaign-name'");
        this.campaignName = app.newElement(LocatorType.CSS, "input[label='Campaign Name']");
        this.descriptionOptional = app.newElement(LocatorType.CSS, "textarea[label='Description (Optional)']");
        this.capmaignGoalDropdown = app.newElement(LocatorType.CSS, "div[class='select-custom-wrapper']");
        this.goal1 = app.newElement(LocatorType.ARIALABEL, "Goal 1");
        this.goal2 = app.newElement(LocatorType.ARIALABEL, "Goal 2");
        campaignsGoalList = Arrays.asList(goal1, goal2);
        this.nextButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary btn-next btn btn-default'");
        this.cancelButton = app.newElement(LocatorType.CSS, "button[class='btn btn-secondary btn-cancel btn btn-default'");
        this.saveAsDraftButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary btn-save btn btn-default'");
        this.campaignNameTitle = app.newElement(LocatorType.CSS, "div[class='campaign-name']");
        this.firstRadioButton = app.newElement(LocatorType.XPATH, "(//td[@style='text-align: center;'])[1]");
        this.selectTemplateTitle = app.newElement(LocatorType.CSS, "div[class='title']");
        this.reviewTitle = app.newElement(LocatorType.CSS, "div[class=\"default-label medium-label\"]");
        this.sendButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary-2 btn-send btn btn-default']");
        this.campaignNameStarMark = app.newElement(LocatorType.XPATH, "//label[text()='Campaign Name']//span");
        this.createdCampaign = app.newElement(LocatorType.XPATH, "//div[contains(text(), \"" + campaignNameRandom + "\")]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
    }

    public void verifyCreatedCampaign() {
        spinner.waitFor().notDisplayed();
        createdCampaign.assertState().displayed();
    }

    public void saveOrDraft(String button) {
        if (("created").equals(button)) {
            clickOnSendButton();
        }
        else if(("saved as draft").equals(button)){
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
        goal1.click();
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

    public void selectAudianceSegmentationTab() {
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

    public void argDropdown() {
        ageRaceGenderDropDown.click();
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
        defaultName.assertState().displayed();
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
     * Clicks in Communications button.
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
