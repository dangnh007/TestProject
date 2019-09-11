package com.pmt.health.utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.objects.user.User;
import com.pmt.health.objects.user.UserUtility;
import com.pmt.health.steps.Configuration;

import java.io.IOException;
import java.util.*;

public class APIUtility {

    private static final String CAMPAIGN_NAME_RANDOM = "Test Automation via API #" + UserUtility.generateUUID(5);
    private static final String VALUE_LIST = "valueList";
    private static final String AUTHORIZATION = "Authorization";
    private static final String PROGRAM_ID = "65b1bc8";
    private static final String VIEW_TYPE = "viewType";
    private static final String IS_EQUAL_TO = "Is equal to";
    private static final String ID = "id";
    private static final String CHANNEL = "channel";
    private static final String VALUE = "value";
    private static final String LABEL = "label";
    private static final String CUSTOM = "Custom";
    private static final String SCHOOL_OF_NURSING_SITE = "Site%2Fhpo-site-wimadisonschoolofnursing";
    private static final String NODE_MEMBER = "nodes";
    private static final String TIME_ZONE = "timeZone";
    private static final String AMERICA_CHICAGO = "America/Chicago";
    private static final String SITE_ID = "siteId";
    private static final String REFERER = "Referer";
    private static final String MAIN_URL = Property.getProgramProperty(Configuration.getEnvironment() + ".url.mc");
    private static final String ENDPOINT_SITE = "/api/schedule/siteDetail";
    private static final String ENDPOINT_TARGET_AND_GOAL = "/api/capacity/saveTargetAndGoal";
    private static final String SITE_ID_SCHOOL_OF_NURSING = "Site/hpo-site-wimadisonschoolofnursing";
    private static final String ENDPOINT_MINIMUM_APPOINTMENT_NOTICE = "/api/schedule/saveMinimumAppointmentNotice";
    private static final String REFERER_SCHEDULE = MAIN_URL + "/settings/scheduling?role=ROLE_MC_SITE_MANAGER";
    private static final String ENDPOINT_HRS_OF_OPERATIONS = "/api/schedule/weeklyHoursOfOperation";
    private static final String ENDPOINT_CALENDARS = "/api/schedule/calendars";
    private static final String REFERER_SCHEDULE_APPOINTMENT = MAIN_URL + "/scheduling/calendar/scheduler?role=ROLE_MC_SITE_MANAGER";
    private static final String ENDPOINT_SCHEDULE_APPOINTMENT = "/api/schedule/scheduleMCAppointment";
    private static final String GROUPS_ENDPOINT = "/api/userAdmin/getGroups";
    private static final String REFERER_CREATE_USER = MAIN_URL + "/userAdmin/createUser/ROLE_MC_SYSTEM_ADMINISTRATOR?role=ROLE_MC_SYSTEM_ADMINISTRATOR";
    private static final String REFERER_CAMPAIGN = MAIN_URL + "/communications/campaigns?role=ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER";
    private static final String REFERER_SEGMENTATION = MAIN_URL + "/communications/segmentation?role=ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER";
    private static final String ENDPOINT_CAMPAIGN = "/api/communications/campaign";
    private static final String ENDPOINT_SEGMENTATION = "/api/communications/segment";

    protected Reporter reporter;
    private HTTP http;
    private User user;

    public APIUtility(Reporter reporter, User user) {
        this.user = user;
        this.reporter = reporter;
        this.http = new HTTP(Configuration.getEnvironmentURL().toString(), reporter);
    }

    public APIUtility(Reporter reporter, User user, HTTP http) {
        this.user = user;
        this.reporter = reporter;
        this.http = http;
    }

