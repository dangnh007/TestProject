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

    public void checkAwardee(String org) {
        WebbElement awardee = app.newElement(LocatorType.ID,org);
        awardee.click();
    }

    public void saveUser() {
        if (saveButton.is().present()) {
            saveButton.click();
        }
    }

    public void enterFirstName(String firstName) {
        firstNameInput.type(firstName);
        if ("".equals(firstName)) {
            firstNameInput.type("a");
            firstNameInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterLastName(String lastName) {
        lastNameInput.type(lastName);
        if ("".equals(lastName)) {
            lastNameInput.type("a");
            lastNameInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterEmail(String email) {
        emailInput.type(email);
        if ("".equals(email)) {
            emailInput.type("a");
            emailInput.type(Keys.BACK_SPACE);
        }
    }

    public void selectRole(String role) {
        rolesInput.type(role + Keys.ENTER);
        if ("".equals(role)) {
            rolesInput.type("a");
            rolesInput.type(Keys.BACK_SPACE);
        }
    }
}


