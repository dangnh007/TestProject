package com.pmt.health.workflows;

import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import cucumber.api.PendingException;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.log4testng.Logger;

import javax.mail.Message;
import java.io.IOException;

/**
 * @author jeff
 */
public abstract class LoginWorkflow extends Workflow {

    private static final Logger log = Logger.getLogger(LoginWorkflow.class);

    /**
     * The Element containing the emailInput field
     */
    protected Element emailInput;

    /**
     * The Element containing the emailInput field text
     */
    protected Element emailOrPhoneInputText;

    /**
     * The Element containing forgot password title on the forgot password subpage
     */
    protected Element forgotPasswordTitle;

    /**
     * The Element containing sign in button to go back to the login page from the forgot password subpage
     */
    protected Element signInLink;

    /**
     * The Element containing the first name input field
     */
    protected Element firstNameInput;

    /**
     * The Element containing error message on forgot password page
     */
    protected Element forgotPasswordError;

    /**
     * The Element containing the last name input field
     */
    protected Element lastNameInput;

    /**
     * The Element containing the passwordInput field
     */
    protected Element passwordInput;

    /**
     * The Element containing the mfa input field
     */
    protected Element mfaInput;


    /**
     * The Element containing the passwordInput field text
     */
    protected Element passwordInputText;

    /**
     * The Element containing emailInput field ,if you have value in it
     */
    protected Element passwordInputEnabled;

    /**
     * The Element containing maximum number of password attempts
     */
    protected Element numberOfAttempts;

    /**
     * The Element containing the loginButton field
     */
    protected Element loginButton;

    /**
     * The Element containing the errorMessage field
     */
    protected Element errorMessage;

    /**
     * The Element containing the logoutButton field
     */
    protected Element logoutButton;

    /**
     * The Element containing the securityQuestion1 field
     */
    protected Element securityQuestion1;

    /**
     * The Element containing the securityQuestion2 field
     */
    protected Element securityQuestion2;

    /**
     * The Element containing the securityQuestion3 field
     */
    protected Element securityQuestion3;

    /**
     * The Element containing the supportQuestion field
     */
    protected Element supportQuestion;

    /**
     * The Element containing the securityAnswer1 field
     */
    protected Element securityAnswer1;

    /**
     * The Element containing the securityAnswer2 field
     */
    protected Element securityAnswer2;

    /**
     * The Element containing the securityAnswer3 field
     */
    protected Element securityAnswer3;

    /**
     * The Element containing the supportAnswer
     */
    protected Element supportAnswer;

    /**
     * The Element containing the forgotPasswordLink field
     */
    protected Element forgotPasswordLink;

    /**
     * The Element containing the userEmailRecover field
     */
    protected Element userEmailRecover;

    /**
     * The Element containing the resetPasswordButton field
     */
    protected Element resetPasswordButton;

    /**
     * The Element containing the resetPasswordInput field
     */
    protected Element resetPasswordInput;

    /**
     * The Element containing the resetPasswordVerifyInput field
     */
    protected Element resetPasswordVerifyInput;

    protected Element managerAccountLink;

    /**
     * The Element containing the app store link for Android
     */
    protected Element androidAppStoreLink;
    /**
     * The element containing the app store link for IOS
     */
    protected Element iosAppStoreLink;

    /**
     * The Element containing the Navigation Bar Title for Android and IOS
     */
    protected Element navigationBarTitle;

    /**
     * The Element containing the progressBar
     */
    protected Element progressBar;

    /**
     * The Element containing the AllOfUsLogo
     */
    protected Element companyLogoLoginPage;
    /**
     * The Element containing title text on the login page
     */
    protected Element titleTextOnTheLoginPage;
    /**
     * The Element containing welcome text on the login page
     */
    protected Element assertWelcomeTextOnLoginPage;

    /**
     * Retrieves the managerAccountLink header
     *
     * @return An Element containing the managerAccountLink
     */
    public Element getManagerAccountLink() {
        return managerAccountLink;
    }

    /**
     * The large header on the left of login page and forgot password pages
     */
    protected Element signInHeaderTitle;

    /**
     * Text below large header on the left of login and forgot password pages
     */
    protected Element signInHeaderText;

    /**
     * Text on lower part of the screen, preceding the links to app stores for IOS and Android
     */
    protected Element signInTextMobileApps;

    /**
     * Link on lower part of the screen leading to Google Play store
     */
    protected Element signInLinksGooglePlay;

