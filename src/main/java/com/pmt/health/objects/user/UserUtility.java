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

public class UserUtility {

    public static final String USER_ID = "userId";
    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String ADMIN_USER = ".admin.user";
    public static final String ADMIN_PASS = ".admin.pass";
    protected final Reporter reporter;
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
}
