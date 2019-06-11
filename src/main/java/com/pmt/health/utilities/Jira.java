package com.pmt.health.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;

import java.io.IOException;

public class Jira {

    private static final String ISSUE = "/rest/api/2/issue/";
    private static final String FIELDS = "fields";

    private Jira() {
    }

    /**
     * Creates a new HTTP class, which will interact with JIRA
     *
     * @return HTTP: an HTTP class which can make calls to JIRA
     */
    public static HTTP getJiraHttp() {
        String link = Property.getJiraProperty("link");
        String username = "";
        if (System.getProperty("jira.username") != null) {
            username = System.getProperty("jira.username");
        }
        String pswd = "";
        if (System.getProperty("jira.password") != null) {
            pswd = System.getProperty("jira.password");
        }
        return new HTTP(link, username, pswd);
    }

    /**
     * Should jira be updated? If so, the test cycle name will be returned. If not, a null will be returned
     *
     * @return String: the test cycle name in JIRA
     */
    public static String updateJIRA() {
        String jira = System.getProperty("jira.update");
        if (jira != null) {
            return jira;
        }
        return null;
    }

    /**
     * Based on the project in the jira.properties file, what is the associated id in JIRA. If no project is
     * specified, 0 will be returned
     *
     * @return Integer: the JIRA specific id, tied to the project
     */
    public static String getProjectId() throws IOException {
        HTTP jira = getJiraHttp();
        String project = Property.getJiraProperty("project").toUpperCase();

        Response response = jira.simpleGet("/rest/api/2/project/" + project);

        if (response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsString();
        }
        return "0";
    }

    /**
     * Based on the provided jira key, what is the associated id in JIRA. If this key can't be located in jira, 0 will be returned
     *
     * @param key - the unique jira key
     * @return Integer: the JIRA specific id associated with the provided key
     */
    public static String getIssueId(String key) throws IOException {
        HTTP jira = getJiraHttp();
        Response response = jira.simpleGet(ISSUE + key);
        if (response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsString();
        }
        return "0";
    }

    /**
     * Based on the test cycle, what is the associated id in JIRA. If no test cycle is
     * provided (null), the default cycle in the properties file will be used. If nothing is specified there,
     * unscheduled will be returned (-1)
     *
     * @return Integer: the JIRA specific id, tied to the project
     */
    public static String getCycleId(String cycle) throws IOException {
        HTTP jira = getJiraHttp();
        String project = Property.getJiraProperty("project").toUpperCase();
        if (cycle == null) {
            cycle = Property.getJiraProperty("project.test.cycle");
        }
        Response response = jira.simpleGet("/rest/api/2/project/" + project + "/versions");
        for (JsonElement element : response.getArrayData()) {
            JsonObject version = element.getAsJsonObject();
            if (cycle.equals(version.get("name").getAsString())) {
                return version.get("id").getAsString();
            }
        }
        return "-1";
    }

    /**
     * Makes a call out to JIRA which updates the issue. All content to update JIRA with should be provided in the
     * fields input. Any input provided in this object will be overwritten, while anything left out will be left as
     * it was
     *
     * @param issue  - the unique jira id of the issue to be updated
     * @param fields - the fields in the jira issue that should be updated, and their values
     * @return Boolean: was the update a success or not
     */
    private static boolean updateIssue(String issue, JsonObject fields) throws IOException {
        HTTP jira = getJiraHttp();
        String issueId = getIssueId(issue);
        JsonObject json = new JsonObject();
        json.add(FIELDS, fields);
        RequestData requestData = new RequestData();
        requestData.setJSON(json);
        Response response = jira.simplePut(ISSUE + issueId, requestData);

        return response.getCode() == 204;
    }

    /**
     * Makes a call out to JIRA to update the title of the issue provided
     *
     * @param issue - the unique jira id of the issue to be updated
     * @param title - the new title of the issue
     * @return Boolean: was the update a success or not
     */
    public static boolean updateTitle(String issue, String title) throws IOException {
        JsonObject fields = new JsonObject();
        fields.addProperty("summary", title);
        return Jira.updateIssue(issue, fields);
    }

    /**
     * Makes a call out to JIRA to update the description of the issue provided
     *
     * @param issue       - the unique jira id of the issue to be updated
     * @param description - the new description of the issue
     * @return Boolean: was the update a success or not
     */
    public static boolean updateDescription(String issue, String description) throws IOException {
        JsonObject fields = new JsonObject();
        fields.addProperty("description", description);
        return Jira.updateIssue(issue, fields);
    }

