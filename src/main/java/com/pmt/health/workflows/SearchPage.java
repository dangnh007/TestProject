package com.pmt.health.workflows;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.log4testng.Logger;
import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.Prospect;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class SearchPage {

    private final App app;
    private final User user;
    private final Prospect prospect;
    private final WebbElement searchMenu;
    private final WebbElement emailSearchField;
    private final WebbElement searchedParticipantEmail;
    private final WebbElement searchButton;
    private final WebbElement createButtonFromSearchResult;
    private final WebbElement spinner;
    private final WebbElement viewButton;
    private final WebbElement viewButton1;
    private final WebbElement viewAppointmentDetailsText;
    private final WebbElement cancelAppointment;
    private final WebbElement cancelAppointmentConfirm;
    private final WebbElement editProspectButton;
    private final WebbElement inputFirstName;
    private final WebbElement inputLastName;
    private final WebbElement inputDateOfBirth;
    private final WebbElement inputPhoneNumberSearchProspect;
    private final WebbElement phoneNumberInput;
    private final WebbElement emailAddressInput;
    private final WebbElement saveProspectDetails;
    private final WebbElement languagesDropDown;
    private final WebbElement selectEnglishLanguage;
    private final WebbElement selectSpanishLanguage;
    private final WebbElement spanProspectAcceptReceiveEmail;
    private final WebbElement checkboxProspectAcceptReceiveEmail;
    private final WebbElement cancelProspectDetails;
    private final WebbElement backSchedulingSearchButton;
    private static final String FIELD_APPOINTMENT_INFO_PATTERN = "(//div[@class='form-group']//div[@class='col-sm-8']/p)";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String IS_SELECTED_CLASS = "is-selected";
    private static final Logger log = Logger.getLogger(SearchPage.class);
    
    public SearchPage(App app, User user, Prospect prospect) {
        this.app = app;
        this.user = user;
        this.prospect = prospect;
        this.searchMenu = app.newElement(LocatorType.CSS, "svg[class*=components-search]");
        this.emailSearchField = app.newElement(LocatorType.CSS, "input[name=emailAddress]");
        this.searchedParticipantEmail = app.newElement(LocatorType.CSS, "div[class=participant-emailAddress] div");
        this.searchButton = app.newElement(LocatorType.CSS, "button[class*=btn-primary]");
        this.createButtonFromSearchResult = app.newElement(LocatorType.CSS, "div[class=\"react-bs-table-container\"] button[class=\"button button-primary btn btn-primary\"]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.viewButton = app.newElement(LocatorType.XPATH, "//strong[text()='View']/parent::button");
        this.viewButton1 = app.newElement(LocatorType.CSS, "input[name=DangNguyen]");
        this.viewAppointmentDetailsText = app.newElement(LocatorType.XPATH, "//h5[text()='Future Appointment Details']");
        this.cancelAppointment = app.newElement(LocatorType.CSS, "button.cancel-btn.btn.btn-link");
        this.cancelAppointmentConfirm = app.newElement(LocatorType.CSS, "button.btn-cancel.btn.btn-default");
        this.editProspectButton = app.newElement(LocatorType.CSS, "button[class*=change-button]");
        this.inputFirstName = app.newElement(LocatorType.CSS, "input[name=firstName]");
        this.inputLastName = app.newElement(LocatorType.CSS, "input[name=lastName]");
        this.inputDateOfBirth = app.newElement(LocatorType.CSS, "input[name=dob]");
        this.inputPhoneNumberSearchProspect = app.newElement(LocatorType.CSS, "input[name=phoneNumber]");
        this.saveProspectDetails = app.newElement(LocatorType.CSS,
                "div.small-button-wrapper button[class*=btn-primary]");
        this.languagesDropDown = app.newElement(LocatorType.CSS, "div[class='language-control']");
        this.phoneNumberInput = app.newElement(LocatorType.CSS, "input[label='Phone Number']");
        this.emailAddressInput = app.newElement(LocatorType.CSS, "input[label='Email Address']");
        this.selectEnglishLanguage = app.newElement(LocatorType.CSS, "div[aria-label='English']");
        this.selectSpanishLanguage = app.newElement(LocatorType.CSS, "div[aria-label='Spanish']");
        this.spanProspectAcceptReceiveEmail = app.newElement(LocatorType.CSS, "label[class*=colored-checkbox-content]");
        this.checkboxProspectAcceptReceiveEmail = app.newElement(LocatorType.CSS, "input[type=checkbox]");
        this.cancelProspectDetails = app.newElement(LocatorType.CSS,
                "div.small-button-wrapper button[class*=btn-cancel]");
        this.backSchedulingSearchButton = app.newElement(LocatorType.XPATH,
                "//span[contains(text(),'Scheduling Search')]");
    }

    /**
     * Search already created appointment
     */
    public void searchAppointmentByEmail() {
        searchMenu.waitFor().displayed();
        searchMenu.click();
        emailSearchField.type(prospect.getEmail());
        searchButton.click();
    }

    /**
     * Search already created appointment by First Name
     */
    public void searchAppointmentByFristName() {
        searchMenu.waitFor().displayed();
        searchMenu.click();
        inputFirstName.type(prospect.getFirstName());
        searchButton.click();
    }

    /**
     * Search already created appointment by Last Name
     */
    public void searchAppointmentByLastName() {
        searchMenu.waitFor().displayed();
        searchMenu.click();
        inputLastName.type(prospect.getLastName());
        searchButton.click();
    }

    /**
     * Search already created appointment by Date Of Birth
     */
    public void searchAppointmentByDateOfBirth() {
        searchMenu.waitFor().displayed();
        searchMenu.click();
        inputDateOfBirth.type(prospect.getDateOfBirth());
        searchButton.click();
    }

    /**
     * Search already created appointment by Phone Number
     */
    public void searchAppointmentByPhone() {
        searchMenu.waitFor().displayed();
        searchMenu.click();
        inputPhoneNumberSearchProspect.type(prospect.getPhoneNumber());
        searchButton.click();
    }

    /**
     * Search already created appointment by Partial Name
     */
    public void searchAppointmentByPartialName() {
        searchMenu.waitFor().displayed();
        searchMenu.click();
        inputFirstName.type(prospect.getFirstName().substring(0, 15));
        searchButton.click();
    }

    /**
     * Verify searched appointment should display in Search Result
     */
    public void assertSearchedAppointment() {
        searchedParticipantEmail.waitFor().present();
        searchedParticipantEmail.assertEquals().text(prospect.getEmail());
    }

    /**
     * Click Create button from search result to create new appointment
     */
    public void clickCreateButton() {
        createButtonFromSearchResult.click();
        spinner.waitFor().notPresent();
    }

    /**
     * Click View button from search result to view prospect and appointment
     */
    public void clickViewButton() {
        viewButton.waitFor().displayed();
        spinner.waitFor().notPresent();
        viewButton.click();
    }

    public void clickExample() {
        viewButton1.waitFor().displayed();
        viewButton1.click();
    }


    public void assertViewAppointmentDetailPage() {
        viewAppointmentDetailsText.assertState().displayed();
    }

    /**
     * Cancel Appointment then click on Confirm pop-up
     */
    public void cancelAppointment() {
        cancelAppointment.click();
        cancelAppointmentConfirm.waitFor().displayed();
        if (cancelAppointmentConfirm.is().displayed()) {
            cancelAppointmentConfirm.click();
        }
    }

        /**
     * Assert full appointment info
     */
    public void assertAppointmentInfo() {
        for (int i = 1; i <= 6; i++) {
            WebbElement fieldValue = app.newElement(LocatorType.XPATH, FIELD_APPOINTMENT_INFO_PATTERN + "[" + i + "]");
            fieldValue.waitFor().displayed();
            Assert.assertNotNull(fieldValue.get().text());
        }
    }

    /**
     * Verify that message of cancel action will be displayed
     */
    public void assertCancelAppointment() {
        String s = "The appointment for " + prospect.getFirstName() + "  " + prospect.getLastName() + " was cancelled.";
        WebbElement messageOfCancelAppointment = app.newElement(LocatorType.XPATH, "//div[contains(text(), '" + s + "')]");
        messageOfCancelAppointment.waitFor().displayed();
    }

    /**
     * Click edit prospect
     */
    public void clickEditProspect() {
        editProspectButton.click();
    }

    /**
     * Edit first name prospect
     */
    public void editFirstNameInput(String newFirstName) {
        inputFirstName.assertState().displayed();
        inputFirstName.click();
        inputFirstName.type(Keys.chord(Keys.CONTROL, "a"));
        inputFirstName.type(newFirstName);
    }

    /**
     * Edit first name prospect
     */
    public void editLastNameInput(String newLastName) {
        inputLastName.assertState().displayed();
        inputLastName.click();
        inputLastName.type(Keys.chord(Keys.CONTROL, "a"));
        inputLastName.type(newLastName);
    }

    /**
     * Edit prospect's first name and last name
     */
    public void editFirstNameAndLastNameProspect(String newFirstName, String newLastName) {
        clickEditProspect();
        editFirstNameInput(newFirstName);
        editLastNameInput(newLastName);
        saveProspectDetails.click();
    }

    /**
     * Assert edit first name and last name prospect
     */
    public void assertEditFirstNameAndLastName(String newFirstName, String newLastName) {
        assertBannerMessageSuccess();
        clickEditProspect();
        inputFirstName.assertEquals().value(newFirstName);
        inputLastName.assertEquals().value(newLastName);
    }

    /**
     * clicks on the language dropdown
     * select other language
     */
    public boolean editLanguage() {
        languagesDropDown.waitFor().displayed();
        languagesDropDown.click();
        boolean isSelectedEnglish = false;
        if (selectEnglishLanguage.get().attribute(ATTRIBUTE_CLASS).contains(IS_SELECTED_CLASS)) {
            selectSpanishLanguage.click();
        } else {
            selectEnglishLanguage.click();
            isSelectedEnglish = true;
        }
        saveProspectDetails.click();

        return isSelectedEnglish;
    }

    /**
     * Assert selected language
     */
    public void assertSelectedLanguage(boolean isSelectedEnglish) {
        assertBannerMessageSuccess();
        clickEditProspect();
        languagesDropDown.waitFor().displayed();
        languagesDropDown.click();
        if (isSelectedEnglish) {
            selectEnglishLanguage.assertContains().attribute(ATTRIBUTE_CLASS, IS_SELECTED_CLASS);
        } else {
            selectSpanishLanguage.assertContains().attribute(ATTRIBUTE_CLASS, IS_SELECTED_CLASS);
        }
        cancelProspectDetails.click();
    }

    /**
     * Edit prospect's email
     */
    public void editEmailInput(String newEmailAddress) {
        emailAddressInput.assertState().displayed();
        emailAddressInput.click();
        emailAddressInput.type(Keys.chord(Keys.CONTROL, "a"));
        emailAddressInput.type(newEmailAddress);
        saveProspectDetails.click();
    }

    /**
     * Assert prospect's email
     */
    public void assertEmailInput(String newEmailAddress) {
        assertBannerMessageSuccess();
        clickEditProspect();
        emailAddressInput.assertEquals().value(newEmailAddress);
    }

    /**
     * Edit prospect's phone
     */
    public void editPhoneInput(String newPhoneNumber) {
        phoneNumberInput.assertState().displayed();
        phoneNumberInput.click();
        phoneNumberInput.type(Keys.chord(Keys.CONTROL, "a"));
        phoneNumberInput.type(newPhoneNumber);
        saveProspectDetails.click();
    }

    /**
     * Assert prospect's phone
     */
    public void assertPhoneInput(String newPhoneNumber) {
        assertBannerMessageSuccess();
        clickEditProspect();
        String valuePhoneNumberInput = phoneNumberInput.get().value().replace("-", "");
        Assert.assertEquals(valuePhoneNumberInput, newPhoneNumber);
    }

    /**
     * Edit Checkbox Prospect Accept Receive Email
     */
    public boolean editCheckboxProspectAcceptReceiveEmail() {
        clickEditProspect();
        boolean isCheckedProspectAcceptReceiveEmail = false;

        if(!checkboxProspectAcceptReceiveEmail.is().checked())
        {
            isCheckedProspectAcceptReceiveEmail = true;
        }

        spanProspectAcceptReceiveEmail.click();
        saveProspectDetails.click();
        return isCheckedProspectAcceptReceiveEmail;
    }

    /**
     * Assert Checkbox Prospect Accept Receive Email
     */
    public void assertCheckboxProspectAcceptReceiveEmail(boolean isCheckedProspectAcceptReceiveEmail) {
        assertBannerMessageSuccess();
        clickEditProspect();
        checkboxProspectAcceptReceiveEmail.is().present();
        Assert.assertEquals(isCheckedProspectAcceptReceiveEmail, checkboxProspectAcceptReceiveEmail.is().checked());
    }

    private void assertBannerMessageSuccess() {
        boolean isWaitForDisplayedBannerMessageSuccess = attemptWaitforBannerMessageSuccess();
        Assert.assertEquals(isWaitForDisplayedBannerMessageSuccess, true);
    }

    private boolean attemptWaitforBannerMessageSuccess() {
        boolean result = false;
        int attempts = 0;
        while(attempts < 5 && !result) {
            try {
                WebbElement messageSuccess = app.newElement(LocatorType.CSS, "div[class*='message animated fade success in']");
                messageSuccess.is().present();
                result = messageSuccess.waitFor().displayed();
            } catch(StaleElementReferenceException | NoSuchElementException e) {
                log.info("Attempt get WebbElement messageSuccess after throw " + e);
            }
            attempts++;
        }
        return result;
    }

    /**
     * assert prospect's info
     */
    public void assertProspectInfo() {
        clickEditProspect();
        inputFirstName.assertEquals().value(prospect.getFirstName());
        inputLastName.assertEquals().value(prospect.getLastName());
        emailAddressInput.assertEquals().value(prospect.getEmail());
        languagesDropDown.waitFor().displayed();
        languagesDropDown.click();
        selectEnglishLanguage.assertContains().attribute(ATTRIBUTE_CLASS, IS_SELECTED_CLASS);
    }

    public void clickBackSchedulingSearchButton() {
        backSchedulingSearchButton.click();
    }

    public void assertProspectOnSearchPage() {
        WebbElement prospectName = app.newElement(LocatorType.CSS, "strong.participant-name");
        prospectName.assertContains().text(prospect.getFirstName() + " " + prospect.getLastName());
    }

    public void assertProspectOnSearchPageAfterSearchByDob() {
        WebbElement prospectName = app.newElement(LocatorType.XPATH,
                "//strong[contains(text(), '" + prospect.getFirstName() + " " + prospect.getLastName() + "')]");
        prospectName.assertState().displayed();
    }
}
