package unit;

import com.pmt.health.utilities.GherkinHelpers;
import com.pmt.health.utilities.Property;
import cucumber.api.Scenario;
import cucumber.runtime.ScenarioImpl;
import gherkin.formatter.model.Tag;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.testng.Assert.*;

public class GherkinHelpersUT {

    private final String jiraLink = Property.getJiraProperty("link") + "/browse/";

    @Test
    public void parseOptionsTest1() {
        String options = "option1";
        List<String> expectedOptionsList = new ArrayList<>(Collections.singletonList("option1"));
        List<String> parsedOptionsList = GherkinHelpers.parseOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void parseOptionsTest2() {
        String options = "option1, option2, option3";
        List<String> expectedOptionsList = new ArrayList<>(Arrays.asList("option1", "option2", "option3"));
        List<String> parsedOptionsList = GherkinHelpers.parseOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void parseOptionsTest3() {
        String options = "  option1,  option2,  option3 ";
        List<String> expectedOptionsList = new ArrayList<>(Arrays.asList("option1", "option2", "option3"));
        List<String> parsedOptionsList = GherkinHelpers.parseOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void parseOptionsTest4() {
        String options = "  option1,  option2,  option3, option1, option2, option3,     option1,  option2,  option3   ";
        List<String> expectedOptionsList = new ArrayList<>(
                Arrays.asList("option1", "option2", "option3", "option1", "option2", "option3", "option1", "option2",
                        "option3"));
        List<String> parsedOptionsList = GherkinHelpers.parseOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void parsePageOptionsTest1() {
        String options = "11:review again";
        List<String> expectedOptionsList = new ArrayList<>(Collections.singletonList("11:review again"));
        List<String> parsedOptionsList = GherkinHelpers.parsePageOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void parsePageOptionsTest2() {
        String options =
                "11:review again,   3:White (For example: English, European, French, German, Irish, Italian, Polish, etc.)";
        List<String> expectedOptionsList = new ArrayList<>(Arrays.asList("11:review again",
                "3:White (For example: English, European, French, German, Irish, Italian, Polish, etc.)"));
        List<String> parsedOptionsList = GherkinHelpers.parsePageOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void parsePageOptionsTest3() {
        String options =
                "11:review again,   3:White (For example: English, European, French, German, Irish, Italian, Polish, etc.)";
        List<String> expectedOptionsList = new ArrayList<>(Arrays.asList("11:review again",
                "3:White (For example: English, European, French, German, Irish, Italian, Polish, etc.)"));
        List<String> parsedOptionsList = GherkinHelpers.parsePageOptions(options);
        assertEquals(expectedOptionsList, parsedOptionsList);
    }

    @Test
    public void getlabelsNullTest() {
        assertTrue(GherkinHelpers.getLabels(null).isEmpty());
    }

    @Test
    public void getLabelsNullValsTest() {
        Set<Tag> tagsSet = new HashSet<>();
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertTrue(GherkinHelpers.getLabels(scenario).isEmpty());
    }

    @Test
    public void getTestsNullValsTest() {
        Set<Tag> tagsSet = new HashSet<>();
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertTrue(GherkinHelpers.getTestLinks(scenario).isEmpty());
    }

    @Test
    public void getTestsTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@tests-mc-12345", 1));
        tagsSet.add(new Tag("@tests-ad-12345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertEquals(GherkinHelpers.getTestLinks(scenario).size(), 1);
        assertTrue(GherkinHelpers.getTestLinks(scenario).contains("mc-12345"));
    }

    @Test
    public void getLabelsTwoLabelsTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        tagsSet.add(new Tag("@Tag2", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertEquals(GherkinHelpers.getLabels(scenario).size(), 2);
        assertTrue(GherkinHelpers.getLabels(scenario).contains("Tag1"));
        assertTrue(GherkinHelpers.getLabels(scenario).contains("Tag2"));
    }

    @Test
    public void getLabelsStripsAtTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertFalse(GherkinHelpers.getLabels(scenario).contains("@Tag1"));
    }

    @Test
    public void getLabelsRemovesFeatureTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@feature-mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertFalse(GherkinHelpers.getLabels(scenario).contains("@feature-mc-345"));
        assertFalse(GherkinHelpers.getLabels(scenario).contains("feature-mc-345"));
    }

    @Test
    public void getLabelsRemovesScenarioTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertFalse(GherkinHelpers.getLabels(scenario).contains("@mc-345"));
        assertFalse(GherkinHelpers.getLabels(scenario).contains("mc-345"));
    }

    @Test
    public void getLabelsRemovesTestsTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@tests-mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertFalse(GherkinHelpers.getLabels(scenario).contains("@mc-345"));
        assertFalse(GherkinHelpers.getLabels(scenario).contains("mc-345"));
        assertFalse(GherkinHelpers.getLabels(scenario).contains("@tests-mc-345"));
        assertFalse(GherkinHelpers.getLabels(scenario).contains("tests-mc-345"));
    }

    @Test
    public void getEpicLinkNullTest() throws MalformedURLException {
        assertNull(GherkinHelpers.getEpicLink(null));
    }

    @Test
    public void getEpicLinkNullValsTest() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink);
        assertEquals(GherkinHelpers.getEpicLink(scenario), link);
    }

    @Test
    public void getEpicLinkNoEpicTest() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink);
        assertEquals(GherkinHelpers.getEpicLink(scenario), link);
    }

