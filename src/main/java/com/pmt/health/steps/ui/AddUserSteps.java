package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;

public class AddUserSteps {

    private final User user;
    private final DeviceController deviceController;
    private final AddUserPage addUserPage;

    public AddUserSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }
}
