package unit;

import com.pmt.health.utilities.GherkinFeature;
import org.apache.commons.io.FileUtils;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static org.testng.Assert.*;

public class GherkinFeatureUT {

    private ITestResult scenario = new ITestResult() {
        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public ITestNGMethod getMethod() {
            return null;
        }

        @Override
        public void setStatus(int status) {

        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public IClass getTestClass() {
            return null;
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        public void setParameters(Object[] parameters) {

        }

        @Override
        public long getStartMillis() {
            return 0;
        }

        @Override
        public long getEndMillis() {
            return 0;
        }

        @Override
        public void setEndMillis(long millis) {

        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setThrowable(Throwable throwable) {

        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public String getHost() {
            return null;
        }

        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public String getTestName() {
            return null;
        }

        @Override
        public String getInstanceName() {
            return "Sample01IT";
        }

        @Override
        public ITestContext getTestContext() {
            return null;
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String name) {
            return null;
        }


    };
    private ITestResult scenarioMultipleOutline1 = new ITestResult() {
        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public ITestNGMethod getMethod() {
            return null;
        }

        @Override
        public void setStatus(int status) {

        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public IClass getTestClass() {
            return null;
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        public void setParameters(Object[] parameters) {

        }

        @Override
        public long getStartMillis() {
            return 0;
        }

        @Override
        public long getEndMillis() {
            return 0;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setThrowable(Throwable throwable) {

        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public String getHost() {
            return null;
        }

        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public void setEndMillis(long millis) {

        }

        @Override
        public String getTestName() {
            return null;
        }

        @Override
        public String getInstanceName() {
            return "Sample02IT";
        }

        @Override
        public ITestContext getTestContext() {
            return null;
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String name) {
            return null;
        }
    };

    private ITestResult scenarioMultipleOutline2 = new ITestResult() {
        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public ITestNGMethod getMethod() {
            return null;
        }

        @Override
        public void setStatus(int status) {

        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public IClass getTestClass() {
            return null;
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        public void setParameters(Object[] parameters) {

        }

        @Override
        public long getStartMillis() {
            return 0;
        }

        @Override
        public long getEndMillis() {
            return 0;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setThrowable(Throwable throwable) {

        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public String getHost() {
            return null;
        }

        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public void setEndMillis(long millis) {

        }

        @Override
        public String getTestName() {
            return null;
        }

        @Override
        public String getInstanceName() {
            return "Sample03IT";
        }

        @Override
        public ITestContext getTestContext() {
            return null;
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String name) {
            return null;
        }
    };

    private ITestResult scenarioOutline = new ITestResult() {
        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public ITestNGMethod getMethod() {
            return null;
        }

        @Override
        public void setStatus(int status) {

        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public IClass getTestClass() {
            return null;
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        public void setParameters(Object[] parameters) {

        }

        @Override
        public long getStartMillis() {
            return 0;
        }

        @Override
        public long getEndMillis() {
            return 0;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setThrowable(Throwable throwable) {

        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public String getHost() {
            return null;
        }

        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public void setEndMillis(long millis) {

        }

        @Override
        public String getTestName() {
            return null;
        }

        @Override
        public String getInstanceName() {
            return "Sample04IT";
        }

        @Override
        public ITestContext getTestContext() {
            return null;
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String name) {
            return null;
        }
    };


    private File runnerDir = new File("target/generated-test-sources/cucumber");
    private File SampleIT = new File("src/test/resources/Sample01IT.java");
    private File SampleMultipleOutline1IT = new File("src/test/resources/Sample02IT.java");
    private File SampleMultipleOutline2IT = new File("src/test/resources/Sample03IT.java");
    private File SampleOutlineIT = new File("src/test/resources/Sample04IT.java");

    @BeforeClass
    public void setupFile() throws IOException {
        runnerDir.mkdir();
        FileUtils.copyFile(SampleIT, new File(runnerDir, "Sample01IT.java"));
        FileUtils.copyFile(SampleMultipleOutline1IT, new File(runnerDir, "Sample02IT.java"));
        FileUtils.copyFile(SampleMultipleOutline2IT, new File(runnerDir, "Sample03IT.java"));
        FileUtils.copyFile(SampleOutlineIT, new File(runnerDir, "Sample04IT.java"));
    }

    @AfterClass
    public void deleteFile() {
        new File(runnerDir, "Sample01IT.java").delete();
        new File(runnerDir, "Sample02IT.java").delete();
        new File(runnerDir, "Sample03IT.java").delete();
        new File(runnerDir, "Sample04IT.java").delete();
    }

    @Test
    public void getScenarioInfoTest() {
        assertEquals(GherkinFeature.getScenarioInfo(scenario), "src/test/resources/sample.feature:27");
    }

    @Test
    public void getFeatureFileTest() {
        assertEquals(GherkinFeature.getFeatureFile(scenario), new File("src/test/resources/sample.feature"));
    }

    @Test
    public void getFeatureNameTest() {
        assertEquals(GherkinFeature.getFeatureName(scenario), "Educational Module");
    }

    @Test
    public void getFeatureTagsTest() {
        assertEquals(GherkinFeature.getFeatureTags(scenario),
                Arrays.asList("@feature-mc-25111", "@dashboard", "@educational", "@educational-module", "@pmi",
                        "@subscriber"));
    }

    @Test
    public void isScenarioOutlineFalseTest() {
        assertFalse(GherkinFeature.isScenarioOutline(scenario));
    }

    @Test
    public void isScenarioOutlineTrueTest() {
        assertTrue(GherkinFeature.isScenarioOutline(scenarioOutline));
    }

    @Test
    public void getScenarioIdTest() {
        assertEquals(GherkinFeature.getScenarioCucumberRun(scenario), 1);
        assertEquals(GherkinFeature.getScenarioCucumberRun(scenarioMultipleOutline1), 2);
        assertEquals(GherkinFeature.getScenarioCucumberRun(scenarioMultipleOutline2), 3);
        assertEquals(GherkinFeature.getScenarioCucumberRun(scenarioOutline), 4);
    }

    @Test
    public void getFeatureKeyTest() {
        assertEquals(GherkinFeature.getFeatureKey(scenario), "mc-25111");
    }

    @Test
    public void getScenarioKeyTest() {
        assertEquals(GherkinFeature.getScenarioKey(scenario), "mc-25112");
    }

    @Test
    public void getScenarioOutlineKeyTest() {
        assertEquals(GherkinFeature.getScenarioKey(scenarioOutline), "mc-25787");
    }

    @Test
    public void getScenarioNameTest() {
        assertEquals(GherkinFeature.getScenarioName(scenario), "User accesses education survey");
    }

    @Test
    public void getScenarioOutlineNameTest() {
        assertEquals(GherkinFeature.getScenarioName(scenarioOutline),
                "Requesting jobs with unavailable userId or ruleId");
    }

    @Test
    public void getScenarioTagsTest() {
        assertEquals(GherkinFeature.getScenarioTags(scenario),
                Arrays.asList("@mc-25112", "@access", "@access-education", "@smoke", "@automates-mc-78567"));
    }

    @Test
    public void getScenarioOutlineTagsTest() {
        assertEquals(GherkinFeature.getScenarioTags(scenarioOutline),
                Arrays.asList("@mc-25787", "@tests-mc-98764", "@scheduling"));
    }

    @Test
    public void getScenarioStepsTest() {
        assertEquals(GherkinFeature.getScenarioSteps(scenario),
                Arrays.asList("Given I have completed ehr consent", "When I login",
                        "#some comment, which is totally fine", "When I select the education survey button",
                        "Then I see the education survey welcome page"));
    }

    @Test
    public void getScenarioOutlineStepsTest() {
        assertEquals(GherkinFeature.getScenarioSteps(scenarioOutline),
                Arrays.asList("Given I am an administrator",
                        "When I make a GET request to the \"/api/schedulers/jobs\" endpoint with parameters:",
                        "| userId | <userId> |", "| ruleId | <ruleId> |", "Then I receive an empty JSON array " +
                                "response",
                        "And I receive a \"200\" response"));
    }

    @Test
    public void getScenarioExamplesTest() {
        assertNull(GherkinFeature.getScenarioExamples(scenario));
    }

    @Test
    public void getScenarioOutlineExamplesTest() {
        assertEquals(GherkinFeature.getScenarioExamples(scenarioOutline),
                "Examples:\n| userId      | ruleId |\n| -1          | 1      |\n" +
                        "# this is an important line\n| currentUser | -2     |");
    }

    @Test
    public void getScenarioOutlineMultipleExamples1Test() {
        assertEquals(GherkinFeature.getScenarioExamples(scenarioMultipleOutline1),
                "@smoke\nExamples:\n| state  |\n| Alaska |\n\n@regression @banner-states\nExamples:\n" +
                        "| state                          |\n| American_Samoa                 |\n" +
                        "| Arkansas                       |\n| Colorado                       |\n" +
                        "| Delaware                       |\n| District_of_Columbia           |\n" +
                        "| Federated_States_of_Micronesia |\n| Guam                           |\n" +
                        "| Hawaii                         |\n| Idaho                          |\n" +
                        "| Indiana                        |\n| Iowa                           |");
    }

    @Test
    public void getScenarioOutlineMultipleExamples2Test() {
        assertEquals(GherkinFeature.getScenarioExamples(scenarioMultipleOutline2),
                "@smoke\nExamples:\n| state  |\n| Alaska |\n\n@regression @banner-states\nExamples:\n" +
                        "| state                          |\n| American_Samoa                 |\n" +
                        "| Arkansas                       |\n| Colorado                       |\n" +
                        "| Delaware                       |\n| District_of_Columbia           |\n" +
                        "| Federated_States_of_Micronesia |\n| Guam                           |\n" +
                        "| Hawaii                         |\n| Idaho                          |\n" +
                        "| Indiana                        |\n| Iowa                           |");
    }

    @Test
    public void getScenarioDescriptionNoneTest() {
        assertEquals(GherkinFeature.getScenarioDescription(scenarioOutline), "");
    }

    @Test
    public void getScenarioDescriptionTest() {
        assertEquals(GherkinFeature.getScenarioDescription(scenario), "A multi-line\ndescription");
    }

    @Test
    public void getTagsTest() {
        assertEquals(GherkinFeature.getTags(scenario).size(), 11);
    }

    @Test
    public void getTagsMultipleExamples1Test() {
        assertEquals(GherkinFeature.getTags(scenarioMultipleOutline1).size(), 10);
    }

    @Test
    public void getTagsMultipleExamples2Test() {
        assertEquals(GherkinFeature.getTags(scenarioMultipleOutline2).size(), 11);
    }

    @Test
    public void containsTagFalseTest() {
        assertFalse(GherkinFeature.containsTag(scenario, "hello-world"));
    }

    @Test
    public void containsTagTest() {
        assertTrue(GherkinFeature.containsTag(scenario, "@smoke"));
    }

    @Test
    public void getTestLinksTest() {
        assertEquals(GherkinFeature.getTestLinks(scenarioOutline), Arrays.asList("mc-98764"));
    }

    @Test
    public void getTestLinksNoneTest() {
        assertEquals(GherkinFeature.getTestLinks(scenario).size(), 0);
    }

    @Test
    public void getAutomateLinksTest() {
        assertEquals(GherkinFeature.getAutomateLinks(scenarioOutline).size(), 0);
    }

    @Test
    public void getAutomateLinksNoneTest() {
        assertEquals(GherkinFeature.getAutomateLinks(scenario), Arrays.asList("mc-78567"));
    }

    @Test
    public void getLabelsTest() {
        assertEquals(GherkinFeature.getLabels(scenario).size(), 8);
    }

    @Test
    public void getLabels2Test() {
        assertEquals(GherkinFeature.getLabels(scenarioOutline).size(), 6);
    }

    @Test
    public void getScenarioOutlineExampleRowNotTest() {
        assertEquals(GherkinFeature.getScenarioOutlineExampleRow(scenario), null);
    }

    @Test
    public void getScenarioOutlineExampleRowTest() {
        assertEquals(GherkinFeature.getScenarioOutlineExampleRow(scenarioOutline), "| currentUser | -2     |");
    }

    @Test
    public void getScenarioOutlineExampleRowMultipleTest() {
        assertEquals(GherkinFeature.getScenarioOutlineExampleRow(scenarioMultipleOutline2), "| Hawaii                         |");
    }

    @Test
    public void getScenarioOutlineExampleRowMultiple2Test() {
        assertEquals(GherkinFeature.getScenarioOutlineExampleRow(scenarioMultipleOutline1), "| Alaska |");
    }
}