    /**
     * Makes a call out to JIRA to update the examples of the issue provided
     *
     * @param issue   - the unique jira id of the issue to be updated
     * @param example - the new examples of the issue
     * @return Boolean: was the update a success or not
     */
    public static boolean updateExamples(String issue, String example) throws IOException {
        JsonObject fields = new JsonObject();
        fields.addProperty("customfield_12501", example);
        return Jira.updateIssue(issue, fields);
    }

    /**
     * Makes a call out to JIRA to update the labels associated with the issue. All old labels will be removed, and
     * the provided labels will be added
     *
     * @param issue  - the unique jira id of the issue to be updated
     * @param labels - a list of labels to add to the issue
     * @return Boolean: was the update a success or not
     */
    public static boolean updateLabels(String issue, String... labels) throws IOException {
        // wipe out the old labels
        JsonObject fields = new JsonObject();
        fields.add("labels", new JsonArray());
        Jira.updateIssue(issue, fields);
        // add the new labels
        JsonArray json = new JsonArray();
        for (String label : labels) {
            json.add(label);
        }
        fields.add("labels", json);
        return Jira.updateIssue(issue, fields);
    }

    /**
     * Retrieves all information about the JIRA issue
     *
     * @param issue - the key of the issue
     * @return JsonObject: a json object with all of the issue data
     */
    public static JsonObject getIssue(String issue) throws IOException {
        HTTP jira = getJiraHttp();
        return jira.simpleGet(ISSUE + issue).getObjectData();
    }

    /**
     * Makes a call out to JIRA to update the test links associated with the issue. All old test links will be removed,
     * and the provided test links will be added
     *
     * @param issue - the unique jira id of the issue to be updated
     * @param links - a list of test links to add to the issue
     * @return Boolean: was the update a success or not
     */
    public static boolean updateTestLinks(String issue, String... links) throws IOException {
        HTTP jira = getJiraHttp();
        // wipe out the old test links
        JsonObject json = getIssue(issue);
        for (JsonElement existingLink : json.get(FIELDS).getAsJsonObject().get("issuelinks").getAsJsonArray()) {
            if ("Test "
                    .equals(existingLink.getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString())) {
                jira.simpleDelete("/rest/api/2/issueLink/" + existingLink.getAsJsonObject().get("id").getAsString());
            }
        }
        // add each link
        boolean pass = true;
        for (String link : links) {
            // setup our property
            JsonObject fields = new JsonObject();
            JsonObject type = new JsonObject();
            type.addProperty("name", "Test ");
            fields.add("type", type);
            JsonObject inward = new JsonObject();
            inward.addProperty("key", issue);
            fields.add("inwardIssue", inward);
            JsonObject outward = new JsonObject();
            outward.addProperty("key", link);
            fields.add("outwardIssue", outward);
            // make the actual call
            RequestData requestData = new RequestData();
            requestData.setJSON(fields);
            Response response = jira.simplePost("/rest/api/2/issueLink", requestData);

            if (response.getCode() != 201) {
                pass = false;
            }
        }
        return pass;
    }

    /**
     * Makes a call out to JIRA to update the automation links associated with the issue. All old automation links will be
     * removed, and the provided test links will be added
     *
     * @param issue - the unique jira id of the issue to be updated
     * @param links - a list of automation links to add to the issue
     * @return Boolean: was the update a success or not
     */
    public static boolean updateAutomateLinks(String issue, String... links) throws IOException {
        HTTP jira = getJiraHttp();
        // wipe out the old test links
        JsonObject json = getIssue(issue);
        for (JsonElement existingLink : json.get(FIELDS).getAsJsonObject().get("issuelinks").getAsJsonArray()) {
            if ("Automate"
                    .equals(existingLink.getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString())) {
                jira.simpleDelete("/rest/api/2/issueLink/" + existingLink.getAsJsonObject().get("id").getAsString());
            }
        }
        // add each link
        boolean pass = true;
        for (String link : links) {
            // setup our property
            JsonObject fields = new JsonObject();
            JsonObject type = new JsonObject();
            type.addProperty("name", "Automate");
            fields.add("type", type);
            JsonObject inward = new JsonObject();
            inward.addProperty("key", issue);
            fields.add("inwardIssue", inward);
            JsonObject outward = new JsonObject();
            outward.addProperty("key", link);
            fields.add("outwardIssue", outward);
            // make the actual call
            RequestData requestData = new RequestData();
            requestData.setJSON(fields);
            Response response = jira.simplePost("/rest/api/2/issueLink", requestData);
            if (response.getCode() != 201) {
                pass = false;
            }
        }
        return pass;
    }
}
