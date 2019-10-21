package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class UserAdminPage {

    private final WebbElement addUserButton;
    private final WebbElement loggedInHeadingUserAdministration;
    private final WebbElement loggedInHeadingUser;
    private final WebbElement loggedInHeadingAdmin;
    private final WebbElement loggedInHeadingSiteManagerUser;
    private final WebbElement createdUser;
    private final WebbElement userAdminButton;
    private final WebbElement userSettingsButton;
    private final WebbElement spinner;
    private final WebbElement createdSortButton;
    private final WebbElement resetMFAButton;
    private final WebbElement changedSuccessMessage;
    private final WebbElement userAdminButtonProgramManager;
    private final WebbElement searchField;
    private final WebbElement tableEmailAssert;
    private final WebbElement editUserHeader;
    private final WebbElement actionDropdown;
    private final WebbElement editAction;

    public UserAdminPage(App app, User user) {
        this.addUserButton = app.newElement(LocatorType.CLASSNAME, "add-user-button");
        this.loggedInHeadingUserAdministration = app.newElement(LocatorType.XPATH, "//h1[text()='User Administration']");
        this.loggedInHeadingAdmin = app.newElement(LocatorType.XPATH, "//h1[text()='Reports']");
        this.loggedInHeadingUser = app.newElement(LocatorType.XPATH, "//h1[text()='Dashboard']");
        this.loggedInHeadingSiteManagerUser = app.newElement(LocatorType.XPATH, "//h1[text()='Appointment Scheduler']");
        this.createdUser = app.newElement(LocatorType.XPATH, "//div[contains(text(), \"" + user.getEmail() + "\")]");
        this.createdSortButton = app.newElement(LocatorType.CSS, "th[data-field='createdDate']");
        this.userAdminButton = app.newElement(LocatorType.CSS, "svg[class*=\"fa-user \"]");
        this.userSettingsButton = app.newElement(LocatorType.XPATH, "//a[contains(text(), 'Settings')]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.resetMFAButton = app.newElement(LocatorType.CSS, "svg[class*=\"shield\"]");
        this.changedSuccessMessage = app.newElement(LocatorType.CSS, "div[class=\"message animated fade success in\"]");
        this.userAdminButtonProgramManager = app.newElement(LocatorType.XPATH, "(//a[@role='button'])[9]");
        this.searchField = app.newElement(LocatorType.CSS, "input[placeholder='Search']");
        this.tableEmailAssert = app.newElement(LocatorType.XPATH, "//div[contains(text(), \"" + user.getSearchedUserEmail() + "\")]");
        this.actionDropdown = app.newElement(LocatorType.CSS, "button[class='action-btn dropdown-toggle btn btn-default']");
        this.editUserHeader = app.newElement(LocatorType.XPATH, "//div[contains(text(),'Edit User')]");
        this.editAction = app.newElement(LocatorType.XPATH, "//li/a[contains(text(),'Edit')]");
    }

    public void assertEditUserHeader() {
        editUserHeader.assertState().displayed();
    }

    public void selectActionDropDown() {
        actionDropdown.click();
        editAction.click();
    }

    public void searchField(String parameter) {
        searchField.type(parameter);
    }

    public void assertSearchedEmail() {
        tableEmailAssert.assertState().displayed();
    }

    /**
     * Clicks on the "User Admin" button
     */
    public void userAdminButtonProgramManager() {
        userAdminButtonProgramManager.click();
    }

    /**
     * Clicks on the "User Admin" button
     */
    public void userAdmin() {
        userAdminButton.click();
        addUserButton.hover();
    }

    /**
     * Clicks on the "User Settings " button
     */
    public void userSettings() {
        userSettingsButton.click();
    }

    /**
     * Clicks on the "Add User" button
     */
    public void addUser() {
        spinner.waitFor().notDisplayed();
        addUserButton.click();
    }

    /**
     * Waits for the header indicating the user has logged in to be displayed.
     */
    public void waitForLoginLoad() {
        loggedInHeadingAdmin.waitFor().displayed();
    }

    /**
     * Waits for the header to be displayed.
     */
    public void waitForLoginLoadSiteManager() {
        loggedInHeadingSiteManagerUser.waitFor().displayed();
    }

    /**
     * Waits for the header to be displayed.
     */
    public void waitForLoginLoadUserAdministration() {
        loggedInHeadingUserAdministration.waitFor().displayed();
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedIn() {
        loggedInHeadingAdmin.assertState().displayed();
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedInUser() {
        loggedInHeadingUser.assertState().displayed();
    }

    /**
     * Waits for the header to be displayed.
     */
    public void assertLoggedInSiteManager() {
        loggedInHeadingSiteManagerUser.assertState().displayed();
    }

    /**
     * Asserts that the created user is present by making sure it's email is present
     */
    public void assertCreatedUser() {
        spinner.waitFor().notDisplayed();
        createdSortButton.click();
        createdUser.assertState().displayed();
    }

    /**
     * Asserts that the created user is present by making sure it's email is present
     */
    public void resetMFACode(String email) {
        searchField.type(email);
        actionDropdown.click();
        resetMFAButton.click();
        changedSuccessMessage.assertState().displayed();
    }
}
