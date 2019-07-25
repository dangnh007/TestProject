package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.SiteSettingsPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
        siteSettingsPage.assertTitle();
    }

    @Then("^I toggle \"([^\"]*)\" accepting appointments$")
    public void toggleOnAcceptingAppointments(String toggle) {
        siteSettingsPage.toggleOnOff(toggle);
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
        siteSettingsPage.editPage();
        siteSettingsPage.customHoursOfOperationDelete();
    }

    @When("^I create new appointment for prospect$")
    public void createNewAppointment() {
        siteSettingsPage.addNewAppointment();
    }

    @When("^I provide participant information$")
    public void provideParticipantInformation() {
        siteSettingsPage.enterFirstName(user.getFirstName());
        siteSettingsPage.enterLastName(user.getLastName());
        siteSettingsPage.enterDateOfBirth(user.getParticipantDateOfBirth());
        siteSettingsPage.enterPhoneNumber(user.getParticipantPhone());
        siteSettingsPage.enterEmailAddress(user.getParticipantEmail());
        siteSettingsPage.selectLanguage();
        siteSettingsPage.completeParticipantInfo();
    }

    @When("^I provide appointment details$")
    public void provideAppointmentDetails() {
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.completeAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.completeAppointmentDetails();
    }

    @When("^I schedule an appointment$")
    public void scheduleAnAppointment() {
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
        siteSettingsPage.assertSuccessAppointmentMessage();
    }

}
