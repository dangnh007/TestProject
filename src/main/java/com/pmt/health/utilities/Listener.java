package com.pmt.health.utilities;

import com.google.gson.JsonObject;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Reporter.Result;
import cucumber.api.Scenario;
import org.testng.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import org.testng.internal.TestResult;
import org.testng.log4testng.Logger;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.pmt.health.steps.Configuration.REPORTER;

/**
 * Appends additional test links and information into the TestNG report file,
 * for easier tracking and viewing of detailed custom test reports. This class
 * should be specified as a listener in the custom-runner class, and/or in the
 * TestNG xml file.
 *
 * @author Max Saperstone
 */
public class Listener extends TestListenerAdapter {

    private static final Logger log = Logger.getLogger(Listener.class);

    private static final String SKIPPING = "Skipping test case ";
    private static final String ANDROID_TAG = "@android";
    private static final String IOS_TAG = "@ios";
    private static final String WEB_TAG = "@web";

    private static final String CUCUMBER_REPORT_LOCATION = "target/cucumber-parallel";
    private static final String CUSTOM_REPORT_LOCATION = "target/detailed-reports";
    private static final String OUTPUT_BREAK = " | ";
    private static final String LINK_START = "<a target='_blank' href='";
    private static final String LINK_MIDDLE = "'>";
    private static final String LINK_END = "</a>";
    private static final String BREAK = "<br />";
    private static final String TIME_UNIT = " seconds";

    private final Zephyr zephyr = new Zephyr();
    private String projectId;
    private String cycle;
    private Configuration configuration = new Configuration(null);

    private List<String> updatedTestCases = new ArrayList<>();

    public Listener() throws IOException {
        super();
    }

    private void uploadTestArtifacts(String executionId, ITestResult result) throws IOException {
        Reporter reporter = (Reporter) result.getAttribute(REPORTER);
        zephyr.uploadExecutionResults(executionId, reporter.getFile());
        for (String screenshot : reporter.getScreenshots()) {
            zephyr.uploadExecutionResults(executionId, new File(screenshot));
        }
        if (Sauce.isUsed()) {
            File file = new File(Sauce.getRecording(result));
            zephyr.uploadExecutionResults(executionId, file);
        }
    }

    /**
     * Checks to see if the skipAll flag is set or not
     *
     * @return boolean
     */
    private boolean skipAll() {
        return System.getProperty("skip.all") != null && "true".equals(System.getProperty("skip.all"));
    }

    /**
     * Before each test runs, check to determine if the corresponding module, based on the scenario tags, is turned
     * on or off. If the module is turned off, skip those tests
     *
     * @param result the TestNG test being executed
     */
    @Override
    public void onTestStart(ITestResult result) {
        if (skipAll()) {
            log.warn(SKIPPING + GherkinFeature.getScenarioKey(result) + " (" + GherkinFeature.getFeatureName(result) +
                    " - " + GherkinFeature.getScenarioName(result) + "), as skip all flag has been set");
            result.setStatus(ITestResult.SKIP);
            throw new SkipException(SKIPPING + " because skip all flag is set.");
        }

        //check for tests that are device specific
        isThisEnvironment(result);
    }

    private void isThisEnvironment(ITestResult result) {
        if (!forEnv(result)) {
            log.warn(SKIPPING + GherkinFeature.getScenarioKey(result) + " (" + GherkinFeature.getFeatureName(result) +
                    " - " + GherkinFeature.getScenarioName(result) + "), as it is not intended for this environment");
            result.setStatus(ITestResult.SKIP);
            throw new SkipException(SKIPPING + " as it is not intended for this environment");
        }
    }

