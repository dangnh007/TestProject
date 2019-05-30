package com.pmt.health.objects.user;

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
import java.util.Random;

public class UserUtility {

    public static final String USER_ID = "userId";
    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String ADMIN_USER = ".admin.user";
    public static final String ADMIN_PASS = ".admin.pass";

    private static final String VQA3 = "VibQA3+";

    protected final Reporter reporter;
    private static Random r = new Random();
    private final User user;
    protected HTTP userHttp;
    private HTTP adminHttp;
    private Logger log = Logger.getLogger(UserUtility.class);

    public UserUtility(User user, Reporter reporter) throws IOException {
        this.user = user;
        this.reporter = reporter;
        this.userHttp = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
        this.userHttp.setSESSION(this.user.getSESSIONToken());
        this.adminHttp = setupAdminHttp(reporter);
    }


    /**
     * Creates an HTTP connection to the admin console, based on the admin user listed in the properties file.
     * If no admin user exists, a null connection will be returned
     *
     * @param reporter
     * @return HTTP
     */
    public static HTTP setupAdminHttp(Reporter reporter) throws IOException {
        // setup our admin form for this information
        HTTP adminHTTP = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
        JsonObject authObject = new JsonObject();
        authObject.addProperty(EMAIL, Property.getDefaultProgramProperty(ADMIN_USER));
        authObject.addProperty(PASSWORD, Property.getDefaultProgramProperty(ADMIN_PASS));
        RequestData requestData = new RequestData();
        requestData.setJSON(authObject);
        try {
            adminHTTP.get("/api/login");
        } catch (VibrentIOException vioe) {
            // Expect 403 to get Session
        }
        Response adminAuth = adminHTTP.simplePost("/api/login", requestData);
        authObject = new JsonObject();
        authObject.addProperty("mfaCode", adminHTTP.obtainOath2Key());
        requestData = new RequestData();
        requestData.setJSON(authObject);
        Response adminAuthCode = adminHTTP.simplePost("/api/login/authenticatorCode", requestData);
        return adminHTTP;
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
}
