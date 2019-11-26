package com.pmt.health.steps.ui;

import com.pmt.health.objects.user.Prospect;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.LoginPage;
import com.pmt.health.workflows.SearchPage;
import com.pmt.health.workflows.SiteSettingsPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchPageSteps {

    private final DeviceController deviceController;
    private final LoginPage loginPage;
    private final SearchPage searchPage;
    private final SiteSettingsPage siteSettingsPage;
    

    public SearchPageSteps(DeviceController deviceController, User user, Prospect prospect) {
        this.deviceController = deviceController;
        searchPage = new SearchPage(this.deviceController.getApp(), user, prospect);
        loginPage = new LoginPage(this.deviceController.getApp(), user);
        siteSettingsPage = new SiteSettingsPage(this.deviceController.getApp(), user, prospect);
    }

    @When("^I am on Search page and search appointment by email$")
    public void searchAppointment() {
        loginPage.loadEnvironment();
        loginPage.login();
        searchPage.searchAppointmentByEmail();
    }

    @Then("^I see appointment displays in Search Result$")
    public void assertSearchedAppointment() {
        searchPage.assertSearchedAppointment();
    }

    @When("^I create new appointment for prospect from Search result$")
    public void createAppointmentFromSearchResult() {
        searchPage.clickCreateButton();
        siteSettingsPage.selectLocation();
        siteSettingsPage.addAppointmentNotes();
        siteSettingsPage.nextForAppointmentDetails();
        siteSettingsPage.selectDate();
        siteSettingsPage.selectTime();
        siteSettingsPage.scheduleAppointment();
        siteSettingsPage.assertSuccessAppointmentMessage();
    }

    @And("^I am on Search page. I can search prospect by email and view future appointment details$")
    public void searchProspectAndViewFutureAppointmentDetails() {
        searchPage.searchAppointmentByEmail();
        searchPage.assertSearchedAppointment();
        searchPage.clickViewButton();
        searchPage.assertViewAppointmentDetailPage();
    }

    @And("^I login as User then I can search prospect by email and view future appointment details$")
    public void loginAsUserAndSearchProspectAndViewFutureAppointmentDetails() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
        searchPage.searchAppointmentByEmail();
        searchPage.assertSearchedAppointment();
        searchPage.clickViewButton();
        searchPage.assertViewAppointmentDetailPage();
    }

    @When("^I cancel that appointment then confirm$")
    public void cancelThatAppointmentThenConfirm() {
        searchPage.cancelAppointment();
    }

    @Then("^I should see the appointment cancel message$")
    public void iShouldSeeTheAppointmentCancelMessage() {
        searchPage.assertCancelAppointment();
    }

    @And("^I can view full appointment details$")
    public void verifyViewAppointmentInSearchPage() {
        searchPage.clickViewButton();
        searchPage.assertAppointmentInfo();
    }

    @Then("^I can edit information of prospect$")
    public void editProspectInfo() {
        String newFirstName = "Automation" + UserUtility.generateUUID(9);
        String newLastName = "User" + UserUtility.generateUUID(9);
        String newPhoneNumber = "09" + UserUtility.generateStringNumber(8);
        String newEmailAddress = UserUtility.makeRandomUserEmail();
        
        searchPage.editFirstNameAndLastNameProspect(newFirstName, newLastName);
        searchPage.assertEditFirstNameAndLastName(newFirstName, newLastName);
        searchPage.editPhoneInput(newPhoneNumber);
        searchPage.assertPhoneInput(newPhoneNumber);
        searchPage.editEmailInput(newEmailAddress);
        searchPage.assertEmailInput(newEmailAddress);
        boolean isSelectedEnglish = searchPage.editLanguage();
        searchPage.assertSelectedLanguage(isSelectedEnglish);
        boolean isCheckedProspectAcceptReceiveEmail = searchPage.editCheckboxProspectAcceptReceiveEmail();
        searchPage.assertCheckboxProspectAcceptReceiveEmail(isCheckedProspectAcceptReceiveEmail);
    }

    @When("^I access Appointment Scheduler for managing existing appointment$")
    public void manageAppointment() {
        searchPage.clickViewButton();
    }

    @And("^I search prospect on Search page by First Name info$")
    public void searchProspectByFristName() {
        searchPage.searchAppointmentByFristName();
    }

    @Then("^I verify prospect's info$")
    public void assertProspectInfo() {
        searchPage.assertProspectInfo();
    }

    @And("^I return to Scheduling Search with in-app button$")
    public void returnSchedulingSearch() {
        searchPage.clickBackSchedulingSearchButton();
    }

    @Then("^I see prospect on the search page$")
    public void assertProspectOnSearchPage() {
        searchPage.assertProspectOnSearchPage();
    }

    @And("^I search prospect on Search page by Last Name info$")
    public void searchProspectByLastName() {
        searchPage.searchAppointmentByLastName();
    }

    @And("^I search prospect on Search page by Email info$")
    public void searchProspectByEmail() {
        searchPage.searchAppointmentByEmail();
    }

    @And("^I search prospect on Search page by Phone info$")
    public void searchProspectByPhone() {
        searchPage.searchAppointmentByPhone();
    }

    @And("^I search prospect on Search page by Date Of Birth info$")
    public void searchProspectByDateOfBirth() {
        searchPage.searchAppointmentByDateOfBirth();
    }

    @And("^I search prospect on Search page by Partial Name info$")
    public void searchProspectByPartialName() {
        searchPage.searchAppointmentByPartialName();
    }

    @Then("^I see prospect on the search page after search by Date Of Birth$")
    public void assertProspectOnSearchPageAfterSearchByDob() {
        searchPage.assertProspectOnSearchPageAfterSearchByDob();
    }
}