    /**
     * Link on lower part of the screen leading to Apple's app store
     */
    protected Element signInLinksAppStore;

    /**
     * Title on forgot password page
     */
    protected Element forgotPasswordTitleText;

    /**
     * Instructional text under title on forgot password page
     */
    protected Element forgotPasswordInstructionsText;

    /**
     * Label for input field on forgot password page
     */
    protected Element forgotPasswordInputLabel;

    /**
     * Button submit on forgot password page
     */
    protected Element forgotPasswordButtonSubmit;

    /**
     * The Element containing the user lockout banner text
     */
    protected Element lockoutErrorMessage;

    /**
     * Verifies that the error message is displayed when user is trying to sign in with invalid credentials
     *
     * @return
     */
    public Element getErrorMessage() {
        return errorMessage;
    }

    /**
     * Asserts that the error message indicating a bad email or password is currently displayed.
     */
    public abstract void assertBadLogin();

    /**
     * Assert the user was not able to log in, checking for a message
     *
     * @param message a string of the message to check for
     */
    public abstract void assertBadLogin(String message);

    /**
     * TODO Make this more general, or rename to show specific function.
     * Asserts that an error corresponding to the email input is currently displayed.
     *
     * @param error text of the error message that should be displayed
     */
    public abstract void assertEmailError(String error);

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public void assertLoggedIn() {
        managerAccountLink.assertState().displayed();
    }

    /**
     * Asserts that the current user is logged out by making sure the login page is displayed.
     */
    public abstract void assertLoggedOut();

    /**
     * Asserts that the login button is currently unable to be clicked, used in the case of proper login format not being followed.
     */
    public abstract void assertLoginButtonDisabled();

    /**
     * TODO Make more general, or rename to show specific function.
     * Asserts that no email error is currently present.
     */
    public abstract void assertNoEmailError();

    /**
     * Enters the username parameter into the Email address or Phone number field
     *
     * @param username the username to type in
     */
    public void enterEmail(String username) {
        getEmail().type(username);
    }

    /**
     * Enters the password parameter into the Password Field
     *
     * @param password the password to type in
     */
    public void enterPassword(String password) {
        getPasswordInput().type(password);
    }

    /**
     * Retrieves the emailInput control
     *
     * @return An Element containing the emailInput control
     */
    public Element getEmail() {
        return emailInput;
    }

    public Element getFirstNameInput() {
        return firstNameInput;
    }

    public Element getLastNameInput() {
        return lastNameInput;
    }

    /**
     * Retrieves the login button
     *
     * @return An Element containing the login button
     */
    public Element getLoginButton() {
        return loginButton;
    }

    /**
     * Retrieves the page footer
     *
     * @return An Element containing the footer
     */
    public abstract Element getFooter();

    /**
     * Retrieves the password input
     *
     * @return An Element containing the password input
     */
    public Element getPasswordInput() {
        return passwordInput;
    }

    /**
     * Gets the link to the password reset page
     *
     * @return An Element containing the link
     */
    public abstract Element getForgotPasswordLink();

    /**
     * Initialize our environment
     */
    public abstract void loadEnvironment();

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
     * Clicks the logout button
     */
    public abstract void logout();

    /**
     * Waits patiently for the login page to finish loading
     */
    public abstract void waitForLoginLoad();

    /**
     * Selects the 'Remember Me' option on the login page to remember user credentials.
     * (Implementation is not possible for mobile devices, do not use in mobile contexts.)
     */
    public abstract void selectRememberMe();

    /**
     * Clears all cookies on the browser / device.
     * (Implementation is not possible for mobile devices, do not use in mobile contexts.)
     */
    public void clearCookies() {
        // Implementation may not be possible for mobile
        throw new PendingException();
    }

    /**
     * Asserts that cookies have been stored on the browser.
     * (Implementation is not possible for mobile devices, do not use in mobile contexts.)
     */
    public void assertCookiesStored() {
        // Implementation may not be possible
        throw new PendingException();
    }

    /**
     * Asserts that cookies have not been stored on the browser / device.
     * (Implementation is not possible for mobile devices, do not use in mobile contexts.)
     */
    public void assertCookiesNotStored() {
        // Implementation may not be possible
        throw new PendingException();
    }