    private void reporterPassFailStep(String action, String expected, Response response, String failMessage) {
        if (response.getCode() == 200) {
            reporter.pass(action, expected, expected + ". " + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        } else {
            reporter.warn(action, expected, failMessage + Reporter.formatAndLabelJson(response, Reporter.RESPONSE));
        }
    }

    /**
     * Lands as user on Settings via the API.
     * toggle On/Off accepting appointments
     */
    public Response toggleOnOffViaApi(String toggle) throws IOException {
        String action = "I toggle accepting appointments via API";
        String expected = "Successfully toggled accepting appointments via the API";
        // setup toggle member as ON or OFF
        Map<String, String> parameters = new HashMap<>();
        parameters.put(SITE_ID, SCHOOL_OF_NURSING_SITE);
        JsonObject toggleOnOff = new JsonObject();
        toggleOnOff.addProperty("acceptingAppointments", "on".equalsIgnoreCase(toggle));
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        //set request
        RequestData requestData = new RequestData();
        requestData.setParams(parameters);
        requestData.setHeaders(referer);
        requestData.setJSON(toggleOnOff);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simplePut(ENDPOINT_SITE, requestData);
        //generate report
        reporterPassFailStep(action, expected, response, "Not successfully set a toggle. ");
        return response;
    }

    /**
     * Lands as user on Settings via the API.
     * set daily target and goal
     */
    public Response setDailyTargetAndGoalViaApi(int target, int goal) throws IOException {
        String action = "I set daily target and goal via API";
        String expected = "Successfully set daily target and goal via the API";
        // setup toggle member as ON or OFF
        JsonObject targetAndGoal = new JsonObject();
        targetAndGoal.addProperty("target", target);
        targetAndGoal.addProperty("goal", goal);
        targetAndGoal.addProperty(SITE_ID, SITE_ID_SCHOOL_OF_NURSING);
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        //set request
        RequestData requestData = new RequestData();
        requestData.setJSON(targetAndGoal);
        requestData.setHeaders(referer);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simplePut(ENDPOINT_TARGET_AND_GOAL, requestData);
        //generate report
        reporterPassFailStep(action, expected, response, "Target and goal was set not successfully. ");
        return response;
    }

    /**
     * Lands as user on Settings via the API.
     * set minimum appointment notice
     */
    public Response setMinimumAppointmentNoticeViaApi(int days) throws IOException {
        String action = "I set minimum appointment notice via API";
        String expected = "Successfully set minimum appointment notice via the API";
        // setup toggle member as ON or OFF
        JsonObject minimumAppointmentNotice = new JsonObject();
        minimumAppointmentNotice.addProperty("minimumAppointmentNotice", days);
        minimumAppointmentNotice.addProperty(SITE_ID, SITE_ID_SCHOOL_OF_NURSING);
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        //set request
        RequestData requestData = new RequestData();
        requestData.setJSON(minimumAppointmentNotice);
        requestData.setHeaders(referer);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simplePut(ENDPOINT_MINIMUM_APPOINTMENT_NOTICE, requestData);
        //generate report
        reporterPassFailStep(action, expected, response, "Minimum appointment notice was set not successfully. ");
        return response;
    }

    /**
     * Lands as user on Settings via the API.
     * create hours of operations
     */
    public Response createCustomHoursOfOperations() throws IOException {
        String action = "I create hours of operations via API";
        String expected = "Successfully create hours of operations via the API";
        // create hours of operations
        JsonObject hoursOfOperations = new JsonObject();
        hoursOfOperations.addProperty(ID, "");
        hoursOfOperations.addProperty("name", CUSTOM);
        hoursOfOperations.addProperty(SITE_ID, SITE_ID_SCHOOL_OF_NURSING);
        hoursOfOperations.addProperty(TIME_ZONE, AMERICA_CHICAGO);
        hoursOfOperations.addProperty("isDefault", false);
        hoursOfOperations.add("status", null);
        //Create calendar object to provide date
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        hoursOfOperations.addProperty("effectiveDate", calendar.getTimeInMillis() / 1000);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        hoursOfOperations.addProperty("effectiveEndDate", calendar.getTimeInMillis() / 1000);
        //create json array for defaultWorkingTimes property
        JsonArray defWorkingTimesArr = new JsonArray();
        defWorkingTimesArr.add(getWorkingTimeObj("Monday", "eeeWLQGp3cVwp9Gx3hODIveaOUw"));
        defWorkingTimesArr.add(getWorkingTimeObj("Tuesday", "FfLLf09xFVX3J12B30JypUI20IY"));
        defWorkingTimesArr.add(getWorkingTimeObj("Wednesday", "nUQpYQNYG9lHMjs2xfrWteNnB0Y"));
        defWorkingTimesArr.add(getWorkingTimeObj("Thursday", "2zOo0AlAUKtzVJeq2q0afvB4hCw"));
        defWorkingTimesArr.add(getWorkingTimeObj("Friday", "PWbTihqUfg2nld2JCb_OY0Qe2PM"));
        defWorkingTimesArr.add(getWorkingTimeObj("Saturday", "KCRyCS7Ds9UxsSXXE89qL_XZsPs"));
        defWorkingTimesArr.add(getWorkingTimeObj("Sunday", "DwLeK8ZuovA8rVwg52zco5N71kw"));
        hoursOfOperations.add("defaultWorkingTimes", defWorkingTimesArr);
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        //set request
        RequestData requestData = new RequestData();
        requestData.setHeaders(referer);
        requestData.setJSON(hoursOfOperations);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simplePut(ENDPOINT_HRS_OF_OPERATIONS, requestData);
        //generate report
        reporterPassFailStep(action, expected, response, "Hours of operations was created not successfully. ");
        return response;
    }

    /**
     * Reusable method to work with dynamic values in Working Time object.
     * sets needed values
     */
    public JsonObject getWorkingTimeObj(String day, String idValue) {
        JsonObject defWorkingTimeObj = new JsonObject();
        defWorkingTimeObj.addProperty(ID, idValue);
        defWorkingTimeObj.addProperty(SITE_ID, SITE_ID_SCHOOL_OF_NURSING);
        defWorkingTimeObj.addProperty("templateName", "");
        defWorkingTimeObj.addProperty("dayOfWeek", day);
        defWorkingTimeObj.add("date", null);
        defWorkingTimeObj.addProperty(TIME_ZONE, AMERICA_CHICAGO);
        defWorkingTimeObj.add("maxAppt", null);
        defWorkingTimeObj.addProperty("isClosed", true);
        //timeBlocks
        JsonArray timeBlocksArr = new JsonArray();
        JsonObject timeBlocks = new JsonObject();
        timeBlocks.addProperty("concurrentAppointments", 3);
        timeBlocks.addProperty("endTime", 1_561_669_200);
        timeBlocks.addProperty(ID, "");
        timeBlocks.addProperty("name", "Update");
        timeBlocks.addProperty("selected", false);
        timeBlocks.addProperty("startTime", 1_561_640_400);
        timeBlocks.addProperty(TIME_ZONE, AMERICA_CHICAGO);
        timeBlocksArr.add(timeBlocks);
        defWorkingTimeObj.add("timeBlocks", timeBlocksArr);
        defWorkingTimeObj.addProperty("availableSlots", false);
        return defWorkingTimeObj;
    }

    /**
     * Lands as user on Settings via the API.
     * return Form ID as a String
     */
    public String getCalendarFormId() throws IOException {
        String action = "I get calendar form id via API";
        String expected = "Successfully get calendar form id via API";
        //Add headers and parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put(SITE_ID, SCHOOL_OF_NURSING_SITE);
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        RequestData requestData = new RequestData();
        requestData.setHeaders(referer);
        requestData.setParams(parameters);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.get(ENDPOINT_CALENDARS, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully get calendar form id via API. ");
        String calendarFormId = "";
        JsonArray jsonArray = response.getArrayData();
        int size = response.getArrayData().size();
        for (int i = 0; i < size; i++) {
            if (jsonArray.get(i).toString().contains(CUSTOM)) {
                calendarFormId = jsonArray.get(i).getAsJsonObject().get(ID).getAsString();
            }
        }
        return calendarFormId;
    }

    /**
     * Lands as user on Settings via the API.
     * delete custom Form
     */
    public Response deleteCustomForm() throws IOException {
        String action = "I delete custom calendar form id via API";
        String expected = "Successfully delete custom calendar form id via API";
        //add headers and parameters
        JsonObject emptyBody = new JsonObject();
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ID, getCalendarFormId());
        RequestData requestData = new RequestData();
        requestData.setJSON(emptyBody);
        requestData.setHeaders(referer);
        requestData.setParams(parameters);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simpleDelete(ENDPOINT_HRS_OF_OPERATIONS, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully deleted custom calendar form via API. ");
        return response;
    }

    /**
     * Schedule appointment for prospect as Site Manager
     */
    public Response scheduleProspectAppointment() throws IOException {
        String action = "I schedule appointment for prospect via API";
        String expected = "Successfully schedule appointment for prospect via API";
        //add headers and parameters
        JsonObject jsonObject = new JsonObject();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        jsonObject.addProperty("time", calendar.getTimeInMillis() / 1000);
        jsonObject.addProperty(SITE_ID, SITE_ID_SCHOOL_OF_NURSING);
        jsonObject.add("prospectId", null);
        jsonObject.add("participantId", null);
        jsonObject.addProperty("lastName", user.getLastName() + " API");
        jsonObject.addProperty("firstName", user.getFirstName() + " API");
        jsonObject.addProperty("emailAddress", user.getParticipantEmail());
        jsonObject.add("dob", null);
        jsonObject.addProperty("language", "en");
        jsonObject.addProperty("emailCommunication", false);
        jsonObject.add("phoneNumber", null);
        jsonObject.addProperty("userId", "");
        jsonObject.addProperty("duration", 90);
        jsonObject.addProperty("appointmentTypeName", "Full Enrollment");
        jsonObject.addProperty("notes", "test automation via API");
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE_APPOINTMENT);
        RequestData requestData = new RequestData();
        requestData.setJSON(jsonObject);
        requestData.setHeaders(referer);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simplePost(ENDPOINT_SCHEDULE_APPOINTMENT, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully schedule appointment for prospect via API. ");
        return response;
    }

    /**
     * Gets groupValue member out of the group endpoint
     */
    public void getSiteGroupValue(String role, String program, String awardee, String org, String site) throws IOException {
        String action = "I get a group value via API";
        String expected = "Successfully get a group value via API";
        //Add headers
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_CREATE_USER);
        Map<String, String> parameter = new HashMap<>();
        parameter.put("roleName", "ROLE_MC_SYSTEM_ADMINISTRATOR");
        RequestData requestData = new RequestData();
        requestData.setParams(parameter);
        http.addHeaders(referer);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.get(GROUPS_ENDPOINT, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully get a group value via API. ");
        String programId = "";
        String siteId = "";
        //Empty jsonArrays to go through the json respond
        JsonArray jsonArray = response.getArrayData();
        JsonArray nodesAwardee = new JsonArray();
        JsonArray nodesOrg = new JsonArray();
        JsonArray nodesSite = new JsonArray();
        //initialize jsonBody as array and sets its size
        int size = response.getArrayData().size();
        /*
         * for-loops to go through the json body, since every level has multiple arrays inside of it
         * nodes it's a member which contains more arrays inside of it
         */
        for (int i = 0; i < size; i++) {
            if (jsonArray.get(i).toString().contains(program)) {
                programId = jsonArray.get(i).getAsJsonObject().get(ID).getAsString();
                nodesAwardee = jsonArray.get(i).getAsJsonObject().get(NODE_MEMBER).getAsJsonArray();
            }
        }
        for (int j = 0; j < nodesAwardee.size() && !awardee.isEmpty(); j++) {
            if (nodesAwardee.get(j).toString().contains(awardee)) {
                nodesOrg = nodesAwardee.get(j).getAsJsonObject().get(NODE_MEMBER).getAsJsonArray();
            }
        }
        for (int k = 0; k < nodesOrg.size() && !org.isEmpty(); k++) {
            if (nodesOrg.get(k).toString().contains(org)) {
                nodesSite = nodesOrg.get(k).getAsJsonObject().get(NODE_MEMBER).getAsJsonArray();
            }
        }
        for (int b = 0; b < nodesSite.size() && !site.isEmpty(); b++) {
            if (nodesSite.get(b).toString().contains(site)) {
                siteId = nodesSite.get(b).getAsJsonObject().get(ID).getAsString();
            }
        }
        //Sets group value depending on the role
        if ("ROLE_MC_NIH".equals(role)) {
            user.setGroupValue(programId);
        }
        if ("ROLE_MC_SITE_MANAGER".equals(role)) {
            user.setGroupValue(siteId);
        }
        if ("ROLE_MC_COMMUNICATIONS_ENGAGEMENT_MANAGER".equals(role)) {
            user.setGroupValue(programId);
        }
    }

    /**
     * Gets name member out of the weeklyHours endpoint
     */
    public void getNameCustomHoursOfOperations() throws IOException {
        String action = "I get name member of custom hours via API";
        String expected = "Successfully get name member of custom hours via API";
        //add headers and parameters
        Map<String, String> referer = new HashMap<>();
        referer.put(REFERER, REFERER_SCHEDULE);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(SITE_ID, SCHOOL_OF_NURSING_SITE);
        http.addHeaders(referer);
        RequestData requestData = new RequestData();
        requestData.setParams(parameters);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simpleGet(ENDPOINT_CALENDARS, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully get name member of custom hours via API. ");
        String formName = "";
        JsonArray jsonArray = response.getArrayData();
        //initialize jsonBody as array and sets its size
        int size = response.getArrayData().size();

        for (int i = 0; i < size; i++) {
            if (jsonArray.get(i).toString().contains(CUSTOM)) {
                formName = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
            }
        }
        user.setHoursOfoperarion(formName);
    }

    /**
     * Creates or drafts campaign.
     */
    public void createOrDraftCampaignViaApi(String createOrDraft, String channel) throws IOException {
        String action = "I " + createOrDraft + " campaign via API";
        String expected = "Successfully " + createOrDraft + " campaign via API";
        //add headers
        Map<String, String> headers = new HashMap<>();
        headers.put(REFERER, REFERER_CAMPAIGN);
        headers.put(AUTHORIZATION, user.getAuthToken());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CHANNEL, channel);
        jsonObject.addProperty("name", CAMPAIGN_NAME_RANDOM);
        jsonObject.addProperty("description", "Test Automation via API");
        jsonObject.addProperty("goal", "1");//Survey/279
        jsonObject.addProperty("associatedSegmentListId", createSegmentationViaApi(channel));
        jsonObject.addProperty("associatedTemplateId", 1_038_584);
        jsonObject.addProperty("sendDate", "");
        jsonObject.addProperty("status", createOrDraft);
        http.addHeaders(headers);
        RequestData requestData = new RequestData();
        requestData.setJSON(jsonObject);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simpleGet(ENDPOINT_CAMPAIGN, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully " + createOrDraft + " campaign via API");
    }

    /**
     * Creates segmentation
     */
    public String createSegmentationViaApi(String channel) throws IOException {
        String action = "I create segmentation via API";
        String expected = "Successfully create segmentation via API";
        //add headers
        Map<String, String> headers = new HashMap<>();
        headers.put(REFERER, REFERER_SEGMENTATION);
        headers.put(AUTHORIZATION, user.getAuthToken());
        //json body
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "API TEST");
        jsonObject.addProperty("owner", "Automation user");
        jsonObject.addProperty(CHANNEL, channel);
        JsonArray orgs = new JsonArray();
        JsonObject orgsObject = new JsonObject();
        orgsObject.addProperty(ID, 25);
        orgsObject.addProperty(VALUE, "Organization/AZ_TUCSON_BANNER_HEALTH");
        orgsObject.addProperty(LABEL, "Banner Health");
        orgsObject.addProperty("parentId", 24);
        orgs.add(orgsObject);
        jsonObject.add("selectedOrgs", orgs);
        JsonArray sites = new JsonArray();
        JsonObject sitesObject = new JsonObject();
        sitesObject.addProperty(ID, 27);
        sitesObject.addProperty(VALUE, "Site/hpo-site-bannerbaywood");
        sitesObject.addProperty(LABEL, "Banner Baywood Medical Center");
        sitesObject.addProperty("parentId", 25);
        sites.add(sitesObject);
        jsonObject.add("selectedSites", sites);
        jsonObject.addProperty("description", "API TEST description");
        jsonObject.add("recipients", null);
        jsonObject.add("lastRefreshed", null);
        jsonObject.add("lastUpdated", null);
        jsonObject.addProperty("groupOperator", "or");
        jsonObject.addProperty("archived", false);
        jsonObject.addProperty("editable", true);
        JsonArray groupList = new JsonArray();
        JsonObject groupListObj = new JsonObject();
        groupListObj.addProperty(ID, PROGRAM_ID);
        groupListObj.addProperty("position", 0);
        groupListObj.addProperty("categoryOperator", "and");
        groupList.add(groupListObj);
        jsonObject.add("segmentationGroupList", groupList);
        JsonArray categoryList = new JsonArray();
        //list of categories for filtering segmentation
        categoryList.add(segmentationCategoryMemberObj("45dad55", PROGRAM_ID, "Program Milestones",
                "programMilestones", "milestone",
                "survey", "milestone", "primaryConsent", "Primary Consent",
                "is", IS_EQUAL_TO,
                "survey", 1, "Eligible, But Not Started",
                "anytime", "on any date (default)"));
        categoryList.add(segmentationCategoryMemberObj("f406869", PROGRAM_ID, "Demographic Segmentation",
                "demographic", "multi",
                "multi",
                "age", "Age",
                "is", IS_EQUAL_TO,
                "age", 3, "35-44"));
        categoryList.add(segmentationCategoryMemberObj("82bbea4", PROGRAM_ID, "Campaign Activity",
                "campaignActivity", "campaign",
                "email", "campaign", "emailSent", "Email Sent",
                "is", IS_EQUAL_TO,
                "email", 1, "Email Campaign 1",
                "anytime", "on any date (default)"));
        jsonObject.add("segmentationCategoryList", categoryList);
        http.addHeaders(headers);
        RequestData requestData = new RequestData();
        requestData.setJSON(jsonObject);
        action += Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
        // make the actual call
        Response response = http.simplePost(ENDPOINT_SEGMENTATION, requestData);
        reporterPassFailStep(action, expected, response, "Not successfully create segmentation via API");
        return response.getObjectData().get("id").getAsString();
    }

    /**
     * Segmentation category member
     * Method takes args for each filter
     */
    @JsonCreator
    private static JsonObject segmentationCategoryMemberObj(String id, String groupId, String title, String category, String viewType,
                                                            String optionType, String optionViewType, String optionValue, String optionLabel,
                                                            String operatorValue, String operatorLabel,
                                                            String multiOptionType, int multiOptionValue, String multiOptionLabel,
                                                            String dateOperatorValue, String dateOperatorLabel) {
        JsonObject categoryListObj =
                segmentationObject(id, groupId, title, category, viewType, operatorValue, operatorLabel, multiOptionType, multiOptionValue, multiOptionLabel);
        JsonObject primaryOption = new JsonObject();
        primaryOption.addProperty("type", optionType);
        primaryOption.addProperty(VIEW_TYPE, optionViewType);
        primaryOption.addProperty(VALUE, optionValue);
        primaryOption.addProperty(LABEL, optionLabel);
        categoryListObj.get(VALUE_LIST).getAsJsonObject().add("selectedPrimaryOption", primaryOption);
        JsonObject dateOperator = new JsonObject();
        dateOperator.addProperty(VALUE, dateOperatorValue);
        dateOperator.addProperty(LABEL, dateOperatorLabel);
        categoryListObj.get(VALUE_LIST).getAsJsonObject().add("selectedDateOperator", dateOperator);
        return categoryListObj;
    }

    /**
     * Segmentation category member
     * Overloaded method to implement various options of filtering
     */
    @JsonCreator
    private static JsonObject segmentationCategoryMemberObj(String id, String groupId, String title, String category, String viewType,
                                                            String optionViewType, String optionValue, String optionLabel,
                                                            String operatorValue, String operatorLabel,
                                                            String multiOptionType, int multiOptionValue, String multiOptionLabel) {
        JsonObject categoryListObj = segmentationObject(id, groupId, title, category, viewType, operatorValue,
                operatorLabel, multiOptionType, multiOptionValue, multiOptionLabel);
        JsonObject primaryOption = new JsonObject();
        primaryOption.addProperty(VIEW_TYPE, optionViewType);
        primaryOption.addProperty(VALUE, optionValue);
        primaryOption.addProperty(LABEL, optionLabel);
        categoryListObj.get(VALUE_LIST).getAsJsonObject().add("selectedPrimaryOption", primaryOption);
        categoryListObj.get(VALUE_LIST).getAsJsonObject().add("selectedDateOperator", null);
        return categoryListObj;
    }

    /**
     * Segmentation member
     * Reusable method for segmentationCategoryMemberObj
     */
    @JsonCreator
    private static JsonObject segmentationObject(String id, String groupId, String title, String category, String viewType,
                                                 String operatorValue, String operatorLabel,
                                                 String multiOptionType, int multiOptionValue, String multiOptionLabel) {
        JsonObject categoryListObj = new JsonObject();
        categoryListObj.addProperty(ID, id);
        categoryListObj.addProperty("groupId", groupId);
        categoryListObj.addProperty("title", title);
        categoryListObj.addProperty("category", category);
        categoryListObj.addProperty(VIEW_TYPE, viewType);
        JsonObject valueList = new JsonObject();
        JsonObject primaryOperator = new JsonObject();
        primaryOperator.addProperty(VALUE, operatorValue);
        primaryOperator.addProperty(LABEL, operatorLabel);
        valueList.add("selectedPrimaryOperator", primaryOperator);
        JsonArray multiOptionsArr = new JsonArray();
        JsonObject multiOptions = new JsonObject();
        multiOptions.addProperty("type", multiOptionType);
        multiOptions.addProperty(VALUE, multiOptionValue);
        multiOptions.addProperty(LABEL, multiOptionLabel);
        multiOptionsArr.add(multiOptions);
        valueList.add("selectedMultiOptions", multiOptionsArr);
        categoryListObj.add(VALUE_LIST, valueList);
        return categoryListObj;
    }
}
