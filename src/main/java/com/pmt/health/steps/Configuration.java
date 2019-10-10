package com.pmt.health.steps;

import com.google.gson.JsonObject;
import com.pmt.health.exceptions.VibrentException;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import com.pmt.health.utilities.Sauce;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.pmt.health.utilities.Sauce.SESSION_ID;
import static org.testng.Reporter.getCurrentTestResult;

public class Configuration {

    public static final String EMPTY = "";
    public static final String PROGRAM_PROP = "program";
    public static final String ENVIRONMENT_PROP = "environment";
    public static final String SCENARIO = "Scenario";
    public static final String REPORTER = "Reporter";
    protected static final Logger log = Logger.getLogger(Configuration.class);
    protected DeviceController deviceController;
    private URL siteURL;
    private String program;
    private String environmentID;

    /**
     * Default constructor for Configuration class, which sets the deviceController - used to access
     *
     * @param deviceController
     */
    public Configuration(DeviceController deviceController) {
        this.deviceController = deviceController;
        program = Property.getProgramProperty(PROGRAM_PROP);
        environmentID = Property.getProgramProperty(getEnvironment() + ".android.env.id");
        if (System.getProperty("androidID") != null) {
            String idOverride = System.getProperty("androidID").toLowerCase();
            if (!"".equals(idOverride)) {
                environmentID = idOverride;
            }
        }
    }

    /**
     * Determines which environment is under test, by obtaining the value from the passed in system property. If no
     * value is set, the default value of 'automation' is used
     *
     * @return
     */
    public static String getEnvironment() {
        return System.getProperty(ENVIRONMENT_PROP, "qa").toLowerCase();
    }

    /**
     * Builds the environment URL, based on the provided environment parameter, and environment site.
     * Defaults to the automation environment.
     *
     * @return url that should be used for specified environment
     */
    public static URL getEnvironmentURL(String env) {
        try {
            if (env.isEmpty()) {
                env = "mc";
            }
            String u = Property.getProgramProperty(getEnvironment() + ".url." + env);
            if (u == null) {
                throw new MalformedURLException(
                        "No URL is defined for the environment '" + getEnvironment() + "' with site '" + env + "'");
            }
            if (u.endsWith("/")) {
                throw new MalformedURLException("Please ensure provided URLs don't end with '/'");
            }
            return new URL(u);
        } catch (MalformedURLException e) {
            log.info(e);
            return null;
        }
    }

    /**
     * Builds the user environment URL, based on the provided environment parameter.
     * Defaults to the automation environment.
     *
     * @return url that should be used for specified environment
     */
    public static URL getMissionControlEnvironmentURL() {
        return getEnvironmentURL("mc");
    }

    public URL getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(URL siteURL) {
        this.siteURL = siteURL;
    }

    /**
     * Determines which program is under test, by obtaining the value from the program properties file
     *
     * @return
     */
    public String getProgram() {
        return program;
    }

    /**
     * Gets the full system information in JSON format
     *
     * @return
     */
    public JsonObject getSystemInfo(Reporter reporter) throws IOException {
        if (getMissionControlEnvironmentURL() == null) {
            log.error("Issue obtaining subscriber environment, unable to run tests");
            return null;
        }
        HTTP http = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
        JsonObject objectData;
        Response response = http.get("/api/systemInfo");
        objectData = response.getObjectData();
        return objectData;
    }

    /**
     * Extracts the JenkinsBuild number from the json system information
     *
     * @param sysInfo a json object of the system information
     * @return
     */
    public String getBuild(JsonObject sysInfo) {
        return EMPTY;
    }

    /**
     * Extracts the Version number from the json system information
     *
     * @param sysInfo a json object of the system information
     * @return
     */
    public String getVersion(JsonObject sysInfo) {
        return sysInfo.get("buildVersion").getAsString();
    }

    /**
     * Builds the environment URL, based on the provided environment parameter, and environment site.
     * Defaults to the automation environment.
     *
     * @return url that should be used for specified environment
     */
    public static URL getEnvironmentURL() {
        return getMissionControlEnvironmentURL();
    }

    /**
     * Sets up the environments, and determines if the test will be run remotely on a hub, or locally
     * Additionally, the site and program are set
     *
     * @param scenario - Provide the Cucumber scenario, to associate with the driver
     */
    @Before
    public void setup(Scenario scenario) throws VibrentException {
        //setup our application information
        setSiteURL(getEnvironmentURL());
        //setup our browser
        if (System.getProperty("hub") != null) {
            try {
                deviceController.setupRemoteController(scenario);
            } catch (IOException e) {
                throw new VibrentException("Unable to setup remote controller.", e);
            }
        } else {
            try {
                deviceController.setupController(scenario);
            } catch (IOException e) {
                throw new VibrentException("Unable to setup local controller. This is almost always a networking error.", e);
            }
        }
        deviceController.getApp().setSite(siteURL);
        deviceController.getApp().setProgram(getProgram());
    }

    @After
    public void teardown(Scenario scenario) {
        ITestResult result = getCurrentTestResult();
        if (result != null) {
            result.setAttribute(SCENARIO, scenario);
            result.setAttribute(REPORTER, deviceController.getReporter());
            if (Sauce.isUsed()) {
                result.setAttribute(SESSION_ID, ((RemoteWebDriver) deviceController.getDriver()).getSessionId());
            }
        }
        deviceController.takeScreenshotAndTeardownController(scenario);
    }
}
