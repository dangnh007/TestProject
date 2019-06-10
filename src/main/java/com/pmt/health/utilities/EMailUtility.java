/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmt.health.utilities;

import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.objects.user.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This utility class will make api calls to Mailtrap.io
 * Retrive data from messages for a given user.
 */

public class EMailUtility {

    public static final String API_TOKEN = "8c32b5e812840810d6449c99dcdc9bf7";
    public static final String EMAIL_URL = "https://mailtrap.io";

    private final User user;
    protected final Reporter reporter;

    public EMailUtility(User user, Reporter reporter) {
        this.user = user;
        this.reporter = reporter;
    }

    public Response emailInbox() throws IOException {
        String action = "Getting into an inbox via the API";
        String expected = "Successfully get into an inbox via the API";
        // setup BaseURL
        HTTP emailAPI = new HTTP(EMAIL_URL, reporter);
        RequestData requestData = new RequestData();
        Map<String, String> token = new HashMap<>();
        token.put("Api-Token", API_TOKEN);
        emailAPI.addHeaders(token);
        requestData.setHeaders(token);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = emailAPI.get("/api/v1/inboxes//611509/messages", requestData);
        if (response.getCode() == 200) {
            reporter.pass(action, expected, expected + ". " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        } else {

            reporter.warn(action, expected, "User not successfully passed authenticator code. " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        }
        return response;
    }
}