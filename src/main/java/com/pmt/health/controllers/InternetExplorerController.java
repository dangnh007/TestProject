package com.pmt.health.controllers;

import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Device;
import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

import java.io.IOException;

import static io.github.bonigarcia.wdm.DriverManagerType.IEXPLORER;


public class InternetExplorerController extends DeviceController {


    /**
     * Default Constructor. Initialize variables
     */
    public InternetExplorerController() {
        device = Device.INTERNETEXPLORER;
        log = Logger.getLogger(InternetExplorerController.class);
        configuration = new Configuration(this);
    }

    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    @Override
    public DesiredCapabilities getDeviceCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
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
            InternetExplorerDriverManager.getInstance(IEXPLORER).forceCache().setup();
            DesiredCapabilities capabilities = getDeviceCapabilities();
            if (getProxy() != null) {
                capabilities.setCapability(CapabilityType.PROXY, getProxy());
            }
            this.driver = new InternetExplorerDriver(capabilities);
        }
        // start and setup our logging
        setupLogging(scenario);
    }
}
