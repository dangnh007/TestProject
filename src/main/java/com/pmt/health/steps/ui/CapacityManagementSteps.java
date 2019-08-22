package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.CapacityManagementPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CapacityManagementSteps {

    private final User user;
    private final DeviceController deviceController;
    private final CapacityManagementPage capacityManagementPage;

    public CapacityManagementSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        capacityManagementPage = new CapacityManagementPage(this.deviceController.getApp(), user);
    }

    @When("^I see tabs Audience Segmentation, Campaigns, Templates$")
    public void selectCommunicationsOption() {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.assertTitle();
        capacityManagementPage.assertAudienceSegmentation();
        capacityManagementPage.assertCampaigns();
        capacityManagementPage.assertTemplates();
    }

    @When("^I verify all values in the Audience Segmentation section$")
    public void verifyAudienceSection() {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.selectAudienceSegmentationTab();
        capacityManagementPage.createNew();
        capacityManagementPage.verifyAudienceSegmentationLabels();
        capacityManagementPage.verifyDefaultValues();
        capacityManagementPage.verifyCommunicationPreferenceValues();
        capacityManagementPage.verifyOrganizationValues();
        capacityManagementPage.verifySiteValues();
        capacityManagementPage.verifySegmentationGroup();
    }

    @When("^I verify \"([^\"]*)\" and Demographic Segmentation$")
    public void selectProgramMilestonesFromSegmentationGroup(String group) {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.selectAudienceSegmentationTab();
        capacityManagementPage.createNew();
        capacityManagementPage.selectProgramSegmentationCategory(group);
        capacityManagementPage.verifyConsentDropdown();
        capacityManagementPage.selectConsent("Primary Consent");
        capacityManagementPage.verifyEqualDropdown();
        capacityManagementPage.verifyStatusDropDown();
        capacityManagementPage.verifyDateDropDown();
        capacityManagementPage.addNewCategory();
        capacityManagementPage.selectProgramSegmentationCategory("Demographic Segmentation");
        capacityManagementPage.argDropdown();
        capacityManagementPage.verifyAgeRaceGenderDropdown();
        capacityManagementPage.selectAgeRaceGender("Age");
        capacityManagementPage.verifyEqualDropDownSecond();
        capacityManagementPage.verifyAgeList();
        capacityManagementPage.argDropdown();
        capacityManagementPage.selectAgeRaceGender("Race");
        capacityManagementPage.verifyRaceList();
        capacityManagementPage.argDropdown();
        capacityManagementPage.selectAgeRaceGender("Gender");
        capacityManagementPage.verifyEqualDropDownSecond();
        capacityManagementPage.verifyGenderList();
    }

    @Then("^I create a new campaign with \"([^\"]*)\" channel$")
    public void verifyCampaignTabAndStartCreatingAnewCampaign(String option) {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.assertTitle();
        capacityManagementPage.selectCampaignsTab();
        capacityManagementPage.assertCampaignsTitle();
        capacityManagementPage.createNew();
        capacityManagementPage.assertNewCampaignsTitle();
        capacityManagementPage.enterCampaignName();
        capacityManagementPage.enterDescriptionOptional();
        capacityManagementPage.selectCampaignGoal();
        capacityManagementPage.selectChannel(option);
        capacityManagementPage.clickOnNextButton();
        capacityManagementPage.verifyCampaignName();
        capacityManagementPage.selectFirstRadioButton();
        capacityManagementPage.clickOnNextButton();
        capacityManagementPage.assertSelectTemplateTitle();
        capacityManagementPage.selectFirstRadioButton();
        capacityManagementPage.clickOnNextButton();
    }

    @Then("^Campaign is \"([^\"]*)\"$")
    public void reviewCampaignAndCreateIt(String button) {
        capacityManagementPage.assertReviewTitle();
        capacityManagementPage.saveOrDraft(button);
        capacityManagementPage.confirmSend();
        capacityManagementPage.verifyCreatedCampaign();
    }

    @Then("^I verify values on Templates tab$")
    public void verifyValuesOnTemplatesTab() {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.assertTitle();
        capacityManagementPage.selectTemplatesTab();
        capacityManagementPage.assertTemplatesTitle();
        capacityManagementPage.orderById();
        capacityManagementPage.orderByName();
        capacityManagementPage.orderByChannel();
        capacityManagementPage.orderByCreatedDate();
        capacityManagementPage.orderByModifiedDate();
    }

    @Then("I verify organizations")
    public void saveOrgAndSiteSegmentation() {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.selectAudienceSegmentationTab();
        capacityManagementPage.createNew();
        capacityManagementPage.verifyOrgList();
    }

    @Then("^I created segmentation with \"([^\"]*)\" and \"([^\"]*)\" on \"([^\"]*)\"$")
    public void createSegmentation(String org, String site, String channel) {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.selectAudienceSegmentationTab();
        capacityManagementPage.createNew();
        capacityManagementPage.typeSegmentationName();
        capacityManagementPage.selectCommunicationPreference(channel);
        capacityManagementPage.selectOrganization(org);
        capacityManagementPage.selectSite(site);
        capacityManagementPage.typeDescription();
        capacityManagementPage.selectProgramSegmentationCategory("Program Milestones");
        capacityManagementPage.consentDropDownClick();
        capacityManagementPage.selectConsent("Primary Consent");
        capacityManagementPage.addNewCategory();
        capacityManagementPage.selectProgramSegmentationCategory("Demographic Segmentation");
        capacityManagementPage.argDropdown();
        capacityManagementPage.selectAgeRaceGender("Age");
        capacityManagementPage.selectAge("25-34");
        capacityManagementPage.saveSegmentation();
    }
}
