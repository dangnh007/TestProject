package com.pmt.health.steps.ui;

import org.openqa.selenium.Keys;

import com.pmt.health.exceptions.VibrentException;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.workflows.AddUserPage;
import com.pmt.health.workflows.UserAdminPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class EditUserSteps {

    private final User user;
    private final DeviceController deviceController;
    private final UserAdminPage userAdminPage;
    private final AddUserPage addUserPage;
    private static final String MESSAGE_EDIT_USER_SUCCESSFULLY = "Your changes have been saved.";
    private String newFirstNameInfo = UserUtility.generateUUID(6);
    private String newLastNameInfo = UserUtility.generateUUID(6);

    public EditUserSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        userAdminPage = new UserAdminPage(this.deviceController.getApp(), user);
        addUserPage = new AddUserPage(this.deviceController.getApp(), user);
    }
    
    @Then("^I edit First Name and Last Name of created user$")
    public void editFirstNameLastNameOfUser() throws InterruptedException {
        this.userAdminPage.goToEditUserPage(user.getSearchedUserEmail());
        Thread.sleep(3000);
        newFirstNameInfo = this.addUserPage.getValueFirstNameInput() + " " + newFirstNameInfo;
        newLastNameInfo = this.addUserPage.getValueLastNameInput() + " " + newLastNameInfo;
        this.addUserPage.clearFirstNameInput();
        this.addUserPage.enterFirstName(newFirstNameInfo);
        this.addUserPage.clearLastNameInput();
        this.addUserPage.enterLastName(newLastNameInfo);
        this.addUserPage.saveUser();
        this.addUserPage.assertDisplayedSuccessMessagePanel();
        this.addUserPage.assertStringSuccessMessagePanel(MESSAGE_EDIT_USER_SUCCESSFULLY);
    }

    @And("^First Name and Last Name of user should be updated successfully$")
    public void verifyEditFirstNameLastNameOfUser() {
        this.userAdminPage.goToEditUserPage(user.getSearchedUserEmail());
        this.addUserPage.assertTextFirstNameInput(newFirstNameInfo);
        this.addUserPage.assertTextLastNameInput(newLastNameInfo);
    }
    
    @And("^I change role of user from \"([^\"]*)\" to \"([^\"]*)\" and \"([^\"]*)\" successfully$")
    public void changeRoleOfUser(String oldRole, String newRole, String site) throws VibrentException {
        this.userAdminPage.goToEditUserPage(user.getSearchedUserEmail());
        this.addUserPage.getRolesInput().type(Keys.BACK_SPACE);
        this.addUserPage.selectRole(UserUtility.convertRoleName(newRole));
        this.addUserPage.checkAwardee(UserUtility.convertTestGroupName(site));
        this.addUserPage.saveUser();
        this.addUserPage.assertDisplayedSuccessMessagePanel();
        this.addUserPage.assertStringSuccessMessagePanel(MESSAGE_EDIT_USER_SUCCESSFULLY);
        this.userAdminPage.goToEditUserPage(user.getSearchedUserEmail());
        this.addUserPage.assertRoleIsSelected(UserUtility.convertRoleName(newRole));
        this.addUserPage.assertRoleIsNotSelected(UserUtility.convertRoleName(oldRole));
    }
    
    @And("^I change role of user from \"([^\"]*)\" to \"([^\"]*)\" and group of user from \"([^\"]*)\" to \"([^\"]*)\" successfully$")
    public void changeGroupOfUser(String oldRole, String newRole, String oldSite, String newSite) throws VibrentException {
        this.userAdminPage.goToEditUserPage(user.getSearchedUserEmail());
        this.addUserPage.getRolesInput().type(Keys.BACK_SPACE);
        this.addUserPage.selectRole(UserUtility.convertRoleName(newRole));
        this.addUserPage.checkAwardee(UserUtility.convertTestGroupName(newSite));
        this.addUserPage.saveUser();
        this.addUserPage.assertDisplayedSuccessMessagePanel();
        this.addUserPage.assertStringSuccessMessagePanel(MESSAGE_EDIT_USER_SUCCESSFULLY);
        this.userAdminPage.goToEditUserPage(user.getSearchedUserEmail());
        this.addUserPage.assertRoleIsSelected(UserUtility.convertRoleName(newRole));
        this.addUserPage.assertRoleIsNotSelected(UserUtility.convertRoleName(oldRole));
        this.addUserPage.assertGroupIsChecked(UserUtility.convertTestGroupName(newSite));
        this.addUserPage.assertGroupIsNotChecked(UserUtility.convertTestGroupName(oldSite));
    }
}