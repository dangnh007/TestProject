/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmt.health.utilities;

import com.google.gson.JsonArray;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.objects.user.User;
import org.apache.commons.lang3.StringUtils;

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
    public static final String MESSAGES_ENDPOINT = "/api/v1/inboxes//611509/messages";

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
        // make the actual call
        Response response = emailAPI.get(MESSAGES_ENDPOINT, requestData);
        generateReport(action, expected, response);
        return response;
    }

    public String emailGetValue() throws IOException, InterruptedException {
        String action = "Getting into an inbox and retrieve message via API";
        String expected = "Successfully get into an inbox and retrieve message via the API";
        // setup BaseURL
        HTTP emailAPI = new HTTP(EMAIL_URL, reporter);
        RequestData requestData = new RequestData();
        Map<String, String> token = new HashMap<>();
        token.put("Api-Token", API_TOKEN);
        emailAPI.addHeaders(token);
        requestData.setHeaders(token);
        //string to return password value
        String password = "";
        int count = 0;
        //do-while to check inbox again if email was not found
        do {
            // make the actual call
            Response response = emailAPI.get(MESSAGES_ENDPOINT, requestData);
            //Initialize ArrayData rom response
            int size = response.getArrayData().size();
            JsonArray arrayData = response.getArrayData();
            //Loop through to get a new response for a valid message
            for (int i = 0; i < size; i++) {
                if (arrayData.get(i).toString().contains(user.getEmail())) {
                    response = emailAPI.get(MESSAGES_ENDPOINT + "/" + arrayData.get(i).getAsJsonObject().get("id") + "/body.html", requestData);
                    //Gets password from the html response
                    String resp = response.getMessage();
                    password = StringUtils.substringBetween(resp, "Password", "</span>");
                    //generate report
                    generateReport(action, expected, response);
                    break;
                }
            }
            count++;
            Thread.sleep(1000);
        } while (password.isEmpty() && count < 5);
        //return string from index where string become valuable
        if (password.isEmpty()) {
            reporter.fail(action, expected, "Value was not found.");
        }
        return password.substring(11);
    }

    private void generateReport(String action, String expected, Response response) {
        if (response.getCode() == 200) {
            reporter.pass(action, expected, expected + ". " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        } else {
            reporter.fail(action, expected, "Value was not found. " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        }
    }
}
