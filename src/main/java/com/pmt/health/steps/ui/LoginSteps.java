package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.LoginPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

    private final User user;
    private final DeviceController deviceController;
    private final LoginPage loginPage;
    private final UserAdminPage userAdminPage;

    public LoginSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        loginPage = new LoginPage(this.deviceController.getApp(), user);
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
    }

    @When("^I (try to)?login as System Administrator$")
    public void loginAsAdministrator(String attempt) {
        this.loginPage.loadEnvironment();
        this.loginPage.loginAdmin();
        if (!"try to ".equals(attempt)) {
            this.userAdminPage.waitForLoginLoad();
        }
    }

    @When("^I login as user$")
    public void loginAsUser() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
    }

    @When("^I login for the first time and set up my credentials$")
    public void setLogin() {
        this.loginPage.loadEnvironment();
        this.loginPage.setLogin();
        this.loginPage.typeNewPassword();
        this.loginPage.clickSubmitButton();
        this.loginPage.deleteCookie();
    }

    @When("^I logout$")
    public void logout() {
        this.loginPage.logout();
    }

    @Then("^I am logged in$")
    public void assertLoggedIn() {
        this.userAdminPage.assertLoggedIn();
    }
}
