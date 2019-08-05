package com.pmt.health.utilities;

import cucumber.api.Scenario;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pmt.health.steps.Configuration.SCENARIO;

public class GherkinFeature {
    private static final Logger log = Logger.getLogger(GherkinFeature.class);
    private static final String PROJECT = "project";
    private static final String PROJECT_LINKS = "project.links";

    private static final String RUNNER_LOCATION = "target/generated-test-sources/cucumber";
    private static final String LOCATE = "Unable to locate feature file at ";
    private static final String READ = "Unable to read feature file at ";

    private GherkinFeature() {
    }

    private static int checkPatternMatch(String line) {
        String cucumberRun = "0";
        Pattern p = Pattern.compile("target/cucumber-parallel/(\\d+).json");
        Matcher m = p.matcher(line);
        if (m.find()) {
            cucumberRun = m.group(1);
        }
        return Integer.parseInt(cucumberRun);
    }

    /**
     * Determines the unique scenarioID, based on the provided test object provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static int getScenarioCucumberRun(ITestResult test) {
        String testRunner = test.getInstanceName();
        File runner = new File(RUNNER_LOCATION, testRunner + ".java");
        try (BufferedReader br = new BufferedReader(new FileReader(runner))) {
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("plugin = ")) {
                    return checkPatternMatch(line);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Unable to locate scenario runner at " + runner.getAbsolutePath() + ". " + e);
            return 0;
        } catch (IOException e) {
            log.error("Unable to read scenario runner at " + runner.getAbsolutePath() + ". " + e);
            return 0;
        }
        return 0;
    }

    /**
     * Retrieves the Gherkin Scenario being executed
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static Scenario getScenario(ITestResult test) {
        return (Scenario) test.getAttribute(SCENARIO);
    }

    public static String getScenarioInfo(ITestResult test) {
        String testRunner = test.getInstanceName();
        File runner = new File(RUNNER_LOCATION, testRunner + ".java");
        try (BufferedReader br = new BufferedReader(new FileReader(runner))) {
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("features = ")) {
                    String[] parts = line.split("\"");
                    return parts[1];
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Unable to locate scenario runner at " + runner.getAbsolutePath() + ". " + e);
            return null;
        } catch (IOException e) {
            log.error("Unable to read scenario runner at " + runner.getAbsolutePath() + ". " + e);
            return null;
        }
        return null;
    }

    /**
     * Determines which feature file the scenario under test is in, based on the provided test object provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static File getFeatureFile(ITestResult test) {
        String scenarioInfo = getScenarioInfo(test);
        if (scenarioInfo == null) {
            return null;
        }
        File featureFile = new File(scenarioInfo.split(":\\d+")[0]);
        if (featureFile.exists()) {
            return featureFile;
        }
        return null;
    }

    /**
     * Using the tags, determines which JIRA key is correlated with this test case
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static String getFeatureKey(ITestResult test) {
        String project = Property.getJiraProperty(PROJECT);
        for (String tag : getFeatureTags(test)) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (tag.startsWith("feature-" + project + "-")) {
                return tag.substring(8);
            }
        }
        return null;
    }

    /**
     * Determines the feature name of the scenario being executed, based on the provided test object provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static String getFeatureName(ITestResult test) {
        File featureFile = getFeatureFile(test);
        if (featureFile == null) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(featureFile))) {
            String currLine;
            while ((currLine = br.readLine()) != null) {
                if (currLine.trim().startsWith("Feature:")) {
                    return currLine.trim().substring(9);
                }
            }
        } catch (FileNotFoundException e) {
            log.error(LOCATE + featureFile.getAbsolutePath() + ". " + e);
            return null;
        } catch (IOException e) {
            log.error(READ + featureFile.getAbsolutePath() + ". " + e);
            return null;
        }
        return null;
    }

    /**
     * Determines the feature tags of the scenario being executed, based on the provided test object provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getFeatureTags(ITestResult test) {
        List<String> tags = new ArrayList<>();
        File featureFile = getFeatureFile(test);
        if (featureFile == null) {
            return tags;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(featureFile))) {
            String text = br.readLine();
            tags.addAll(Arrays.asList(text.split(" ")));
        } catch (FileNotFoundException e) {
            log.error(LOCATE + featureFile.getAbsolutePath() + ". " + e);
            return null;
        } catch (IOException e) {
            log.error(READ + featureFile.getAbsolutePath() + ". " + e);
            return null;
        }
        return tags;
    }

    private static int getScenarioLine(ITestResult test) {
        File featureFile = getFeatureFile(test);
        if (featureFile == null) {
            return -1;
        }
        String scenarioInfo = getScenarioInfo(test);
        if (scenarioInfo == null) {
            return -1;
        }
        return Integer.valueOf(scenarioInfo.substring(scenarioInfo.lastIndexOf(':') + 1));
    }

    private static List<String> getFeatureUntil(File featureFile, int scenarioLine) {
        if (featureFile == null || scenarioLine == -1) {
            return null;
        }
        List<String> fileList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(featureFile))) {
            int lines = 0;
            String currLine;
            while ((currLine = br.readLine()) != null) {
                fileList.add(currLine);
                lines++;
                if (lines == scenarioLine) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            log.error(LOCATE + featureFile.getAbsolutePath() + ". " + e);
            return null;
        } catch (IOException e) {
            log.error(READ + featureFile.getAbsolutePath() + ". " + e);
            return null;
        }
        return fileList;
    }

    private static List<String> getFeatureAfter(File featureFile, int scenarioLine) {
        if (featureFile == null) {
            return null;
        }
        List<String> fileList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(featureFile))) {
            int lines = 0;
            String currLine;
            while ((currLine = br.readLine()) != null) {
                lines++;
                if (lines > scenarioLine) {
                    fileList.add(currLine);
                }
            }
        } catch (FileNotFoundException e) {
            log.error(LOCATE + featureFile.getAbsolutePath() + ". " + e);
            return null;
        } catch (IOException e) {
            log.error(READ + featureFile.getAbsolutePath() + ". " + e);
            return null;
        }
        return fileList;
    }

    private static String getFullScenarioLine(ITestResult test) {
        List<String> fileList = getFeatureUntil(getFeatureFile(test), getScenarioLine(test));
        if (fileList == null) {
            return null;
        }
        for (int i = fileList.size() - 1; i >= 0; i--) {
            String currLine = fileList.get(i);
            if (currLine.trim().startsWith(SCENARIO)) {
                return currLine.trim();
            }
        }
        return null;
    }

    private static int getScenarioLineNumber(ITestResult test) {
        int scenarioLine = getScenarioLine(test);
        List<String> fileList = getFeatureUntil(getFeatureFile(test), scenarioLine);
        if (fileList == null) {
            return 0;
        }
        for (int i = fileList.size() - 1; i >= 0; i--) {
            String currLine = fileList.get(i);
            if (currLine.trim().startsWith(SCENARIO)) {
                return i + 1;
            }
        }
        return 0;
    }

    public static boolean isScenarioOutline(ITestResult test) {
        String scenarioLine = getFullScenarioLine(test);
        if (scenarioLine == null) {
            return false;
        }
        return "Scenario Outline".equals(scenarioLine.trim().split(":")[0].trim());
    }

    /**
     * Determines the scenario name of the scenario being executed, based on the provided test object provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static String getScenarioName(ITestResult test) {
        String scenarioLine = getFullScenarioLine(test);
        if (scenarioLine == null) {
            return null;
        }
        return scenarioLine.trim().split(":")[1].trim();
    }

    /**
     * Determines the scenario tags of the scenario being executed, based on the provided test object provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getScenarioTags(ITestResult test) {
        List<String> tags = new ArrayList<>();
        int foundExamples = 0;
        boolean foundSteps = false;
        List<String> fileList = getFeatureUntil(getFeatureFile(test), getScenarioLine(test));
        if (fileList == null) {
            return tags;
        }
        for (int i = fileList.size() - 1; i >= 0; i--) {
            String currLine = fileList.get(i);
            if (isStep(currLine)) {
                foundSteps = true;
            }
            if ("Examples:".equals(currLine.trim())) {
                foundExamples++;
            }
            if (currLine.trim().startsWith("@") && !(isScenarioOutline(test) && foundExamples >= 2 && !foundSteps)) {
                tags.addAll(Arrays.asList(currLine.trim().split(" ")));
                if ((foundSteps && isScenarioOutline(test)) || !isScenarioOutline(test)) {
                    return tags;
                }
            }
        }
        return tags;
    }

    public static String getScenarioDescription(ITestResult test) {
        StringBuilder sb = new StringBuilder();
        List<String> fileList = getFeatureAfter(getFeatureFile(test), getScenarioLineNumber(test));
        if (fileList == null) {
            return "";
        }
        for (String line : fileList) {
            String trimLine = line.trim();
            if (isStep(trimLine)) {
                break;
            }
            sb.append(trimLine).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Using the tags, determines which JIRA key is correlated with this test case
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static String getScenarioKey(ITestResult test) {
        String project = Property.getJiraProperty(PROJECT);
        for (String tag : getTags(test)) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (tag.startsWith(project + "-")) {
                return tag;
            }
        }
        return null;
    }

    /**
     * Using the scenario name, the test steps associated with that scenario are retrieved
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getScenarioSteps(ITestResult test) {
        List<String> steps = new ArrayList<>();
        List<String> fileList = getFeatureAfter(getFeatureFile(test), getScenarioLineNumber(test));
        if (fileList == null) {
            return steps;
        }
        boolean captureSteps = false;
        for (String line : fileList) {
            String trimLine = line.trim();
            if (isStep(trimLine)) {
                captureSteps = true;
            }
            if (captureSteps) {
                if (isStep(trimLine) || isDataTable(trimLine)) {
                    steps.add(trimLine);
                } else if (!"".equals(trimLine)) {
                    break;
                }
            }
        }
        return steps;
    }

    /**
     * What is the scenario outline example under test. i.e. the data from the table row
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static String getScenarioOutlineExampleRow(ITestResult test) {
        if (!isScenarioOutline(test)) {
            return null;
        }
        int exampleRow = getScenarioLine(test);
        return Objects.requireNonNull(getFeatureAt(getFeatureFile(test), exampleRow)).trim();
    }

    private static String getFeatureAt(File featureFile, int scenarioLine) {
        if (featureFile == null) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(featureFile))) {
            int lines = 0;
            String currLine;
            while ((currLine = br.readLine()) != null) {
                lines++;
                if (lines == scenarioLine) {
                    return currLine;
                }
            }
        } catch (FileNotFoundException e) {
            log.error(LOCATE + featureFile.getAbsolutePath() + ". " + e);
            return null;
        } catch (IOException e) {
            log.error(READ + featureFile.getAbsolutePath() + ". " + e);
            return null;
        }
        return null;
    }

    /**
     * Checks for whether the given step is a test step or not. Looks to see if the step starts with Given, When, or
     * Then. Also supports Ands and Thens. Comments are allowed, and DataTables starting with | are also recognized
     *
     * @param line
     * @return
     */
    private static boolean isDataTable(String line) {
        String trimLine = line.trim();
        return trimLine.startsWith("|");
    }

