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

    @When("^I select Communications option and see tabs Audience Segmentation, Campaigns, Templates$")
    public void selectCommunicationsOption() {
        capacityManagementPage.selectCommunications();
        capacityManagementPage.assertTitle();
        capacityManagementPage.assertAudienceSegmentation();
        capacityManagementPage.assertCampaigns();
        capacityManagementPage.assertTemplates();
    }

    @When("^I verify all values in the Audience Segmentation section$")
    public void verifyAudienceSection() {
        capacityManagementPage.createNewSegmentation();
        capacityManagementPage.verifyAudienceSegmentationLabels();
        capacityManagementPage.verifyDefaultValues();
        capacityManagementPage.verifyCommunicationPreferenceValues();
        capacityManagementPage.verifyOrganizationValues();
        capacityManagementPage.verifySiteValues();
        capacityManagementPage.verifySegmentationGroup();
    }

    @When("^I select \"([^\"]*)\" from Segmentation Group$")
    public void selectProgramMilestonesFromSegmentationGroup(String group) {
        capacityManagementPage.createNewSegmentation();
        capacityManagementPage.selectProgramSegmentationCategory(group);
    }

    @When("^I verify all values on the Program Milestones category$")
    public void verifyAllValuesOnTheProgramMilestonesCategory() {
        capacityManagementPage.verifyConsentDropdown();
        capacityManagementPage.selectConsent("Primary Consent");
        capacityManagementPage.verifyEqualDropdown();
        capacityManagementPage.verifyStatusDropDown();
        capacityManagementPage.verifyDateDropDown();
    }

    @When("^I select Demographic Segmentation from Segmentation Group$")
    public void selectDemographicSegmentationFromSegmentationGroup() {
        capacityManagementPage.addNewCategory();
        capacityManagementPage.selectProgramSegmentationCategory("Demographic Segmentation");
    }

    @When("^I verify all values on the Demographic Segmentation category$")
    public void verifyAllValuesOnTheDemographicSegmentationCategory() {
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
}
