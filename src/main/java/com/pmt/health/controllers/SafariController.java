package com.pmt.health.controllers;

import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Device;
import cucumber.api.Scenario;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.log4testng.Logger;

import java.io.IOException;

public class SafariController extends DeviceController {

    /**
     * Default constructor. Initializes variables.
     */
    public SafariController() {
        device = Device.SAFARI;
        log = Logger.getLogger(SafariController.class);
        configuration = new Configuration(this);
    }

    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    @Override
    public DesiredCapabilities getDeviceCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        capabilities = getDeviceDetails(capabilities);
        return capabilities;
    }

    /**
     * Initializes this deviceController for this scenario
     *
     * @param scenario A Scenario object
     */
    @Override
    public void setupController(Scenario scenario) throws IOException {
        if (!scenario.getSourceTagNames().contains("@api")) {
            DesiredCapabilities capabilities = getDeviceCapabilities();
            if (getProxy() != null) {
                capabilities.setCapability(CapabilityType.PROXY, getProxy());
            }
            this.driver = new SafariDriver(capabilities);
        }
        // start and setup our logging
        setupLogging(scenario);
    }
}
