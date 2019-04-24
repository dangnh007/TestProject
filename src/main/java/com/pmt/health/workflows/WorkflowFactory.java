package com.pmt.health.workflows;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.web.*;

public class WorkflowFactory {

    private WorkflowFactory() {
    }

    public static LoginWorkflow getLoginPage(DeviceController deviceController, User user) {
        return new LoginPageWeb((WebApp) deviceController.getApp(), user);
    }
}
