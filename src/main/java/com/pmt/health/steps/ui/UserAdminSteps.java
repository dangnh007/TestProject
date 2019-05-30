package com.pmt.health.steps.ui;

import com.pmt.health.exceptions.VibrentJSONException;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Description;

import java.io.IOException;

public class UserAdminSteps {

    private final User user;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final AddUserPage addUserPage;

    public UserAdminSteps(DeviceController deviceController, User user) throws IOException, VibrentJSONException {
        this.user = user;
        this.deviceController = deviceController;
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }

    @Description("Creating a user with a specific parameters")
    @When("^I create user with \"([^\"]*)\"$")
    public void createUser(String role) throws IOException, VibrentJSONException {
        this.userAdminPage.addUser();
        this.addUserPage.enterFirstName(user.getFirstName());
        this.addUserPage.enterLastName(user.getLastName());
        this.addUserPage.enterEmail(user.getEmail());
        this.addUserPage.selectRole(role);
        this.addUserPage.defaultAwardee();
        this.addUserPage.saveUser();
    }

    @Then("^I see created user$")
    public void assertCreatedUser() {
        this.userAdminPage.assertCreatedUser();
    }
}
