package integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pmt.health.exceptions.VibrentIOException;
import com.pmt.health.utilities.Constants;
import com.pmt.health.utilities.Zephyr;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ZephyrTest {
    private final Zephyr zephyr = new Zephyr();
    private final String username = System.getProperty("jira.username").toLowerCase();

    private final SimpleDateFormat dt = new SimpleDateFormat("dd/MMM/yy");

    private final JsonParser parser = new JsonParser();
    private final String projectId = "10903";
    private final String testId = "66075";
    private final String testName = "ac-21484";

    private String newCycle;
    private String newExecution;

    @Test
    public void addTestStepTest() throws IOException {
        zephyr.clearTestSteps(testName);
        assertTrue(zephyr.addTestStep(testName, "Test Step"));
    }

    @Test(dependsOnMethods = {"addTestStepTest"})
    public void getTestStepsTest() throws IOException {
        assertEquals(zephyr.getTestSteps(testName).size(), 1);
        assertEquals(zephyr.getTestSteps(testName).get(0).getAsJsonObject().get("step").getAsString(), "Test Step");
    }

    @Test(dependsOnMethods = {"getTestStepsTest"})
    public void deleteTestStepsTest() throws IOException {
        zephyr.clearTestSteps(testName);
        assertEquals(zephyr.getTestSteps(testName).size(), 0);
    }

    @Test
    public void createCycleTest() throws IOException {
        newCycle = zephyr.createCycle(projectId, "Test Cycle", "Test Description");
    }

    @Test(dependsOnMethods = {"createCycleTest"})
    public void getCycleTest() throws IOException {
        Date today = new Date();
        String date = dt.format(today);
        JsonObject cycle = zephyr.getCycle(newCycle);
        cycle.remove("createdDate");
        assertEquals(cycle, parser.parse("{\"endDate\":\"" + date +
                "\",\"description\":\"Test Description\",\"versionName\":\"Unscheduled\",\"sprintId\":null,\"versionId\":-1,\"environment\":\"\",\"build\":\"\",\"createdBy\":\"" +
                username + "\",\"name\":\"Test Cycle\",\"modifiedBy\":\"" + username + "\",\"id\":" + newCycle +
                ",\"projectId\":" + projectId + ",\"startDate\":\"" + date + "\"}"));
    }

    @Test(dependsOnMethods = {"getCycleTest"})
    public void updateCycleTest() throws IOException {
        Date today = new Date();
        String date = dt.format(today);
        assertTrue(zephyr.updateCycle(newCycle, "Updated Test Cycle", "Updated Description", null, null));
        JsonObject cycle = zephyr.getCycle(newCycle);
        cycle.remove("createdDate");
        assertEquals(cycle, parser.parse("{\"endDate\":\"" + date +
                "\",\"description\":\"Updated Description\",\"versionName\":\"Unscheduled\",\"sprintId\":null,\"versionId\":-1,\"environment\":\"\",\"build\":\"\",\"createdBy\":\"" +
                username + "\",\"name\":\"Updated Test Cycle\",\"modifiedBy\":\"" + username + "\",\"id\":" + newCycle +
                ",\"projectId\":" + projectId + ",\"startDate\":\"" + date + "\"}"));
    }

    @Test(dependsOnMethods = {"updateCycleTest"})
    public void addTestToCycleTest() throws IOException {
        assertTrue(zephyr.addTestToCycle(projectId, newCycle, testName));
    }

    @Test(dependsOnMethods = {"updateCycleTest"})
    public void createExecutionTest() throws IOException {
        newExecution = zephyr.createExecution(projectId, newCycle, testId);
    }

    @Test(dependsOnMethods = {"createExecutionTest"})
    public void getExecutionTest() throws IOException {
        JsonObject execution = zephyr.getExecution(newExecution);
        assertEquals(execution.get("id").getAsString(), newExecution);
        assertEquals(execution.get("executionStatus").getAsString(), "-1");
        assertEquals(execution.get("cycleId").getAsString(), newCycle);
        assertEquals(execution.get("cycleName").getAsString(), "Updated Test Cycle");
        assertEquals(execution.get("projectId").getAsString(), projectId);
        assertEquals(execution.get("modifiedBy").getAsString(), username);
        assertEquals(execution.get("issueId").getAsString(), testId);
        assertEquals(execution.get("issueKey").getAsString(), testName.toUpperCase());
    }

    @Test(dependsOnMethods = {"getExecutionTest"})
    public void markExecutionPassedTest() throws IOException {
        zephyr.markExecutionPassed(newExecution);
        assertEquals(zephyr.getExecution(newExecution).get("executionStatus").getAsInt(), 1);
    }

    @Test(dependsOnMethods = {"markExecutionPassedTest"})
    public void markExecutionFailedTest() throws IOException {
        zephyr.markExecutionFailed(newExecution);
        assertEquals(zephyr.getExecution(newExecution).get("executionStatus").getAsInt(), 2);
    }

    @Test(dependsOnMethods = {"markExecutionFailedTest"})
    public void markExecutionWIPTest() throws IOException {
        zephyr.markExecutionWIP(newExecution);
        assertEquals(zephyr.getExecution(newExecution).get("executionStatus").getAsInt(), 3);
    }

    @Test(dependsOnMethods = {"markExecutionWIPTest"})
    public void markExecutionBlockedTest() throws IOException {
        zephyr.markExecutionBlocked(newExecution);
        assertEquals(zephyr.getExecution(newExecution).get("executionStatus").getAsInt(), 4);
    }

    @Test(dependsOnMethods = {"markExecutionBlockedTest"})
    public void markExecutionUnexecutedTest() throws IOException {
        zephyr.markExecutionUnexecuted(newExecution);
        assertEquals(zephyr.getExecution(newExecution).get("executionStatus").getAsInt(), -1);
    }

    @Test(dependsOnMethods = {"markExecutionUnexecutedTest"})
    public void uploadExecutionResultsFileTest() throws IOException {
        zephyr.uploadExecutionResults(newExecution, new File("Jenkinsfile"));
    }

    @Test(dependsOnMethods = {"uploadExecutionResultsFileTest"})
    public void uploadExecutionResultsImageTest() throws IOException {
        zephyr.uploadExecutionResults(newExecution, new File("src/test/resources/signature.png"));
    }

    @Test(dependsOnMethods = {"uploadExecutionResultsImageTest"})
    public void getExecutionAttachmentsTest() throws IOException {
        JsonArray attachments = zephyr.getExecutionAttachments(newExecution);
        assertEquals(attachments.size(), 1);
        assertEquals(attachments.get(0).getAsJsonObject().get("fileName").getAsString(), "Jenkinsfile");
    }

    @Test(dependsOnMethods = {"getExecutionAttachmentsTest"})
    public void deleteCycleTest() throws IOException {
        assertTrue(zephyr.deleteCycle(newCycle));
        try {
            JsonObject cycle = zephyr.getCycle(newCycle);
            Assert.fail(Constants.SHOULD_FAIL);
        } catch (VibrentIOException vioe) {
            Assert.assertEquals(vioe.getResponseData(), "{\"Error\":\"Cycle does not exist.\"}");
            Assert.assertEquals(vioe.getResponseCode(), 406);
        }
    }
}
