package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.CapacityManagementPage;
import com.pmt.health.workflows.UserAdminPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CapacityManagementSteps {

    private final User user;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final CapacityManagementPage capacityManagementPage;
    private static final String TOTAL_DAILY_TARGET_AWARDEE_VALUE = "50";
    private static final String TOTAL_DAILY_GOAL_AWARDEE_VALUE = "60";
    private static final String TOTAL_DAILY_TARGET_ORG_VALUE = "20";
    private static final String TOTAL_DAILY_GOAL_ORG_VALUE = "30";
    private static final String DAILY_TARGET_ORG_VALUE = "10";
    private static final String DAILY_GOAL_ORG_VALUE = "10";

    public CapacityManagementSteps(User user, DeviceController deviceController) {
        this.user = user;
        this.deviceController = deviceController;
        capacityManagementPage = new CapacityManagementPage(this.deviceController.getApp(), user);
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
    }

    @When("^I navigate to Capacity Management$")
    public void navigateToCapacityManagement() {
        userAdminPage.clickCapacityManagementButton();
    }

    @When("^I select Awardee \"([^\"]*)\" in location selector at the top page$")
    public void selectAwardee(String awardee) {
        capacityManagementPage.clickSelectArrow();
        capacityManagementPage.clickSelectAwardee(awardee);
        capacityManagementPage.assertDisplayAwardeeName(awardee);
    }

    @And("^I pressed Edit button$")
    public void pressEditButton() {
        capacityManagementPage.clickEditButton();
    }

    @Then("^User can edit Total Daily Target and Total Daily Goal of Awardee \"([^\"]*)\"$")
    public void editTargetAndGoalOfAwardee(String awardee) {
        capacityManagementPage.enterTotalDailyTarget(TOTAL_DAILY_TARGET_AWARDEE_VALUE);
        capacityManagementPage.enterTotalDailyGoal(TOTAL_DAILY_GOAL_AWARDEE_VALUE);
        capacityManagementPage.clickSaveButton();
        capacityManagementPage.assertTotalDailyTargetSpan(TOTAL_DAILY_TARGET_AWARDEE_VALUE);
        capacityManagementPage.assertTotalDailyGoalSpan(TOTAL_DAILY_GOAL_AWARDEE_VALUE);
    }

    @And("^I should see \"([^\"]*)\"$")
    public void verifyEditTargetAndGoalOfAwardee(String expectedMessageEditSuccess) {
        capacityManagementPage.assertEditSuccessMessage(expectedMessageEditSuccess);
    }

    @And("^I go into daily goal and target of organization \"([^\"]*)\", pressed Edit button$")
    public void goIntoDailyGoalAndTargetOfOrganizationAndPressEdit(String org) {
        capacityManagementPage.assertDisplayOrgName(org);
        capacityManagementPage.clickOrgName();
        capacityManagementPage.clickEditButton();
    }

    @Then("^User can edit Total Daily Target and Total Daily Goal of Organization \"([^\"]*)\" under selected Awardee$")
    public void editTargetAndGoalOfOrg(String org) {
        capacityManagementPage.enterTotalDailyTarget(TOTAL_DAILY_TARGET_ORG_VALUE);
        capacityManagementPage.enterTotalDailyGoal(TOTAL_DAILY_GOAL_ORG_VALUE);
        capacityManagementPage.clickSaveButton();
        capacityManagementPage.assertTotalDailyTargetSpan(TOTAL_DAILY_TARGET_ORG_VALUE);
        capacityManagementPage.assertTotalDailyGoalSpan(TOTAL_DAILY_GOAL_ORG_VALUE);
    }

    @Then("^User can edit Total Daily Target and Total Daily Goal of Sites under selected Organization \"([^\"]*)\"$")
    public void editTargetAndGoalUnderOrg(String org) {
        capacityManagementPage.enterDailyTarget(DAILY_TARGET_ORG_VALUE);
        capacityManagementPage.enterDailyGoal(DAILY_GOAL_ORG_VALUE);
        capacityManagementPage.clickSaveButton();
        capacityManagementPage.assertDailyTargetSpan(DAILY_TARGET_ORG_VALUE);
        capacityManagementPage.assertDailyGoalSpan(DAILY_GOAL_ORG_VALUE);
    }
}
