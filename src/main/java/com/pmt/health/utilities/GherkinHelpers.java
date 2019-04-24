package com.pmt.health.utilities;

import cucumber.api.Scenario;
import org.testng.log4testng.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GherkinHelpers {

    private static final Logger log = Logger.getLogger(GherkinHelpers.class);
    private static final String PROJECT = "project";

    private GherkinHelpers() {
    }

    public static List<String> parseOptions(String options) {
        ArrayList<String> optionsList = new ArrayList<>();
        for (String option : options.split(",")) {
            optionsList.add(option.trim());
        }
        return optionsList;
    }

    public static List<String> parsePageOptions(String options) {
        ArrayList<String> optionsList = new ArrayList<>();
        Matcher pageOptionsMatch = Pattern.compile("(?:\\d+:.+?(?=, +\\d|$))").matcher(options);
        while (pageOptionsMatch.find()) {
            optionsList.add(pageOptionsMatch.group().trim());
        }
        return optionsList;
    }

    /**
     * Determines all labels (feature and scenario) of the scenario being executed, based on the provided test object
     * provided. This excludes any tag starting with '@feature-' (identified the feature), @[project] (identifies the
     * key), or @tests- (identifies a link)
     *
     * @param scenario the cucumber scenario being executed
     * @return
     */
    public static Collection<String> getLabels(Scenario scenario) {
        String project = Property.getJiraProperty(PROJECT);
        Collection<String> tags = new HashSet<>();
        if (scenario == null) {
            return tags;
        }
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (!tag.startsWith(project + "-") && !tag.startsWith("feature-" + project + "-") &&
                    !tag.startsWith("tests-" + project + "-")) {
                tags.add(tag);
            }
        }
        return tags;
    }

    public static URL getEpicLink(Scenario scenario) {
        if (scenario == null) {
            return null;
        }
        String project = Property.getJiraProperty(PROJECT);
        String feature = "";
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (tag.startsWith("feature-" + project + "-")) {
                feature = tag.substring(8);
                break;
            }
        }
        String link = Property.getJiraProperty("link");
        try {
            return new URL(link + "/browse/" + feature);
        } catch (MalformedURLException e) {
            log.error(e);
        }
        return null;
    }

    public static String getTestKey(Scenario scenario) {
        if (scenario == null) {
            return null;
        }
        if (scenario.getSourceTagNames().isEmpty()) {
            return null;
        }
        String project = Property.getJiraProperty(PROJECT);
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (tag.startsWith(project + "-")) {
                return tag;
            }
        }
        return null;
    }

    public static Collection<String> getTestLinks(Scenario scenario) {
        Collection<String> links = new ArrayList<>();
        if (scenario == null) {
            return null;
        }
        String project = Property.getJiraProperty(PROJECT);
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.startsWith("@")) {
                tag = tag.substring(1);
            }
            if (tag.startsWith("tests-" + project + "-")) {
                links.add(tag.substring(6));
            }
        }
        return links;
    }

    public static URL getTestURL(Scenario scenario) {
        if (scenario == null) {
            return null;
        }
        String key = getTestKey(scenario);
        if (key == null) {
            key = "";
        }
        String link = Property.getJiraProperty("link");
        try {
            return new URL(link + "/browse/" + key);
        } catch (MalformedURLException e) {
            log.error(e);
        }
        return null;
    }

    /**
     * Return the feature name. This comes from the scenario, and is the title after the keyword 'Feature:' in the
     * feature file
     *
     * @param scenario the cucumber scenario being executed
     * @return String
     */
    public static String getFeatureName(Scenario scenario) {
        if (scenario == null) {
            return null;
        }
        String scenarioId = scenario.getId();
        if (scenarioId == null) {
            return null;
        }
        String[] parts = scenarioId.split(";");
        return parts[0].replaceAll("-", " ").replaceAll("/", "");
    }

    /**
     * Generate the filename which the detailed reports should be written to. The filename is the jira key, appended
     * with an .html. If this is a scenario outline, the line of the outline is appended to the jira key.
     *
     * @param scenario the cucumber scenario being executed
     * @return String
     */
    public static String getFileName(Scenario scenario) {
        String fileName = getTestKey(scenario);
        if (fileName == null) {
            return null;
        }
        if (scenario.getId() != null) {
            String[] parts = scenario.getId().split(";");
            if (parts.length >= 4) {
                fileName += "-" + (Integer.parseInt(parts[3]) - 1);
            }
        }
        return fileName + ".html";
    }
}
