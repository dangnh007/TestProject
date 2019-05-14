package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.openqa.selenium.Keys;
import org.testng.log4testng.Logger;

/**
 * @author jeff
 */
public class LoginPage {

    private static final String COOKIE_NAME = "token";
    private static final String SCREEN_SIZE = "screensize";

    private final WebbElement loginPage;
    private final WebbElement emailMessage;
    private final WebbElement loggedInHeading;
    private final App app;
    private final WebbElement emailInput;
    private final WebbElement passwordInput;
    private final WebbElement loginButton;
    private final WebbElement mfaInput;
    Logger log = Logger.getLogger(LoginPage.class);
    private User user;

    /**
     * @param app the web instance of the application under test
     */
    public LoginPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.loginPage = app.newElement(LocatorType.CLASSNAME, "signin-signup");
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.passwordInput = app.newElement(LocatorType.NAME, "password");
        this.loginButton = app.newElement(LocatorType.CLASSNAME, "submit-button");
        this.mfaInput = app.newElement(LocatorType.NAME, "enter6DigitCode");
        this.emailMessage =
                app.newElement(LocatorType.ID, "usernameEmail").findChild(app.newElement(LocatorType.TAGNAME, "div"));
        this.loggedInHeading = app.newElement(LocatorType.XPATH, "//h1[text()='User Administration']");
    }

    public void enterEmail(String username) {
        emailInput.type(username);
        if ("".equals(username)) {
            emailInput.type("a");
            emailInput.type(Keys.BACK_SPACE);
        }
    }

    public void enterPassword(String password) {
        passwordInput.type(password);
        if ("".equals(password)) {
            passwordInput.type("a");
            passwordInput.type(Keys.BACK_SPACE);
        }
    }

    public Element getEmail() {
        emailInput.waitFor().displayed();
        return emailInput;
    }

    public Element getLoginButton() {
        return loginButton;
    }

    public Element getPasswordInput() {
        passwordInput.waitFor().displayed();
        return passwordInput;
    }

    /**
     * Opens the initial web environment, and sets the screensize according to passed in parameters
     */
    public void loadEnvironment() {
        if (System.getProperty(SCREEN_SIZE) != null && !"".equals(System.getProperty(SCREEN_SIZE))) {
            try {
                int width = Integer.parseInt(System.getProperty(SCREEN_SIZE).split("x")[0]);
                int height = Integer.parseInt(System.getProperty(SCREEN_SIZE).split("x")[1]);
                app.resize(width, height);
            } catch (Exception e) {
                log.debug(e);
                app.maximize();
            }
        } else {
            app.maximize();
        }
        app.goToURL(app.getSite().toString());
        clearCookies();
    }

    /**
     * Clears all cookies from the browser.
     */
    public void clearCookies() {
        app.deleteAllCookies();
    }

    public void logout() {
        // TODO
    }

    /**
     * Waits for the header indicating the user has logged in to be displayed.
     */
    public void waitForLoginLoad() {
        loggedInHeading.waitFor().displayed();
    }

    /**
     * Activates the login control for the user parameter
     *
     * @param user A User with an email and password
     */
    public void login(User user) {
        enterEmail(user.getEmail());
        enterPassword(user.getPassword());
        getLoginButton().waitFor().displayed();
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
        enterMFA(HTTP.obtainOath2Key());
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
    }

    private void enterMFA(String obtainOath2Key) {
        getMFA().type(obtainOath2Key);
    }

    private Element getMFA() {
        return mfaInput;
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedIn() {
        loggedInHeading.assertState().displayed();
    }
}
