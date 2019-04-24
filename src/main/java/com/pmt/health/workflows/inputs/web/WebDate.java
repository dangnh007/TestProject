package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;

import java.util.Date;

public class WebDate extends WebInput {

    private final Date date;

    public WebDate(WebApp app, ApiInput api, String uiLabel, Date date) {
        super(app, api, uiLabel, Input.INPUT_SIMPLE_DATE_FORMAT.format(date), false);
        this.date = date;
    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    @Override
    public Date getUserValues() {
        return date;
    }
}
