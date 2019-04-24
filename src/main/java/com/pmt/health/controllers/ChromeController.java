package com.pmt.health.controllers;

import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.Property;
import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

import java.io.IOException;


public class ChromeController extends DeviceController {


    /**
     * Default Constructor. Initialize variables
     */
    public ChromeController() {
        device = Device.CHROME;
        log = Logger.getLogger(ChromeController.class);
        configuration = new Configuration(this);
    }

    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    @Override
    public DesiredCapabilities getDeviceCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        //maximum test duration(3 hours) for longest forms to run on Sauce Labs
        capabilities.setCapability("maxDuration", "10800");
        capabilities = getDeviceDetails(capabilities);
        return capabilities;
    }

    /**
     * Create a deviceController driven by ChromeDriver for a browser. If the browser has headless
     * parameters or proxy parameters associated, those will be configured here
     *
     * @param scenario - Provide the Cucumber scenario, to associate with the driver
     */
    @Override
    public void setupController(Scenario scenario) throws IOException {
        if (!scenario.getSourceTagNames().contains("@api")) {
            WebDriverManager.chromedriver().forceCache().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            DesiredCapabilities capabilities = getDeviceCapabilities();
            if (Property.isHeadless()) {
                chromeOptions.setHeadless(true);
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--mute-audio");
            }
            if (getProxy() != null) {
                chromeOptions.setProxy(getProxy());
            }
            chromeOptions = chromeOptions.merge(capabilities);
            this.driver = new ChromeDriver(chromeOptions);
        }
        // start and setup our logging
        setupLogging(scenario);
    }
}
