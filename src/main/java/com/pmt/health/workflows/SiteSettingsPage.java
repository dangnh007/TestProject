package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;

public class SiteSettingsPage {

    private final App app;

    private static final String DATA_VALUE = "data-value";

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
    private final WebbElement newAppointmentButton;
    private final WebbElement firstNameInput;
    private final WebbElement lastNameInput;
    private final WebbElement dateOfBirthInput;
    private final WebbElement phoneNumberInput;
    private final WebbElement emailAddressInput;
    private final WebbElement nextButton;
    private final WebbElement backButton;
    private final WebbElement languagesDropDown;
    private final WebbElement appointmentNotes;
    private final WebbElement todayDate;
    private final WebbElement time8am;
    private final WebbElement scheduleButton;
    private final WebbElement messageOfSuccessAppointment;
    private final WebbElement warning;
    private final WebbElement ignoreWarning;

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
        this.appointmentNotice = app.newElement(LocatorType.CSS, "div[class='Select has-value Select--single']");
        this.hoursOfOperation = app.newElement(LocatorType.XPATH, "//button[contains(text(), 'Default Hours of Operation')]");
        this.customHrName = app.newElement(LocatorType.CSS, "input.week-working-time-input.form-control");
        this.timeFrom = app.newElement(LocatorType.XPATH, "(//span[@class='Select-arrow'])[3]");
        this.timeTo = app.newElement(LocatorType.XPATH, "(//span[@class='Select-arrow'])[4]");
        this.hoBlockUpdate = app.newElement(LocatorType.XPATH, "//button[contains(text(),'update')]");
        this.hoFormUpdate = app.newElement(LocatorType.XPATH, "//strong[contains(text(),'Update')]");
        this.deleteButton = app.newElement(LocatorType.XPATH, "//strong[contains(text(), 'Delete')]");
        this.deleteConfirm = app.newElement(LocatorType.XPATH, "//button[contains(text(), 'Yes, Delete Hours')]");
        this.newAppointmentButton = app.newElement(LocatorType.CSS, "button[class='button button-default new-appointment-btn btn btn-default']");
        this.firstNameInput = app.newElement(LocatorType.CSS, "input[label='First Name']");
        this.lastNameInput = app.newElement(LocatorType.CSS, "input[label='Last Name']");
        this.dateOfBirthInput = app.newElement(LocatorType.CSS, "input[label='DOB (MM/DD/YYYY)']");
        this.phoneNumberInput = app.newElement(LocatorType.CSS, "input[label='Phone Number']");
        this.emailAddressInput = app.newElement(LocatorType.CSS, "input[label='Email Address']");
        this.nextButton = app.newElement(LocatorType.CSS, "button[class='btn-next btn btn-default']");
        this.backButton = app.newElement(LocatorType.CSS, "button[class='btn-back btn btn-default']");
        this.languagesDropDown = app.newElement(LocatorType.CSS, "div[class='language-control']");
        this.appointmentNotes = app.newElement(LocatorType.CSS, "textarea[name='notes']");
        this.todayDate = app.newElement(LocatorType.CSS, "tr > td > div.rdtDay.rdtToday");
        this.time8am = app.newElement(LocatorType.XPATH, "//div[contains(text(),'08:00 AM')]");
        this.scheduleButton = app.newElement(LocatorType.CSS, "button[class='btn btn-default btn-schedule pull-right btn btn-default']");
        this.messageOfSuccessAppointment = app.newElement(LocatorType.XPATH, "//div[contains(text(), 'Appointment successfully created.')]");
        this.warning = app.newElement(LocatorType.CSS, "p[class='warning-text']");
        this.ignoreWarning = app.newElement(LocatorType.CSS, "button[class='btn-ghost btn-ghost-primary btn btn-default']");
    }


    public void assertSuccessAppointmentMessage() {
        messageOfSuccessAppointment.waitFor().displayed();
    }

    public void scheduleAppointment() {
        scheduleButton.click();
    }

    public void selectTime() {
        time8am.click();
    }

    public void selectDate() {
        String today = todayDate.get().attribute(DATA_VALUE);
        int tomorrow = Integer.parseInt(today) + 1;
        WebbElement tomorrowDate = app.newElement(LocatorType.XPATH, "//tr/td[@" + DATA_VALUE + "='" + tomorrow + "']");
        tomorrowDate.click();
    }

    public void addAppointmentNotes() {
        appointmentNotes.type("test appointment");
    }

    public void selectLanguage() {
        languagesDropDown.waitFor().displayed();
        languagesDropDown.click();
        WebbElement selectLanguage = app.newElement(LocatorType.CSS, "div[aria-label='English']");
        selectLanguage.click();
    }

    public void completeParticipantInfo() {
        nextButton.click();
    }

    public void completeAppointmentDetails() {
        if (backButton.is().present())
            nextButton.click();
    }

    public void addNewAppointment() {
        newAppointmentButton.click();
    }

    public void enterEmailAddress(String emailAddress) {
        emailAddressInput.type(emailAddress);
        if ("".equals(emailAddress)) {
            emailAddressInput.type("a");
            emailAddressInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterPhoneNumber(String phoneNumber) {
        phoneNumberInput.type(phoneNumber);
        if ("".equals(phoneNumber)) {
            phoneNumberInput.type("a");
            phoneNumberInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterDateOfBirth(String dateOfBirth) {
        dateOfBirthInput.type(dateOfBirth);
        if ("".equals(dateOfBirth)) {
            dateOfBirthInput.type("a");
            dateOfBirthInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterLastName(String lastName) {
        lastNameInput.type(lastName);
        if ("".equals(lastName)) {
            lastNameInput.type("a");
            lastNameInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterFirstName(String firstName) {
        firstNameInput.type(firstName);
        if ("".equals(firstName)) {
            firstNameInput.type("a");
            firstNameInput.type(Keys.BACK_SPACE);
        }
    }

    public void deleteCustomHours() {
        deleteButton.waitFor().displayed();
        deleteButton.click();
        deleteConfirm.waitFor().displayed();
        deleteConfirm.click();
    }

    public void hoFormUpdate() {
        hoFormUpdate.click();
        if(warning.waitFor().displayed() && warning.is().present()) {
            ignoreWarning.click();
        }
    }

    public void setCustomTime() {
        this.timeFrom.click();
        WebbElement amTime = app.newElement(LocatorType.CSS, "div[aria-label='08:00 AM']");
        amTime.click();
        this.timeTo.click();
        WebbElement pmTime = app.newElement(LocatorType.CSS, "div[aria-label='04:00 PM']");
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
        appointmentNotice.waitFor().displayed();
        appointmentNotice.click();
        WebbElement appNoticeSelection = app.newElement(LocatorType.CSS, "div[aria-label='" + day + "']");
        appNoticeSelection.click();
    }

    public void setTarget(String target) {
        editTargetField.clear();
        editTargetField.type(target);
    }

    public void toggleOnOff(String toggle) {
        WebbElement onOff = app.newElement(LocatorType.CSS, "input[" + DATA_VALUE + "]");
        onOff.waitFor().present();
        if ("on".equalsIgnoreCase(toggle) && "false".equalsIgnoreCase(onOff.get().attribute(DATA_VALUE))) {
            toggleOnOff.click();
        }
        if ("off".equalsIgnoreCase(toggle) && "true".equalsIgnoreCase(onOff.get().attribute(DATA_VALUE))) {
            toggleOnOff.click();
        }
    }

    public void editPage() {
        pencilButton.click();
    }

    public void saveChanges() {
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

