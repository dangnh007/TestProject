package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.SiteSettingsPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;

public class SiteSettingsSteps {

    private final User user;
    private final DeviceController deviceController;
    private final SiteSettingsPage siteSettingsPage;
    private final UserAdminPage userAdminPage;

    public SiteSettingsSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        siteSettingsPage = new SiteSettingsPage(this.deviceController.getApp(), user);
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
    }

    @Then("^I go to Settings Page$")
    public void goToSettingsPage() {
        userAdminPage.userSettings();
    }

    @Then("^I landed on Settings Page$")
    public void verifySettingsPage() {
        siteSettingsPage.assertTitle();
    }

    @Then("^I toggle \"([^\"]*)\" accepting appointments$")
    public void toggleOnAcceptingAppointments(String toggle) {
        siteSettingsPage.toggleOnOff();
    }

    @Then("^I set target to \"([^\"]*)\"$")
    public void setTargetTo(String target) {
        siteSettingsPage.editPage();
        siteSettingsPage.setTarget(target);
    }

    @Then("^I set appointment notice \"([^\"]*)\"$")
    public void setAppointmentNotice(String day) {
        siteSettingsPage.appNotice(day);
    }

    @Then("^I save changes$")
    public void saveChanges() {
        siteSettingsPage.saveChanges();
    }

    @Then("^I see successful message$")
    public void saveChangesMessage() {
        siteSettingsPage.assertMessage();
    }

    @Then("^I set custom hrs of operations$")
    public void setCustomHrsOfOperations() {
        siteSettingsPage.editPage();
        siteSettingsPage.customHoursOfOperation();
        siteSettingsPage.setCustomHrName();
        siteSettingsPage.checkDay();
        siteSettingsPage.hoFormUpdate();
    }

    @Then("^I delete custom hrs of operations$")
    public void deleteCustomHrsOfOperations() {
        siteSettingsPage.customHoursOfOperationDelete();
    }
}
