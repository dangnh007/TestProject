package com.pmt.health.objects.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pmt.health.exceptions.VibrentIOException;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

public class UserUtility {

    private static final String PASSWORD = "password";//NOSONAR
    private static final String MFA = "mfaCode";
    private static final String EMAIL = "email";
    private static final String OAUTH_ENDPOINT = "/api/oauth/token";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String GRANT_TYPE = "grant_type";
    private static final String ADMIN_USER = ".admin.user";
    private static final String ADMIN_PASS = ".admin.pass";
    private static final String LOGIN_MESSAGE = "Logging in via the API";
    private static final String REFERER = "Referer";
    private static final String LOGIN_ENDPOINT = "/api/login";
    private static final String PASS = Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS);
    private static final String VQA3 = "VibQA3+";
    private static final String MAIN_URL = Property.getProgramProperty(Configuration.getEnvironment() + ".url.mc");
    private static final String REFERER_CREATE_USER = MAIN_URL + "/userAdmin/createUser/ROLE_MC_SYSTEM_ADMINISTRATOR?role=ROLE_MC_SYSTEM_ADMINISTRATOR";

    protected final Reporter reporter;
    private static SecureRandom r = new SecureRandom();
    private HTTP adminHttp;
    private User user;
    private static final Logger log = Logger.getLogger(UserUtility.class);

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

    /**
     * Refactored method for reporter
     * @param action what it does
     * @param expected values or response
     * @param response actual response
     * @param failMessage massage displayed if there is a failure
     */
    private void reporterPassFailStep(String action, String expected, Response response, String failMessage) {
        if (response != null && response.getCode() == 200) {
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
        StringBuilder action = new StringBuilder(LOGIN_MESSAGE);
        String expected = "Successfully pass authenticator code for admin via the API";
        // setup our user mfa
        //Set headers and body
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_CREATE_USER);
        RequestData requestData = new RequestData();
        adminHttp.addHeaders(referer);
        JsonObject mfa = new JsonObject();
        mfa.addProperty(MFA, HTTP.obtainOath2Key());
        requestData.setJSON(mfa);
        action.append(Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD));
        // make the actual call
        Response response = null;
        int count = 0;
        while (count < 5) {
            try {
                response = adminHttp.simplePost("/api/login/authenticatorCode", requestData);
                break;
            } catch (VibrentIOException vioe) {
                log.info(vioe);
                count++;
                log.error("Failed MFA for logging in");
                log.error(requestData.getJSON());
                mfa.addProperty(MFA, HTTP.obtainOath2Key());
                requestData.setJSON(mfa);
                action.append(Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    log.error(ex);
                    Thread.currentThread().interrupt();
                }
            }
        }
        reporterPassFailStep(action.toString(), expected, response, "Admin not successfully passed authenticator code. ");
        return response;
    }

    /**
     * Creates user with reusable parameters
     */
    public Response apiCreateUser(String role) throws IOException {
        String uEmail = UserUtility.makeRandomUserEmail();
        String action = "Create user via the API";
        String expected = "Successfully created user via the API";
        //setup our body for creating user
        JsonObject createUser = new JsonObject();
        createUser.addProperty(EMAIL, uEmail);
        createUser.addProperty("firstName", user.getFirstName());
        createUser.addProperty("lastName", user.getLastName());
        //some of the fields in the body has array parameter
        JsonArray roles = new JsonArray();
        roles.add(role);
        JsonArray groups = new JsonArray();
        groups.add(user.groupValue);
        createUser.add("roles", roles);
        createUser.add("groups", groups);
        //Set headers and body
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_CREATE_USER);
        RequestData requestData = new RequestData();
        adminHttp.addHeaders(referer);
        requestData.setJSON(createUser);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simplePost("/api/userAdmin/user?roleName=ROLE_MC_SYSTEM_ADMINISTRATOR", requestData);
        reporterPassFailStep(action, expected, response, "User has been not created. ");
        if(user.searchedUserEmail.isEmpty()) {
            user.setSearchedUserEmail(uEmail);
        }
        user.setEmail(uEmail);
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
        if(user.searchedUserSecret.isEmpty()) {
            user.setSearchedUserSecret(secretKey);
        }

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
        StringBuilder action = new StringBuilder(LOGIN_MESSAGE);
        String expected = "Successfully pass authenticator code for user via the API";
        // setup our user mfa
        JsonObject mfa = new JsonObject();
        mfa.addProperty(MFA, HTTP.obtainOath2KeyCreatedUser(user.getSecretKey()));
        RequestData requestData = new RequestData();
        requestData.setJSON(mfa);
        action.append(Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD));
        // make the actual call
        Response response = null;
        int count = 0;
        while (count < 5) {
            try {
                response = adminHttp.simplePost("/api/login/authenticatorCode", requestData);
                break;
            } catch (VibrentIOException vioe) {
                log.info(vioe);
                count++;
                log.info("Failed MFA for logging in");
                mfa.addProperty(MFA, HTTP.obtainOath2Key());
                requestData.setJSON(mfa);
                action.append(Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    log.error(ex);
                    Thread.currentThread().interrupt();
                }
            }
        }
        reporterPassFailStep(action.toString(), expected, response, "User not successfully passed authenticator code. ");
        //Gets userId value from response
        if (response != null) {
            String userId = response.getObjectData().get("data").getAsJsonObject().get("userPreferences").getAsJsonObject().get("userId").getAsString();
            user.setUserId(userId);
        }
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

    /**
     * Gets authorization token.
     */
    public void getAuthorizationToken() throws IOException {
        String action = "I get authorization token via API";
        String expected = "Successfully got authorization token campaign via API";
        RequestData requestData = new RequestData();
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = adminHttp.simpleGet(LOGIN_ENDPOINT, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully get authorization token via API");
        String authToken = response.getObjectData().get("jwtToken").getAsString();
        user.setAuthToken("Bearer " + authToken);
    }

    /**
     * Gets access token.
     */
    public void getAccessToken() throws IOException {
        String action = "I get access token via API";
        String expected = "Successfully got access token via API";
        HTTP http = new HTTP(Property.getProgramProperty(Configuration.getEnvironment() + ".url.sub"));
        // setup parameters
        Map<String, String> parameter = new HashMap<>();
        parameter.put(CLIENT_ID, "missionControl");
        parameter.put(CLIENT_SECRET, "pmiSecret");
        parameter.put(GRANT_TYPE, "client_credentials");
        RequestData requestData = new RequestData();
        requestData.setParams(parameter);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.post(OAUTH_ENDPOINT, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully get access token via API");
        String authToken = response.getObjectData().get("access_token").getAsString();
        user.setAuthToken("Bearer " + authToken);
    }
}
