package com.pmt.health.steps.ui;

import com.github.javafaker.Faker;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;
import com.pmt.health.workflows.UserAdminPage;
import cucumber.api.java.en.Then;
import org.springframework.context.annotation.Description;

public class UserAdminSteps {

    private final User user;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final AddUserPage addUserPage;

    /**
     * Faker class called to use testing library
     */
    Faker faker = new Faker();
    private final String firstName = faker.name().firstName();
    private final String lastName = faker.name().lastName();
    private final String email = (firstName+"."+lastName).toLowerCase() + "@gmail.com";

    public UserAdminSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }

    @Description("Creating a user with a specific parameters")
    @Then("^I create user with \"([^\"]*)\"$")
    public void createUser(String role) {
        this.userAdminPage.addUser();
        this.addUserPage.enterFirstName(firstName);
        this.addUserPage.enterLastName(lastName);
        this.addUserPage.enterEmail(email);
        this.addUserPage.selectRole(role);


    }

}
