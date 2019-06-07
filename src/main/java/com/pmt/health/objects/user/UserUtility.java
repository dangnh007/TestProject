package com.pmt.health.objects.user;

import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;

import java.io.IOException;
import java.util.Random;

public class UserUtility {

    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ADMIN_USER = ".admin.user";
    public static final String ADMIN_PASS = ".admin.pass";

    private static final String VQA3 = "VibQA3+";

    protected final Reporter reporter;
    private static Random r = new Random();
    private final User user;
    protected HTTP userHttp;
    private HTTP adminHttp;

    public UserUtility(User user, Reporter reporter) {
        this.user = user;
        this.reporter = reporter;
        this.userHttp = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
        this.userHttp.setSESSION(this.user.getSESSIONToken());
        this.adminHttp = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
    }

    /**
     * Generates a semi-unique string to place on the end of a test user email address.
     * This is to get around an issue with GMail not forwarding complex email addresses.
     * Also uses the randomly generated String in filling up support and security qustions
     *
     * @return A String containing a random guid depending on size provided.
     */
    public static String generateUUID(int size) {
        r.setSeed(r.nextInt() * System.currentTimeMillis());
        int guidLength = size;
        char[] myGuid = new char[guidLength];
        char[] letterSet = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        for (int i = 0; i < guidLength; i++) {
            char c = letterSet[r.nextInt(letterSet.length)];
            myGuid[i] = c;
        }
        return new String(myGuid);
    }

    /**
     * Generates a semi-unique string to place on the end of a test user email address.
     * Also uses the randomly generated String in filling up support and security qustions
     *
     * @return A String containing random guid with the size of 16 characters
     */
    public static String generateUUID() {
        return generateUUID(16);
    }

    /**
     * Creates a random user prepended with VibrentQA3+ every time and generates DOB with age of 20 years.
     */
    public static String makeRandomUserEmail() {
        return makeRandomUserEmail(VQA3);
    }

    public static String makeRandomUserEmail(String preamble) {
        String uuid = generateUUID();
        String env = Property.getProgramProperty("email.env");
        String emailDomain = Property.getProgramProperty("email.domain");
        if (emailDomain != null) {
            return preamble + env + "+" + uuid + "@" + emailDomain + ".com";
        } else {
            return preamble + uuid + "@example.com";
        }
    }

    /**
     * Logs as System admin user in via the API.
     * Pass credentials
     */
    public Response apiLoginAdmin() throws IOException {
        String action = "Logging in via the API";
        String expected = "Successfully login in via the API";
        // setup our user credentials
        JsonObject credentials = new JsonObject();
        credentials.addProperty(EMAIL, Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_USER));
        credentials.addProperty(PASSWORD, Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
        RequestData requestData = new RequestData();
        requestData.setJSON(credentials);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/login", requestData);
        if (response.getCode() == 200) {
            reporter.pass(action, expected, expected + ". " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        } else {
            reporter.warn(action, expected, "User not successfully logged in. " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        }
        return response;
    }

    /**
     * Logs as System admin user in via the API.
     * Pass authenticator code
     */
    public Response apiLoginAdminMFA() throws IOException {
        String action = "Logging in via the API";
        String expected = "Successfully pass authenticator code via the API";
        // setup our user mfa
        JsonObject mfa = new JsonObject();
        mfa.addProperty("mfaCode", HTTP.obtainOath2Key());
        RequestData requestData = new RequestData();
        requestData.setJSON(mfa);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/login/authenticatorCode", requestData);
        if (response.getCode() == 200) {
            reporter.pass(action, expected, expected + ". " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        } else {
            reporter.warn(action, expected, "User not successfully passed authenticator code. " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        }
        return response;
    }
}
