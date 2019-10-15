package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Description;

public class UserAdminSteps {

    private final User user;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final AddUserPage addUserPage;

    public UserAdminSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }

    @Description("Creating a user with a specific parameters")
    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\" level$")
    public void createUser(String role, String org) {
        this.userAdminPage.userAdmin();
        this.userAdminPage.addUser();
        this.addUserPage.enterFirstName(user.getFirstName());
        this.addUserPage.enterLastName(user.getLastName());
        this.addUserPage.enterEmail(UserUtility.makeRandomUserEmail());
        this.addUserPage.selectRole(role);
        this.addUserPage.checkAwardee(org);
        this.addUserPage.saveUser();
    }

    @Then("^User has been created$")
    public void assertCreatedUser() {
        this.userAdminPage.assertCreatedUser();
    }

    @Then("^I am logged in as user$")
    public void loggedInAsUser() {
        this.userAdminPage.assertLoggedInUser();
    }

    @Then("^I update user to \"([^\"]*)\" role and \"([^\"]*)\" org$")
    public void navigateToUserAdminPage(String role, String org) {
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.searchField(user.getSearchedUserEmail());
        this.userAdminPage.assertSearchedEmail();
        this.userAdminPage.selectActionDropDown();
        this.userAdminPage.assertEditUserHeader();
        this.addUserPage.enterFirstName("Updated User");
        this.addUserPage.enterLastName("Updated Automation");
        this.addUserPage.selectRole(role);
        this.addUserPage.checkAwardee(org);
        this.addUserPage.saveUser();
    }
}