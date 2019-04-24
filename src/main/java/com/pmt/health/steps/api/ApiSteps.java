package com.pmt.health.steps.api;

import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.APIUtility;
import org.testng.log4testng.Logger;

import java.io.IOException;

public class ApiSteps {

    private final DeviceController deviceController;
    private final User user;
    private final UserUtility userUtility;
    private final APIUtility apiUtility;
    // used to share context of responses and requests between step declarations and workflows
    protected RequestData requestData;
    private Logger log = Logger.getLogger(ApiSteps.class);

    public ApiSteps(DeviceController deviceController, User user, RequestData requestData) throws IOException {
        this.user = user;
        this.userUtility = new UserUtility(user, deviceController.getReporter());
        this.apiUtility = new APIUtility(user, deviceController, requestData);
        this.deviceController = deviceController;
        this.requestData = requestData;
    }

}
