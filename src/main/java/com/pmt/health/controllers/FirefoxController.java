package com.pmt.health.controllers;

import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.Property;
import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

import java.io.IOException;

import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;


public class FirefoxController extends DeviceController {


    /**
     * Default Constructor. Initialize variables
     */
    public FirefoxController() {
        device = Device.FIREFOX;
        log = Logger.getLogger(FirefoxController.class);
        configuration = new Configuration(this);
    }


    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    @Override
    public DesiredCapabilities getDeviceCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities = getDeviceDetails(capabilities);
        return capabilities;
    }


    /**
     * Create a deviceController driven by FirefoxDriver for a browser. If the browser has headless
     * parameters or proxy parameters associated, those will be configured here
     *
     * @param scenario - Provide the Cucumber scenario, to associate with the driver
     */
    @Override
    public void setupController(Scenario scenario) throws IOException {
        if (!scenario.getSourceTagNames().contains("@api")) {
            FirefoxDriverManager.getInstance(FIREFOX).forceCache().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            DesiredCapabilities capabilities = getDeviceCapabilities();
            if (Property.isHeadless()) {
                firefoxOptions.setHeadless(true);
                firefoxOptions.addArguments("--window-size=1920,1080");
                firefoxOptions.addArguments("--mute-audio");
            }
            if (getProxy() != null) {
                firefoxOptions.setProxy(getProxy());
            }
            firefoxOptions = firefoxOptions.merge(capabilities);
            this.driver = new FirefoxDriver(firefoxOptions);
        }
        // start and setup our logging
        setupLogging(scenario);
    }
}
