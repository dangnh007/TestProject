package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;

public class AddUserPage {

    private final App app;
    private final WebbElement firstNameInput;
    private final WebbElement lastNameInput;
    private final WebbElement emailInput;
    private final WebbElement rolesInput;
    private final WebbElement saveButton;

    private User user;

    public AddUserPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.firstNameInput = app.newElement(LocatorType.NAME, "firstName");
        this.lastNameInput = app.newElement(LocatorType.NAME, "lastName");
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.rolesInput = app.newElement(LocatorType.CSS, "input[role*=combobox]");
        this.saveButton = app.newElement(LocatorType.ID, "save");
    }

    /**
     * click on the checkbox with parameterised organization/awardee
     *
     * @param org passing organization value
     */
    public void checkAwardee(String org) {
        WebbElement awardee = app.newElement(LocatorType.ID, org);
        awardee.click();
    }

    /**
     * checks if element is present
     * and if it is, clicks on the save button
     */
    public void saveUser() {
        if (saveButton.is().present()) {
            saveButton.click();
        }
    }

    /**
     * input for the first name field
     *
     * @param firstName passing first name parameter
     */
    public void enterFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.type(firstName);
        if ("".equals(firstName)) {
            firstNameInput.type("a");
            firstNameInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * input for the last name field
     *
     * @param lastName passing last name parameter
     */
    public void enterLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.type(lastName);
        if ("".equals(lastName)) {
            lastNameInput.type("a");
            lastNameInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * input for the email field
     *
     * @param email passing email parameter
     */
    public void enterEmail(String email) {
        emailInput.type(email);
        if ("".equals(email)) {
            emailInput.type("a");
            emailInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * input for the user role field
     *
     * @param role passing user role parameter
     */
    public void selectRole(String role) {
        rolesInput.type(Keys.DELETE);
        rolesInput.type(role + Keys.ENTER);
        if ("".equals(role)) {
            rolesInput.type("a");
            rolesInput.type(Keys.BACK_SPACE);
        }
    }

    public void inputUserInfo(String firstName, String lastName, String email, String role, String org) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        selectRole(role);
        checkAwardee(org);
    }
}


