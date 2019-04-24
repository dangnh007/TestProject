package com.pmt.health.workflows.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.LoginWorkflow;
import cucumber.api.PendingException;
import org.openqa.selenium.Keys;
import org.testng.log4testng.Logger;

import javax.mail.Message;

/**
 * @author jeff
 */
public class LoginPageWeb extends LoginWorkflow {

    private static final String COOKIE_NAME = "token";
    private static final String SCREEN_SIZE = "screensize";

    private final WebbElement loginPage;
    private final WebbElement hiddenElement;
    private final WebbElement footer;
    private final WebbElement loginBanner;
    private final WebbElement emailMessage;
    private final WebbElement loggedInHeading;
    private final WebbElement rememberMeCheck;
    private final WebbElement lockedUserMessage;
    Logger log = Logger.getLogger(LoginPageWeb.class);
    private User user;

    /**
     * @param app the web instance of the application under test
     */
    public LoginPageWeb(WebApp app, User user) {
        this.app = app;
        this.user = user;
        this.loginPage = app.newElement(LocatorType.CLASSNAME, "signin-signup");
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.passwordInput = app.newElement(LocatorType.NAME, "password");
        this.loginButton = app.newElement(LocatorType.CLASSNAME, "submit-button-login");
        this.hiddenElement = app.newElement(LocatorType.CSS, "div[aria-hidden='false']");
        this.footer = app.newElement(LocatorType.DATATARGET, "@footer|signedIn|copyrightNotice");
        this.loginBanner = app.newElement(LocatorType.ID, "bannerMessage");
        this.mfaInput = app.newElement(LocatorType.NAME, "enter6DigitCode");
        this.emailMessage =
                app.newElement(LocatorType.ID, "usernameEmail").findChild(app.newElement(LocatorType.TAGNAME, "div"));
        this.loggedInHeading = app.newElement(LocatorType.XPATH, "//h1[text()='User Administration']");
        this.rememberMeCheck = app.newElement(LocatorType.ID, "persist");
        this.forgotPasswordLink = app.newElement(LocatorType.DATATARGET, "signin.action.forgotPassword");
        this.userEmailRecover = app.newElement(LocatorType.XPATH, "//input[@id='username']");
        this.resetPasswordButton = app.newElement(LocatorType.XPATH, "//button[@type='submit']");
        this.newPasswordInput = app.newElement(LocatorType.NAME, "newPassword");
        this.managerAccountLink = app.newElement(LocatorType.ID, "manager-account");
        this.lockedUserMessage = app.newElement(LocatorType.XPATH,
                " //span[contains(text(), 'We’re sorry! Your account has been locked out. Please reset your password after one hour or contact')]");
        this.androidAppStoreLink = app.newElement(LocatorType.DATATARGET, "@login|link|playStore");
        this.iosAppStoreLink = app.newElement(LocatorType.DATATARGET, "@login|link|appStore");
        this.companyLogoLoginPage = app.newElement(LocatorType.DATATARGET, "@header|link|logo");
        this.forgotPasswordTitle = app.newElement(LocatorType.XPATH, "//div[contains(text(), 'Forgot Password?')]");
        this.signInLink = app.newElement(LocatorType.XPATH, "//span[contains(text(), 'Sign In')]");
        this.titleTextOnTheLoginPage = app.newElement(LocatorType.XPATH, "//h1[@class='instructions-title ng-scope ng-binding']");
        this.assertWelcomeTextOnLoginPage = app.newElement(LocatorType.XPATH, "//h3[@class='instructions-text ng-scope ng-binding']");
        this.forgotPasswordError = app.newElement(LocatorType.DATATARGET, "@username|error|message");
        this.signInHeaderTitle = app.newElement(LocatorType.DATATARGET, "signin.cta.header");
        this.signInHeaderText = app.newElement(LocatorType.DATATARGET, "signin.cta.text");
        this.signInTextMobileApps = app.newElement(LocatorType.DATATARGET, "signin.text.mobileapps");
        this.signInLinksGooglePlay = app.newElement(LocatorType.DATATARGET, "signin.action.googleplay");
        this.signInLinksAppStore = app.newElement(LocatorType.DATATARGET, "signin.action.appstore");
        this.forgotPasswordTitleText = app.newElement(LocatorType.DATATARGET, "forgotPassword.title");
        this.forgotPasswordInstructionsText = app.newElement(LocatorType.DATATARGET, "forgotPassword.messages.loginHelp");
        this.forgotPasswordInputLabel = app.newElement(LocatorType.DATATARGET, "signin.fields.login.label");
        this.forgotPasswordButtonSubmit = app.newElement(LocatorType.DATATARGET, "forgotPassword.button.submit");
        this.showPasswordButton = app.newElement(LocatorType.XPATH, "//a[contains (text(), 'Show')]");
        this.showPasswordButton = app.newElement(LocatorType.XPATH, "//a[contains (text(), 'Show')]");
        this.passwordFieldVisibleText = app.newElement(LocatorType.XPATH, "//input[@id='userPassword' and @type='text']");
    }

