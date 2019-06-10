package com.pmt.health.steps.api;

import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import org.testng.log4testng.Logger;

public class AuthSteps {

    public static final String UTF_8 = "UTF-8";
    private final User user;
    private final UserUtility userUtility;
    private final DeviceController deviceController;
    protected RequestData requestData;

    // used to share context of responses and requests between step declarations and workflows

    private String uniqueEmail;
    private String name;
    private String lastName;

    private Logger log = Logger.getLogger(AuthSteps.class);

    public AuthSteps(DeviceController deviceController, User user, RequestData requestData) {
        this.user = user;
        this.userUtility = new UserUtility(deviceController.getReporter(), user);
        this.deviceController = deviceController;
        this.requestData = requestData;
    }

}
