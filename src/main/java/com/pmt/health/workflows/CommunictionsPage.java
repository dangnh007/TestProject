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
public class CommunictionsPage {

    private final App app;
    private User user;
    Logger log = Logger.getLogger(CommunictionsPage.class);

    //generates random name for the test automation field
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
    private final WebbElement siteDisable;
    private final WebbElement segmentationGroupDropDown;
    private final WebbElement programSegmentation;
    private final WebbElement demographicSegmentation;
    private final WebbElement addCategoryButton;
    private final WebbElement consentDropDown;
    private final WebbElement campaignTypeDropDown;
    private final WebbElement ehrConsentResponse;
    private final WebbElement physicalMeasurement;
    private final WebbElement biosample;
    private final WebbElement equalDropDown;
    private final WebbElement isExactlyEqualTo;
    private final WebbElement isNotEqualTo;
    private final WebbElement isAnyOf;
    private final WebbElement isNotAnyOf;
    private final WebbElement statusDropDown;
    private final WebbElement yes;
    private final WebbElement no;
    private final WebbElement notSure;
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
    private final WebbElement testOrg;
    private final WebbElement warning;
    private final WebbElement sendNowButton;
    private final WebbElement successMessage;
    private final WebbElement notExactlyFor;
    private final WebbElement exactlyFor;
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
    private final WebbElement inputSearchName;
    private final WebbElement resultItem;
    private final WebbElement confirmCloneBtn;
    private final WebbElement copyBtn;
    private final WebbElement spinner;

