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
    private final WebbElement assignToDropDownBtn;
    private final WebbElement hamburgerMenu;
    private final WebbElement myAppointmentCheckBox;
    private final WebbElement appointmentEventBlock;
    private final WebbElement nextDayButton;
    private final WebbElement spinner;
    private final WebbElement calendarMode;
    private final WebbElement calendarSwitcherRight;
    private final WebbElement firstTimeBlock;
    private final WebbElement selectedSchedulerSite;
    private final WebbElement eventBlock;
    private final WebbElement activeDate;
    private final WebbElement showDetailBtn;
    private final WebbElement calendarModeDropDown;
    private final WebbElement calendarWeekMode;
    private final WebbElement prospectEmail;
    private String calendarViewType;



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
        this.activeDate = app.newElement(LocatorType.CSS, "tr > td > div.rdtDay.rdtActive");
        this.time8am = app.newElement(LocatorType.XPATH, "//div[contains(text(),'08:00 AM')]");
        this.scheduleButton = app.newElement(LocatorType.CSS, "button[class='btn btn-default btn-schedule pull-right btn btn-default']");
        this.messageOfSuccessAppointment = app.newElement(LocatorType.XPATH, "//div[contains(text(), 'Appointment successfully created.')]");
        this.warning = app.newElement(LocatorType.CSS, "p[class='warning-text']");
        this.ignoreWarning = app.newElement(LocatorType.CSS, "button[class='btn-ghost btn-ghost-primary btn btn-default']");
        this.calendarMode = app.newElement(LocatorType.CSS, "div[class*='calendar-mode-selector'] span[class='Select-value-label']");
        this.calendarSwitcherRight = app.newElement(LocatorType.CSS, "svg[data-icon='chevron-right']");
        this.firstTimeBlock = app.newElement(LocatorType.CSS, "div[class='appt-column']:first-of-type button[class*='time-open']:last-of-type");
        this.selectedSchedulerSite = app.newElement(LocatorType.CSS, "input[name='site']");
        this.eventBlock = app.newElement(LocatorType.CSS, "button[class*='event-block-content']");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.showDetailBtn = app.newElement(LocatorType.XPATH, "//button[text()='Show Details']");
        this.assignToDropDownBtn = app.newElement(LocatorType.XPATH, "//label[text()=\"Assign to\"]/..//span[@class=\"Select-arrow-zone\"]");
        this.hamburgerMenu = app.newElement(LocatorType.CSS, "svg[class*=\"sliders\"]");
        this.myAppointmentCheckBox = app.newElement(LocatorType.CSS, "span[class*=\"user-appointments\"] span[class=\"colored-checkbox-checkmark\"]");
        this.appointmentEventBlock = app.newElement(LocatorType.CSS, "div[class=\"event-block \"]");
        this.nextDayButton = app.newElement(LocatorType.CSS, "button[class*=calendarSwitcher-navigation-index__right]");
        this.calendarWeekMode = app.newElement(LocatorType.CSS, "div[aria-label='Week']");
        this.calendarModeDropDown = app.newElement(LocatorType.CSS, "div[class*='calendar-mode-selector'] span[class='Select-arrow-zone']");
        this.prospectEmail = app.newElement(LocatorType.CSS, "div[class=\"existing-participant-email\"]");
    }

    /**
     * waits until success message is displayed
     */
    public void assertSuccessAppointmentMessage() {
        messageOfSuccessAppointment.waitFor().displayed();
    }

    /**
     * clicks on schedule appointment button
     */
    public void scheduleAppointment() {
        scheduleButton.click();
    }

    /**
     * clicks on 8:00 AM button
     */
    public void selectTime() {
        time8am.click();
    }

    /**
     * checks today's date
     * parsing attribute to set date for a next day
     */
    public void selectDate() {
        String today = todayDate.get().attribute(DATA_VALUE);
        int tomorrow = Integer.parseInt(today) + 1;
        WebbElement tomorrowDate = app.newElement(LocatorType.XPATH, "//tr/td[@" + DATA_VALUE + "='" + tomorrow + "']");
        tomorrowDate.click();
    }

    /**
     * types string into notes field
     */
    public void addAppointmentNotes() {
        appointmentNotes.type("test appointment");
    }

    /**
     * clicks on the language dropdown
     * selects English language of current dropdown
     */
    public void selectLanguage() {
        languagesDropDown.waitFor().displayed();
        languagesDropDown.click();
        WebbElement selectLanguage = app.newElement(LocatorType.CSS, "div[aria-label='English']");
        selectLanguage.click();
    }

    /**
     * clicks on the next button
     */
    public void completeParticipantInfo() {
        nextButton.click();
    }

    /**
     * checks if the back button is present
     * and if it is, clicks on the next button
     */
    public void completeAppointmentDetails() {
        if (backButton.is().present())
            nextButton.click();
    }

    /**
     * clicks on the new appointment button
     */
    public void addNewAppointment() {
        newAppointmentButton.click();
    }

    /**
     * types parameterized string into email address field
     *
     * @param emailAddress string parameter
     */
    public void enterEmailAddress(String emailAddress) {
        emailAddressInput.type(emailAddress);
        if ("".equals(emailAddress)) {
            emailAddressInput.type("a");
            emailAddressInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * types parameter into phone number field
     *
     * @param phoneNumber long parameter
     */
    public void enterPhoneNumber(String phoneNumber) {
        phoneNumberInput.type(phoneNumber);
        if ("".equals(phoneNumber)) {
            phoneNumberInput.type("a");
            phoneNumberInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * types parameter into dob field
     *
     * @param dateOfBirth date parameter
     */
    public void enterDateOfBirth(String dateOfBirth) {
        dateOfBirthInput.type(dateOfBirth);
        if ("".equals(dateOfBirth)) {
            dateOfBirthInput.type("a");
            dateOfBirthInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * types parameter into last name field
     *
     * @param lastName string parameter
     */
    public void enterLastName(String lastName) {
        lastNameInput.type(lastName);
        if ("".equals(lastName)) {
            lastNameInput.type("a");
            lastNameInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * types parameter into first name field
     *
     * @param firstName string parameter
     */
    public void enterFirstName(String firstName) {
        firstNameInput.type(firstName);
        if ("".equals(firstName)) {
            firstNameInput.type("a");
            firstNameInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * clicks on the delete button
     * if delete confirmation frame pops up
     * clicks on the confirm button
     */
    public void deleteCustomHours() {
        deleteButton.waitFor().displayed();
        deleteButton.click();
        deleteConfirm.waitFor().displayed();
        deleteConfirm.click();
    }

    /**
     * clicks on the button to update information filled in HOO form
     */
    public void hoFormUpdate() {
        hoFormUpdate.click();
        if (warning.is().present() && warning.waitFor().displayed()) {
            ignoreWarning.click();
        }
    }

    /**
     * sets time frame "from-to" for the time blocks of HOO
     */
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

    /**
     * Sets up hours of operation frame
     */
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
            dayCheck.assertState().enabled();
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

    /**
     * clicks on HOO dropdown
     * selects create new custom
     */
    public void customHoursOfOperation() {
        hoursOfOperation.click();
        WebbElement customHrSelection = app.newElement(LocatorType.CSS, "span.glyphicon.glyphicon-plus");
        customHrSelection.click();
    }

    /**
     * deletes "Custom" hours of operation
     */
    public void customHoursOfOperationDelete() {
        hoursOfOperation.click();
        WebbElement customHrSelection = app.newElement(LocatorType.CSS, "div[title=Custom]");
        customHrSelection.click();
        deleteCustomHours();
    }

    /**
     * selects day with parametrized attribute
     *
     * @param day passing string parameter
     */
    public void appNotice(String day) {
        appointmentNotice.waitFor().displayed();
        appointmentNotice.click();
        WebbElement appNoticeSelection = app.newElement(LocatorType.CSS, "div[aria-label='" + day + "']");
        appNoticeSelection.click();
    }

    /**
     * sets target parameter
     *
     * @param target target parameter
     */
    public void setTarget(String target) {
        editTargetField.clear();
        editTargetField.type(target);
    }

    /**
     * checks if toggle on/off button is true or false to activate or deactivate it
     *
     * @param toggle toggle on/off button parameter
     */
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

    /**
     * clicks on the pencil/edit button
     */
    public void editPage() {
        pencilButton.click();
    }

    /**
     * clicks on the save button
     */
    public void saveChanges() {
        saveButton.click();
    }

    /**
     * checks if current title is displayed
     */
    public void assertTitle() {
        siteSettingsHeading.waitFor().displayed();
        siteSettingsHeading.assertState().displayed();
    }

    /**
     * checks if the success massage is displayed
     */
    public void assertMessage() {
        messageOfChanges.waitFor().displayed();
    }

    /**
     * Assign appointment to a user
     */
    public void assignToUser(String assignedUser) {
        WebbElement assignedToUser = app.newElement(LocatorType.XPATH, "//div[@class=\"Select-option\" and contains(text(), \"" + assignedUser + "\")]");
        assignToDropDownBtn.click();
        assignedToUser.click();
    }

    /**
     * Enable the checkbox "Show My Appointment"
     */
    public void showMyAppointment() {
        hamburgerMenu.click();
        myAppointmentCheckBox.click();
    }

    /**
     * Click forward arrow to go to next day
     */
    public void goToNextDay() {
        nextDayButton.click();
        spinner.waitFor().notPresent();
    }

    /**
     * Assert "Show My Appointment" function
     */
    public void assertShowMyAppointment() {
        appointmentEventBlock.assertState().displayed();
    }
  
    public void assertToggleAcceptingAppointments(Boolean onOff) {
        WebbElement toggle = app.newElement(LocatorType.CSS, "input[" + DATA_VALUE + "=" + onOff + "]");
        toggle.assertState().present();
    }

    public void assertDailyGoal(String goalNumber) {
        WebbElement goal = app.newElement(LocatorType.XPATH, "//input[@class='edit-goal form-control' and @value='" + goalNumber + "']");
        goal.assertState().present();
    }

    public void assertDailyTarget(String targetNumber) {
        WebbElement target = app.newElement(LocatorType.XPATH, "//input[@class='edit-target form-control' and @value='" + targetNumber + "']");
        target.assertState().present();
    }

    public void assertDefaultPMB() {
        WebbElement pmB = app.newElement(LocatorType.XPATH, "//input[@class='input-duration form-control' and @value='0:30']");
        pmB.assertState().present();
    }

    public void assertDefaultFullEnrollment() {
        WebbElement fullEnrolment = app.newElement(LocatorType.XPATH, "//input[@class='input-duration form-control' and @value='1:30']");
        fullEnrolment.assertState().present();
    }

    public void assertMinimumAppointmentNotice(String days) {
        appointmentNotice.click();
        WebbElement appNoticeSelection = app.newElement(LocatorType.CSS, "div[aria-label='" + days + "']");
        appNoticeSelection.assertState().present();
    }

    public void assertCustomHoursOfOperations() {
        hoursOfOperation.click();
        WebbElement customHrSelection = app.newElement(LocatorType.CSS, "div[title=Custom]");
        customHrSelection.assertState().present();
    }

    public void assertSchedulerSite(String site) {
        selectedSchedulerSite.assertEquals().value(site);
    }

    private void switchRightOnCalendar() {
        calendarSwitcherRight.click();
    }

    public void doubleClickOnTimeBlockToCreateNewAppointment() {
        firstTimeBlock.doubleClick();
    }

    public void switchToFindFirstAvailableTimeBlock() {
        switchRightOnCalendar();
        spinner.waitFor().notDisplayed();
        while (eventBlock.get().matchCount() > 0) {
            switchRightOnCalendar();
            spinner.waitFor().notDisplayed();
        }
    }

    public void selectActiveDate() {
        WebbElement activeDateBtn = app.newElement(LocatorType.XPATH, "//tr/td[@" + DATA_VALUE + "='" + activeDate.get().attribute(DATA_VALUE) + "']");
        activeDateBtn.click();
    }

    public void assertCreatedAppointment() {
        eventBlock.waitFor().displayed();
        eventBlock.doubleClick();
        if("Week".equals(calendarViewType)) {
            spinner.waitFor().notDisplayed();
            eventBlock.doubleClick();
        }
        showDetailBtn.waitFor().displayed();
        showDetailBtn.click();
        prospectEmail.assertContains().text(user.getParticipantEmail());
    }

    public void switchCalendarToWeekView() {
        calendarModeDropDown.waitFor().displayed();
        calendarModeDropDown.click();
        calendarWeekMode.click();
    }

    public void assertCalendarPage(String viewType) {
        calendarViewType = viewType;
        calendarMode.waitFor().displayed();
        calendarMode.assertEquals().text(viewType);
    }
}

