package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.Prospect;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.SearchPage;
import com.pmt.health.workflows.SiteSettingsPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SiteSettingsSteps {

    private final User user;
    private final DeviceController deviceController;
    private final SiteSettingsPage siteSettingsPage;
    private final SearchPage searchPage;
    private final UserAdminPage userAdminPage;
    private final Prospect prospect;

    public SiteSettingsSteps(DeviceController deviceController, User user, Prospect prospect) {
        this.user = user;
        this.deviceController = deviceController;
        this.prospect = prospect;
        siteSettingsPage = new SiteSettingsPage(this.deviceController.getApp(), user, prospect);
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
        searchPage = new SearchPage(this.deviceController.getApp(), user, prospect);
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
        siteSettingsPage.enterFirstName(prospect.getFirstName());
        siteSettingsPage.enterLastName(prospect.getLastName());
        siteSettingsPage.enterEmailAddress(prospect.getEmail());
        siteSettingsPage.selectLanguage();
        siteSettingsPage.completeParticipantInfo();
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.completeAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
        siteSettingsPage.assertSuccessAppointmentMessage();
    }

    @When("^I create new appointment for prospect from first available future time block$")
    public void createNewAppointmentOnTimeBlock() {
        siteSettingsPage.switchToFindFirstAvailableTimeBlock();
        siteSettingsPage.doubleClickOnTimeBlockToCreateNewAppointment();
        siteSettingsPage.enterFirstName(prospect.getFirstName());
        siteSettingsPage.enterLastName(prospect.getLastName());
        siteSettingsPage.enterEmailAddress(prospect.getEmail());
        siteSettingsPage.selectLanguage();
        siteSettingsPage.completeParticipantInfo();
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.completeAppointmentDetails();
        siteSettingsPage.selectActiveDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
    }

    @When("^I create new appointment for prospect and assign to \"([^\"]*)\"$")
    public void createNewAppointmentAndAssignUser(String assignedUser) {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.enterFirstName(prospect.getFirstName());
        siteSettingsPage.enterLastName(prospect.getLastName());
        siteSettingsPage.enterEmailAddress(prospect.getEmail());
        siteSettingsPage.selectLanguage();
        siteSettingsPage.completeParticipantInfo();
        siteSettingsPage.assignToUser(assignedUser);
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.completeAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
        siteSettingsPage.assertSuccessAppointmentMessage();
    }

    @Then("^I can search created appointment by email that was started from selected time$")
    public void assertCreatedAppointment() {
        siteSettingsPage.assertSuccessAppointmentMessage();
        siteSettingsPage.assertCreatedAppointment();
        searchPage.searchAppointmentByEmail();
        searchPage.assertSearchedAppointment();
    }

    @When("^I am on Hamburger menu and I check \"Show My Appointment\"")
    public void showMyAppointment() {
        siteSettingsPage.goToNextDay();
        siteSettingsPage.showMyAppointment();
    }

    @When("^I see Appointment showing up")
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

    @And("^I am on Calendar \"([^\"]*)\" view and Scheduler site is selected as \"([^\"]*)\"$")
    public void assertCalendarPage(String viewType, String site) {
        if ("Week".equals(viewType)) {
            siteSettingsPage.switchCalendarToWeekView();
        } else if ("Month".equals(viewType)) {
            siteSettingsPage.switchCalendarToMonthView();
        }
        
        siteSettingsPage.assertCalendarPage(viewType);
        siteSettingsPage.assertSchedulerSite(site);
    }

    @When("^I re-scheduled appointment at \"([^\"]*)\"$")
    public void reScheduledAppointment(String time) {
        siteSettingsPage.reScheduledAppointment(time);
    }

    @When("^Details information of re-scheduled appointment is displayed and placed at \"([^\"]*)\"$")
    public void assertRescheduledAppointmentInfo(String time) {
        siteSettingsPage.assertRescheduledAppointmentInfo(time);
    }

    @When("^I edit by increasing duration, assign to \"([^\"]*)\", select \"([^\"]*)\" outcome and add notes$")
    public void editFutureAppointment(String assignedUser, String outCome) {
        siteSettingsPage.clickEditAppointmentDetail();
        siteSettingsPage.increaseDuration();
        siteSettingsPage.assignToUser(assignedUser);
        siteSettingsPage.selectOutComeStatus(outCome);
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.saveEditedAppointment();
    }

    @And("^I should see a message$")
    public void assertMessageAfterSaveChanges() {
        siteSettingsPage.assertMessageAfterSaveChanges();
    }

    @Then("^I can see current month view and current day is highlighted$")
    public void assertHighlightCurrentDayOnMonthView() {
        siteSettingsPage.assertHighlightCurrentDayOnMonthView();
    }

    @Then("^I can see the appointment on Calendar \"([^\"]*)\" view$")
    public void assertAppointmentOnCalendar(String viewType) {
        siteSettingsPage.assertAppointmentOnCalendar();
    }
    @Then("^I can see the appointment Scheduler$")
    public void assertAppointmentSchedulerView() {
        siteSettingsPage.assertAppointmentSchedulerView();
    }

    @And("^I access any future day with hours of operation$")
    public void doubleClickOnHoursOperation() {
        siteSettingsPage.doubleClickHoursOfOperations();
    }

    @When("^I am on Appointment Scheduler Search page and search prospect by first name$")
    public void searchByFirstNameOnAppointmentScheduler() {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.clickLabelSearchOnAppointmentScheduler();
        siteSettingsPage.enterFirstName("Search " + user.getFirstName() + " API");
        siteSettingsPage.clickSearchButton();
    }

    @When("^I am on Appointment Scheduler Search page and search prospect by last name$")
    public void searchByLastNameOnAppointmentScheduler() {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.clickLabelSearchOnAppointmentScheduler();
        siteSettingsPage.enterLastName("Search " + user.getLastName() + " API");
        siteSettingsPage.clickSearchButton();
    }

    @When("^I am on Appointment Scheduler Search page and search prospect by email$")
    public void searchByEmailOnAppointmentScheduler() {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.clickLabelSearchOnAppointmentScheduler();
        siteSettingsPage.enterEmailAddress(user.getParticipantEmail());
        siteSettingsPage.clickSearchButton();
    }

    @When("^I am on Appointment Scheduler Search page and search prospect by phone number \"([^\"]*)\"$")
    public void searchByPhoneOnAppointmentScheduler(String phoneNumber) {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.clickLabelSearchOnAppointmentScheduler();
        siteSettingsPage.enterPhoneNumber(phoneNumber);
        siteSettingsPage.clickSearchButton();
    }

    @When("^I am on Appointment Scheduler Search page and search prospect by DOB$")
    public void searchByDOBOnAppointmentScheduler() {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.clickLabelSearchOnAppointmentScheduler();
        siteSettingsPage.enterDateOfBirth("10/10/2000");
        siteSettingsPage.clickSearchButton();
    }

    @When("^I am on Appointment Scheduler Search page and search prospect by partial first name \"([^\"]*)\"$")
    public void searchByParticipantIDOnAppointmentScheduler(String partialName) {
        siteSettingsPage.addNewAppointment();
        siteSettingsPage.clickLabelSearchOnAppointmentScheduler();
        siteSettingsPage.enterFirstName(partialName);
        siteSettingsPage.clickSearchButton();
    }

    @Then("^I see prospect displays in Search Result$")
    public void assertResultItem() {
        siteSettingsPage.assertResultItem();
    }

    @Then("^I see that prospects displays in Search Result$")
    public void assertProspectsInResult() {
        siteSettingsPage.assertProspectsInResult();
    }
}
