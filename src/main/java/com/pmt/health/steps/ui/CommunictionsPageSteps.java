package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.CommunictionsPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Description;

public class CommunictionsPageSteps {

    private final User user;
    private final DeviceController deviceController;
    private final CommunictionsPage communictionsPage;

    public CommunictionsPageSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        communictionsPage = new CommunictionsPage(this.deviceController.getApp(), user);
    }

    @When("^I see tabs Audience Segmentation, Campaigns, Templates$")
    public void selectCommunicationsOption() {
        communictionsPage.selectCommunications();
        communictionsPage.assertTitle();
        communictionsPage.assertAudienceSegmentation();
        communictionsPage.assertCampaigns();
        communictionsPage.assertTemplates();
    }

    @When("^I verify all values in the Audience Segmentation section$")
    public void verifyAudienceSection() {
        communictionsPage.selectCommunications();
        communictionsPage.selectAudienceSegmentationTab();
        communictionsPage.createNew();
        communictionsPage.verifyAudienceSegmentationLabels();
        communictionsPage.verifyDefaultValues();
        communictionsPage.verifyCommunicationPreferenceValues();
        communictionsPage.verifyOrganizationValues();
        communictionsPage.verifySiteValues();
        communictionsPage.verifySegmentationGroup();
    }

    @Description("Passing three filters as a parameter for a new segmentation page")
    @When("^I verify \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void selectProgramMilestonesFromSegmentationGroup(String filter1, String filter2, String filter3) {
        communictionsPage.selectCommunications();
        communictionsPage.selectAudienceSegmentationTab();
        communictionsPage.createNew();
        communictionsPage.selectProgramSegmentationCategory(filter1);
        communictionsPage.verifyConsentDropdown();
        communictionsPage.selectConsent("EHR Consent Response");
        communictionsPage.verifyEqualDropdown();
        communictionsPage.verifyStatusDropDown();
        communictionsPage.verifyDateDropDown();
        communictionsPage.addNewCategory();
        communictionsPage.selectProgramSegmentationCategory(filter2);
        communictionsPage.argDropdown();
        communictionsPage.verifyAgeRaceGenderDropdown();
        communictionsPage.selectAgeRaceGender("Age");
        communictionsPage.verifyEqualDropDownSecond();
        communictionsPage.verifyAgeList();
        communictionsPage.argDropdown();
        communictionsPage.selectAgeRaceGender("Race");
        communictionsPage.verifyRaceList();
        communictionsPage.argDropdown();
        communictionsPage.selectAgeRaceGender("Gender");
        communictionsPage.verifyEqualDropDownSecond();
        communictionsPage.verifyGenderList();
        communictionsPage.addNewCategory();
        communictionsPage.selectProgramSegmentationCategory(filter3);
        communictionsPage.verifyCampaignActivityDropDown();
        communictionsPage.selectEmailFilter("Email Opened");
    }

    @Then("^I create a new campaign with \"([^\"]*)\" channel$")
    public void verifyCampaignTabAndStartCreatingAnewCampaign(String option) {
        communictionsPage.selectCommunications();
        communictionsPage.assertTitle();
        communictionsPage.selectCampaignsTab();
        communictionsPage.assertCampaignsTitle();
        communictionsPage.createNew();
        communictionsPage.assertNewCampaignsTitle();
        communictionsPage.enterCampaignName();
        communictionsPage.selectCampaignType("Single");
        communictionsPage.enterDescriptionOptional();
        communictionsPage.selectCampaignGoal();
        communictionsPage.selectChannel(option);
        communictionsPage.clickOnNextButton();
        communictionsPage.verifyCampaignName();
        communictionsPage.selectFirstRadioButton();
        communictionsPage.clickOnNextButton();
        communictionsPage.assertSelectTemplateTitle();
        communictionsPage.selectFirstRadioButton();
        communictionsPage.clickOnNextButton();
    }

    @Then("^Campaign is \"([^\"]*)\"$")
    public void reviewCampaignAndCreateIt(String button) {
        communictionsPage.assertReviewTitle();
        communictionsPage.saveOrDraft(button);
        communictionsPage.confirmSend();
        communictionsPage.verifyCreatedCampaign();
    }

    @Then("^I verify values on Templates tab$")
    public void verifyValuesOnTemplatesTab() {
        communictionsPage.selectCommunications();
        communictionsPage.assertTitle();
        communictionsPage.selectTemplatesTab();
        communictionsPage.assertTemplatesTitle();
        communictionsPage.orderById();
        communictionsPage.orderByName();
        communictionsPage.orderByChannel();
        communictionsPage.orderByCreatedDate();
        communictionsPage.orderByModifiedDate();
    }

    @Then("I verify organizations")
    public void saveOrgAndSiteSegmentation() {
        communictionsPage.selectCommunications();
        communictionsPage.selectAudienceSegmentationTab();
        communictionsPage.createNew();
        communictionsPage.verifyOrgList();
    }

    @Then("^I created segmentation with \"([^\"]*)\" and \"([^\"]*)\" on (Email|SMS) channel$")
    public void createSegmentation(String org, String site, String channel) {
        communictionsPage.selectCommunications();
        communictionsPage.selectAudienceSegmentationTab();
        communictionsPage.createNew();
        communictionsPage.typeSegmentationName();
        communictionsPage.selectCommunicationPreference(channel);
        communictionsPage.selectOrganization(org);
        communictionsPage.selectSite(site);
        communictionsPage.typeDescription();
        communictionsPage.selectProgramSegmentationCategory("Program Milestones");
        communictionsPage.consentDropDownClick();
        communictionsPage.selectConsent("EHR Consent Response");
        communictionsPage.selectStatusDropDown("Yes");
        communictionsPage.addNewCategory();
        communictionsPage.selectProgramSegmentationCategory("Demographic Segmentation");
        communictionsPage.argDropdown();
        communictionsPage.selectAgeRaceGender("Age");
        communictionsPage.selectAge("25-34");
        communictionsPage.addNewCategory();
        communictionsPage.selectProgramSegmentationCategory("Campaign Activity");
        communictionsPage.verifyCampaignActivityDropDown();
        communictionsPage.selectEmailFilter("Email Opened");
        communictionsPage.saveSegmentation();
    }

    @And("^I am on Audience Segmentation page$")
    public void goToAudienceSegmentationPage() {
        communictionsPage.selectCommunications();
        communictionsPage.selectAudienceSegmentationTab();
    }

    @And("^I search segmentation by name \"([^\"]*)\" successfully$")
    public void searchSegmentationByName(String name) {
        communictionsPage.inputSearchName(name);
        communictionsPage.assertSearchResult();
    }

    @And("^I clone this segmentation successfully$")
    public void cloneSegmentation() {
        communictionsPage.cloneSegmentation();
        communictionsPage.saveSegmentation();
        communictionsPage.waitForSpinnerDisappear();
    }

    @And("^Clone segmentation named \"([^\"]*)\" is displayed on grid$")
    public void verifyCloneSegmentation(String name) {
        communictionsPage.selectCommunications();
        communictionsPage.selectAudienceSegmentationTab();
        communictionsPage.inputSearchName(name);
        communictionsPage.assertSearchResult();
    }
}
