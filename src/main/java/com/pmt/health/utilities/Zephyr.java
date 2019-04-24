package com.pmt.health.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Zephyr {

    private static final String PROJECT = "projectId";
    private static final String VERSION = "versionId";
    private static final String ZAPI_TEST = "/rest/zapi/latest/teststep/";
    private static final String ZAPI_CYCLE = "/rest/zapi/latest/cycle/";
    private final HTTP service;

    public Zephyr() {
        service = Jira.getJiraHttp();
    }

    public boolean addTestStep(String testKey, String step) throws IOException {
        String id = Jira.getIssueId(testKey);
        JsonObject jsonStep = new JsonObject();
        jsonStep.addProperty("step", step);
        RequestData request = new RequestData();
        request.setJSON(jsonStep);
        return service.simplePost(ZAPI_TEST + id, request).getCode() == 200;
    }

    public JsonArray getTestSteps(String testKey) throws IOException {
        return service.simpleGet(ZAPI_TEST + Jira.getIssueId(testKey)).getObjectData().get("stepBeanCollection").getAsJsonArray();
    }

    public void clearTestSteps(String testKey) throws IOException {
        String id = Jira.getIssueId(testKey);
        JsonArray steps = getTestSteps(testKey);
        for (JsonElement step : steps) {
            String stepId = ((JsonObject) step).get("id").getAsString();
            service.simpleDelete(ZAPI_TEST + id + "/" + stepId);
        }
    }

    public JsonObject getCycle(String cycleId) throws IOException {
        return service.simpleGet(ZAPI_CYCLE + cycleId).getObjectData();
    }

    public String createCycle(String projectId, String cycleName) throws IOException {
        return createCycle(projectId, Jira.getCycleId(null), cycleName, "", "", "");
    }

    public String createCycle(String projectId, String cycleName, String cycleDescription) throws IOException {
        return createCycle(projectId, Jira.getCycleId(null), cycleName, cycleDescription, "", "");
    }

    private String createCycle(String projectId, String version, String cycleName, String cycleDescription, String build,
                            String environment) throws IOException {
        JsonObject cycle = new JsonObject();
        cycle.addProperty("clonedCycleId", "");
        cycle.addProperty(PROJECT, String.valueOf(projectId));
        cycle.addProperty("name", cycleName);
        cycle.addProperty("description", cycleDescription);
        cycle.addProperty("build", build);
        cycle.addProperty("environment", environment);
        Date today = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MMM/yy");
        String start = dt.format(today);
        cycle.addProperty("startDate", start);
        cycle.addProperty("endDate", start);
        cycle.addProperty(VERSION, version);
        RequestData requestData = new RequestData();
        requestData.setJSON(cycle);
        Response response = service.simplePost(ZAPI_CYCLE, requestData);
        if (response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsString();
        }
        return "0";
    }

    public boolean updateCycle(String cycleId, String cycleName, String cycleDescription, String build,
                               String environment) throws IOException {
        JsonObject cycle = new JsonObject();
        cycle.addProperty("id", cycleId);
        if (cycleName != null) {
            cycle.addProperty("name", cycleName);
        }
        if (cycleDescription != null) {
            cycle.addProperty("description", cycleDescription);
        }
        if (build != null) {
            cycle.addProperty("build", build);
        }
        if (environment != null) {
            cycle.addProperty("environment", environment);
        }
        RequestData requestData = new RequestData();
        requestData.setJSON(cycle);
        Response response = service.simplePut(ZAPI_CYCLE, requestData);
        return response.getCode() == 200;
    }

    public boolean deleteCycle(String cycleId) throws IOException {
        Response response = service.simpleDelete(ZAPI_CYCLE + cycleId);
        return response.getCode() == 200;
    }

    public boolean addTestToCycle(String projectId, String cycleId, String... testIds) throws IOException {
        JsonArray tests = new JsonArray();
        for (String testId : testIds) {
            tests.add(testId);
        }
        JsonObject testForCycle = new JsonObject();
        testForCycle.addProperty(PROJECT, projectId);
        testForCycle.addProperty("cycleId", cycleId);
        testForCycle.add("issues", tests);
        testForCycle.addProperty(VERSION, "-1");
        testForCycle.addProperty("method", "1");
        RequestData requestData = new RequestData();
        requestData.setJSON(testForCycle);
        Response response = service.simplePost("/rest/zapi/latest/execution/addTestsToCycle", requestData);
        return response.getCode() == 200;
    }

    public JsonObject getExecution(String executionId) throws IOException {
        return service.simpleGet("/rest/zapi/latest/execution/" + executionId).getObjectData()
                .getAsJsonObject("execution");
    }

    @SuppressWarnings("squid:S1751")
    public String createExecution(String projectId, String cycleId, String testId) throws IOException {
        JsonObject execution = new JsonObject();
        execution.addProperty(PROJECT, projectId);
        execution.addProperty("cycleId", cycleId);
        execution.addProperty("issueId", testId);
        execution.addProperty(VERSION, "-1");
        RequestData requestData = new RequestData();
        requestData.setJSON(execution);
        Response response = service.simplePost("/rest/zapi/latest/execution", requestData);
        Set<Map.Entry<String, JsonElement>> entries = response.getObjectData().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonObject newExecution = entry.getValue().getAsJsonObject();
            return newExecution.get("id").getAsString();
        }
        return "-1";
    }

    private void executeTestInCycle(String executionId, int status) throws IOException {
        JsonObject execution = new JsonObject();
        execution.addProperty("status", status);
        RequestData requestData = new RequestData();
        requestData.setJSON(execution);
        service.simplePut("/rest/zapi/latest/execution/" + executionId + "/execute", requestData);
    }

    public void markExecutionPassed(String executionId) throws IOException {
        executeTestInCycle(executionId, 1);
    }

    public void markExecutionFailed(String executionId) throws IOException {
        executeTestInCycle(executionId, 2);
    }

    public void markExecutionWIP(String executionId) throws IOException {
        executeTestInCycle(executionId, 3);
    }

    public void markExecutionBlocked(String executionId) throws IOException {
        executeTestInCycle(executionId, 4);
    }

    public void markExecutionUnexecuted(String executionId) throws IOException {
        executeTestInCycle(executionId, -1);
    }

    public void uploadExecutionResults(String executionId, File results) throws IOException {
        //set our upload xsrf headers
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        service.addHeaders(headers);
        //make our call and response
        Map<String, String> params = new HashMap<>();
        params.put("entityId", String.valueOf(executionId));
        params.put("entityType", "EXECUTION");
        RequestData requestData = new RequestData();
        requestData.setParams(params);
        service.post("/rest/zapi/latest/attachment", requestData, results);
        //reset our headers
        service.resetHeaders();
    }

    public JsonArray getExecutionAttachments(String executionId) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("entityId", String.valueOf(executionId));
        params.put("entityType", "EXECUTION");
        RequestData requestData = new RequestData();
        requestData.setParams(params);
        return service.simpleGet("/rest/zapi/latest/attachment/attachmentsByEntity", requestData).getObjectData()
                .getAsJsonArray("data");
    }

    public JsonObject getExecutionAttachment(int fileId) throws IOException {
        return service.simpleGet("/rest/zapi/latest/attachment/" + fileId + "/file").getObjectData();
    }
}
