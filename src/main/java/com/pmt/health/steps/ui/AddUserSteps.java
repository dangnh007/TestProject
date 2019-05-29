package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;
import cucumber.api.java.en.Then;
import org.springframework.context.annotation.Description;

public class AddUserSteps {

    private final User user;
    private final DeviceController deviceController;
    private final AddUserPage addUserPage;

    public AddUserSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }

    @Description("Creating a user with a specific parameters")
    @Then("^I set auth level Awardee$")
    public void setAuthLvl() {
        addUserPage.defaultAwardee();
        addUserPage.saveUser();
    }

    @Then("^I set group as Test Awardee$")
    public void setGroup() {
        //TODO
    }
}