    @Override
    public void assertBadLogin() {
        hiddenElement.waitFor().displayed();
        loginBanner.assertEquals()
                .text("Invalid email address or password. Please try again or click forgot password.");
    }

    /**
     * Asserts that the login error message equals the message expected
     *
     * @param message string of the message to compare to the login error message
     */
    @Override
    public void assertBadLogin(String message) {
        lockedUserMessage.waitFor().displayed();
        hiddenElement.waitFor().present();
        loginBanner.assertEquals().text(message);
    }

    @Override
    public void assertEmailError(String error) {
        emailMessage.assertEquals().text(error);
        emailMessage.assertContains().clazz("input-error-message");
    }

    @Override
    public void assertLoggedOut() {
        loginPage.assertState().displayed();
    }

    @Override
    public void assertLoginButtonDisabled() {
        loginButton.assertState().notEnabled();
    }

    @Override
    public void assertNoEmailError() {
        emailMessage.assertState().notPresent();
    }

    @Override
    public void enterEmail(String username) {
        emailInput.type(username);
        if ("".equals(username)) {
            emailInput.type("a");
            emailInput.type(Keys.BACK_SPACE);
        }
    }

    @Override
    public void enterPassword(String password) {
        passwordInput.type(password);
        if ("".equals(password)) {
            passwordInput.type("a");
            passwordInput.type(Keys.BACK_SPACE);
        }
    }

    @Override
    public Element getEmail() {
        emailInput.waitFor().displayed();
        return emailInput;
    }

    /**
     * Returns spanish and english footer.
     *
     * @return footer element
     */
    @Override
    public Element getFooter() {
        return footer;
    }

    @Override
    public Element getLoginButton() {
        return loginButton;
    }

    @Override
    public Element getPasswordInput() {
        passwordInput.waitFor().displayed();
        return passwordInput;
    }

    /**
     * Opens the initial web environment, and sets the screensize according to passed in parameters
     */
    @Override
    public void loadEnvironment() {
        if (System.getProperty(SCREEN_SIZE) != null && !"".equals(System.getProperty(SCREEN_SIZE))) {
            try {
                int width = Integer.parseInt(System.getProperty(SCREEN_SIZE).split("x")[0]);
                int height = Integer.parseInt(System.getProperty(SCREEN_SIZE).split("x")[1]);
                ((WebApp) app).resize(width, height);
            } catch (Exception e) {
                log.debug(e);
                ((WebApp) app).maximize();
            }
        } else {
            ((WebApp) app).maximize();
        }
        clearCookies();
        if (user.getVanityCode() != null) {
            ((WebApp) app).goToURL(app.getSite().toString() + "#/register?c=" + user.getVanityCode());
        } else {
            ((WebApp) app).goToURL(app.getSite().toString());
        }
        if (Property.isAlertEnabled()) {
            // this internally checks if alert is present and tries to dismiss it only if it is present
            ((WebApp) app).acceptAlert();
        }
        if (Property.maintenanceMode()) {
            //fix to allow testing on 'blocked' sites
            ((WebApp) app).setCookie(Property.getMaintenanceCookie());
            app.refresh();
        }
        // changing the logic to look for second alert as it seems to be coming up sometime once and sometimes twice for automated runs
        if (Property.isAlertEnabled() && !this.companyLogoLoginPage.is().present()) {
            // this internally checks if alert is present and tries to dismiss it only if it is present
            ((WebApp) app).acceptAlert();
        }
    }

    @Override
    public void logout() {
        // TODO
    }

    /**
     * Waits for the header indicating the user has logged in to be displayed.
     */
    @Override
    public void waitForLoginLoad() {
        loggedInHeading.waitFor().displayed();
    }

    /**
     * Checks the 'Keep me logged in' box on the login page.
     */
    @Override
    public void selectRememberMe() {
        rememberMeCheck.waitFor().enabled();
        rememberMeCheck.click();
    }

