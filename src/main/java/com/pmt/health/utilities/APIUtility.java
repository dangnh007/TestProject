package com.pmt.health.utilities;

import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;

public class APIUtility {

    protected final User user;
    protected final UserUtility userUtility;
    protected final DeviceController deviceController;
    protected RequestData requestData;
    protected HTTP http;
    protected Reporter reporter;

    public APIUtility(User user, DeviceController deviceController, RequestData requestData) {
        this.user = user;
        this.reporter = deviceController.getReporter();
        this.userUtility = new UserUtility(deviceController.getReporter(),user);
        this.deviceController = deviceController;
        this.requestData = requestData;
    }
}
