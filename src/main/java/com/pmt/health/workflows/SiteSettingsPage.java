package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;

public class SiteSettingsPage {
    private final App app;
    private final WebbElement siteSettingsHeading;
    private final WebbElement toggleOnOff;
    private final WebbElement pencilButton;
    private final WebbElement saveButton;
    private final WebbElement editTargetField;
    private final WebbElement messageOfChanges;
    private final WebbElement appointmentNotice;
    private final WebbElement hoursOfOperation;
    private final WebbElement customHrName;
    private final WebbElement timeFrom;
    private final WebbElement timeTo;
    private final WebbElement hoBlockUpdate;
    private final WebbElement hoFormUpdate;
    private final WebbElement deleteButton;
    private final WebbElement deleteConfirm;


    Logger log = Logger.getLogger(SiteSettingsPage.class);
    private User user;

    public SiteSettingsPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.siteSettingsHeading = app.newElement(LocatorType.XPATH, "//h1[text()='Settings']");
        this.toggleOnOff = app.newElement(LocatorType.CLASSNAME, "switch");
        this.pencilButton = app.newElement(LocatorType.CLASSNAME, "pencil-button");
        this.saveButton = app.newElement(LocatorType.ID, "saveBtn");
        this.editTargetField = app.newElement(LocatorType.CSS, "input.edit-target.form-control");
        this.messageOfChanges = app.newElement(LocatorType.XPATH, "//div[contains(text(), 'Your changes have been saved.')]");
        this.appointmentNotice = app.newElement(LocatorType.XPATH, "(//span[@class='Select-arrow'])[2]");
        this.hoursOfOperation = app.newElement(LocatorType.XPATH, "//button[contains(text(), 'Default Hours of Operation')]");
        this.customHrName = app.newElement(LocatorType.CSS, "input.week-working-time-input.form-control");
        this.timeFrom = app.newElement(LocatorType.XPATH, "(//span[@class='Select-arrow'])[3]");
        this.timeTo = app.newElement(LocatorType.XPATH, "(//span[@class='Select-arrow'])[4]");
        this.hoBlockUpdate = app.newElement(LocatorType.XPATH, "//button[contains(text(),'update')]");
        this.hoFormUpdate = app.newElement(LocatorType.XPATH, "//strong[contains(text(),'Update')]");
        this.deleteButton = app.newElement(LocatorType.XPATH, "//strong[contains(text(), 'Delete')]");
        this.deleteConfirm = app.newElement(LocatorType.XPATH, "//button[contains(text(), 'Yes, Delete Hours')]");
    }

    public void deleteCustomHours() {
        deleteButton.waitFor().displayed();
        deleteButton.click();
        deleteConfirm.waitFor().displayed();
        deleteConfirm.click();
    }

    public void hoFormUpdate() {
        hoFormUpdate.click();
    }

    public void setCustomTime() {
        this.timeFrom.click();
        WebbElement amTime = app.newElement(LocatorType.XPATH, "//div[@aria-label='08:00 AM']");
        amTime.click();
        this.timeTo.click();
        WebbElement pmTime = app.newElement(LocatorType.XPATH, "//div[@aria-label='04:00 PM']");
        pmTime.click();
    }

    public void setCustomHrName() {
        customHrName.type("Custom");
    }

    public void checkDay() {
        String pContains = "//p[contains(text(), ";
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        for (String day : days) {
            WebbElement dayCheck = app.newElement(LocatorType.XPATH, pContains + "\"" + day + "\")]/../../../../..//span");
            dayCheck.click();
            WebbElement addBlockButton = app.newElement(LocatorType.XPATH, pContains + "\"" + day + "\")]/following::button[contains(text(), 'Add Block')][1]");
            addBlockButton.click();
            WebbElement blockLabel = app.newElement(LocatorType.XPATH, pContains + "\"" + day + "\")]/following::input[@placeholder='Block Label']");
            blockLabel.type("Update");
            WebbElement concurrentAp = app.newElement(LocatorType.XPATH, pContains + "\"" + day + "\")]/following::input[@class='concurrent-input form-control']");
            concurrentAp.type("3");
            setCustomTime();
            hoBlockUpdate.click();
        }
    }

    public void customHoursOfOperation() {
        hoursOfOperation.click();
        WebbElement customHrSelection = app.newElement(LocatorType.CSS, "span.glyphicon.glyphicon-plus");
        customHrSelection.click();
    }

    public void customHoursOfOperationDelete() {
        hoursOfOperation.click();
        WebbElement customHrSelection = app.newElement(LocatorType.CSS, "div[title=Custom]");
        customHrSelection.click();
        deleteCustomHours();
    }

    public void appNotice(String day) {
        appointmentNotice.click();
        WebbElement appNoticeSelection = app.newElement(LocatorType.XPATH, "//div[@aria-label='" + day + "']");
        appNoticeSelection.click();
    }

    public void setTarget(String target) {
        editTargetField.clear();
        editTargetField.type(target);
    }

    public void toggleOnOff() {
        toggleOnOff.click();
    }

    public void editPage() {
        pencilButton.click();
    }

    public void saveChanges() {
        saveButton.waitFor().displayed();
        saveButton.click();
    }

    public void assertTitle() {
        siteSettingsHeading.waitFor().displayed();
        siteSettingsHeading.assertState().displayed();
    }

    public void assertMessage() {
        messageOfChanges.waitFor().displayed();
        messageOfChanges.assertState().displayed();
    }
}

