package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.CapacityManagementPage;

public class CapacityManagementSteps {

    private final User user;
    private final DeviceController deviceController;
    private final CapacityManagementPage capacityManagementPage;

    public CapacityManagementSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        capacityManagementPage = new CapacityManagementPage(this.deviceController.getApp(), user);
    }
}
