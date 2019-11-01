package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.LoginPage;
import com.pmt.health.workflows.SearchPage;
import com.pmt.health.workflows.SiteSettingsPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchPageSteps {

    private final User user;
    private final DeviceController deviceController;
    private final LoginPage loginPage;
    private final SearchPage searchPage;
    private final SiteSettingsPage siteSettingsPage;

    public SearchPageSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        searchPage = new SearchPage(this.deviceController.getApp(), user);
        loginPage = new LoginPage(this.deviceController.getApp(), user);
        siteSettingsPage = new SiteSettingsPage(this.deviceController.getApp(), user);
    }

    @When("^I am on Search page and search appointment by email$")
    public void searchAppointment() {
        loginPage.loadEnvironment();
        loginPage.login();
        searchPage.searchAppointment();
    }

    @Then("^I see appointment displays in Search Result$")
    public void assertSearchedAppointment() {
        searchPage.assertSearchedAppointment();
    }

    @When("^I create new appointment for prospect from Search result$")
    public void createAppointmentFromSearchResult() {
        searchPage.clickCreateButton();
        siteSettingsPage.selectLocation();
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.nextForAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
        siteSettingsPage.assertSuccessAppointmentMessage();
    }
}
