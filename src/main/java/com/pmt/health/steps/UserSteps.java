package com.pmt.health.steps;

//DO NOT REMOVE THESE OR GHERKIN BUILDER BREAKS!

import com.pmt.health.objects.user.User;
public class UserSteps {

    protected DeviceController deviceController;
    protected User user;

    public UserSteps(DeviceController deviceController, User user) {
        this.deviceController = deviceController;
        this.user = user;
    }
}