    /**
     * Checks for whether the given step is a test step or not. Looks to see if the step starts with Given, When, or
     * Then. Also supports Ands and Thens. Comments are allowed, they start with '#'
     *
     * @param line
     * @return
     */
    @SuppressWarnings("squid:S1067")
    private static boolean isStep(String line) {
        String trimLine = line.trim();
        return trimLine.startsWith("Given") || trimLine.startsWith("When") || trimLine.startsWith("Then") ||
                trimLine.startsWith("And") || trimLine.startsWith("But") || trimLine.startsWith("#");
    }

    /**
     * Using the scenario name, the example use cases are retrieved. If this is not a scenario outline, null will be
     * returned
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static String getScenarioExamples(ITestResult test) {
        StringBuilder sb = new StringBuilder();
        if (!isScenarioOutline(test)) {
            return null;
        }
        List<String> fileList = getFeatureAfter(getFeatureFile(test), getScenarioLineNumber(test));
        if (fileList == null) {
            return "";
        }
        boolean store = false;
        for (String line : fileList) {
            String trimLine = line.trim();
            if ("Examples:".equals(trimLine) || trimLine.startsWith("@")) {
                store = true;
            }
            if (trimLine.startsWith("@" + Property.getJiraProperty(PROJECT))) {
                break;
            }
            if (store) {
                sb.append(trimLine).append("\n");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Determines all tags (feature and scenario) of the scenario being executed, based on the provided test object
     * provided
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getTags(ITestResult test) {
        List<String> tags = new ArrayList<>();
        tags.addAll(getFeatureTags(test));
        tags.addAll(getScenarioTags(test));
        return tags;
    }

    /**
     * Provides a quick check to determine if a scenario has a tag, based on the provided test object
     * provided
     *
     * @param test the TestNG test being executed
     * @param tag  the tag to contain
     * @return
     */
    public static Boolean containsTag(ITestResult test, String tag) {
        return getTags(test).contains(tag);
    }

