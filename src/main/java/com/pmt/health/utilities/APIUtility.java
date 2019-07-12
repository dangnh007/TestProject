package com.pmt.health.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.objects.user.User;
import com.pmt.health.steps.Configuration;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class APIUtility {

    private static final String ID = "id";
    private static final String TIME_ZONE = "timeZone";
    private static final String AMERICA_CHICAGO = "America/Chicago";
    private static final String SITE_ID = "siteId";
    private static final String REFERER = "Referer";
    private static final String MAIN_URL = Property.getProgramProperty(Configuration.getEnvironment() + ".url.sub");
    private static final String ENDPOINT_SITE = "/api/schedule/siteDetail";
    private static final String ENDPOINT_TARGET_AND_GOAL = "/api/capacity/saveTargetAndGoal";
    private static final String SITE_ID_SCHOOL_OF_NURSING = "Site/hpo-site-wimadisonschoolofnursing";
    private static final String ENDPOINT_MINIMUM_APPOINTMENT_NOTICE = "/api/schedule/saveMinimumAppointmentNotice";
    private static final String REFERER_SCHEDULE = MAIN_URL + "/settings/scheduling?role=ROLE_MC_SITE_MANAGER";
    private static final String ENDPOINT_HRS_OF_OPERATIONS = "/api/schedule/weeklyHoursOfOperation";
    private static final String ENDPOINT_CALENDARS = "/api/schedule/calendars";
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
        parameters.put(SITE_ID, "Site%2Fhpo-site-wimadisonschoolofnursing");
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
    public Response createCustomHoursOfOperationS() throws IOException {
        String action = "I create hours of operations via API";
        String expected = "Successfully create hours of operations via the API";
        // create hours of operations
        JsonObject hoursOfOperations = new JsonObject();
        hoursOfOperations.addProperty(ID, "");
        hoursOfOperations.addProperty("name", "Custom");
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
     * set needed values
     */
    private JsonObject getWorkingTimeObj(String day, String idValue) {
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
        timeBlocks.addProperty("endTime", 1561669200);
        timeBlocks.addProperty(ID, "");
        timeBlocks.addProperty("name", "Update");
        timeBlocks.addProperty("selected", false);
        timeBlocks.addProperty("startTime", 1561640400);
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
        parameters.put(SITE_ID, "Site%2Fhpo-site-wimadisonschoolofnursing");
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
            if (jsonArray.get(i).toString().contains("Custom")) {
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

}
