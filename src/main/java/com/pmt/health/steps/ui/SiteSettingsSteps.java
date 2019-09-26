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

    @Then("^I set new Site Settings with toggle \"([^\"]*)\", target \"([^\"]*)\", days \"([^\"]*)\"$")
    public void setNewSiteSettings(String toggle, String target, String day) {
        userAdminPage.userSettings();
        siteSettingsPage.assertTitle();
        siteSettingsPage.toggleOnOff(toggle);
        siteSettingsPage.editPage();
        siteSettingsPage.setTarget(target);
        siteSettingsPage.appNotice(day);
        siteSettingsPage.saveChanges();
        siteSettingsPage.editPage();
        siteSettingsPage.customHoursOfOperation();
        siteSettingsPage.setCustomHrName();
        siteSettingsPage.checkDay();
        siteSettingsPage.hoFormUpdate();
        siteSettingsPage.saveChanges();
    }

    @Then("^I set default Site Settings with toggle \"([^\"]*)\", target \"([^\"]*)\", days \"([^\"]*)\"$")
    public void setDefaultSiteSettings(String toggle, String target, String day) {
        userAdminPage.userSettings();
        siteSettingsPage.assertTitle();
        siteSettingsPage.toggleOnOff(toggle);
        siteSettingsPage.editPage();
        siteSettingsPage.setTarget(target);
        siteSettingsPage.appNotice(day);
        siteSettingsPage.saveChanges();
        siteSettingsPage.editPage();
        siteSettingsPage.customHoursOfOperationDelete();

    }

    @When("^I create new appointment for prospect$")
    public void createNewAppointment() {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.enterFirstName(user.getFirstName());
        siteSettingsPage.enterLastName(user.getLastName());
        siteSettingsPage.enterEmailAddress(user.getParticipantEmail());
        siteSettingsPage.selectLanguage();
        siteSettingsPage.completeParticipantInfo();
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.completeAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.completeAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
        siteSettingsPage.assertSuccessAppointmentMessage();
    }
}