    /**
     * Determines all links (feature and scenario) of the scenario being executed, based on the provided test object
     * provided. These are identified as tags starting with '@tests-'
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getTestLinks(ITestResult test) {
        List<String> links = new ArrayList<>();
        List<String> testLinks = Property.getJiraProperties(PROJECT_LINKS);
        for (String tag : getTags(test)) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            for (String project : testLinks) {
                if (tag.startsWith("tests-" + project + "-")) {
                    links.add(tag.substring(6));
                }
            }
        }
        return links;
    }

    /**
     * Determines all links (feature and scenario) of the scenario being executed, based on the provided test object
     * provided. These are identified as tags starting with '@automates-'
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getAutomateLinks(ITestResult test) {
        List<String> links = new ArrayList<>();
        List<String> testLinks = Property.getJiraProperties(PROJECT_LINKS);
        for (String tag : getTags(test)) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            for (String project : testLinks) {
                if (tag.startsWith("automates-" + project + "-")) {
                    links.add(tag.substring(10));
                }
            }
        }
        return links;
    }

    /**
     * Determines all labels (feature and scenario) of the scenario being executed, based on the provided test object
     * provided. This excludes any tag starting with '@feature-' (identified the feature), @[project] (identifies the
     * key), or @tests- (identifies a link)
     *
     * @param test the TestNG test being executed
     * @return
     */
    public static List<String> getLabels(ITestResult test) {
        List<String> links = new ArrayList<>();
        String project = Property.getJiraProperty(PROJECT);
        List<String> testLinks = Property.getJiraProperties(PROJECT_LINKS);
        for (String tag : getTags(test)) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (!tag.startsWith(project + "-") && isLink(testLinks, tag)) {
                links.add(tag);
            }
        }
        return links;
    }

    private static boolean isLink(List<String> testLinks, String tag) {
        boolean add = true;
        for (String testLink : testLinks) {
            if (tag.startsWith("feature-" + testLink + "-") || tag.startsWith("tests-" + testLink + "-") || tag.startsWith("automates-" + testLink + "-")) {
                add = false;
                break;
            }
        }
        return add;
    }
}
