package com.pmt.health.interactions.application;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.Reporter;
import org.openqa.selenium.WebDriver;

public class AppFactory {

    private AppFactory() {
    }

    /**
     * This method instantiates and returns an App of the appropriate type for the Driver
     *
     * @param driver   The WebDriver to use
     * @param device   The Device flag
     * @param reporter The reporter in which to put the results
     * @return An App
     */
    public static App getApp(WebDriver driver, Device device, Reporter reporter) {
        return new WebApp(driver, device, reporter);
    }
}