    /**
     * Clears all cookies from the browser.
     */
    @Override
    public void clearCookies() {
        ((WebApp) app).deleteAllCookies();
    }

    /**
     * Asserts that cookies have been stored in the browser.
     */
    @Override
    public void assertCookiesStored() {
        app.azzert().cookieExists(COOKIE_NAME);
    }

    /**
     * Asserts that cookies have not been stored in the browser.
     */
    @Override
    public void assertCookiesNotStored() {
        app.azzert().cookieNotExists(COOKIE_NAME);
    }

    /**
     * Assert the password reset message is displayed
     */
    @Override
    public void assertPasswordResetMessageDisplayed() {
        assertBadLogin(
                "We’re sorry! Your account has been locked out. Please reset your password after one hour or contact help@joinallofus.org.");
    }

    /**
     * Answers the security questions for a certain user
     *
     * @param user the user object used to get the answers for the security questions
     */
    @Override
    public void fillOutSecurityQuestions(User user) {
        securityQuestion1.type(user.getSecurityAnswer(1));
        securityQuestion2.type(user.getSecurityAnswer(2));
        securityQuestion3.type(user.getSecurityAnswer(3));
        resetPasswordButton.click();
    }

    /**
     * Assert the password reset success message is displayed
     */
    @Override
    public void assertPasswordResetSuccess() {
        throw new PendingException();
    }


    /**
     * Clicks the Forgot Password Link
     * Types in the email to reset the password and submits the form
     *
     * @param user provides the information for the current user
     */
    @Override
    public void fillOutEmail(User user) {
        forgotPasswordLink.click();
        userEmailRecover.type(user.getEmail());
        resetPasswordButton.click();
    }

    /**
     * Fills out security questions and appropriate answers for them
     *
     * @param user provides the information for the current user
     */
    @Override
    public void fillOutSecurityAnswers(User user, String url) {
        ((WebApp) app).goToURL(url);
        securityAnswer1.type(user.getSecurityAnswer(1));
        securityAnswer2.type(user.getSecurityAnswer(2));
        securityAnswer3.type(user.getSecurityAnswer(3));
        resetPasswordButton.click();
    }

    /**
     * Types in a new updated value for the password
     * Resets the password for the current user
     *
     * @param user provides the information for the current user
     */
    @Override
    public void resetPassword(User user) {
        newPasswordInput.type(user.getPassword());
        resetPasswordButton.click();
    }

    /**
     * Assert that all fields in the security questions form are displayed
     */
    @Override
    public void assertSecurityQuestionsFormPresent() {
        securityQuestion1.assertState().displayed();
        securityAnswer1.assertState().displayed();
        securityQuestion2.assertState().displayed();
        securityAnswer2.assertState().displayed();
        securityQuestion3.assertState().displayed();
        securityAnswer3.assertState().displayed();
    }

    /**
     * Assert the security question error message is displayed
     *
     * @param message string of the error message to check for
     */
    @Override
    public void assertSecurityQuestionError(String message) {
        app.azzert().textPresent(message);
    }

    @Override
    public Element getForgotPasswordLink() {
        return forgotPasswordLink;
    }

    /**
     * Goes to the url retrieved from the email to reset the password
     *
     * @param updatedPassword the value of the new password
     */
    @Override
    public void updatePassword(User user, String updatedPassword) {
        user.setPassword(updatedPassword);
        newPasswordInput.type(user.getPassword());
    }

    /**
     * Verifies if the reset password button is enabled or disabled
     *
     * @param status is either "enabled" or "disabled"
     *               asserting the status of the button
     */
    @Override
    public void assertResetPasswordButtonStatus(String status) {
        if ("disabled".equals(status)) {
            resetPasswordButton.assertState().notEnabled();
        }
        if ("enabled".equals(status)) {
            resetPasswordButton.assertState().enabled();
        }
    }

    /**
     * Navigate to the url
     */
    @Override
    public void goToResetPasswordPage(String url) {
        ((WebApp) app).goToURL(url);
    }

    /**
     * Tests whether an email was received by determining if the array contains an object
     *
     * @param messages An Array (potentially empty) of Message objects.
     */
    @Override
    public void assertEmailReceived(Message[] messages) {
        app.azzert().emailReceived(messages);
    }

    /**
     * Validates that user can see password as plain text
     */
    @Override
    public void iSeeUsersPasswordAsAText(User user) {
        passwordFieldVisibleText.waitFor().displayed();
        passwordFieldVisibleText.assertState().displayed();
    }
}
