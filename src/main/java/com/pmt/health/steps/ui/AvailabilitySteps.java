package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AvailabilityPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AvailabilitySteps {

    private final User user;
    private final DeviceController deviceController;
    private final AvailabilityPage availabilityPage;

    public AvailabilitySteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        availabilityPage = new AvailabilityPage(this.deviceController.getApp(), user);
    }

    @And("^I am on Availability page$")
    public void clickAvailabilityTab() {
        availabilityPage.clickAvailabilityTab();
    }

    @When("^I see current month$")
    public void assertCurrentMonthOnAvailabilityTab() {
        availabilityPage.assertCurrentMonthOnAvailabilityPage();
    }

    @Then("^I see current day highlighted$")
    public void assertCurrentDayHighlighted() {
        availabilityPage.assertCurrentDayHighlighted();
    }

    @When("^I select a day and add hours of operation$")
    public void selectCurrentDayAndAddHourOfOperation() {
        availabilityPage.clickCurrentDayOnAvailabilityPage();
        availabilityPage.addHourOfOperation();
    }

    @Then("^I verify new hours of operation and delete$")
    public void verifyNewHoursOfOperation() throws InterruptedException {
        availabilityPage.verifyNewHoursOfOperation();
        clickAvailabilityTab();
        availabilityPage.clickCurrentDayOnAvailabilityPage();
        availabilityPage.deleteHoursOfOperation();
    }

    @Then("^I see custom hours of operation$")
    public void verifyCustomHoursOfOperationAddedViaAPI() {
        availabilityPage.verifyCustomHoursOfOperationAddedViaAPI();
    }

    @When("^I select a day with hours of operation and edit hours of operation$")
    public void editHoursOfOperation() throws InterruptedException {
        availabilityPage.clickTomorrowDiv();
        availabilityPage.editHoursOfOperation();
    }

    @When("^I select a day with hours of operation and delete$")
    public void deleteHoursOfOperation() throws InterruptedException {
        availabilityPage.clickCurrentDayOnAvailabilityPage();
        availabilityPage.deleteHoursOfOperation();
    }

    @Then("^I verify hours of operation after editing$")
    public void verifyHoursOfOperationAfterEditing() {
        availabilityPage.verifyHoursOfOperationAfterEditing();
    }

    @Then("^I verify site is closed$")
    public void verifyClosedSite() {
        availabilityPage.verifyClosedSite();
    }
}
