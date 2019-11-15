package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.testng.log4testng.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AvailabilityPage {

    private final App app;
    private final WebbElement hoBlockUpdate;
    private final WebbElement spinner;
    private final WebbElement availabilityTab;
    private final WebbElement calendarHeaderText;
    private final WebbElement currentDay;
    private final WebbElement addBlockBtn;
    private final WebbElement startTimeDropDown;
    private final WebbElement endTimeDropDown;
    private final WebbElement time12am;
    private final WebbElement time1145pm;
    private final WebbElement concurrentInput;
    private final WebbElement customSettingSaveBtn;
    private final WebbElement customHrName;
    private final WebbElement text12amTo1145pm;
    private final WebbElement text8amTo4pm;
    private final WebbElement text5amTo11pm;
    private final WebbElement time11pm;
    private final WebbElement time5am;
    private final WebbElement calendarTab;
    private final WebbElement timeOpenBtn;
    private final WebbElement tomorrowDiv;
    private final WebbElement editBtn;
    private final WebbElement deleteBtn;
    private final WebbElement continueBtn;
    private final WebbElement closedDate;
    private static final String AMERICA_CHICAGO = "America/Chicago";
    Logger log = Logger.getLogger(AvailabilityPage.class);
    private User user;

    public AvailabilityPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.hoBlockUpdate = app.newElement(LocatorType.XPATH, "//button[contains(text(),'update')]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.availabilityTab = app.newElement(LocatorType.CSS, "a[href='/scheduling/availability']");
        this.calendarTab = app.newElement(LocatorType.CSS, "a[href='/scheduling/calendar']");
        this.calendarHeaderText = app.newElement(LocatorType.CSS, "div[class='calendar-header-text']");
        this.currentDay = app.newElement(LocatorType.CSS, "div[class*='calendar-current-day active']");
        this.addBlockBtn = app.newElement(LocatorType.CSS, "div[class*='add-block-button'] button");
        this.startTimeDropDown = app.newElement(LocatorType.CSS, "div[class*='start-time-select']");
        this.endTimeDropDown = app.newElement(LocatorType.CSS, "div[class*='end-time-select']");
        this.time12am = app.newElement(LocatorType.XPATH, "//div[contains(@class,'Select-option')][text()='12:00 AM']");
        this.time11pm = app.newElement(LocatorType.XPATH, "//div[contains(@class,'Select-option')][text()='11:00 PM']");
        this.time5am = app.newElement(LocatorType.XPATH, "//div[contains(@class,'Select-option')][text()='05:00 AM']");
        this.time1145pm = app.newElement(LocatorType.XPATH, "//div[contains(@class,'Select-option')][text()='11:45 PM']");
        this.concurrentInput = app.newElement(LocatorType.CSS, "input[class='concurrent-input form-control']");
        this.customSettingSaveBtn = app.newElement(LocatorType.CSS, "button[class*='button-save']");
        this.customHrName = app.newElement(LocatorType.CSS, "input[class*='name-block-time']");
        this.text12amTo1145pm = app.newElement(LocatorType.XPATH, "//div[contains(@class,'calendar-current-day')]/parent::div/following::div//div[text()='12:00 AM TO 11:45 PM']");
        this.text8amTo4pm = app.newElement(LocatorType.XPATH, "//div[contains(@class,'calendar-current-day')]/parent::div/following::div//div[text()='08:00 AM TO 04:00 PM']");
        this.text5amTo11pm = app.newElement(LocatorType.XPATH, "//div[contains(@class,'calendar-current-day')]/parent::div/following::div//div[text()='05:00 AM TO 11:00 PM']");
        this.timeOpenBtn = app.newElement(LocatorType.CSS, "button[class*='time-open']");
        this.tomorrowDiv = app.newElement(LocatorType.XPATH, "//div[contains(@class,'calendar-current-day')]/parent::div/parent::td/following::div[1]");
        this.closedDate = app.newElement(LocatorType.XPATH, "//div[contains(@class,'calendar-current-day')]/parent::div/following-sibling::div[contains(@class,'closed-dates')]");
        this.editBtn = app.newElement(LocatorType.XPATH, "//button[contains(@class,'control-setting-block-time')][text()='edit']");
        this.deleteBtn = app.newElement(LocatorType.XPATH, "//button[contains(@class,'control-setting-block-time')][text()='delete']");
        this.continueBtn = app.newElement(LocatorType.XPATH, "//button[text()='Continue']");
    }

    private void setCustomHrName() {
        customHrName.type("Custom");
    }

    public void clickAvailabilityTab() {
        availabilityTab.waitFor().enabled();
        availabilityTab.click();
        spinner.waitFor().notDisplayed();
    }

    public void assertCurrentMonthOnAvailabilityPage() {
        DateFormat sdf = new SimpleDateFormat("MMMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        String expectDate = sdf.format(calendar.getTime());

        String action = "Assert Current Month On Availability Page";
        calendarHeaderText.waitFor().displayed();
        if (expectDate.equalsIgnoreCase(calendarHeaderText.get().text())) {
            app.getReporter().pass(action, expectDate, calendarHeaderText.get().text());
        } else {
            app.getReporter().fail(action, expectDate, calendarHeaderText.get().text());
        }
    }

    public void assertCurrentDayHighlighted() {
        DateFormat sdf = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(AMERICA_CHICAGO));
        String expectDay = sdf.format(calendar.getTime());
        String action = "Assert Highlighted day On Availability Page";
        currentDay.waitFor().displayed();
        if (expectDay.equals(currentDay.get().text())) {
            app.getReporter().pass(action, expectDay, currentDay.get().text());
        } else {
            app.getReporter().fail(action, expectDay, currentDay.get().text());
        }
    }

    public void clickCurrentDayOnAvailabilityPage() {
        currentDay.waitFor().enabled();
        currentDay.click();
    }

    public void addHourOfOperation() {
        addBlockBtn.waitFor().enabled();
        addBlockBtn.click();
        setCustomHrName();
        startTimeDropDown.click();
        time12am.click();
        endTimeDropDown.click();
        time1145pm.click();
        concurrentInput.type("3");
        hoBlockUpdate.click();
        customSettingSaveBtn.waitFor().enabled();
        customSettingSaveBtn.click();
        spinner.waitFor().notDisplayed();
    }

    public void verifyNewHoursOfOperation() {
        text12amTo1145pm.waitFor().displayed();
        calendarTab.click();
        spinner.waitFor().notDisplayed();
        String action = "Assert number of time open button On Calendar Day View after adding hours of operation";
        String expectNumber = "285";
        timeOpenBtn.waitFor().displayed();
        String actualNumber = "" + timeOpenBtn.getWebElements().size();
        if (expectNumber.equals("" + actualNumber)) {
            app.getReporter().pass(action, expectNumber, actualNumber);
        } else {
            app.getReporter().fail(action, expectNumber, actualNumber);
        }
    }

    public void verifyCustomHoursOfOperationAddedViaAPI() {
        text8amTo4pm.waitFor().displayed();
    }

    public void clickTomorrowDiv() {
        tomorrowDiv.waitFor().displayed();
        tomorrowDiv.click();
    }

    public void editHoursOfOperation() {
        editBtn.click();
        startTimeDropDown.click();
        time5am.click();
        endTimeDropDown.click();
        time11pm.click();
        hoBlockUpdate.click();
        if (!"0".equals("" + continueBtn.getWebElements().size())) {
            continueBtn.click();
        }
        customSettingSaveBtn.waitFor().enabled();
        customSettingSaveBtn.click();
        spinner.waitFor().notDisplayed();
    }

    public void verifyHoursOfOperationAfterEditing() {
        text5amTo11pm.waitFor().displayed();
    }

    public void deleteHoursOfOperation() {
        deleteBtn.waitFor().enabled();
        deleteBtn.click();
        customSettingSaveBtn.waitFor().enabled();
        customSettingSaveBtn.click();
        spinner.waitFor().notDisplayed();
    }

    public void verifyClosedSite() {
        closedDate.waitFor().displayed();
    }
}
