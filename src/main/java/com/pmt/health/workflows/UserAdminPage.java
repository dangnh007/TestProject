package com.pmt.health.workflows;

import com.pmt.health.exceptions.VibrentException;
import com.pmt.health.exceptions.VibrentJSONException;
import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.TestUser;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.ui.UserAdminSteps;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;
import org.testng.log4testng.Logger;

import java.io.IOException;

public class UserAdminPage {


    private final App app;
    private final WebbElement addUserButton;
    private final WebbElement loggedInHeading;
    private final WebbElement createdUser;


    Logger log = Logger.getLogger(LoginPage.class);
    private User user;
    private TestUser testUser;


    public UserAdminPage(App app, User user, TestUser testUser) {
        this.app = app;
        this.user = user;

        this.addUserButton = app.newElement(LocatorType.CLASSNAME, "add-user-button");
        this.loggedInHeading = app.newElement(LocatorType.XPATH, "//h1[text()='User Administration']");
        this.createdUser = app.newElement(LocatorType.XPATH, "//div[contains(text(), \""+testUser.getEmail()+"\")]");
    }

    /**
     * Clicks on the "Add User" button
     */
    public void addUser() {
        addUserButton.click();
    }

    /**
     * Waits for the header indicating the user has logged in to be displayed.
     */
    public void waitForLoginLoad() {
        loggedInHeading.waitFor().displayed();
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedIn() {
        loggedInHeading.assertState().displayed();
    }

    /**
     * Asserts that the created user is present by making sure it's email is present
     */
    public void assertCreatedUser() {
        createdUser.assertState().displayed();
        System.out.println(createdUser);

    }

}


