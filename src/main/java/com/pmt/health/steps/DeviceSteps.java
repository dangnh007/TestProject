package com.pmt.health.steps;

import cucumber.api.java.en.When;
import org.springframework.context.annotation.Description;

import java.io.IOException;

public class DeviceSteps {

    protected DeviceController deviceController;

    public DeviceSteps(DeviceController deviceController) {
        this.deviceController = deviceController;
    }

    @Description("Waits certain number of seconds explicitly")
    @When("^I wait (\\d+) seconds$")
    public void iWaitFiveSeconds(int number) {
        deviceController.getApp().wait((double) number);
    }

    @Description("Enables/Disables the internet connection")
    @When("^I (enable|disable) the internet connection$")
    public void internetConnectionToggle(String option) {
        if ("disable".equals(option)) {
            try {
                Runtime.getRuntime().exec("networksetup -setairportpower en0 off");
            } catch (IOException e) {
                deviceController.log.info(e);
                try {
                    Runtime.getRuntime().exec("netsh wlan disconnect");
                } catch (IOException e1) {
                    deviceController.log.info(e1);
                }
            }
        } else {
            try {
                Runtime.getRuntime().exec("networksetup -setairportpower en0 on");
            } catch (IOException e) {
                deviceController.log.info(e);
                try {
                    Runtime.getRuntime().exec("netsh wlan connect");
                } catch (IOException e1) {
                    deviceController.log.info(e1);
                }
            }
        }
    }

}
