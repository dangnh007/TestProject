package com.pmt.health.steps.api;

import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.Configuration;
import com.pmt.health.steps.DeviceController;
import com.pmt.health.utilities.APIUtility;
import com.pmt.health.utilities.EMailUtility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

public class ApiSteps {

    private final UserUtility userUtility;
    private final APIUtility apiUtility;
    private final EMailUtility eMailUtility;
    // used to share context of responses and requests between step declarations and workflow
    protected RequestData requestData;
    private final User user;
    private HTTP http;


    public ApiSteps(DeviceController deviceController, RequestData requestData, User user) {
        this.http = new HTTP(Configuration.getEnvironmentURL().toString());
        this.user = user;
        this.userUtility = new UserUtility(deviceController.getReporter(), user, http);
        this.requestData = requestData;
        this.eMailUtility = new EMailUtility(user, deviceController.getReporter());
        this.apiUtility = new APIUtility(deviceController.getReporter(), user, http);
    }

    @When("^I login as System Administrator via API$")
    public void loginAsSystemAdminViaAPI() throws IOException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
    }

    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" via API$")
    public void createUser(String role, String program, String awardee, String org, String site) throws IOException {
        apiUtility.getSiteGroupValue(role, program, awardee, org, site);
        userUtility.apiCreateUser(role);
    }

    @Then("^I check email inbox$")
    public void emailInbox() throws IOException {
        eMailUtility.emailInbox();
    }

    @And("^I verify email and get its id$")
    public void getEmailId() throws IOException, InterruptedException {
        eMailUtility.emailGetValue();
    }

    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void createUserAPI(String role, String program, String awardee, String org, String site) throws IOException, InterruptedException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
        apiUtility.getSiteGroupValue(role, program, awardee, org, site);
        userUtility.apiCreateUser(role);
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

    @Then("^I set up my credentials via API$")
    public void setUpMyCredentialsViaAPI() throws IOException {
        userUtility.retrieveAndSetUserSecretKey();
        userUtility.apiLoginUserMFA();
        userUtility.apiSetPassword();
    }

    @Then("^I toggle \"([^\"]*)\" accepting appointments via API$")
    public void toggleAcceptingAppointmentsViaAPI(String toggle) throws IOException {
        apiUtility.toggleOnOffViaApi(toggle);
    }

    @Then("^I set daily \"([^\"]*)\" and \"([^\"]*)\" via API$")
    public void setDailyGoalAndTargetViaAPI(int target, int goal) throws IOException {
        apiUtility.setDailyTargetAndGoalViaApi(target, goal);
    }

    @Then("^I set \"([^\"]*)\" of minimum appointment notice via API$")
    public void setMinimumAppointmentNotice(int days) throws IOException {
        apiUtility.setMinimumAppointmentNoticeViaApi(days);
    }

    @Then("^I set custom hours of operations via API$")
    public void setCustomHoursOfOperationsViaAPI() throws IOException {
        apiUtility.createCustomHoursOfOperationS();
    }

    @Then("^I set default hours of operations via API$")
    public void getFormId() throws IOException {
        apiUtility.deleteCustomForm();
    }

    @Then("^I create new appointment for prospect via API$")
    public void createNewAppointmentForProspectViaAPI() throws IOException {
        apiUtility.scheduleProspectAppointment();
    }
}