    public CommunictionsPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.campaignTypeDropDown = app.newElement(LocatorType.XPATH, "(//div[@class=' select-custom-wrapper'])[1]");
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
        this.defaultCommunicationPreference = app.newElement(LocatorType.XPATH, "//div[text()='Select Channel']");
        this.defaultOrganization = app.newElement(LocatorType.XPATH, "//div[text()='All Organizations (Default)']");
        this.defaultSite = app.newElement(LocatorType.XPATH, "//div[text()='All Sites (Default)']");
        this.communicationPreferenceDropDown = app.newElement(LocatorType.ID, "comm-preference-select");
        this.emailPreference = app.newElement(LocatorType.ARIALABEL, "Email");
        communicationList = Arrays.asList(emailPreference);
        this.organizationDropDown = app.newElement(LocatorType.XPATH, "(//div[@class=' select-custom-wrapper'])[2]");
        this.siteDisable = app.newElement(LocatorType.CSS, "div[class*='Select is-clearable is-disabled']");
        this.segmentationGroupDropDown = app.newElement(LocatorType.ID, "add-category-select");
        this.programSegmentation = app.newElement(LocatorType.ARIALABEL, "Program Milestones");
        this.demographicSegmentation = app.newElement(LocatorType.ARIALABEL, "Demographic Segmentation");
        segmentationList = Arrays.asList(programSegmentation, demographicSegmentation);
        this.addCategoryButton = app.newElement(LocatorType.CSS, "button[class='btn btn-primary button-lg btn btn-default']");
        this.consentDropDown = app.newElement(LocatorType.ID, "primary-select-value");
        this.ehrConsentResponse = app.newElement(LocatorType.ARIALABEL, "EHR Consent Response");
        this.physicalMeasurement = app.newElement(LocatorType.ARIALABEL, "Physical Measurement");
        this.biosample = app.newElement(LocatorType.ARIALABEL, "Biosample");
        consentList = Arrays.asList(ehrConsentResponse, physicalMeasurement, biosample);
        this.equalDropDown = app.newElement(LocatorType.ID, "multi-operator-select");
        this.isExactlyEqualTo = app.newElement(LocatorType.ARIALABEL, "is exactly equal to");
        this.isNotEqualTo = app.newElement(LocatorType.ARIALABEL, "is not equal to");
        this.isAnyOf = app.newElement(LocatorType.ARIALABEL, "is any of");
        this.isNotAnyOf = app.newElement(LocatorType.ARIALABEL, "is not any of");
        equalList = Arrays.asList(isExactlyEqualTo, isNotEqualTo, isAnyOf, isNotAnyOf);
        this.statusDropDown = app.newElement(LocatorType.ID, "modifier-select");
        this.yes = app.newElement(LocatorType.ARIALABEL, "Yes");
        this.no = app.newElement(LocatorType.ARIALABEL, "No");
        this.notSure = app.newElement(LocatorType.ARIALABEL, "Not sure");
        statusList = Arrays.asList(yes, no, notSure);
        this.dateDropDown = app.newElement(LocatorType.ID, "date-operator-select");
        this.onAnyDate = app.newElement(LocatorType.ARIALABEL, "on any date (default)");
        this.onExactDate = app.newElement(LocatorType.ARIALABEL, "on exact date");
        this.before = app.newElement(LocatorType.ARIALABEL, "before");
        this.after = app.newElement(LocatorType.ARIALABEL, "after");
        this.onOrBefore = app.newElement(LocatorType.ARIALABEL, "on or before");
        this.onOrAfter = app.newElement(LocatorType.ARIALABEL, "on or after");
        this.isWithin = app.newElement(LocatorType.ARIALABEL, "within the last");
        this.isNotWithin = app.newElement(LocatorType.ARIALABEL, "not within the last");
        this.exactlyFor = app.newElement(LocatorType.ARIALABEL, "exactly for");
        this.notExactlyFor = app.newElement(LocatorType.ARIALABEL, "not exactly for");
        dateList = Arrays.asList(onAnyDate, onExactDate, before, after, onOrBefore, onOrAfter, isWithin, isNotWithin, exactlyFor, notExactlyFor);
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
        this.capmaignGoalDropdown = app.newElement(LocatorType.XPATH, "(//div[@class='select-custom-wrapper'])[2]");
        this.biospecimenGoal = app.newElement(LocatorType.ARIALABEL, "Complete Biospecimen");
        this.pmBGoal = app.newElement(LocatorType.ARIALABEL, "Complete PM/B");
        this.samplesToIzolateDnaGoal = app.newElement(LocatorType.ARIALABEL, "Complete Samples to Isolate DNA");
        campaignsGoalList = Arrays.asList(biospecimenGoal, pmBGoal, samplesToIzolateDnaGoal);
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
        this.testOrg = app.newElement(LocatorType.ARIALABEL, "TEST AUTOMATION ORGANIZATION");
        orgList = Arrays.asList(testOrg);
        this.warning = app.newElement(LocatorType.CSS, "p[class='title']");
        this.sendNowButton = app.newElement(LocatorType.CSS, "button[class='button-send-now btn btn-primary']");
        this.successMessage = app.newElement(LocatorType.CSS, "div[class='message animated fade success in']");
        this.inputSearchName = app.newElement(LocatorType.CSS, "input[class='searchinput']");
        this.resultItem = app.newElement(LocatorType.CSS, "table[class='table datatable-custom-body'] tr");
        this.copyBtn = app.newElement(LocatorType.CSS, "svg[data-icon='copy']");
        this.confirmCloneBtn = app.newElement(LocatorType.CSS, "button[aria-label='Confirm clone']");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
    }

    public void selectCampaignType(String type) {
        campaignTypeDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, type);
        option.click();
    }

    /**
     * clicks on the send now button if met condition
     */
    public void confirmSend() {
        if (warning.is().present()) {
            sendNowButton.click();
        }
    }

    /**
     * waits for the button to be displayed
     * clicks on the send button
     */
    public void saveSegmentation() {
        saveButton.waitFor().displayed();
        saveButton.click();
    }

    /**
     * clicks on the organization dropdown
     * verifies values of organization list
     */
    public void verifyOrgList() {
        organizationDropDown.click();
        for (WebbElement each : orgList) {
            each.assertState().displayed();
        }
    }

    public void typeDescription() {
        descriptionTextForm.type("Test Automation");
    }

    /**
     * clicks on the parameter from site dropdown
     *
     * @param site string parameter
     */
    public void selectSite(String site) {
        siteDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, site);
        option.click();
    }

    /**
     * clicks on the parameter from organization dropdown
     *
     * @param org string parameter
     */
    public void selectOrganization(String org) {
        organizationDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, org);
        option.click();
    }

    /**
     * clicks on the parameter from communication preferences dropdown
     *
     * @param channel string parameter
     */
    public void selectCommunicationPreference(String channel) {
        communicationPreferenceDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, channel);
        option.click();
    }

    /**
     * types string into segment name field
     */
    public void typeSegmentationName() {
        segmentationName.type(campaignNameRandom);
    }

    /**
     * Verifies if correct title is displayed
     */
    public void assertTemplatesTitle() {
        templatesTitle.assertState().displayed();
    }

    /**
     * clicks on the name order
     * asserts order
     */
    public void orderByName() {
        nameOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=name]>span[class='custom-default-caret']");
        order.assertState().present();
    }

    /**
     * clicks on the id order
     * asserts order
     */
    public void orderById() {
        idOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=id]>span[class='custom-default-caret']");
        order.assertState().present();
    }

    /**
     * clicks on the channel order
     * asserts order
     */
    public void orderByChannel() {
        channelOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=channel]>span[class='custom-default-caret']");
        order.assertState().present();
    }

    /**
     * clicks on the created date order
     * asserts order
     */
    public void orderByCreatedDate() {
        createDateOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=createdDate]>span[class='custom-default-caret']");
        order.assertState().present();
    }

    /**
     * clicks on the date order
     * asserts order
     */
    public void orderByModifiedDate() {
        modifiedDateOrder.click();
        WebbElement order = app.newElement(LocatorType.CSS, "th[data-field=modifiedDate]>span[class='custom-default-caret']");
        order.assertState().present();
    }

    /**
     * waits until successful message is displayed
     */
    public void verifyCreatedCampaign() {
        successMessage.is().displayed();
    }

    /**
     * clicks on the button depends on condition
     */
    public void saveOrDraft(String button) {
        if (("created").equals(button)) {
            clickOnSendButton();
        } else if (("saved as draft").equals(button)) {
            saveAsDraftButton.waitFor().displayed();
            saveDraft();
        }
    }

    /**
     * waits for the button to be displayed
     * clicks on the send button
     */
    public void clickOnSendButton() {
        sendButton.waitFor().displayed();
        sendButton.click();
    }

    /**
     * Verifies if correct title is displayed
     */
    public void assertReviewTitle() {
        reviewTitle.assertState().displayed();
    }

    /**
     * Verifies if correct title is displayed
     */
    public void assertSelectTemplateTitle() {
        selectTemplateTitle.assertState().displayed();
    }

    /**
     * checks first radio button in the list on the page
     */
    public void selectFirstRadioButton() {
        firstRadioButton.click();
    }

    /**
     * Verifies correct campaign name
     * checks if it is displayed
     */
    public void verifyCampaignName() {
        if (campaignNameRandom.equals(campaignNameTitle.get().text())) {
            campaignNameTitle.assertState().displayed();
        }
    }

    /**
     * clicks on the save as draft button
     */
    public void saveDraft() {
        saveAsDraftButton.click();
    }

    /**
     * clicks on the cancel button
     */
    public void clickOnCancelButton() {
        cancelButton.click();
    }

    /**
     * waits for the button to be displayed
     * clicks on the next button
     */
    public void clickOnNextButton() {
        nextButton.waitFor().displayed();
        nextButton.click();
    }

    /**
     * selects channel based on passed parameter
     */
    public void selectChannel(String option) {
        WebbElement radioOption = app.newElement(LocatorType.CSS, "input[label='" + option + "']");
        WebbElement radioSelect = app.newElement(LocatorType.XPATH, "//span[text()='" + option + "']");
        if (!radioOption.is().checked()) {
            radioSelect.click();
        }
    }

    /**
     * clicks on the campaign goal dropdown
     * clicks on selected value
     */
    public void selectCampaignGoal() {
        capmaignGoalDropdown.click();
        for (WebbElement each : campaignsGoalList) {
            each.assertState().displayed();
        }
        pmBGoal.click();
    }

    /**
     * types campaign name and checks field on presence
     */
    public void enterCampaignName() {
        campaignNameStarMark.assertState().displayed();
        campaignName.type(campaignNameRandom);
    }

    /**
     * types campaign name and checks field on presence
     */
    public void enterDescriptionOptional() {
        descriptionOptional.type("Test Automation");
    }

    /**
     * Verifies if correct title is displayed
     */
    public void assertNewCampaignsTitle() {
        newCampaignsTitle.assertState().displayed();
    }

    /**
     * Verifies if correct title is displayed
     */
    public void assertCampaignsTitle() {
        campaignsTitle.assertState().displayed();
    }

    /**
     * checks if tab is active,
     * if it is not clicks on the tab's button
     */
    public void selectCampaignsTab() {
        WebbElement nonActive = app.newElement(LocatorType.XPATH, "//li[@class='sub-tab ']/a[@href='/communications/campaigns']");
        if (nonActive.is().present()) {
            tabCampaigns.click();
        }
    }

    /**
     * checks if tab is active,
     * if it is not clicks on the tab's button
     */
    public void selectTemplatesTab() {
        WebbElement nonActive = app.newElement(LocatorType.XPATH, "//li[@class='sub-tab ']/a[@href='/communications/templates']");
        if (nonActive.is().present()) {
            tabTemplates.click();
        }
    }

    /**
     * checks if tab is active,
     * if it is not clicks on the tab's button
     */
    public void selectAudienceSegmentationTab() {
        WebbElement nonActive = app.newElement(LocatorType.XPATH, "//li[@class='sub-tab ']/a[@href='/communications/segmentation']");
        if (nonActive.is().present()) {
            tabAudienceSegmentation.click();
        }
    }

    /**
     * clicks on the arg dropdown
     * verifies age list of current dropdown
     */
    public void verifyAgeList() {
        ageRaceGenderValuesDropDown.click();
        for (WebbElement each : ageList) {
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the arg dropdown
     * selects current parameter
     *
     * @param age passing age value
     */
    public void selectAge(String age) {
        ageRaceGenderValuesDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, age);
        option.click();
        ageRaceGenderValuesDropDown.click();
    }

    /**
     * clicks on the arg dropdown
     * verifies gender list of current dropdown
     */
    public void verifyGenderList() {
        ageRaceGenderValuesDropDown.click();
        for (WebbElement each : genderList) {
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the arg dropdown
     * verifies race list of current dropdown
     */
    public void verifyRaceList() {
        ageRaceGenderValuesDropDown.click();
        for (WebbElement each : raceList) {
            each.assertState().displayed();
        }
    }

    public void addNewCategory() {
        addLink.click();
    }

    /**
     * clicks on the equal to dropdown
     * verifies values of current dropdown
     */
    public void verifyEqualDropDownSecond() {
        equalDropDownSecond.click();
        for (WebbElement each : equalList) {
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the parametrized value
     *
     * @param ageRaceGender passing arg option
     */
    public void selectAgeRaceGender(String ageRaceGender) {
        WebbElement option = app.newElement(LocatorType.ARIALABEL, ageRaceGender);
        option.click();
    }

    /**
     * clicks on the parametrized value
     *
     * @param emailFilter passing email filter
     */
    public void selectEmailFilter(String emailFilter) {
        WebbElement option = app.newElement(LocatorType.ARIALABEL, emailFilter);
        option.click();
    }

    /**
     * clicks on the multi valuable dropdown
     */
    public void argDropdown() {
        ageRaceGenderDropDown.click();
    }

    /**
     * clicks on the campaign activity dropdown
     * verifies values of current dropdown
     */
    public void verifyCampaignActivityDropDown() {
        campaignActivityDropDown.click();
        for (WebbElement each : emailList) {
            each.assertState().displayed();
        }
    }

    /**
     * verifies values of previously clicked dropdown
     */
    public void verifyAgeRaceGenderDropdown() {
        for (WebbElement each : ageRaceGenderList) {
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the date dropdown
     * verifies values of current dropdown
     */
    public void verifyDateDropDown() {
        dateDropDown.click();
        for (WebbElement each : dateList) {
            each.move();
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the status dropdown
     * verifies values of current dropdown
     */
    public void verifyStatusDropDown() {
        statusDropDown.click();
        for (WebbElement each : statusList) {
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the status dropdown
     * verifies values of current dropdown
     */
    public void selectStatusDropDown(String select) {
        statusDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, select);
        option.click();
    }

    /**
     * clicks on the equals to dropdown
     * verifies values of current dropdown
     */
    public void verifyEqualDropdown() {
        equalDropDown.click();
        for (WebbElement each : equalList) {
            each.assertState().displayed();
        }
    }

    /**
     * clicks on the consent dropdown
     */
    public void consentDropDownClick() {
        consentDropDown.click();
    }

    /**
     * clicks on the consent dropdown
     * verifies values of current dropdown
     */
    public void verifyConsentDropdown() {
        consentDropDown.click();
        for (WebbElement each : consentList) {
            each.assertState().displayed();
        }
    }

    /**
     * select consent in previously clicked dropdown
     *
     * @param consent parameter of the consent value
     */
    public void selectConsent(String consent) {
        WebbElement option = app.newElement(LocatorType.ARIALABEL, consent);
        option.click();
    }

    /**
     * clicks on the segmentation category dropdown
     * select parameter and clicks on to add it
     *
     * @param group passing group parameter
     */
    public void selectProgramSegmentationCategory(String group) {
        segmentationGroupDropDown.click();
        WebbElement option = app.newElement(LocatorType.ARIALABEL, group);
        option.click();
        addCategoryButton.click();
    }

    /**
     * clicks on the segmentation groups dropdown
     * verifies values of current dropdown
     */
    public void verifySegmentationGroup() {
        segmentationGroupDropDown.click();
        for (WebbElement each : segmentationList) {
            each.assertState().displayed();
        }
        addCategoryButton.assertState().displayed();
    }

    /**
     * Verifies that site dropdown is is disable
     */
    public void verifySiteValues() {
        siteDisable.assertState().displayed();
    }

    /**
     * clicks on the organization dropdown
     * verifies one of the organizations of current dropdown
     */
    public void verifyOrganizationValues() {
        organizationDropDown.click();
        testOrg.assertState().displayed();
    }

    /**
     * clicks on the communication preference dropdown
     * verifies values of current dropdown
     */
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

    /**
     * Input search name
     */
    public void inputSearchName(String search) {
        inputSearchName.type(search);
    }

    public void assertSearchResult() {
        resultItem.waitFor().displayed();
    }

    public void cloneSegmentation() {
        copyBtn.click();
        confirmCloneBtn.waitFor().displayed();
        confirmCloneBtn.click();
    }

    public void waitForSpinnerDisappear() {
        spinner.waitFor().notDisplayed();
    }
}
