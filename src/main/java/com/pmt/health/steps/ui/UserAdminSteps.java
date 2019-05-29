package com.pmt.health.steps.ui;

import com.pmt.health.exceptions.VibrentJSONException;
import com.pmt.health.objects.user.TestUser;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;
import org.springframework.context.annotation.Description;

import java.io.IOException;

public class UserAdminSteps {

    private final User user;
    private final TestUser testUser;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final AddUserPage addUserPage;

    public UserAdminSteps(DeviceController deviceController, User user, TestUser testUser) throws IOException, VibrentJSONException {
        this.user = user;
        this.testUser = testUser;
        this.deviceController = deviceController;
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user, testUser);
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }

    @Description("Creating a user with a specific parameters")
    @Then("^I create user with \"([^\"]*)\"$")
    public void createUser(String role) throws IOException, VibrentJSONException {
        this.userAdminPage.addUser();
        this.addUserPage.enterFirstName(testUser.getFirstName());
        this.addUserPage.enterLastName(testUser.getLastName());
        this.addUserPage.enterEmail(testUser.getEmail());
        this.addUserPage.selectRole(role);
    }

    @Then("^I see created user$")
    public void assertCreatedUser() {
        this.userAdminPage.assertCreatedUser();
    }
}
