package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.Constants;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class UserAdminPage {

    private final App app;

    private final WebbElement addUserButton;
    private final WebbElement loggedInHeadingUserAdministration;
    private final WebbElement loggedInHeadingUser;
    private final WebbElement loggedInHeadingAdmin;
    private final WebbElement loggedInHeadingSiteManagerUser;
    private final WebbElement loggedInHeadingProgramManagerUser;
    private final WebbElement createdUser;
    private final WebbElement userAdminButton;
    private final WebbElement reportButton;
    private final WebbElement dashboardButton;
    private final WebbElement schedulingButton;
    private final WebbElement capacityManagementButton;
    private final WebbElement searchButton;
    private final WebbElement callContactLogButton;
    private final WebbElement engagementHistoryButton;
    private final WebbElement communicationsButton;
    private final WebbElement userSettingsButton;
    private final WebbElement spinner;
    private final WebbElement createdSortButton;
    private final WebbElement leftMenu;
    private final WebbElement divSelectRole;
    private final WebbElement rolesInput;
    private final WebbElement divSelectNoResults;
    private final WebbElement roleSelectOption;
    private static final int ADMINISTRATOR_MENU_ITEM_NUMBER = 1;
    private static final int NIH_MENU_ITEM_NUMBER = 5;
    private static final int PROGRAM_MANAGER_MENU_ITEM_NUMBER = 9;
    private static final int SITE_MANAGER_MENU_ITEM_NUMBER = 4;
    private static final int SUPPORT_ADMIN_MENU_ITEM_NUMBER = 1;
    private static final int PROGRAM_MANAGER_ROLE_NUMBER = 7;
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
    private final WebbElement editButton;
    private final WebbElement firstNameEmptyError;
    private final WebbElement lastNameEmptyError;
    private final WebbElement roleEmptyError;
    private final WebbElement groupEmptyError;
    private final WebbElement emailInput;
    private final WebbElement firstNameInput;
    private final WebbElement lastNameInput;
    private final WebbElement selectedRoles;
    private final WebbElement firstGroup;
    private final WebbElement cancelButton;
    private String firstNameBeforeEdit = "";
    private String lastNameBeforeEdit = "";
    private String userEmailBeforeEdit = "";
    private final String invalidName;
    private final AddUserPage addUserPage;
    private final User user;
    private final WebbElement unlockActionLink;
    private final WebbElement deleteActionLink;
    private final WebbElement userDeleteConfirmationButton;
    private static final String DIV_CONTAIN_TEXT_PATTERN_XPATH = "//div[contains(text(),";

    public UserAdminPage(App app, User user) {
        this.app = app;
        this.addUserButton = app.newElement(LocatorType.CLASSNAME, "add-user-button");
        this.loggedInHeadingUserAdministration = app.newElement(LocatorType.XPATH,
                "//h1[text()='User Administration']");
        this.loggedInHeadingAdmin = app.newElement(LocatorType.XPATH, "//h1[text()='Reports']");
        this.loggedInHeadingUser = app.newElement(LocatorType.XPATH, "//h1[text()='Dashboard']");
        this.loggedInHeadingSiteManagerUser = app.newElement(LocatorType.XPATH, "//h1[text()='Appointment Scheduler']");
        this.loggedInHeadingProgramManagerUser = app.newElement(LocatorType.XPATH, "//h1[text()='Dashboard']");
        this.createdUser = app.newElement(LocatorType.XPATH, DIV_CONTAIN_TEXT_PATTERN_XPATH + " \"" + user.getEmail() + "\")]");
        this.createdSortButton = app.newElement(LocatorType.CSS, "th[data-field='createdDate']");
        this.reportButton = app.newElement(LocatorType.XPATH, "//p[text()='Report']/parent::a");
        this.schedulingButton = app.newElement(LocatorType.XPATH, "//p[text()='Scheduling']/parent::a");
        this.dashboardButton = app.newElement(LocatorType.XPATH, "//p[text()='Dashboard']/parent::a");
        this.capacityManagementButton = app.newElement(LocatorType.XPATH, "//p[text()='Capacity Management']/parent::a");
        this.searchButton = app.newElement(LocatorType.XPATH, "//p[text()='Search']/parent::a");
        this.callContactLogButton = app.newElement(LocatorType.XPATH, "//p[text()='Call/Contact Log']/parent::a");
        this.engagementHistoryButton = app.newElement(LocatorType.XPATH, "//p[text()='Engagement History']/parent::a");
        this.communicationsButton = app.newElement(LocatorType.XPATH, "//p[text()='Communications']/parent::a");
        this.userAdminButton = app.newElement(LocatorType.CSS, "svg[class*=\"fa-user \"]");
        this.userDeleteConfirmationButton = app.newElement(LocatorType.CSS, "button[class=\"button-warning btn btn-primary\"]");
        this.userDeleteCancelButton = app.newElement(LocatorType.CSS, "button[class=\"button-warning btn btn-secondary\"]");
        this.userAdminButtonProgramManager = app.newElement(LocatorType.XPATH, "(//a[@role='button'])[9]");
        this.userSettingsButton = app.newElement(LocatorType.XPATH, "//a[contains(text(), 'Settings')]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.leftMenu = app.newElement(LocatorType.XPATH, "//span[text()='Expand Menu']/parent::a/parent::li/following-sibling::li[not(contains(@class,'hidden'))]");
        this.divSelectRole = app.newElement(LocatorType.CSS, "div.Select-placeholder");
        this.rolesInput = app.newElement(LocatorType.CSS, "input[role*=combobox]");
        this.divSelectNoResults = app.newElement(LocatorType.CSS, "div.Select-noresults");
        this.roleSelectOption = app.newElement(LocatorType.CSS, "div.Select-option");
        this.lockActionLink = app.newElement(LocatorType.CSS, "svg[data-icon*='lock']");
        this.divStatusOfUser = app.newElement(LocatorType.XPATH, "//td[contains(@tabindex,'6')]/div[contains(@class,'cell-container')]");
        this.divLoginMessage = app.newElement(LocatorType.CSS, "div.login-error-message");
        this.userResetPasswordButton = app.newElement(LocatorType.CSS, "svg[class*=\"fa-sync\"]");
        this.resetMFAButton = app.newElement(LocatorType.CSS, "svg[class*=\"shield\"]");
        this.changedSuccessMessage = app.newElement(LocatorType.CSS, "div[class=\"message animated fade success in\"]");
        this.searchField = app.newElement(LocatorType.CSS, "input[placeholder='Search']");
        this.tableEmailAssert = app.newElement(LocatorType.XPATH, DIV_CONTAIN_TEXT_PATTERN_XPATH + " \"" + user.getSearchedUserEmail() + "\")]");
        this.actionDropdown = app.newElement(LocatorType.CSS, "button[class='action-btn dropdown-toggle btn btn-default']");
        this.editButton = app.newElement(LocatorType.CSS, "a[href*='/userAdmin/editUser/']");
        this.user = user;
        addUserPage = new AddUserPage(app, user);
        this.firstNameEmptyError = app.newElement(LocatorType.CSS, "input[name=firstName] + span.help-block");
        this.lastNameEmptyError = app.newElement(LocatorType.CSS, "input[name=lastName] + span.help-block");
        this.roleEmptyError = app.newElement(LocatorType.CSS, "div.Select.is-searchable.Select--multi + span.help-block");
        this.groupEmptyError = app.newElement(LocatorType.CSS, "div[class*='container-content-right'] span[class='help-block']");
        this.firstNameInput = app.newElement(LocatorType.NAME, "firstName");
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.lastNameInput = app.newElement(LocatorType.NAME, "lastName");
        this.invalidName = "qwertyuiopasdfghjklzxcvbnmpoquqjskvbcndmfnrrcffcrree";
        this.selectedRoles = app.newElement(LocatorType.CSS, "span.Select-value-label");
        this.firstGroup = app.newElement(LocatorType.XPATH, "(//input[@name='groupIds'])[1]");
        this.cancelButton = app.newElement(LocatorType.CSS, "input[value='Cancel']");
        this.editUserHeader = app.newElement(LocatorType.CSS, "div[class='heading-create_user']");
        this.editAction = app.newElement(LocatorType.CSS, "a[href*='editUser']");
        this.unlockActionLink = app.newElement(LocatorType.CSS, "svg[data-icon*='unlock']");
        this.deleteActionLink = app.newElement(LocatorType.CSS, "svg[class*='trash']");
    }

    public void assertEditUserHeader() {
        editUserHeader.assertState().displayed();
        editUserHeader.assertContains().text("Edit User");
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
     * Wait the "User Admin" button display
     */
    public void waitUserAdminButtonDisplay() {
        userAdminButton.waitFor().displayed();
    }

    /**
     * Clicks on the "User Settings " button
     */
    public void userSettings() {
        userSettingsButton.click();
    }

    /**
     * Clicks on the "Add User" button
     * @throws InterruptedException 
     */
    public void addUser() {
        spinner.waitFor().notDisplayed();
        addUserButton.waitFor().enabled();
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
    public void assertLoggedInAdministrator() {
        loggedInHeadingUserAdministration.assertState().displayed();
    }

    public void assertLoggedInProgramManager() {
        loggedInHeadingProgramManagerUser.assertState().displayed();
    }

    /**
     * Asserts that the created user is present by making sure it's email is present
     */
    public void assertCreatedUser() {
        spinner.waitFor().notDisplayed();
        createdSortButton.click();
        createdUser.assertState().displayed();
    }

    public void enterSearch(String searchString) {
        searchField.clear();
        searchField.type(searchString);
        spinner.waitFor().notDisplayed();
    }

    public void waitForSpinnerDisappear() {
        spinner.waitFor().notDisplayed();
    }

    public void clickEditButton() {
        editButton.click();
    }

    public void verifyEmailFieldIsUnEditable() {
        userEmailBeforeEdit = emailInput.get().value();
        emailInput.assertState().notEditable();
    }

    public void validateEditWithEmptyFirstName() {
        firstNameBeforeEdit = firstNameInput.get().value();
        firstNameInput.clear();
        this.addUserPage.saveUser();
        firstNameEmptyError.assertState().displayed();
        firstNameInput.type(firstNameBeforeEdit);
    }

    public void validateEditWithEmptyLastName() {
        lastNameBeforeEdit = lastNameInput.get().value();
        lastNameInput.clear();
        this.addUserPage.saveUser();
        lastNameEmptyError.assertState().displayed();
        lastNameInput.type(lastNameBeforeEdit);
    }

    public void validateEditWithInvalidFirstName() {
        firstNameInput.clear();
        firstNameInput.type(invalidName);
        this.addUserPage.saveUser();
        firstNameEmptyError.assertState().displayed();
        firstNameInput.clear();
        firstNameInput.type(firstNameBeforeEdit);
    }

    public void validateEditWithInvalidLastName() {
        lastNameInput.clear();
        lastNameInput.type(invalidName);
        this.addUserPage.saveUser();
        lastNameEmptyError.assertState().displayed();
        lastNameInput.clear();
        lastNameInput.type(lastNameBeforeEdit);
    }

    public void validateEditWithEmptyRoleAndGroup() {
        int selectedRoleNumber = selectedRoles.getWebElements().size();
        for (int i = 1; i <= selectedRoleNumber; i++) {
            rolesInput.type(Keys.BACK_SPACE);
        }
        this.addUserPage.saveUser();
        roleEmptyError.assertState().displayed();
        this.addUserPage.selectRole("Program Manager");
        groupEmptyError.assertState().displayed();
        firstGroup.click();
    }

    public void changeNameAndClickCancelButton() {
        String newName = "New Name";
        firstNameInput.clear();
        firstNameInput.type(newName);
        lastNameInput.clear();
        lastNameInput.type(newName);
        cancelButton.click();
    }

    public void assertUserInfoAfterCancelEditing() {
        spinner.waitFor().notDisplayed();
        searchField.type(userEmailBeforeEdit);
        spinner.waitFor().notDisplayed();
        clickActionButton();
        clickEditButton();
        waitForSpinnerDisappear();
        Assert.assertEquals(firstNameBeforeEdit, firstNameInput.get().value());
        Assert.assertEquals(lastNameBeforeEdit, lastNameInput.get().value());
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
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedInAsAdministrator() {
        userAdminButton.assertState().displayed();
        int actualNumber = leftMenu.getWebElements().size();
        Assert.assertEquals(actualNumber, ADMINISTRATOR_MENU_ITEM_NUMBER);
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedInAsNIH() {
        dashboardButton.assertState().displayed();
        schedulingButton.assertState().displayed();
        reportButton.assertState().displayed();
        userAdminButton.assertState().displayed();
        capacityManagementButton.assertState().displayed();
        int actualNumber = leftMenu.getWebElements().size();
        Assert.assertEquals(actualNumber, NIH_MENU_ITEM_NUMBER);
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedInAsProgramManager() {
        dashboardButton.assertState().displayed();
        schedulingButton.assertState().displayed();
        searchButton.assertState().displayed();
        reportButton.assertState().displayed();
        callContactLogButton.assertState().displayed();
        engagementHistoryButton.assertState().displayed();
        communicationsButton.assertState().displayed();
        userAdminButton.assertState().displayed();
        capacityManagementButton.assertState().displayed();
        int actualNumber = leftMenu.getWebElements().size();
        Assert.assertEquals(actualNumber, PROGRAM_MANAGER_MENU_ITEM_NUMBER);
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedInAsSiteManager() {
        schedulingButton.assertState().displayed();
        searchButton.assertState().displayed();
        reportButton.assertState().displayed();
        userAdminButton.assertState().displayed();
        int actualNumber = leftMenu.getWebElements().size();
        Assert.assertEquals(actualNumber, SITE_MANAGER_MENU_ITEM_NUMBER);
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedInAsSupportAdmin() {
        userAdminButton.assertState().displayed();
        int actualNumber = leftMenu.getWebElements().size();
        Assert.assertEquals(actualNumber, SUPPORT_ADMIN_MENU_ITEM_NUMBER);
    }

    private void assertRoleNumberCanCreate(String role) {
        divSelectRole.click();
        if (Constants.PROGRAM_MANAGER_ROLE.equals(role)) {
            roleSelectOption.waitFor().displayed();
            int actualNumber = roleSelectOption.getWebElements().size();
            Assert.assertEquals(actualNumber, PROGRAM_MANAGER_ROLE_NUMBER);
        }
    }

    private void assertCannotCreateWithRole(String role) {
        userAdmin();
        addUser();
        rolesInput.type(role + Keys.ENTER);
        divSelectNoResults.assertState().displayed();
    }

    public void assertRolesProgramManagerCanCreate() {
        String[] roles = {"NIH", "System Administrator", "Administrator", "Hierarchy Manager"};
        userAdmin();
        addUser();
        assertRoleNumberCanCreate(Constants.PROGRAM_MANAGER_ROLE);
        for (String role : roles
        ) {
            assertCannotCreateWithRole(role);
        }
    }

    public void waitForMessageOfChange() {
        changedSuccessMessage.waitFor().displayed();
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
        changedSuccessMessage.assertState().displayed();
        if ("Your changes have been saved.".equals(changedSuccessMessage.get().text())) {
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

    public void clickEditActionLink() {
        editAction.click();
    }

    public void goToEditUserPage(String email) {
        waitUserAdminButtonDisplay();
        userAdmin();
        enterSearch(email);
        clickActionButton();
        clickEditActionLink();
    }
}