    /**
     * Before each test runs, it checks scenario tag "env-xxx" and will run scenario based of environment that you specify
     *
     * @param result the TestNG test being executed
     * @return true if tag is matching with environment, will skip running scenario if tag is not matching env.
     */
    public static boolean forEnv(ITestResult result) {
        boolean forSpecificEnv = false;
        for (String tag : GherkinFeature.getTags(result)) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (tag.startsWith("env-")) {
                forSpecificEnv = true;
                break;
            }
        }
        if (forSpecificEnv) {
            return GherkinFeature.containsTag(result, "@env-" + Configuration.getEnvironment());
        }
        return true;
    }

    /**
     * Runs the default TestNG onStart, and then creates a new testing cycle in
     * JIRA
     */
    @Override
    public void onStart(ITestContext context) {
        super.onStart(context);
        if (Jira.updateJIRA() != null && !skipAll()) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            try {
                projectId = Jira.getProjectId();
                cycle = zephyr.createCycle(projectId,
                        Device.getDeviceInfo() + " " + Jira.updateJIRA() + " " + dateFormat.format(date));
            } catch (IOException e) {
                log.error("Create Cycle", e);
            }
        }
   }

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);
        if (Jira.updateJIRA() != null && !skipAll()) {
            JsonObject systemInfo;
            try {
                systemInfo = configuration.getSystemInfo(null);
            } catch (IOException e) {
                log.error("Unsupported Encoding in onFinish.", e);
                systemInfo = new JsonObject();
            }
            try {
                zephyr.updateCycle(cycle, null,
                        "Automated testing run on version " + configuration.getVersion(systemInfo) + " of " + configuration.getProgram(),
                        "", Configuration.getEnvironment());
            } catch (IOException e) {
                log.error("Update Cycle", e);
            }
        }
        if (System.getProperty("archive") != null && "true".equals(System.getProperty("archive"))) {
            String timeStamp = new SimpleDateFormat("-yyyy-mm-dd-hh-mm-ss").format(new Date());
            String sourceDirectory = "target";
            String targetDirectory =
                    "test-test-archive/" + System.getProperty("tags").substring(1) + timeStamp + ".zip";

            File archiveDirectory = new File("test-test-archive");
            if (!archiveDirectory.exists()) {
                archiveDirectory.mkdir();
            }

            ZipUtil.pack(new File(sourceDirectory), new File(targetDirectory));
        }
    }

    /**
     * Runs the default TestNG onTestFailure, and adds additional information
     * into the testng reporter
     */
    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        log.error(result.getInstanceName() + " failed.");
        try {
            output(result);
            Sauce.updateJob(result);
            if (Jira.updateJIRA() != null) {
                updateTestSteps(result);
                String executionId = recordTest(result);
                zephyr.markExecutionFailed(executionId);
                uploadTestArtifacts(executionId, result);
            }
        } catch (IOException e) {
            log.error(result.getTestName() + " FAILED\n*****************************************************************\n", e);
        }
    }

    /**
     * Runs the default TestNG onTestSkipped, and adds additional information
     * into the testng reporter
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        try {
            if (Jira.updateJIRA() != null) {
                updateTestSteps(result);
                if (!skipAll()) {
                    String executionId = recordTest(result);
                    zephyr.markExecutionUnexecuted(executionId);
                }
            }
        } catch (IOException uee) {
            log.error("Update Jira Test Steps on Skipped.", uee);
        }
    }

    /**
     * Runs the default TestNG onTestSuccess, and adds additional information
     * into the testng reporter
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        log.info(result.getInstanceName() + " was successful.");
        try {
            output(result);
            Sauce.updateJob(result);
            if (Jira.updateJIRA() != null) {
                updateTestSteps(result);
                String executionId = recordTest(result);
                zephyr.markExecutionPassed(executionId);
                uploadTestArtifacts(executionId, result);
            }
        } catch (IOException uee) {
            log.error("Update Jira Test Steps on Success.", uee);
        }
    }

    // Sonar does not regard Assert.fail as stopping
    @SuppressWarnings("squid:S2259")
    private void output(ITestResult result) {
        if (result.getStatus() == TestResult.SKIP) {
            log.info("This test was skipped. There is no scenario.");
            return;
        }
        Scenario scenario = GherkinFeature.getScenario(result);
        if (scenario == null) {
            log.error("Scenario was null. This is a problem.");
            Assert.fail("Scenario was null.");
        }

        File cucumberOutput = new File(
                CUCUMBER_REPORT_LOCATION + File.separator + GherkinFeature.getScenarioCucumberRun(result) + File.separator +
                        "index.html");
        File customOutput =
                new File(CUSTOM_REPORT_LOCATION + File.separator + GherkinHelpers.getFileName(scenario));
        org.testng.Reporter.log(LINK_START + GherkinHelpers.getTestURL(scenario) + LINK_MIDDLE +
                GherkinHelpers.getFeatureName(scenario) + " - " + scenario.getName() + LINK_END);
        org.testng.Reporter.log(
                BREAK + LINK_START + "../../" + cucumberOutput.getPath() + LINK_MIDDLE + "Brief Report" + LINK_END +
                        OUTPUT_BREAK + LINK_START + "../../" + customOutput.getPath() + LINK_MIDDLE +
                        "Detailed Report" + LINK_END);
        org.testng.Reporter.log(BREAK + Result.values()[result.getStatus()] + OUTPUT_BREAK +
                (result.getEndMillis() - result.getStartMillis()) / 1000 + TIME_UNIT);
        Reporter reporter = (Reporter) result.getAttribute(REPORTER);
        if (reporter == null) {
            log.error("Unable to retrieve Reporter object. NO REPORT WILL BE CREATED.");
            return;
        }
        reporter.finalizeOutputFile(result.getStatus());
    }

    private String recordTest(ITestResult result) throws IOException {
        String testKey = GherkinFeature.getScenarioKey(result);
        zephyr.addTestToCycle(projectId, cycle, testKey);
        return zephyr.createExecution(projectId, cycle, Jira.getIssueId(testKey));
    }

    /**
     * Update the corresponding JIRA test case with the test information from the Gherkin file.
     *
     * @param result the TestNG test being executed
     */
    @SuppressWarnings("squid:S2250")
    private void updateTestSteps(ITestResult result) throws IOException {
        // get initial test information
        String testKey = GherkinFeature.getScenarioKey(result);
        // update scenario
        if (updatedTestCases.contains(testKey)) {
            return;
        }
        updatedTestCases.add(testKey);
        Jira.updateTitle(testKey, GherkinFeature.getScenarioName(result));
        Jira.updateDescription(testKey, GherkinFeature.getScenarioDescription(result)); //get the githash
        Collection<String> testLinks = GherkinFeature.getTestLinks(result);
        Jira.updateTestLinks(testKey, testLinks.toArray(new String[testLinks.size()]));
        Collection<String> automateLinks = GherkinFeature.getAutomateLinks(result);
        Jira.updateAutomateLinks(testKey, automateLinks.toArray(new String[automateLinks.size()]));
        Collection<String> labels = GherkinFeature.getLabels(result);
        Jira.updateLabels(testKey, labels.toArray(new String[labels.size()]));
        // remove all prior test cases
        zephyr.clearTestSteps(testKey);
        //add new test steps
        List<String> steps = GherkinFeature.getScenarioSteps(result);
        for (String step : steps) {
            zephyr.addTestStep(testKey, step);
        }
        //add example data
        String examples = GherkinFeature.getScenarioExamples(result);
        if (examples != null) {
            Jira.updateExamples(testKey, examples);
        }
    }
}