    @Test
    public void getEpicLinkNoEpic2Test() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink);
        assertEquals(GherkinHelpers.getEpicLink(scenario), link);
    }

    @Test
    public void getEpicLinkHasFeatureTest() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@feature-mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink + "mc-345");
        assertEquals(GherkinHelpers.getEpicLink(scenario), link);
    }

    @Test
    public void getTestKeyNullTest() {
        assertNull(GherkinHelpers.getTestKey(null));
    }

    @Test
    public void getTestKeyNullValsTest() {
        Set<Tag> tagsSet = new HashSet<>();
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertNull(GherkinHelpers.getTestKey(scenario));
    }

    @Test
    public void getTestKeyNoTestTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertNull(GherkinHelpers.getTestKey(scenario));
    }

    @Test
    public void getTestKeyNoTest2Test() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@feature-mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertNull(GherkinHelpers.getTestKey(scenario));
    }

    @Test
    public void getTestKeyHasFeatureTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertEquals(GherkinHelpers.getTestKey(scenario), "mc-345");
    }

    @Test
    public void getTestLinkNullTest() {
        assertNull(GherkinHelpers.getTestURL(null));
    }

    @Test
    public void getTestLinkNullValsTest() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink);
        assertEquals(GherkinHelpers.getTestURL(scenario), link);
    }

    @Test
    public void getTestLinkNoTestTest() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink);
        assertEquals(GherkinHelpers.getTestURL(scenario), link);
    }

    @Test
    public void getTestLinkNoTest2Test() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@feature-mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink);
        assertEquals(GherkinHelpers.getTestURL(scenario), link);
    }

    @Test
    public void getTestLinkHasFeatureTest() throws MalformedURLException {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@mc-345", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        URL link = new URL(jiraLink + "mc-345");
        assertEquals(GherkinHelpers.getTestURL(scenario), link);
    }

    @Test
    public void getFeatureNameNullTest() {
        assertNull(GherkinHelpers.getFeatureName(null));
    }

    @Test
    public void getFeatureNameNullValsTest() {
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, null, gherkinScenario);
        assertNull(GherkinHelpers.getFeatureName(scenario));
    }

    @Test
    public void getFeatureNameNoFeatureTest() {
        String scenarioName = "World";
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, scenarioName, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, null, gherkinScenario);
        assertNull(GherkinHelpers.getFeatureName(scenario));
    }

    @Test
    public void getFeatureNameNoScenarioTest() {
        String feaureName = "Hello";
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, feaureName);
        Scenario scenario = new ScenarioImpl(null, null, gherkinScenario);
        assertEquals(GherkinHelpers.getFeatureName(scenario), feaureName);
    }

    @Test
    public void getFeatureNameTest() {
        String feaureName = "Hello";
        String scenarioName = "World";
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, scenarioName, null, 0, feaureName);
        Scenario scenario = new ScenarioImpl(null, null, gherkinScenario);
        assertEquals(GherkinHelpers.getFeatureName(scenario), feaureName);
    }

    @Test
    public void getFileNameNullTest() {
        assertNull(GherkinHelpers.getFileName(null));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getFileNameNullValsTest() {
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, null, gherkinScenario);
        assertNull(GherkinHelpers.getFileName(scenario));
    }

    @Test
    public void getFileNameNoTagsTest() {
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, new HashSet<>(), gherkinScenario);
        assertNull(GherkinHelpers.getFileName(scenario));
    }

    @Test
    public void getFileNameNoKeyTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertNull(GherkinHelpers.getFileName(scenario));
    }

    @Test
    public void getFileNameTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@Tag1", 1));
        tagsSet.add(new Tag("@mc-1234", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, null);
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertEquals(GherkinHelpers.getFileName(scenario), "mc-1234.html");
    }

    @Test
    public void getFileNameBadOutlineTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@mc-1234", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0, "some feature");
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertEquals(GherkinHelpers.getFileName(scenario), "mc-1234.html");
    }

    @Test
    public void getFileNameOutlineTest() {
        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(new Tag("@mc-1234", 1));
        gherkin.formatter.model.Scenario gherkinScenario =
                new gherkin.formatter.model.Scenario(null, null, null, null, null, 0,
                        "some feature;some scenario;some description;4");
        Scenario scenario = new ScenarioImpl(null, tagsSet, gherkinScenario);
        assertEquals(GherkinHelpers.getFileName(scenario), "mc-1234-3.html");
    }
}