package com.pmt.health.steps.api;

import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import org.testng.log4testng.Logger;

import java.io.IOException;

public class AuthSteps {

    public static final String UTF_8 = "UTF-8";
    private final User user;
    private final UserUtility userUtility;
    private final DeviceController deviceController;
    protected RequestData requestData;

    // used to share context of responses and requests between step declarations and workflows

    private String uniqueEmail = "VibrentQA3+" + UserUtility.generateUUID() + "@gmail.com";
    private String name = "Alex";
    private String lastName = "Duchenko";

    private Logger log = Logger.getLogger(AuthSteps.class);

    public AuthSteps(DeviceController deviceController, User user, RequestData requestData) throws IOException {
        this.user = user;
        this.userUtility = new UserUtility(user, deviceController.getReporter());
        this.deviceController = deviceController;
        this.requestData = requestData;
    }

}