    /**
     * Log in, then press the login button X times
     *
     * @param user    the user object to be logged in with
     * @param attempt how many times to attempt to login
     */
    public void loginXTimes(User user, int attempt) {
        enterEmail(user.getEmail());
        String password = user.getPassword();
        for (int i = 0; i < attempt && emailInput.is().displayed(); i++) {
            getEmail().clear();
            enterEmail(user.getEmail());
            getPasswordInput().clear();
            enterPassword(password);
            getLoginButton().click();
        }
    }

    /**
     * Determines if the password reset message is displayed
     */
    public abstract void assertPasswordResetMessageDisplayed();

    /**
     * Fills out security questions for the user
     *
     * @param user The User to get questions and answers
     */
    public abstract void fillOutSecurityQuestions(User user);

    /**
     * Resets the user password
     *
     * @param user The User to change
     */
    public abstract void resetPassword(User user);

    /**
     * Determine if the password reset was successful
     */
    public abstract void assertPasswordResetSuccess();

    /**
     * Tests whether an email was received by determining if the array contains an object
     *
     * @param m An Array (potentially empty) of Message objects.
     */
    public abstract void assertEmailReceived(Message[] m);

    /**
     * Assert that all fields in the security questions form are displayed
     */
    public void assertSecurityQuestionsFormPresent() {
        securityQuestion1.assertState().displayed();
        securityAnswer1.assertState().displayed();
        securityQuestion2.assertState().displayed();
        securityAnswer2.assertState().displayed();
        securityQuestion3.assertState().displayed();
        securityAnswer3.assertState().displayed();
    }

    /**
     * Assert the security questions are present and displayed
     */
    public void assertSecurityQuestionsPresent() {
        securityQuestion1.assertState().displayed();
        securityQuestion2.assertState().displayed();
        securityQuestion3.assertState().displayed();
    }

    /**
     * Fills out the user email address in the form
     *
     * @param user The user to retrieve the email address from
     */
    public abstract void fillOutEmail(User user);

    /**
     * Fills out the security answers
     *
     * @param user to use for questions and answers
     * @param url  the url to visit to fill out the security questions
     */
    public abstract void fillOutSecurityAnswers(User user, String url);

    /**
     * Determine if there is an error message displayed
     *
     * @param message The error message to search for
     */
    public abstract void assertSecurityQuestionError(String message);

    /**
     * Goes to the url retrieved from the email to reset the password
     *
     * @param user            to use for questions and answers
     * @param updatedPassword the value of the new password
     */
    public abstract void updatePassword(User user, String updatedPassword);

    /**
     * Verifies if the reset password button is enabled or disabled
     *
     * @param status is either "enabled" or "disabled"
     *               asserting the status of the button
     */
    public void assertResetPasswordButtonStatus(String status) {
        throw new PendingException();
    }

    /**
     * Click the password reset button
     */
    public void clickPasswordReset() {
        resetPasswordButton.click();
    }

    /**
     * Navigate to the reset password page, grabs url from the previous call to the
     * users email; only valid on the web
     *
     * @param url the url to visit to fill out the security questions
     */
    public void goToResetPasswordPage(String url) {
        throw new PendingException();
    }

    /**
     * Verifies if the App Store links for Android and IOS is present on Sign in page
     * only valid on the web
     */
    public void assertAppStoreLinksPresent() {
        androidAppStoreLink.waitFor().displayed();
        androidAppStoreLink.assertState().displayed();
        iosAppStoreLink.waitFor().displayed();
        iosAppStoreLink.assertState().displayed();
    }

    /**
     * Enters the first name into the first name input field
     *
     * @param firstName
     */
    public void enterFirstName(String firstName) {
        getFirstNameInput().type(firstName);
    }

    /**
     * Enters the last name into the first name input field
     *
     * @param lastName
     */
    public void enterLastName(String lastName) {
        getLastNameInput().type(lastName);
    }

    /**
     * Accesses the forgot password page by clicking "Forgot Your Password?" link on the login page
     */
    public void clickForgotPasswordLink() {
        forgotPasswordLink.click();
    }

    /**
     * Verifies user is on the forgot password sub page
     */
    public void verifyForgotPasswordPage() {
        forgotPasswordTitle.assertState().displayed();
        signInLink.assertState().displayed();
        resetPasswordButton.assertState().displayed();
    }

