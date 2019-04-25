package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.LoginPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Description;

public class LoginSteps {

    private final User user;
    private final DeviceController deviceController;
    private final LoginPage loginPage;

    public LoginSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        loginPage = new LoginPage(this.deviceController.getApp(), user);
    }

    @Description("Logs in with the option of providing an attempt prefix for special login cases.")
    @When("^I (try to )?login$")
    public void login(String attempt) {
        this.loginPage.loadEnvironment();
        this.loginPage.login(this.user);
        if (!"try to ".equals(attempt)) {
            this.loginPage.waitForLoginLoad();
        }
    }

    @When("^I logout$")
    public void logout() {
        this.loginPage.logout();
    }

    @Then("^I am logged in$")
    public void assertLoggedIn() {
        loginPage.assertLoggedIn();
    }
}
