package com.pmt.health.steps;

//DO NOT REMOVE THESE OR GHERKIN BUILDER BREAKS!

import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import org.testng.log4testng.Logger;

import java.io.IOException;

public class UserSteps {

    private final RequestData requestData;
    private final UserUtility userUtility;
    protected DeviceController deviceController;
    protected User user;
    private Logger log = Logger.getLogger(UserSteps.class);

    public UserSteps(DeviceController deviceController, User user, RequestData requestData) throws IOException {
        this.deviceController = deviceController;
        this.requestData = requestData;
        this.user = user;
        this.userUtility = new UserUtility(user, deviceController.getReporter());
    }
}
