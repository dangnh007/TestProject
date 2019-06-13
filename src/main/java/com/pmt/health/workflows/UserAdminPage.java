package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class UserAdminPage {

    private final App app;
    private final WebbElement addUserButton;
    private final WebbElement loggedInHeadingAdmin;
    private final WebbElement createdUser;
    private final WebbElement userAdminButton;
    private final WebbElement spinner;
    private final WebbElement createdSortButton;

    private User user;

    public UserAdminPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.addUserButton = app.newElement(LocatorType.CLASSNAME, "add-user-button");
        this.loggedInHeadingAdmin = app.newElement(LocatorType.XPATH, "//h1[text()='Reports']");
        this.createdUser = app.newElement(LocatorType.XPATH, "//div[contains(text(), \"" + user.getEmail() + "\")]");
        this.createdSortButton = app.newElement(LocatorType.CSS, "th[data-field='createdDate']");
        this.userAdminButton = app.newElement(LocatorType.CSS, "ul li:nth-of-type(4) a");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
    }

    public void userAdmin() {
        userAdminButton.click();
        addUserButton.hover();
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
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedIn() {
        loggedInHeadingAdmin.assertState().displayed();
    }

    /**
     * Asserts that the created user is present by making sure it's email is present
     */
    public void assertCreatedUser() {
        spinner.waitFor().notDisplayed();
        createdSortButton.click();
        createdUser.assertState().displayed();
    }
}


