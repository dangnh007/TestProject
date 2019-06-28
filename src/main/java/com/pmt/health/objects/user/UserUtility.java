package com.pmt.health.objects.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;

import java.io.IOException;
import java.util.*;

public class UserUtility {

    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ADMIN_USER = ".admin.user";
    public static final String ADMIN_PASS = ".admin.pass";
    public static final String LOGIN_MESSAGE = "Logging in via the API";

    private static final String LOGIN_ENDPOINT = "/api/login";
    private static final String PASS = Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS);
    private static final String VQA3 = "VibQA3+";
    private static final String MAIN_URL = Property.getProgramProperty(Configuration.getEnvironment() + ".url.sub");
    private static final String REFERER_CREATE_USER = MAIN_URL + "/userAdmin/createUser/ROLE_MC_SYSTEM_ADMINISTRATOR?role=ROLE_MC_SYSTEM_ADMINISTRATOR";

    protected final Reporter reporter;
    private static Random r = new Random();
    private HTTP adminHttp;
    private User user;

    public UserUtility(Reporter reporter, User user) {
        this.user = user;
        this.reporter = reporter;
        this.adminHttp = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
    }

    public UserUtility(Reporter reporter, User user, HTTP http) {
        this.user = user;
        this.reporter = reporter;
        this.adminHttp = http;
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
        String action = LOGIN_MESSAGE;
        String expected = "Successfully login in as admin via the API";
        // setup our user credentials
        JsonObject credentials = new JsonObject();
        credentials.addProperty(EMAIL, Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_USER));
        credentials.addProperty(PASSWORD, PASS);
        RequestData requestData = new RequestData();
        requestData.setJSON(credentials);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost(LOGIN_ENDPOINT, requestData);
        reporterPassFailStep(action, expected, response, "Admin not successfully logged in. ");
        return response;
    }

    private void reporterPassFailStep(String action, String expected, Response response, String failMessage) {
        if (response.getCode() == 200) {
            reporter.pass(action, expected, expected + ". " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        } else {
            reporter.warn(action, expected, failMessage + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        }
    }

    /**
     * Logs as System admin user in via the API.
     * Pass authenticator code
     */
    public Response apiLoginAdminMFA() throws IOException {
        String action = LOGIN_MESSAGE;
        String expected = "Successfully pass authenticator code for admin via the API";
        // setup our user mfa
        JsonObject mfa = new JsonObject();
        mfa.addProperty("mfaCode", HTTP.obtainOath2Key());
        RequestData requestData = new RequestData();
        requestData.setJSON(mfa);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/login/authenticatorCode", requestData);
        reporterPassFailStep(action, expected, response, "Admin not successfully passed authenticator code. ");
        return response;
    }

    public Response apiCreateUser(String role, String group) throws IOException {
        String action = "Create user via the API";
        String expected = "Successfully created user via the API";
        //setup our body for creating user
        JsonObject createUser = new JsonObject();
        createUser.addProperty(EMAIL, user.getEmail());
        createUser.addProperty("firstName", user.getFirstName());
        createUser.addProperty("lastName", user.getLastName());
        //some of the fields in the body has array parameter
        JsonArray roles = new JsonArray();
        roles.add(role);
        JsonArray groups = new JsonArray();
        groups.add(group);
        createUser.add("roles", roles);
        createUser.add("groups", groups);
        //Set headers and body
        Map<String, String> referer = new HashMap<>();
        referer.put("Referer", REFERER_CREATE_USER);
        RequestData requestData = new RequestData();
        adminHttp.addHeaders(referer);
        requestData.setJSON(createUser);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/userAdmin/user?roleName=ROLE_MC_SYSTEM_ADMINISTRATOR", requestData);
        reporterPassFailStep(action, expected, response, "User has been not created. ");
        return response;
    }

    /**
     * Logs as user in via the API.
     * gets secret while login
     */
    public void retrieveAndSetUserSecretKey() throws IOException {
        String action = LOGIN_MESSAGE;
        String expected = "Successfully got a secret Key via the API";
        // setup our user credentials
        JsonObject credentials = new JsonObject();
        credentials.addProperty(EMAIL, user.getEmail());
        credentials.addProperty(PASSWORD, user.getPassword());
        RequestData requestData = new RequestData();
        requestData.setJSON(credentials);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost(LOGIN_ENDPOINT, requestData);
        reporterPassFailStep(action, expected, response, "Failed to find secret key. ");
        //gets secret key from response
        String secretKey = response.getObjectData().get("data").getAsJsonObject().get("secret").getAsString();
        user.setSecretKey(secretKey);
    }

    /**
     * Logs as user in via the API.
     */
    public void apiLoginUser() throws IOException {
        String action = LOGIN_MESSAGE;
        String expected = "Successfully login in as user via the API";
        // setup our user credentials
        JsonObject credentials = new JsonObject();
        credentials.addProperty(EMAIL, user.getEmail());
        credentials.addProperty(PASSWORD, PASS);
        RequestData requestData = new RequestData();
        requestData.setJSON(credentials);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost(LOGIN_ENDPOINT, requestData);
        reporterPassFailStep(action, expected, response, "User not successfully logged in. ");
    }

    /**
     * Logs as user in via the API.
     * Pass authenticator code
     * sets userId in the User object
     */
    public void apiLoginUserMFA() throws IOException {
        String action = LOGIN_MESSAGE;
        String expected = "Successfully pass authenticator code for user via the API";
        // setup our user mfa
        JsonObject mfa = new JsonObject();
        mfa.addProperty("mfaCode", HTTP.obtainOath2KeyCreatedUser(user.getSecretKey()));
        RequestData requestData = new RequestData();
        requestData.setJSON(mfa);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/login/authenticatorCode", requestData);
        reporterPassFailStep(action, expected, response, "User not successfully passed authenticator code. ");
        //Gets userId value from response
        String userId = response.getObjectData().get("data").getAsJsonObject().get("userPreferences").getAsJsonObject().get("userId").getAsString();
        user.setUserId(userId);
    }

    /**
     * Logs as user in via the API.
     * gets secret while login
     */
    public void apiSetPassword() throws IOException {
        String action = LOGIN_MESSAGE;
        String expected = "Successfully set password via the API";
        // setup our user credentials
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("newPassword", PASS);
        jsonObject.addProperty("userId", user.getUserId());
        RequestData requestData = new RequestData();
        requestData.setJSON(jsonObject);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/changePassword", requestData);
        reporterPassFailStep(action, expected, response, "Failed to set new password. ");
    }
}
