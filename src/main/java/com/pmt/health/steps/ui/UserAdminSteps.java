package com.pmt.health.steps.ui;

import org.springframework.context.annotation.Description;

import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.Constants;
import com.pmt.health.workflows.AddUserPage;
import com.pmt.health.workflows.LoginPage;
import com.pmt.health.workflows.UserAdminPage;
import com.pmt.health.utilities.EMailUtility;
import com.pmt.health.utilities.Property;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

public class UserAdminSteps {

    private final User user;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final LoginPage loginPage;
    private final AddUserPage addUserPage;
    private final EMailUtility emailUtility;
    private static final String ADMIN_PASS = ".admin.pass";

    public UserAdminSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
        this.emailUtility = new EMailUtility(user, deviceController.getReporter());
        loginPage = new LoginPage(this.deviceController.getApp(), user);
    }

    @Description("Creating a user with a specific parameters")
    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\" level$")
    public void createUser(String role, String org) {
        this.userAdminPage.userAdmin();
        this.userAdminPage.addUser();
        this.addUserPage.enterFirstName(user.getFirstName());
        this.addUserPage.enterLastName(user.getLastName());
        this.addUserPage.enterEmail(UserUtility.makeRandomUserEmail());
        this.addUserPage.selectRole(role);
        this.addUserPage.checkAwardee(org);
        this.addUserPage.saveUser();
    }

    @Then("^User has been created$")
    public void assertCreatedUser() {
        this.userAdminPage.assertCreatedUser();
    }

    @Then("^I try to delete that temp user$")
    public void tryDeleteUser() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.tryDeleteTempUser(user.getSearchedUserEmail());
    }

    @And("^The alert message is displayed with options Delete User and Cancel$")
    public void verifyDeleteAlertMessage() {
        this.userAdminPage.verifyDeleteAlertMessage();
        this.loginPage.logout();
    }

    @When("^I try to delete that temp user but choose Cancel button$")
    public void tryDeleteButClickCancel() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.tryDeleteButClickCancel(user.getSearchedUserEmail());
    }

    @Then("^The temp user still be persistent$")
    public void assertTempUserPersistent() {
        this.userAdminPage.assertUserPersistent(user.getSearchedUserEmail());
        this.loginPage.logout();
    }

    @When("^I delete that temp user$")
    public void deleteTempUser() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.deleteUser(user.getSearchedUserEmail());
    }

    @Then("^The temp user should be removed$")
    public void assertTempUserRemoved() {
        this.userAdminPage.assertUserRemoved(user.getSearchedUserEmail());
    }

    @Then("^I lock user and status of this user should be changed to Disabled and locked user can not login$")
    public void lockTempUser() throws IOException, InterruptedException {
        this.userAdminPage.userAdmin();
        this.userAdminPage.enterSearch(user.getSearchedUserEmail());
        this.userAdminPage.clickActionButton();
        this.userAdminPage.clickLockActionLink();
        this.userAdminPage.assertLockOrUnlockUserSuccess();
        this.userAdminPage.enterSearch(user.getSearchedUserEmail());
        this.userAdminPage.assertStatusUser(Constants.DISABLED_STATUS);
        this.emailUtility.assertEmailForUser(Constants.LOCK_EMAIL_KEYWORD, user.getSearchedUserEmail());
        loginByLockedUser();
    }

    private void loginByLockedUser() {
        this.loginPage.logout();
        this.loginPage.loadEnvironment();
        this.loginPage.loginWithoutMFA(user.getSearchedUserEmail());
        this.userAdminPage.assertLoginByLockedUser(Constants.ERROR_WHEN_LOGIN_BY_LOCKED_USER);
    }

    @And("^I reset password for temp user$")
    public void resetTempUserPassword() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.userAdmin();
        this.userAdminPage.resetUserPassword(user.getSearchedUserEmail());
        this.loginPage.logout();
    }

    @And("^An email notification should be received$")
    public void assertEmailForResetPassword() throws IOException, InterruptedException {
        this.emailUtility.assertEmailForUser(Constants.EMAIL_RESET_PASSWORD, user.getSearchedUserEmail());
    }

    @And("^I login as temp user again by reset password successfully$")
    public void loginUserResetPassword() throws IOException, InterruptedException {
        this.emailUtility.getResetPasswordLink(user.getSearchedUserEmail());
        this.loginPage.loadSpecifyEnvironment(emailUtility.getResetPasswordLink(user.getSearchedUserEmail()));
        this.loginPage.enterPassword(Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
        this.loginPage.clickSubmitButton();
        this.loginPage.firstLoginForResetPassword(user.getSearchedUserEmail());
    }

    @When("^I reset MFA code for temp user$")
    public void resetMFACode() {
        this.loginPage.loadEnvironment();
        this.loginPage.login();
        this.userAdminPage.userAdmin();
        this.userAdminPage.resetMFACode(user.getSearchedUserEmail());
        this.loginPage.logout();
    }

    @Then("^I login temp user with new secret key$")
    public void loginResetMFAUser() {
        this.loginPage.loginResetMFAUser();
        this.loginPage.logout();
    }

    @Then("^I am logged in as user$")
    public void loggedInAsUser() {
        this.userAdminPage.assertLoggedInUser();
    }

    @Then("^I update user to \"([^\"]*)\" role and \"([^\"]*)\" org$")
    public void navigateToUserAdminPage(String role, String org) {
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.searchField(user.getSearchedUserEmail());
        this.userAdminPage.assertSearchedEmail();
        this.userAdminPage.selectActionDropDown();
        this.userAdminPage.assertEditUserHeader();
        this.addUserPage.enterFirstName("Updated User");
        this.addUserPage.enterLastName("Updated Automation");
        this.addUserPage.selectRole(role);
        this.addUserPage.checkAwardee(org);
        this.addUserPage.saveUser();
    }

    @And("^Lock user, status of user is Disabled$")
    public void lockSearchedUser() throws InterruptedException {
        this.userAdminPage.userAdmin();
        this.userAdminPage.enterSearch(user.getSearchedUserEmail());
        this.userAdminPage.assertCreatedUser(user.getSearchedUserEmail());
        this.userAdminPage.clickActionButton();
        this.userAdminPage.clickLockActionLink();
        this.userAdminPage.assertLockOrUnlockUserSuccess();
        this.userAdminPage.enterSearch(user.getSearchedUserEmail());
        this.userAdminPage.assertStatusUser(Constants.DISABLED_STATUS);
    }

    @Then("^I unlock that user$")
    public void unlockSearchedUser() {
        this.userAdminPage.clickActionButton();
        this.userAdminPage.clickUnLockActionLink();
    }

    @And("^User is unlock successfully and can log into system successfully$")
    public void unlockSearchedUserSuccessfully() throws InterruptedException, IOException {
        this.userAdminPage.assertLockOrUnlockUserSuccess();
        this.emailUtility.assertEmailForUser(Constants.UNLOCK_EMAIL_KEYWORD, user.getSearchedUserEmail());
        this.userAdminPage.enterSearch(user.getSearchedUserEmail());
        this.userAdminPage.assertStatusUser(Constants.ACTIVE_STATUS);
        this.loginPage.logout();
        //user can log in
        this.loginPage.firstLoginSearchedUser(user);
        this.loginPage.assertUserLoginSuccess();
        this.loginPage.logout();
    }

    @Then("^I found created user by searching email$")
    public void foundCreatedUserBySearchingEmail() {
        this.userAdminPage.userAdminButtonProgramManager();
        this.userAdminPage.searchField(user.getSearchedUserEmail());
        this.userAdminPage.assertSearchedEmail();
    }
}
