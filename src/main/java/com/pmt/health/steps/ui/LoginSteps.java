package com.pmt.health.steps.ui;

import com.pmt.health.exceptions.VibrentException;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.EMailUtility;
import com.pmt.health.utilities.Property;
import com.pmt.health.workflows.LoginWorkflow;
import com.pmt.health.workflows.WorkflowFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.Keys;
import org.springframework.context.annotation.Description;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSteps {

    private final User user;
    private final DeviceController deviceController;
    private final LoginWorkflow loginPage;
    private String passwordResetEmailContent;
    private String url;

    public LoginSteps(DeviceController deviceController, User user) {
        this.user = user;
        this.deviceController = deviceController;
        loginPage = WorkflowFactory.getLoginPage(this.deviceController, user);
    }

    @Description("Validating footers on registration page,based on selected language.")
    @Then("^I see the footer$")
    public void assertElementWithText() {
        String englishFooter = "C 2019 All of Us, all rights reserved";
        String spanishFooter = "C 2019 All of Us, todos los derechos reservados";
        //This fix is for the Sauce Labs where it's not able to locate the element if it's not physically visible on the page.
        for (int i = 0; i < 20; i++) {
            loginPage.getManagerAccountLink().type(Keys.ARROW_DOWN);
        }
        loginPage.getFooter().waitFor().displayed();
        if (loginPage.getFooter() != null && loginPage.getFooter().get().text().equals(englishFooter)) {
            this.loginPage.getFooter().assertEquals().text(englishFooter);
        } else {
            loginPage.getFooter().assertEquals().text(spanishFooter);
        }
    }

    @Then("^I do not see the footer$")
    public void assertNoFooter() {
        if (loginPage.getFooter() != null) {
            this.loginPage.getFooter().assertState().notDisplayed();
        }
    }

    @Then("^the login isn't enabled$")
    public void checkLoginButtonDisabled() {
        this.loginPage.assertLoginButtonDisabled();
    }

    @Then("^I am logged in$")
    public void assertLoggedIn() {
        loginPage.assertLoggedIn();
    }

    @Then("^I am logged out$")
    public void confirmLoggedOut() {
        this.loginPage.assertLoggedOut();
    }

    @Description("This method captures the accessibility tags from the pagecurrently on the screen. If you perform a " +
            "step that causes you to launcha new page and then call this method, you will see the new screen" +
            ".<p>Usage: I capture tags \"user selects number of cigarettes a day\" This will create a file whose name" +
            " contains user_selects_number_of_cigarettes_a_day")
    @Then("^I capture tags \"(.*)\"$")
    public void captureTags(String step) {
        this.loginPage.captureTags(step);
    }

    @Then("^I see the email error of \"(.*)\"$")
    public void emailErrorCheck(String error) {
        this.loginPage.assertEmailError(error);
    }

    @Description("Logs in with the option of providing an attempt prefix for special login cases.")
    @When("^I (try to )?login$")
    public void login(String attempt) {
        this.loginPage.loadEnvironment();
        if ("select remember me and ".equals(attempt)) {
            this.loginPage.selectRememberMe();
        }
        this.loginPage.login(this.user);
        if (!"try to ".equals(attempt)) {
            this.loginPage.waitForLoginLoad();
        }
    }


    @When("^I logout$")
    public void logout() {
        this.loginPage.logout();
    }

    @When("^I navigate to the login page$")
    public void navigateToLogin() {
        this.loginPage.loadEnvironment();
    }

    @When("^I (.*)log into the application using \"([^\"]*)\" and \"([^\"]*)\"$")
    public void loginSQLInjection(String attempt, String username, String password) {
        this.user.setEmail(username);
        this.user.setPassword(password);
        login(attempt);
    }

    @Description("Activates the password reset link")
    @When("^I request a password reset link$")
    public void selectForgotPasswordLink() {
        this.loginPage.getForgotPasswordLink().click();
    }

    @Description(
            "Tests whether an email matching the parameter subject line was receivedby the parameter email's address")
    @Then("^I receive the \"([^\"]*)\" email$")
    public void receiveSubjectEmailInLanguage(String subject) throws MessagingException, IOException {
        EMailUtility gmu = new EMailUtility();
        Message[] m = gmu.recipientSubjectSearchEmails(user.getEmail(), subject, 45);
        this.loginPage.assertEmailReceived(m);
        if (m != null) {
            passwordResetEmailContent = m[0].getDataHandler().getContent().toString();
        }
    }

    @Description("Assert an error message is present and displayed")
    @Then("^I see the login error message \"([^\"]*)\"$")
    public void assertError(String error) {
        switch (error) {
            case "Invalid email address or password.":
                this.loginPage.assertBadLogin();
                break;
            case "Please enter a valid email address":
                this.loginPage.assertEmailError(error);
                break;
            case "No email error message":
                this.loginPage.assertNoEmailError();
                break;
            default:
                this.loginPage.assertBadLogin(error);
        }
    }

    @Description("Waits for a determined amount of time, then performs check to see if user is still logged in.")
    @Then("^After (\\d+) minutes I am still logged in$")
    public void assertLoginPersist(int minutes) {
        throw new PendingException();
    }

    @Then("^cookies are( not)? stored$")
    public void assertCookiesStored(String qualifier) {
        if (" not".equals(qualifier)) {
            this.loginPage.assertCookiesNotStored();
        } else {
            this.loginPage.assertCookiesStored();
        }
    }

    @Description("Click the password reset email link")
    @When("^I open the password reset email link$")
    public void clickPasswordResetEmailLink() throws VibrentException {
        if (this.passwordResetEmailContent != null) {
            Pattern p = Pattern.compile(".*href=\"(.*reset.*)\".*");
            Matcher m = p.matcher(this.passwordResetEmailContent);
            if (m.find()) {
                String myUrl = m.group(1);
                this.url = myUrl;
                Pattern k = Pattern.compile(".*key=(\\d+)[^0-9].*");
                Matcher km = k.matcher(myUrl);
                if (km.find()) {
                    this.user.setPasswordResetKey(km.group(1));
                } else {
                    throw new VibrentException("Could not retrieve the reset key from email.");
                }
                loginPage.goToResetPasswordPage(myUrl);
            } else {
                throw new VibrentException("Could not retrieve the reset url from email.");
            }
        }
    }

    @Description("Load the environment and call the login X times method")
    @When("^I try to login (\\d+) times in a row$")
    public void iTryToLoginXTimesInARow(int attempts) {
        this.loginPage.loadEnvironment();
        this.loginPage.loginXTimes(this.user, attempts);
    }

    @Description("Assert the password reset message is displayed")
    @Then("^I see the password reset message$")
    public void iSeeThePasswordResetMessage() {
        loginPage.assertPasswordResetMessageDisplayed();
    }

    @Description("Clicks the Forgot Password link; types in the email address where the reset link should be sent " +
            "out; submits the form")
    @When("^I type in my email information on forgot password page$")
    public void fillOutEmail() {
        loginPage.fillOutEmail(this.user);
    }

    @Description("Fills out security questions and appropriate answers for them on the security questions page")
    @When("^I type in the answers on security questions page$")
    public void fillOutSecurityAnswers() {
        loginPage.fillOutSecurityAnswers(this.user, url);
    }

    @Description("Types in new updated value of the password for the user resets the passwords for the current user")
    @Then("^I reset my password$")
    public void resetPassword() {
        loginPage.resetPassword(this.user);
    }

    @Description("Fill out the security questions")
    @When("^I type in my security questions$")
    public void iFillOutMySecurtiyQuestions() {
        loginPage.fillOutSecurityQuestions(this.user);
    }

    @Description("Assert the password reset success message is displayed")
    @Then("^I see the successful password reset message$")
    public void iSeeTheSuccessfulPasswordResetMessage() {
        loginPage.assertPasswordResetMessageDisplayed();
    }

    @Description("Assert the security questions are present")
    @Then("^I see the security questions$")
    public void iSeeTheSecurityQuestions() {
        loginPage.assertSecurityQuestionsPresent();
    }

    @Description("Assert that the form for the user to fill out their security questions is present")
    @Then("^I see the survey to type in my security questions$")
    public void iSeeTheFormToFillOutMySecurityQuestions() {
        loginPage.assertSecurityQuestionsFormPresent();
    }

    @Description("Goes to the url retrieved from the email to reset the password")
    @When("^I update the password to \"([^\"]*)\" on the password reset page$")
    public void updatePasswordOnPasswordReset(String updatedPassword) {
        loginPage.updatePassword(this.user, updatedPassword);
    }

    @Description("Changes user's password to provided one and do not reset password")
    @When("^I change password to \"([^\"]*)\" on the password reset page without proceeding$")
    public void updateUserPasswordAndDoNotProceed(String newPassword) {
        loginPage.updateUserPasswordAndDoNotProceed(this.user, newPassword);
    }

    @Description("Verifies if the reset password button is enabled or disabled")
    @Then("^the reset password status shows up as \"([^\"]*)\"$")
    public void assertResetPasswordButtonStatus(String status) {
        loginPage.assertResetPasswordButtonStatus(status);
    }

    @Description("Click the reset password button")
    @When("^I select the reset password button$")
    public void iClickTheResetPasswordButton() {
        loginPage.clickPasswordReset();
    }

    @Description("Verifies if app store links are present for mobile apps")
    @Then("^I see app store links$")
    public void assertAppStoreLinksPresent() {
        loginPage.assertAppStoreLinksPresent();
    }

    @When("^I register with the vanity url of the HPO \"([^\"]*)\"$")
    public void registerWithGroup(String group) throws IOException {
        String vanity = Property.getDefaultProgramProperty("." + group + ".vanitycode");
        user.setVanityCode(vanity);
        this.loginPage.loadEnvironment();
    }

    @Description("Accesses the forgot your password subpage by clicking the forgot your password link on the login page")
    @When("^I access forgot your password subpage$")
    public void accessForgotPasswordPage() {
        loginPage.clickForgotPasswordLink();
    }

    @Description("Verifies user is on the forgot your password subpage")
    @Then("^I see forgot your password subpage$")
    public void verifyForgotPasswordPage() {
        loginPage.verifyForgotPasswordPage();
    }

    @Description("Verifies title text on login page")
    @Then("^I should see the title text \"([^\"]*)\" on login page$")
    public void assertTitleTextOnLoginPage(String text) {
        loginPage.assertTitleTextOnLoginPage(text);
    }

    @Description("Verifies welcome text on login page")
    @Then("^I should see the welcome text \"([^\"]*)\" on login page$")
    public void assertWelcomeTextOnLoginPage(String text) {
        loginPage.assertWelcomeTextOnLoginPage(text);
    }

    @Description("Click show or hide password button")
    @When("^I activate (Show|Hide) password on login page$")
    public void activateShowOrHidePasswordButton(String option) throws IOException {
        loginPage.activateShowOrHidePasswordButton(option);
    }

    @Description("Checks if password changed to text")
    @Then("^I see users password as a text on login page$")
    public void iSeeUsersPasswordAsAText() throws IOException {
        loginPage.iSeeUsersPasswordAsAText(this.user);
    }

    @Description("Checks if show or hide password is displayed")
    @Then("^I see (Show|Hide) password on login page$")
    public void iSeeShowOrHidePasswordButton(String option) throws IOException {
        loginPage.iSeeShowOrHidePasswordButton(option);
    }

    @Description("Provide email and password on a login page without proceeding")
    @Given("^I provide login information without proceeding$")
    public void iProvideLoginInformationWithoutProceeding() throws IOException {
        loginPage.iProvideLoginInformationWithoutProceeding(this.user);
    }

    @Description("Verifies the error message on the forgot password page")
    @Then("^I see the error \"([^\"]*)\" on forgot password page$")
    public void assertForgotPasswordError(String error) {
        loginPage.assertForgotPasswordError(error);
    }

}
