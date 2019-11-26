package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.application.selenified.Get;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.utilities.LocatorType;
import junit.framework.Assert;
import org.testng.log4testng.Logger;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportSchedulingPage {
    private static final Logger log = Logger.getLogger(Get.class);
    private final WebbElement reportMenu;
    private final WebbElement dateRangePicker;
    private final WebbElement dateRangeToday;
    private final WebbElement downloadButton;

    public ReportSchedulingPage(App app) {
        this.reportMenu = app.newElement(LocatorType.ID, "icon-Report");
        this.dateRangePicker = app.newElement(LocatorType.ID, "date-range-picker");
        this.downloadButton = app.newElement(LocatorType.ID, "btnDownloadReport");
        this.dateRangeToday = app.newElement(LocatorType.CSS, ".daterangepicker-content li:nth-of-type(1)");
    }

    public void clickReportMenu() {
        reportMenu.waitFor().enabled();
        reportMenu.click();
    }

    public void selectDateRange() {
        dateRangePicker.waitFor().enabled();
        dateRangePicker.click();
    }

    public void selectTodayFromDateRange() {
        dateRangeToday.waitFor().enabled();
        dateRangeToday.click();
    }

    public void downloadReport() {
        downloadButton.waitFor().enabled();
        downloadButton.click();
    }

    public static String findDirPath() throws IOException, InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String command = "find / -name ScheduleReport_" + dateFormat.format(date) + "_to_" + dateFormat.format(date) + ".csv";
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line = "";
        String output = "";

        while ((line = buf.readLine()) != null) {
            output += line;
        }
        return output;
    }
    public static String checkDownloadFile() throws IOException, InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String line = "";
        String cvsSplitBy = ",";
        String result = null;
        int countExistData = 0;
        int countLine = 0;
        String csvFile = findDirPath();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] report = line.split(cvsSplitBy);
                System.out.println("Site: " + report[0] + "Date Time: " + report[1]);
                if (!report[0].contains("TEST AUTOMATION SITE") || !report[1].contains(dateFormat.format(date) + " 02:00 PM")){
                    countExistData = countExistData + 1;
                }
                countLine = countLine + 1;
            }
        } catch (IOException e) {
            log.debug(e);
        }
        System.out.println("findPath: " + findDirPath());
        if (countExistData == 1 && countLine > 1){
            result = "PASSED";
        }
        else {
            result = "FAILED";
        }
        return result;
    }

    public void assertDownloadFile() throws IOException, InterruptedException {
        Assert.assertEquals(checkDownloadFile(), "PASSED");
    }


    public void latestFile() {
        String fileName = null;
        File dir = new File("/home/PMTAutomationFramework/");
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("NULL");
        }
        else {
            File lastModifiedFile = files[0];
            for (int i = 1; i < files.length; i++) {
                if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                    lastModifiedFile = files[i];
                    fileName = files[i].getName();
                }
            }
        }
        System.out.println("File moi nhat la: " + fileName);
    }

}

