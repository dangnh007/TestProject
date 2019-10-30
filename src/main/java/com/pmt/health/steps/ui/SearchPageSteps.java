package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.LoginPage;
import com.pmt.health.workflows.SearchPage;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class SearchPageSteps {

    private final User user;
    private final DeviceController deviceController;
    private final LoginPage loginPage;
    private final SearchPage searchPage;

    public SearchPageSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        searchPage = new SearchPage(this.deviceController.getApp(), user);
        loginPage = new LoginPage(this.deviceController.getApp(), user);
    }

    @When("^I am on Search page and search appointment by email$")
    public void searchAppointment() {
        loginPage.loadEnvironment();
        loginPage.login();
        searchPage.searchAppointment();
    }

    @Then("^I can see appointment displays in Search Result$")
    public void assertSearchedAppointment() {
        searchPage.assertSearchedAppointment();
    }
}
