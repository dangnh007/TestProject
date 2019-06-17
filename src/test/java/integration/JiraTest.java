package integration;

import com.google.gson.JsonObject;
import com.pmt.health.exceptions.VibrentIOException;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.utilities.Constants;
import com.pmt.health.utilities.Jira;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.*;

public class JiraTest {

    private String update;

    @BeforeClass
    public void setupArrays() {
        if (System.getProperty("jira.update") != null) {
            update = System.getProperty("jira.update");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (update != null) {
            System.setProperty("jira.update", update);
        }
    }

    @AfterMethod
    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("jira.update");
    }

    @Test
    public void getJiraHttpTest() {
        assertEquals(Jira.getJiraHttp().getClass(), HTTP.class);
    }

    @Test
    public void updateJiraEmptyTest() {
        assertNull(Jira.updateJIRA());
    }

    @Test
    public void updateJiraFalseTest() {
        System.setProperty("jira.update", "Hello World");
        assertEquals(Jira.updateJIRA(), "Hello World");
    }

    @Test
    public void getProjectIdTest() throws IOException {
        assertEquals(Jira.getProjectId(), "11705");
    }

    @Test
    public void getIssueIdNullTest() throws IOException {
        try {
            Jira.getIssueId(null);
            Assert.fail(Constants.SHOULD_FAIL);
        } catch (VibrentIOException vioe) {
            assertEquals(vioe.getResponseCode(), 404);
            Assert.assertEquals(vioe.getResponseData(), "{\"errorMessages\":[\"Issue Does Not Exist\"],\"errors\":{}}");
        }
    }

    @Test
    public void getIssueIdBadTest() throws IOException {

        try {
            Jira.getIssueId("BAD_KEY");
            Assert.fail(Constants.SHOULD_FAIL);
        } catch (VibrentIOException vioe) {
            assertEquals(vioe.getResponseCode(), 404);
            Assert.assertEquals(vioe.getResponseData(), "{\"errorMessages\":[\"Issue Does Not Exist\"],\"errors\":{}}");
        }
    }

    @Test
    public void getIssueIdGoodTest() throws IOException {
        assertEquals(Jira.getIssueId("ac-21484"), "66075");
    }

    @Test
    public void getCycleIdNullTest() throws IOException {
        assertEquals(Jira.getCycleId(null), "-1");
    }

    @Test
    public void getCycleIdGoodTest() throws IOException {
        assertEquals(Jira.getCycleId("R10.1"), "15803");
    }

    @Test
    public void getCycleIdBadKeyTest() throws IOException {
        assertEquals(Jira.getCycleId("BAD_KEY"), "-1");
    }

    @Test
    public void updateTitleTest() throws IOException {
        JsonObject fields = new JsonObject();
        assertTrue(Jira.updateTitle("ac-21484", "SAMPLE "));
    }

    @Test
    public void updateDescriptionTest() throws IOException {
        JsonObject fields = new JsonObject();
        assertTrue(Jira.updateDescription("ac-21484", "DO NOT DELETE ME, I'M USED FOR INTEGRATION TESTING!"));
    }

    @Test
    public void updateExampleTest() throws IOException {
        JsonObject fields = new JsonObject();
        assertTrue(Jira.updateExamples("ac-21484",
                "    Examples:\n      | form             |\n      | InitialConsent   |\n" +
                        "      | TheBasics        |\n      | OverallHealth    |\n" +
                        "      | Lifestyle        |\n      | EHRConsentPII    |\n      | All_Information  |"));
    }

    @Test
    public void updateLabelsTest() throws IOException {
        assertTrue(Jira.updateLabels("ac-21484", "no-ui"));
    }

    @Test
    public void addTestLinkTest() throws IOException {
        assertTrue(Jira.updateTestLinks("ac-21484", "ac-20000"));
    }

    @Test
    public void addAutomateLinkTest() throws IOException {
        assertTrue(Jira.updateAutomateLinks("ac-21484", "ac-20000"));
    }
}
