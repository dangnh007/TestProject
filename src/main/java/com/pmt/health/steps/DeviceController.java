package com.pmt.health.steps;

import com.pmt.health.exceptions.AppNotFoundException;
import com.pmt.health.exceptions.InvalidDeviceException;
import com.pmt.health.interactions.application.App;
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import com.pmt.health.utilities.Sauce;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class controls an appium application
 *
 * @author Coveros
 */
public abstract class DeviceController {

    protected static final String CUSTOM_REPORT_LOCATION = "target/detailed-reports";
    protected static final String DEVICE_ORIENTATION = "deviceOrientation";
    protected Logger log = Logger.getLogger(DeviceController.class);
    protected Device device;
    protected Reporter reporter;
    protected WebDriver driver;
    protected Scenario scenario;
    protected PrintStream tagFile;
    protected Configuration configuration = null;
    private App app;

    /**
     * Retrieves the application passed from the commandline. If we're testing on sauce, it properly appends the
     * needed sauce parameters. If the application doesn't exist, the tests error out
     *
     * @return
     */
    protected String getApplication() {
        String appString = System.getProperty("app", System.getProperty("environment", "automation") + ".apk");
        if (Sauce.isUsed()) {
            return "sauce-storage:" + appString;
        }
        File appFile = new File(appString);
        if (!appFile.exists()) {
            log.warn("Can't find App: " + appString);
            throw new AppNotFoundException("Provided app at " + appString + " was not found");
        }
        return appFile.getAbsolutePath();
    }

    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    protected abstract DesiredCapabilities getDeviceCapabilities() throws InvalidDeviceException;

    /**
     * Retrieves all of the valid device information from the commandline: version, platform, platform version,
     * browser name, device name, and device orientation
     *
     * @param capabilities - the desiredCapabilities to be appended to
     * @return DesiredCapabilities - the provided desiredCapabilities with the new device information appended to it,
     * or overwritten
     */
    protected DesiredCapabilities getDeviceDetails(DesiredCapabilities capabilities) {
        if (!Device.getDeviceVersion().isEmpty()) {
            String deviceVersion = Device.getDeviceVersion();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceVersion);
        }
        if (!Device.getDevicePlatform().isEmpty()) {
            capabilities.setCapability(CapabilityType.PLATFORM, Device.getDevicePlatform());
        }
        if (!Device.getBrowserName().isEmpty()) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, Device.getBrowserName());
            capabilities.setBrowserName(Device.getBrowserName());
        }
        if (!Device.getDeviceOrientation().isEmpty()) {
            capabilities.setCapability(DEVICE_ORIENTATION, Device.getDeviceOrientation());
        }
        return capabilities;
    }

    /**
     * Retrieves the App under test
     *
     * @return an App object
     */
    public App getApp() {
        return app;
    }

    /**
     * Sets the app to the passed parameter
     *
     * @param app An App object
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * Retrieves the Device under test
     *
     * @return A Device object
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Retrieves the Driver to control this test
     *
     * @return A WebDriver object
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Sets the WebDriver to the passed parameter
     *
     * @param driver A WebDriver object
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Retrieves the Output FILE storing these test results
     *
     * @return An Reporter object
     */
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * Sets the Output FILE to the passed parameter
     *
     * @param reporter An Reporter object
     */
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * Initializes this deviceController for this scenario
     *
     * @param scenario A Scenario object
     */
    @Before
    // Overriding methods may throw InvalidDeviceException even if this method cannot.
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    protected void setupController(Scenario scenario) throws IOException, InvalidDeviceException {
        this.scenario = scenario;
        setupLogging(scenario);
    }

    /**
     * Initializes and retrieves the proxy if necessary for this test
     *
     * @return A Proxy object
     */
    protected Proxy getProxy() {
        if (System.getProperty("proxy") != null) {
            String setProxy = System.getProperty("proxy");
            // set the proxy information
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(setProxy).setFtpProxy(setProxy).setSslProxy(setProxy).setSocksProxy(setProxy);
            return proxy;
        }
        return null;
    }

    /**
     * Sets up logging for this scenario. Prepares report reporter
     *
     * @param scenario A Scenario object
     */
    protected void setupLogging(Scenario scenario) throws IOException {
        setReporter(new Reporter(CUSTOM_REPORT_LOCATION, scenario, getDevice(), this.configuration));
        setApp(new App(getDriver(), getDevice(), getReporter()));
        getReporter().setApp(getApp());
    }

    /**
     * Sets up the WebDriver instance to run on the provided hub, instead of locally
     *
     * @param scenario - the Cucumber scenario
     * @throws MalformedURLException
     * @throws InvalidDeviceException
     */
    protected void setupRemoteController(Scenario scenario) throws IOException, InvalidDeviceException {
        DesiredCapabilities capabilities = getDeviceCapabilities();
        capabilities.setCapability("name", scenario.getId().replaceAll("-", " ").replaceAll(";", ": "));
        if (getProxy() != null) {
            capabilities.setCapability(CapabilityType.PROXY, getProxy());
        }
        setDriver(new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), capabilities));
        // start and setup our logging
        setupLogging(scenario);
    }

    /**
     * Takes a screenshot and tears down this instance.
     *
     * @param scenario A scenario object
     */
    @After
    protected void takeScreenshotAndTeardownController(Scenario scenario) {
        try {
            if (driver != null && Property.takeScreenshot() && Device.getDevice() != Device.HTMLUNIT) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }
        } catch (ClassCastException | WebDriverException e) {
            log.error(e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
            // TODO: Make sure that we're still catching all errors in listener even when we don't have a reporters.
            int errors = 0;
            if(reporter != null) {
                 errors = reporter.getErrors();
                if (errors > 0) {
                    log.error("* * * * * * Found " + errors + " errors in the scenario: " + scenario.getName());
                }
            } else {
                log.error("There was no reporter so no way of pulling those types of errors. This is probably okay.");
            }
            Assert.assertEquals(errors + " Errors", 0 + " Errors");
            Assert.assertFalse(scenario.isFailed(), "Scenario Failed even though reporter had no errors");
        }
    }
}
