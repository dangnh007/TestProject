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

    @When("^I create new appointment for prospect and assign to \"([^\"]*)\"$")
    public void createNewAppointmentAndAssignUser(String assignedUser) {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.enterFirstName(user.getFirstName());
        siteSettingsPage.enterLastName(user.getLastName());
        siteSettingsPage.enterEmailAddress(user.getParticipantEmail());
        siteSettingsPage.selectLanguage();
        siteSettingsPage.completeParticipantInfo();
        siteSettingsPage.assignToUser(assignedUser);
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

    @When("^I am on Hamburger menu and I check \"Show My Appointment\"")
    public void showMyAppointment() {
        siteSettingsPage.goToNextDay();
        siteSettingsPage.showMyAppointment();
    }

    @Then("^I can see Appointment showing up")
    public void assertShowMyAppointment() {
        siteSettingsPage.assertShowMyAppointment();
    }
      
    @When("^I see edited site settings toggle \"([^\"]*)\", target \"([^\"]*)\", goal \"([^\"]*)\", days \"([^\"]*)\"$")
    public void iSeeEditedSiteSettingsToggleTargetGoalDays(String toggle, String target, String goal, String days) {
        userAdminPage.userSettings();
        if (("on").equals(toggle)) {
            siteSettingsPage.assertToggleAcceptingAppointments(true);
        }
        siteSettingsPage.editPage();
        siteSettingsPage.assertDailyGoal(goal);
        siteSettingsPage.assertDailyTarget(target);
        siteSettingsPage.assertDefaultFullEnrollment();
        siteSettingsPage.assertDefaultPMB();
        siteSettingsPage.assertMinimumAppointmentNotice(days);
        siteSettingsPage.assertCustomHoursOfOperations();
    }
}
