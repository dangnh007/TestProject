package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;
import org.testng.log4testng.Logger;

public class AddUserPage {


    private final App app;
    private final WebbElement firstNameInput;
    private final WebbElement lastNameInput;
    private final WebbElement emailInput;
    private final WebbElement rolesInput;


    Logger log = Logger.getLogger(LoginPage.class);
    private User user;

    public AddUserPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.firstNameInput = app.newElement(LocatorType.NAME, "firstName");
        this.lastNameInput = app.newElement(LocatorType.NAME, "lastName");
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.rolesInput = app.newElement(LocatorType.XPATH, "//input[contains(@role, 'combobox')]");
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


