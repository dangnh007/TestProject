package com.pmt.health.controllers;

import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Device;
import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.OperaDriverManager;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;
import java.io.IOException;
import static io.github.bonigarcia.wdm.DriverManagerType.OPERA;

public class OperaController extends DeviceController {

    /**
     * Default Constructor. Initialize variables
     */
    public OperaController() {
        device = Device.OPERA;
        log = Logger.getLogger(OperaController.class);
        configuration = new Configuration(this);
    }

    /**
     * Initializes and retrieves the device capabilities.
     *
     * @return DesiredCapabilities object containing this device's requested capabilities
     */
    @Override
    public DesiredCapabilities getDeviceCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.opera();
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
        if (!scenario.getSourceTagNames().contains("@api")) {
            OperaDriverManager.getInstance(OPERA).forceCache().setup();
            DesiredCapabilities capabilities = getDeviceCapabilities();
            if (getProxy() != null) {
                capabilities.setCapability(CapabilityType.PROXY, getProxy());
            }
            this.driver = new OperaDriver(capabilities);
        }
        // start and setup our logging
        setupLogging(scenario);
    }
}
