package com.pmt.health.controllers;

import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Device;
import cucumber.api.Scenario;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

import java.io.IOException;

/**
 * This class controls a headless chrome browser
 *
 * @author Coveros
 */
public class HtmlUnitController extends DeviceController {

    /**
     * Default Constructor. Initialize variables
     */
    public HtmlUnitController() {
        device = Device.HTMLUNIT;
        log = Logger.getLogger(HtmlUnitController.class);
    }

    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    @Override
    public DesiredCapabilities getDeviceCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
        capabilities = getDeviceDetails(capabilities);
        return capabilities;
    }


    /**
     * Initializes scenario
     *
     * @param scenario A Scenario object
     */
    @Override
    public void setupController(Scenario scenario) throws IOException {
        DesiredCapabilities capabilities = getDeviceCapabilities();
        if (getProxy() != null) {
            capabilities.setCapability(CapabilityType.PROXY, getProxy());
        }
        this.driver = new HtmlUnitDriver(capabilities);
        // start and setup our logging
        setupLogging(scenario);
    }
}
