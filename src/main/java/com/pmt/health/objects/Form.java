package com.pmt.health.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Form {

    private static final Logger log = Logger.getLogger(Form.class);

    private final HTTP http;

    private final String name;
    private String id;
    private String formId;

    public Form(String name, HTTP http, String formId) {
        log.info("Form name: " + name + ", formId: " + formId);
        this.name = name;
        this.http = http;
        this.formId = formId;
    }

    public Form(String name, HTTP http, String id, String formId) {
        log.info("Form name: " + name + ", id: " + id + ", formId: " + formId);
        this.name = name;
        this.http = http;
        this.id = id;
        this.formId = formId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getVersionId() throws IOException {
        return getFormVersionInfo().get("id").getAsInt();
    }

    public JsonArray getPages() throws IOException {
        return getFormVersionInfo().getAsJsonObject("editMode").getAsJsonArray("pages");
    }

    private JsonObject getFormVersionInfo() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("projection", "ACTIVE_ONLY");
        RequestData requestData = new RequestData();
        requestData.setParams(parameters);
        Response response = http.get("/api/forms/" + formId, requestData);
        JsonObject json = response.getObjectData();
        return json.getAsJsonArray("formVersions").get(0).getAsJsonObject();
    }

    public interface ProgramForm {
        String getTitle();
    }
}
