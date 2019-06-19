package com.pmt.health.steps.api;

import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.EMailUtility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

public class ApiSteps {

    private final UserUtility userUtility;
    private final EMailUtility eMailUtility;
    // used to share context of responses and requests between step declarations and workflows
    protected RequestData requestData;
    private final User user;

    public ApiSteps(DeviceController deviceController, RequestData requestData, User user) {
        this.user = user;
        this.userUtility = new UserUtility(deviceController.getReporter(), user);
        this.requestData = requestData;
        this.eMailUtility = new EMailUtility(user, deviceController.getReporter());
    }

    @When("^I login as System Administrator via API$")
    public void loginAsSystemAdminViaAPI() throws IOException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
    }

    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\" via API$")
    public void createUser(String role, String group) throws IOException {
        userUtility.apiCreateUser(role, group);
    }

    @Then("^I check email inbox$")
    public void emailInbox() throws IOException {
        eMailUtility.emailInbox();
    }

    @And("^I verify email and get its id$")
    public void getEmailId() throws IOException {
        eMailUtility.emailGetValue();
    }

    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void createUserAPI(String role, String group) throws IOException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
        userUtility.apiCreateUser(role, group);
        eMailUtility.emailInbox();
        eMailUtility.emailGetValue();
    }

    @Then("^I login as user via API$")
    public void loginAsUserViaAPI() throws IOException {
        userUtility.retrieveAndSetUserSecretKey();
        userUtility.apiLoginUserMFA();
        userUtility.apiSetPassword();
        userUtility.apiLoginUser();
        userUtility.apiLoginUserMFA();
    }
}
