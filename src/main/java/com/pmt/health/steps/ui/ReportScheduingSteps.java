package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AvailabilityPage;
import com.pmt.health.workflows.ReportSchedulingPage;
import cucumber.api.java.en.Then;

import java.io.IOException;


public class ReportScheduingSteps {

    private final DeviceController deviceController;
    private final ReportSchedulingPage reportSchedulingPage;
    private final AvailabilityPage availabilityPage;

    public ReportScheduingSteps(DeviceController deviceController, User user) {
        this.deviceController = deviceController;
        reportSchedulingPage = new ReportSchedulingPage(this.deviceController.getApp());
        availabilityPage = new AvailabilityPage(this.deviceController.getApp(), user);
    }

    @Then("^I am on Reports Scheduling tab. I can select default Today from date range and download the report$")
    public void selectDefaultTodayAndDownloadTheReport() throws InterruptedException {
        reportSchedulingPage.clickReportMenu();
        reportSchedulingPage.selectDateRange();
        reportSchedulingPage.selectTodayFromDateRange();
        reportSchedulingPage.downloadReport();
        // delete hours of operation
        availabilityPage.clickScheduleMenu();
        availabilityPage.clickAvailabilityTab();
        availabilityPage.clickCurrentDayOnAvailabilityPage();
        availabilityPage.deleteHoursOfOperation();
    }

    @Then("^Verify CSV file in filesystem$")
    public void verifyDownloadCSVFile() {
        reportSchedulingPage.latestFile();
    }


}
