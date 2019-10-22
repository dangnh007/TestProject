package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Constants;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.utilities.Property;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.log4testng.Logger;

public class LoginPage {

    private static final String SCREEN_SIZE = "screensize";
    private static final String ADMIN_PASS = ".admin.pass";
    private final App app;
    private final WebbElement emailInput;
    private final WebbElement passwordInput;
    private final WebbElement loginButton;
    private final WebbElement mfaInput;
    private final WebbElement userDropdown;
    private final WebbElement logoutButton;
    private final WebbElement okButton;
    private final WebbElement passwordSet;
    private final WebbElement submitButton;
    private final WebbElement secretKey;
    private final WebbElement forgotPassword;
    private final WebbElement submitEmailAddress;
    private final WebbElement divMessage;

    Logger log = Logger.getLogger(LoginPage.class);
    private User user;

    /**
     * @param app the web instance of the application under test
     */
    public LoginPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.passwordInput = app.newElement(LocatorType.NAME, "password");
        this.loginButton = app.newElement(LocatorType.CLASSNAME, "submit-button");
        this.mfaInput = app.newElement(LocatorType.NAME, "enter6DigitCode");
        this.userDropdown = app.newElement(LocatorType.CSS, "button[class='dropdown-toggle btn btn-default']");
        this.logoutButton = app.newElement(LocatorType.XPATH, "//a[text()='Log out']");
        this.okButton = app.newElement(LocatorType.ID, "btnOK");
        this.secretKey = app.newElement(LocatorType.CSS, "div.scan-qr-code-secret");
        this.passwordSet = app.newElement(LocatorType.CSS, "input[name=password]");
        this.submitButton = app.newElement(LocatorType.CSS, "input.center-block.submit-button.btn.btn-primary");
        this.forgotPassword = app.newElement(LocatorType.CSS, "a[href*='/forgotpassword']");
        this.submitEmailAddress = app.newElement(LocatorType.CSS, "input[value*='Submit']");
        this.divMessage = app.newElement(LocatorType.CSS, "div.initial-pwd-msg.message");
    }

    /**
     * Activates the login flow for just created user
     */
    public void setLogin() {
        enterEmail(user.getEmail());
        enterPassword(user.getPassword());
        getLoginButton().waitFor().displayed();
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
        setKey();
        okButton.click();
        enterMFA(HTTP.obtainOath2KeyCreatedUser(user.getSecretKey()));
        getLoginButton().click();
    }

    /**
     * Activates the login control for default user "System Administrator"
     */
    public void loginAdmin() {
        enterEmail(Property.getProgramProperty(Configuration.getEnvironment() + ".admin.user"));
        enterPassword(Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
        getLoginButton().waitFor().displayed();
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
        enterMFA(HTTP.obtainOath2Key());
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
    }

    /**
     * Types email address with current parameter
     *
     * @param username username value
     */
    public void enterEmail(String username) {
        emailInput.type(username);
        if ("".equals(username)) {
            emailInput.type("a");
            emailInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * Types password with current parameter
     *
     * @param password password value
     */
    public void enterPassword(String password) {
        passwordInput.type(password);
        if ("".equals(password)) {
            passwordInput.type("a");
            passwordInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * locates login button
     */
    public Element getLoginButton() {
        return loginButton;
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
    }

    /**
     * calls method of app class to delete cookies of the session
     */
    public void deleteCookie() {
        app.deleteAllCookies();
    }

    /**
     * logs user out by clicking on dropdown and clicking on logout button
     */
    public void logout() {
        userDropdown.click();
        logoutButton.click();
    }

    /**
     * Activates the login control for the user parameter
     */
    public void login() {
        enterEmail(user.getEmail());
        enterPassword(Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
        getLoginButton().waitFor().displayed();
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
        enterMFA(HTTP.obtainOath2KeyCreatedUser(user.getSecretKey()));
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
    }

    /**
     * Activates the login control for the edited user parameter
     */
    public void loginEditedUser() {
        enterEmail(user.getSearchedUserEmail());
        enterPassword(Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
        getLoginButton().waitFor().displayed();
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
        enterMFA(HTTP.obtainOath2KeyCreatedUser(user.getSearchedUserSecret()));
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
    }

    /**
     * Types mfa with current parameter
     *
     * @param obtainOath2Key method which converts mfa
     */
    private void enterMFA(String obtainOath2Key) {
        getMFA().type(obtainOath2Key);
    }

    /**
     * locates mfa inout fields
     */
    private Element getMFA() {
        return mfaInput;
    }

    /**
     * clicks on submit button
     */
    public void clickSubmitButton() {
        submitButton.click();
    }

    /**
     * Types new password for newly created user
     */
    public void typeNewPassword() {
        passwordSet.type(Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
    }

    /**
     * sets secret key for newly created user via UI
     */
    public void setKey() {
        secretKey.waitFor().displayed();
        String key = secretKey.get().text();
        user.setSecretKey(key);
    }

    public void forgotPassword() {
        this.forgotPassword.waitFor().displayed();
        this.forgotPassword.click();
    }

    public void submitEmailAddress() {
        this.submitEmailAddress.waitFor().displayed();
        this.submitEmailAddress.click();
    }

    public void assertForgotPasswordMessage() {
        this.submitEmailAddress.waitFor().notDisplayed();
        this.divMessage.waitFor().displayed();
        String message = this.divMessage.get().text();
        Assert.assertTrue(message.contains(Constants.REQUEST_FORGOT_PASSWORD_MESSAGE));
    }

    /**
     * Opens the initial web environment, and sets the screensize according to passed in parameters
     */
    public void loadSpecifyEnvironment(String url) {
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
        app.goToURL(url);
    }

    /**
     * Login for user who has just been reset MFA code
     */
    public void loginResetMFAUser() {
        enterEmail(user.getSearchedUserEmail());
        enterPassword(Property.getProgramProperty(Configuration.getEnvironment() + ADMIN_PASS));
        getLoginButton().waitFor().displayed();
        if (getLoginButton().is().enabled()) {
            getLoginButton().click();
        }
        // Set new secret key for Searched User
        secretKey.waitFor().displayed();
        String key = secretKey.get().text();
        user.setSearchedUserSecret(key);
        // Enter new MFA code with new secret key
        okButton.click();
        enterMFA(HTTP.obtainOath2KeyCreatedUser(user.getSearchedUserSecret()));
        getLoginButton().click();
    }
}