    /**
     * Saves the initial redux state
     */
    public void saveInitialReduxState(User user, DeviceController deviceController) {
        WebStorage webStorage = (WebStorage) new Augmenter().augment(deviceController.getDriver());
        LocalStorage localStorage = webStorage.getLocalStorage();
        for (String key : localStorage.keySet()) {
            if ("reduxState".equals(key) && !"".equals(localStorage.getItem(key))) {
                user.setCleanReduxState(localStorage.getItem(key));
                return;
            }
        }
    }

    /**
     * Saves the user redux state, to be called after logging in
     */
    public void saveUserReduxState(User user, DeviceController deviceController) {
        WebStorage webStorage = (WebStorage) new Augmenter().augment(deviceController.getDriver());
        LocalStorage localStorage = webStorage.getLocalStorage();
        for (String key : localStorage.keySet()) {
            if ("reduxState".equals(key) && !"".equals(localStorage.getItem(key))) {
                user.setReduxState(localStorage.getItem(key));
                return;
            }
        }
    }

    /**
     * Validates the tittle text on the login page
     *
     * @param text
     */
    public void assertTitleTextOnLoginPage(String text) {
        titleTextOnTheLoginPage.assertContains().text(text);
    }

    /**
     * The Element containing welcome text on the login page
     */
    protected Element welcomeTextOnLoginPage;

    /**
     * Validates the welcome text on the login page
     *
     * @param text
     */
    public void assertWelcomeTextOnLoginPage(String text) {
        welcomeTextOnLoginPage.assertContains().text(text);
    }

    /**
     * Verifies if the error message on the forgot password page displayed
     */
    public void assertForgotPasswordError(String error) {
        forgotPasswordError.assertState().displayed();
        forgotPasswordError.assertContains().text(error);
    }

    /**
     * Verifies that Strings rendered on Forgot Password page correspond to values coming back from API
     * via String Resources functionality for element's keys
     */
    public void assertAllStringMatchApiForgotPasswordPage() throws IOException {
        // this could have a common functionality, will be changed once ATA-2679 has gone through testing stage
        throw new PendingException();
    }

    /**
     * Verifies that Strings rendered on Login page correspond to values coming back from API
     * via String Resources functionality for element's keys
     */
    public void assertAllStringMatchApiLoginPage() throws IOException {
        // this could have a common functionality, will be changed once ATA-2679 has gone through testing stage
        throw new PendingException();
    }

    /**
     * The element contains new password input
     */
    protected Element newPasswordInput;

    /**
     * Goes to the url retrieved from the email to reset the password
     *
     * @param user        to use for questions and answers
     * @param newPassword the value of the new password
     */
    public void updateUserPasswordAndDoNotProceed(User user, String newPassword) {
        user.setPassword(newPassword);
        newPasswordInput.type(user.getPassword());
    }

    /**
     * The Element containing the show password button
     */
    protected Element showPasswordButton;

    /**
     * The Element containing the hide password button
     */
    protected Element hidePasswordButton;

    /**
     * The Element containing the hide password key
     */
    protected Element keyHidePasswordButton;

    /**
     * Clicks on show or hide password button depending on the choise provided in step
     */
    public void activateShowOrHidePasswordButton(String option) throws IOException {
        switch (option.toLowerCase()) {
            case "show":
                showPasswordButton.waitFor().displayed();
                showPasswordButton.click();
                break;
            case "hide":
                showPasswordButton.waitFor().displayed();
                showPasswordButton.click();
                hidePasswordButton.click();
                break;
            default:
                throw new PendingException();
        }
    }

    /**
     * Validates that user can see show or hide password button
     */
    public void iSeeShowOrHidePasswordButton(String option) throws IOException {
        switch (option.toLowerCase()) {
            case "show":
                showPasswordButton.waitFor().displayed();
                showPasswordButton.assertEquals().text(option);
                break;
            case "hide":
                hidePasswordButton.waitFor().displayed();
                hidePasswordButton.assertEquals().text(option);
                break;
            default:
                throw new PendingException();
        }

    }

    /**
     * The Element containing the password as a plain text inside password field
     */
    protected Element passwordFieldVisibleText;

    /**
     * Validates that user can see password as plain text
     */
    public abstract void iSeeUsersPasswordAsAText(User user) throws IOException;

    /**
     * Provides user email and password without proceeding
     */
    public void iProvideLoginInformationWithoutProceeding(User user) throws IOException {
        getEmail().clear();
        enterEmail(user.getEmail());
        getPasswordInput().clear();
        enterPassword(user.getPassword());
    }
}
