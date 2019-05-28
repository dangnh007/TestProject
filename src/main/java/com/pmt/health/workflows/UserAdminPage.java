package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;
import org.testng.log4testng.Logger;

public class UserAdminPage {


    private final App app;
    private final WebbElement addUserButton;
    private final WebbElement loggedInHeading;

    Logger log = Logger.getLogger(LoginPage.class);
    private User user;

    public UserAdminPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.addUserButton = app.newElement(LocatorType.CLASSNAME, "add-user-button");
        this.loggedInHeading = app.newElement(LocatorType.XPATH, "//h1[text()='User Administration']");
    }

    public void addUser() {
        if (addUserButton.is().present()) {
            addUserButton.click();
        }
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
}
