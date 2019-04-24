package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.objects.user.UserValue;
import com.pmt.health.workflows.inputs.ApiInput;

import java.util.List;

public class WebQuiz extends WebVerticalRadio {
    public WebQuiz(WebApp app, ApiInput api, String uiLabel, UserValue... values) {
        super(app, api, uiLabel, values);
    }

    public WebQuiz(WebApp app, ApiInput api, String uiLabel, List<UserValue> values) {
        super(app, api, uiLabel, values);
    }
}
