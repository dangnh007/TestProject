package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class UserAdminPage {

    private final App app;

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
    private final WebbElement userDeleteCancelButton;
    private final WebbElement lockActionLink;
    private final WebbElement divStatusOfUser;
    private final WebbElement divLoginMessage;
    private final WebbElement userResetPasswordButton;
    private final WebbElement resetMFAButton;
    private final WebbElement changedSuccessMessage;
    private final WebbElement userAdminButtonProgramManager;
    private final WebbElement searchField;
    private final WebbElement tableEmailAssert;
    private final WebbElement editUserHeader;
    private final WebbElement actionDropdown;
    private final WebbElement editAction;
    private final WebbElement unlockActionLink;
    private final WebbElement divMessageSuccess;
    private final WebbElement deleteActionLink;
    private final WebbElement userDeleteConfirmationButton;
    private static final String DIV_CONTAIN_TEXT_PATTERN_XPATH = "//div[contains(text(),";

    public UserAdminPage(App app, User user) {
        this.app = app;
        this.addUserButton = app.newElement(LocatorType.CLASSNAME, "add-user-button");
        this.loggedInHeadingUserAdministration = app.newElement(LocatorType.XPATH, "//h1[text()='User Administration']");
        this.loggedInHeadingAdmin = app.newElement(LocatorType.XPATH, "//h1[text()='Reports']");
        this.loggedInHeadingUser = app.newElement(LocatorType.XPATH, "//h1[text()='Dashboard']");
        this.loggedInHeadingSiteManagerUser = app.newElement(LocatorType.XPATH, "//h1[text()='Appointment Scheduler']");
        this.createdUser = app.newElement(LocatorType.XPATH, DIV_CONTAIN_TEXT_PATTERN_XPATH + " \"" + user.getEmail() + "\")]");
        this.createdSortButton = app.newElement(LocatorType.CSS, "th[data-field='createdDate']");
        this.userAdminButton = app.newElement(LocatorType.CSS, "svg[class*=\"fa-user \"]");
        this.userDeleteConfirmationButton = app.newElement(LocatorType.CSS, "button[class=\"button-warning btn btn-primary\"]");
        this.userDeleteCancelButton = app.newElement(LocatorType.CSS, "button[class=\"button-warning btn btn-secondary\"]");
        this.userAdminButtonProgramManager = app.newElement(LocatorType.XPATH, "(//a[@role='button'])[9]");
        this.userSettingsButton = app.newElement(LocatorType.XPATH, "//a[contains(text(), 'Settings')]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.lockActionLink = app.newElement(LocatorType.CSS, "svg[data-icon*='lock']");
        this.divStatusOfUser = app.newElement(LocatorType.XPATH, "//td[contains(@tabindex,'6')]/div[contains(@class,'cell-container')]");
        this.divLoginMessage = app.newElement(LocatorType.CSS, "div.login-error-message");
        this.userResetPasswordButton = app.newElement(LocatorType.CSS, "svg[class*=\"fa-sync\"]");
        this.resetMFAButton = app.newElement(LocatorType.CSS, "svg[class*=\"shield\"]");
        this.changedSuccessMessage = app.newElement(LocatorType.CSS, "div[class=\"message animated fade success in\"]");
        this.searchField = app.newElement(LocatorType.CSS, "input[placeholder='Search']");
        this.tableEmailAssert = app.newElement(LocatorType.XPATH, DIV_CONTAIN_TEXT_PATTERN_XPATH + " \"" + user.getSearchedUserEmail() + "\")]");
        this.actionDropdown = app.newElement(LocatorType.CSS, "button[class='action-btn dropdown-toggle btn btn-default']");
        this.editUserHeader = app.newElement(LocatorType.CSS, "div[class='heading-create_user']");
        this.editAction = app.newElement(LocatorType.CSS, "a[href*='editUser']");
        this.unlockActionLink = app.newElement(LocatorType.CSS, "svg[data-icon*='unlock']");
        this.divMessageSuccess = app.newElement(LocatorType.CSS, "div[class*='message animated fade success in']");
        this.deleteActionLink = app.newElement(LocatorType.CSS, "svg[class*='trash']");
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
        userAdminButton.assertState().enabled();
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
     * Asserts that the current user is logged out by making sure the login page is
     * displayed.
     */
    public void assertLoggedIn() {
        loggedInHeadingAdmin.assertState().displayed();
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is
     * displayed.
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

    public void assertLoginByLockedUser(String expectedStatus) {
        String action = "Assert Login By Locked User";
        divLoginMessage.assertState().displayed();
        if (expectedStatus.equals(divLoginMessage.get().text())) {
            app.getReporter().pass(action, expectedStatus, divLoginMessage.get().text());
        } else {
            app.getReporter().fail(action, expectedStatus, divLoginMessage.get().text());
        }
    }

    /**
     * Try to delete Temp user without confirmation
     */
    public void tryDeleteTempUser(String tempUser) {
        searchField.type(tempUser);
        actionDropdown.click();
        deleteActionLink.click();
    }

    /**
     * Verify Delete alert message. It should have two options: Delete User and Cancel
     */
    public void verifyDeleteAlertMessage() {
        userDeleteConfirmationButton.assertState().displayed();
        userDeleteCancelButton.assertState().displayed();
        userDeleteCancelButton.click();
    }

    /**
     * Delete specify user
     */
    public void deleteUser(String userEmail) {
        userAdmin();
        spinner.waitFor().notDisplayed();
        searchField.type(userEmail);
        actionDropdown.click();
        deleteActionLink.click();
        userDeleteConfirmationButton.click();
        spinner.waitFor().notDisplayed();
    }

    /**
     * Try to delete user but click Cancel button
     */
    public void tryDeleteButClickCancel(String userEmail) {
        spinner.waitFor().notDisplayed();
        searchField.type(userEmail);
        actionDropdown.click();
        deleteActionLink.click();
        userDeleteCancelButton.click();
        spinner.waitFor().notDisplayed();
    }

    /**
     * Assert a user that is available when searching
     */
    public void assertUserPersistent(String userEmail) {
        userAdmin();
        spinner.waitFor().notDisplayed();
        searchField.type(userEmail);
        actionDropdown.assertState().displayed();
    }

    /**
     * Assert a user that was removed
     */
    public void assertUserRemoved(String userEmail) {
        userAdmin();
        spinner.waitFor().notDisplayed();
        searchField.type(userEmail);
        actionDropdown.assertState().notDisplayed();
    }

    /**
     * Reset a specify user password
     */
    public void resetUserPassword(String userEmail) {
        searchField.type(userEmail);
        actionDropdown.click();
        userResetPasswordButton.click();
        spinner.waitFor().notDisplayed();
        changedSuccessMessage.assertState().displayed();
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

    public void assertCreatedUser(String email) {
        spinner.waitFor().notDisplayed();
        WebbElement divCreatedUser = app.newElement(LocatorType.XPATH,
                DIV_CONTAIN_TEXT_PATTERN_XPATH + " \"" + email + "\")]");
        divCreatedUser.assertState().displayed();
    }

    public void enterSearch(String searchString) {
        searchField.type(searchString);
    }

    public void clickActionButton() {
        actionDropdown.click();
    }

    public void clickLockActionLink() {
        lockActionLink.click();
    }

    public void clickUnLockActionLink() {
        unlockActionLink.click();
    }

    public void assertLockOrUnlockUserSuccess() throws InterruptedException {
        Thread.sleep(2000);
        divMessageSuccess.assertState().displayed();
        if ("Your changes have been saved.".equals(divMessageSuccess.get().text())) {
            app.getReporter().pass("Lock/Unlock user success");
        } else {
            app.getReporter().fail("Lock/Unlock user failed");
        }
    }

    public void assertStatusUser(String expectedStatus) {
        String action = "Assert Status of User";
        divStatusOfUser.assertState().displayed();
        if (expectedStatus.equals(divStatusOfUser.get().text())) {
            app.getReporter().pass(action, expectedStatus, divStatusOfUser.get().value());
        } else {
            app.getReporter().fail(action, expectedStatus, divStatusOfUser.get().value());
        }
    }

    public void deleteCreatedUser(String userEmail) throws InterruptedException {
        Thread.sleep(3000);
        userAdmin();
        spinner.waitFor().notDisplayed();
        searchField.type(userEmail);
        Thread.sleep(3000);
        actionDropdown.click();
        deleteActionLink.click();
        userDeleteConfirmationButton.click();
        spinner.waitFor().notDisplayed();
    }
}
