package com.pmt.health.steps;

//DO NOT REMOVE THESE OR GHERKIN BUILDER BREAKS!

import com.pmt.health.objects.user.User;
import cucumber.api.java.en.Given;
import org.springframework.context.annotation.Description;

public class UserSteps {

    protected DeviceController deviceController;
    protected User user;

    public UserSteps(DeviceController deviceController, User user) {
        this.deviceController = deviceController;
        this.user = user;
    }

    @Description("Uses the environment's existing admin user")
    @Given("^I am an admin user$")
    public void useExistingAdmin() {
        //TODO
    }
}
