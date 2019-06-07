package com.pmt.health.steps.api;

import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import cucumber.api.java.en.When;

import java.io.IOException;

public class ApiSteps {

    private final UserUtility userUtility;
    // used to share context of responses and requests between step declarations and workflows
    protected RequestData requestData;

    public ApiSteps(DeviceController deviceController, RequestData requestData) {
        this.userUtility = new UserUtility(deviceController.getReporter());
        this.requestData = requestData;
    }

    @When("^I login as System Administrator via API$")
    public void loginAsSystemAdminViaAPI() throws IOException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
    }
}
