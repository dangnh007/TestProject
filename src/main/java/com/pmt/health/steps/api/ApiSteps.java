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

/**
 * Step definition class for API calls.
 */
public class ApiSteps {

    private final UserUtility userUtility;
    private final APIUtility apiUtility;
    private final EMailUtility eMailUtility;
    // used to share context of responses and requests between step declarations and workflow
    protected RequestData requestData;
    private final User user;
    private HTTP http;

    /**
     * Constructor of ApiSteps class.
     *
     * @param deviceController - object of the class which controls an application.
     * @param requestData      - object of the class which holds data needed to provide to the HTTP calls.
     * @param user             - object of the user class where we store user related values and methods.
     */
    public ApiSteps(DeviceController deviceController, RequestData requestData, User user) {
        this.http = new HTTP(Configuration.getEnvironmentURL().toString());
        this.user = user;
        this.userUtility = new UserUtility(deviceController.getReporter(), user, http);
        this.requestData = requestData;
        this.eMailUtility = new EMailUtility(user, deviceController.getReporter());
        this.apiUtility = new APIUtility(deviceController.getReporter(), user, http);
    }

    /**
     * Logs in as System Administrator via API.
     * Provides methods with user info such as email and pass.
     * Generates 6 digit mfa code.
     *
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @When("^I login as System Administrator via API$")
    public void loginAsSystemAdminViaAPI() throws IOException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
    }

    /**
     * Creates new user based on parameters.
     * Get and Post method are chained for this implementation.
     *
     * @param role    - sets the role for user/roleName endpoint.
     * @param program - sets program for user/groupName endpoint.
     * @param awardee - sets awardee for user/groupName endpoint.
     * @param org     - sets organization for user/groupName endpoint.
     * @param site    - sets site for user/groupName endpoint.
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" via API$")
    public void createUser(String role, String program, String awardee, String org, String site) throws IOException {
        apiUtility.getSiteGroupValue(role, program, awardee, org, site);
        userUtility.apiCreateUser(role);
    }

    /**
     * Uses api Get call to retrieve value from the messages of mailTrap.
     *
     * @throws IOException          signals that an I/O exception of some sort has occurred.
     * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied.
     */
    @And("^I verify email and get its id$")
    public void getEmailId() throws IOException, InterruptedException {
        eMailUtility.emailInbox();
        eMailUtility.emailGetValue();
    }

    /**
     * Does login via API, creates new user based on parameters and gets values using emailUtility.
     *
     * @param role    - sets the role for user/roleName endpoint.
     * @param program - sets program for user/groupName endpoint.
     * @param awardee - sets awardee for user/groupName endpoint.
     * @param org     - sets organization for user/groupName endpoint.
     * @param site    - sets site for user/groupName endpoint.
     * @throws IOException          signals that an I/O exception of some sort has occurred.
     * @throws InterruptedException when a thread is waiting, sleeping, or otherwise occupied.
     */
    @When("^I create user with \"([^\"]*)\" and \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void createUserAPI(String role, String program, String awardee, String org, String site) throws IOException, InterruptedException {
        userUtility.apiLoginAdmin();
        userUtility.apiLoginAdminMFA();
        apiUtility.getSiteGroupValue(role, program, awardee, org, site);
        userUtility.apiCreateUser(role);
        eMailUtility.emailInbox();
        eMailUtility.emailGetValue();
    }

    /**
     * Logs in as newly created user via API
     * Several methods were chained together for this implementation.
     *
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I login as user via API$")
    public void loginAsUserViaAPI() throws IOException {
        userUtility.retrieveAndSetUserSecretKey();
        userUtility.apiLoginUserMFA();
        userUtility.apiSetPassword();
        userUtility.apiLoginUser();
        userUtility.apiLoginUserMFA();
    }

    /**
     * Sets new password and stores authorization token.
     *
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I set up my credentials via API$")
    public void setUpMyCredentialsViaAPI() throws IOException {
        userUtility.retrieveAndSetUserSecretKey();
        userUtility.apiLoginUserMFA();
        userUtility.apiSetPassword();
    }

    /**
     * Send PUT request to set new site settings.
     * Sets custom hours of operations via API.
     *
     * @param target sets the target value for the site settings.
     * @param goal   sets the goal value for the site setting.
     * @param days   sets a minimum appointment value.
     * @param toggle sets accepting appointment toggle On or Off.
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I set new Site Settings with toggle \"([^\"]*)\", target \"([^\"]*)\", goal \"([^\"]*)\", days \"([^\"]*)\" via API")
    public void setNewSiteSettingsViaApi(String toggle, int target, int goal, int days) throws IOException {
        apiUtility.toggleOnOffViaApi(toggle);
        apiUtility.setDailyTargetAndGoalViaApi(target, goal);
        apiUtility.setMinimumAppointmentNoticeViaApi(days);
        apiUtility.createCustomHoursOfOperations();
        apiUtility.getNameCustomHoursOfOperations();
        if (user.getHoursOfoperarion().isEmpty()) {
            apiUtility.createCustomHoursOfOperations();
        }
    }

    /**
     * Send PUT request to set default site settings.
     * Sets default hours of operations via API.
     *
     * @param target sets the target value for the site settings.
     * @param goal   sets the goal value for the site setting.
     * @param days   sets a minimum appointment value.
     * @param toggle sets accepting appointment toggle On or Off.
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I set default Site Settings with toggle \"([^\"]*)\", target \"([^\"]*)\", goal \"([^\"]*)\", days \"([^\"]*)\" via API$")
    public void getFormId(String toggle, int target, int goal, int days) throws IOException {
        apiUtility.toggleOnOffViaApi(toggle);
        apiUtility.setDailyTargetAndGoalViaApi(target, goal);
        apiUtility.setMinimumAppointmentNoticeViaApi(days);
        apiUtility.deleteCustomForm();
    }

    /**
     * Sends POST request to the user/schedule endpoint and sets particular values.
     *
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I create new appointment for prospect via API$")
    public void createNewAppointmentForProspectViaAPI() throws IOException {
        apiUtility.scheduleProspectAppointment();
    }

    /**
     * Sends POST request to create or draft campaign via API.
     *
     * @param createOrDraft sets option between create or draft.
     * @param channel       sets a channel for campaign.
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I create \"([^\"]*)\" campaign with \"([^\"]*)\" via API$")
    public void createOrDraftCampaignViaAPI(String createOrDraft, String channel) throws IOException {
        userUtility.getAuthorizationToken();
        apiUtility.createOrDraftCampaignViaApi(createOrDraft, channel);
    }

    /**
     * Sends POST request to segmentation via API.
     *
     * @param channel sets a channel for campaign.
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I create new segmentation with (Email|SMS) channel via API$")
    public void createNewSegmentationViaAPI(String channel) throws IOException {
        userUtility.getAuthorizationToken();
        apiUtility.createSegmentationViaApi(channel);
    }

    /**
     * Retrieves access token and updates groups
     *
     * @throws IOException signals that an I/O exception of some sort has occurred.
     */
    @Then("^I create test groups via API$")
    public void createTestGroups() throws IOException {
        userUtility.getAccessToken();
        apiUtility.addTestingGroups();
    }
}
