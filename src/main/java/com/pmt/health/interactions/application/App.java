package com.pmt.health.interactions.application;

import com.pmt.health.interactions.application.selenified.Assert;
import com.pmt.health.interactions.application.selenified.Get;
import com.pmt.health.interactions.application.selenified.Is;
import com.pmt.health.interactions.application.selenified.WaitFor;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.objects.Page;
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class App {

    protected static final String SECONDS = " seconds";
    protected static final int SECOND_MULTIPLIER = 1000;
    // constants
    private static final String WAITED = "Waited ";
    private static final Map<String, String> MAPPING = new LinkedHashMap<>();
    private static final StringBuilder unSanitized = new StringBuilder();
    private static final StringBuilder sanitized = new StringBuilder();

    // Assemble the map once.
    static {
        // Apostrophes
        MAPPING.put("\u02BC", "'");
        MAPPING.put("\u0301", "'");
        MAPPING.put("\u02B9", "'");
        MAPPING.put("\u05F3", "'");
        MAPPING.put("\u2032", "'");
        MAPPING.put("\u02C8", "'");
        MAPPING.put("\u030D", "'");
        MAPPING.put("\u2018", "'");
        MAPPING.put("\u2019", "'");

        // Spaces
        MAPPING.put("\u00A0", " ");
        MAPPING.put("\u2000", " ");
        MAPPING.put("\u2001", " ");
        MAPPING.put("\u2002", " ");
        MAPPING.put("\u2003", " ");
        MAPPING.put("\u2004", " ");
        MAPPING.put("\u2005", " ");
        MAPPING.put("\u2006", " ");
        MAPPING.put("\u2007", " ");
        MAPPING.put("\u2008", " ");
        MAPPING.put("\u2009", " ");
        MAPPING.put("\u200A", " ");
        MAPPING.put("\u202F", " ");
        MAPPING.put("\u3000", " ");
        MAPPING.put("\n", " ");

        // Dashes
        MAPPING.put("\u058A", "-");
        MAPPING.put("\u05BE", "-");
        MAPPING.put("\u2010", "-");
        MAPPING.put("\u2011", "-");
        MAPPING.put("\u2012", "-");
        MAPPING.put("\u2013", "-");
        MAPPING.put("\u2014", "-");
        MAPPING.put("\u2015", "-");

        // Copyright
        MAPPING.put("\u00A9", "C");

        //'&' sign
        MAPPING.put("\uD83D\uDE74", "&");
        MAPPING.put("\u0026", "&");
        MAPPING.put("\uFF06", "&");
        MAPPING.put("\uFE60", "&");
        MAPPING.put("\uD83D\uDE75", "&");

        // Commas
        MAPPING.put("\u060C", ",");
        MAPPING.put("\u201A", ",");
        MAPPING.put("\u2E41", ",");
        MAPPING.put("\u3001", ",");

        // Odd letter-like characters
        MAPPING.put("\u2146", "d");


        // Builds sanitized versions based on length of unsanitary entries.
        for (Map.Entry entry : MAPPING.entrySet()) {
            unSanitized.append(entry.getKey());
            sanitized.append(entry.getValue());
        }
    }

    protected Logger log = Logger.getLogger(App.class);
    // this will be the name of the reporter we write all commands out to
    protected Reporter reporter;
    protected Device device;
    protected URL site;
    protected String program;
    // this is the driver that will be used for all selenium actions
    protected WebDriver driver;
    // the is class to determine if something exists
    protected Is is;
    // the wait class to determine if we need to wait for something
    protected WaitFor waitFor;
    // the get class to retrieve information about the app
    protected Get get;
    protected String step = "none";
    // A universal storage location for pages that we want to check back on
    private Page storedPage;
    // the assert class to verify information about the app
    private Assert azzert;
    private int runCount = 0;
    private String scenario = "none";
    private String dirname = "/tmp";

    /**
     * Sets up the app object. Browser, and Output are defined here, which will
     * control actions and all logging and records
     *
     * @param driver - the driver we are running the tests with
     * @param device - what device is being used
     * @param file   - the TestOutput reporter. This is provided by the SeleniumTestBase
     *               functionality
     */
    public App(WebDriver driver, Device device, Reporter reporter) {
        this.driver = driver;
        this.device = device;
        this.reporter = reporter;
        if (driver != null) {
            is = new Is(driver);
            waitFor = new WaitFor(driver, reporter);
            get = new Get(driver);
            azzert = new Assert(this, reporter);
            log = Logger.getLogger(App.class);
            dirname = reporter.getFile().getParent();
        }
    }

    /**
     * Case and encoding insensitive XPATH translate function builder.
     * Sanitizes the variety of utf-8 inputs that cause us daily pain, as well as making comparisons case insensitive.
     * Example Usage:
     * With Function
     * <p>
     * MobileElement desiredRadioOption = app.newElement(LocatorType.XPATH,
     * "//android.widget.LinearLayout[starts-with(" + getSanitizedTranslate("@content-description", "I Love Adam", false) + ")]");
     * <p>
     * Explicitly
     * <p>
     * MobileElement desiredRadioOption = app.newElement(LocatorType.XPATH,
     * "//android.widget.LinearLayout[" + getSanitizedTranslate("@content-description", "I Love Adam", true) + "]");
     *
     * @param field  name of the field that is being searched for (don't forget @ and () modifiers if necessary)
     * @param value  value that is being searched for
     * @param equals determines whether the xpath is using =, or a function for searching purposes
     * @return XPATH translate function with sanitized utf-8 characters
     */
    public static String getSanitizedTranslate(String field, String value, boolean equals) {
        String equality = equals ? "=" : ",";
        return "translate(" + field + ",\"ABCDEFGHIJKLMNOPQRSTUVWXYZ" + unSanitized.toString() +
                "\", \"abcdefghijklmnopqrstuvwxyz" + sanitized.toString() + "\")" + equality + "\"" +
                value.toLowerCase() + "\"";
    }

    /**
     * Sanitizes a java string with our accepted utf-8 to ascii conversions.
     * Example Usage
     * header.assertContains().text(getSanitizedString("Kendrick Lamar"));
     *
     * @param toSanitize the UTF-8 string that we want to be sanitized
     * @return ASCII version of parameter
     */
    public static String getSanitizedString(String toSanitize) {
        StringBuilder sanitized = new StringBuilder();
        for (int i = 0; i < toSanitize.length(); i++) {
            String character = String.valueOf(toSanitize.charAt(i));
            if (MAPPING.get(character) != null) {
                sanitized.append(MAPPING.get(character));
            } else {
                sanitized.append(character);
            }
        }
        return sanitized.toString().trim();
    }

    public abstract Element newElement(LocatorType type, String locator);

    /**
     * Retrieves the name of the scenario under test
     *
     * @return A String contianing the name of the scenario
     */
    public String getScenario() {
        return scenario;
    }

    /**
     * Sets the Scenario
     *
     * @param scenario A Scenario
     */
    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    /**
     * Retrieve the name of the step being executed.
     *
     * @return the name of the step being executed
     */
    public String getStep() {
        return step;
    }

    /**
     * Sets the step being executed. This is useful for debugging sessions
     *
     * @param step A String containing the name of the step
     */
    public void setStep(String step) {
        this.step = step;
    }

    /**
     * Retrieves the output reporter responsible for logging all actions and assertions
     * associated with this particular test
     *
     * @return Reporter: the reporter recording all actions
     */
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * Retrieves the Program. TODO: Define this
     *
     * @return A String containing the name of the Program.
     */
    public String getProgram() {
        return program;
    }

    /**
     * Sets the Program to the passed parameter. TODO: Define this
     *
     * @param program A String containing the name of the program
     */
    public void setProgram(String program) {
        this.program = program;
    }

    /**
     * Retrieve the Site being tested
     *
     * @return A URL containing the site being tested
     */
    public URL getSite() {
        return site;
    }

    /**
     * Sets the URL to the site under test
     *
     * @param site A URL
     */
    public void setSite(URL site) {
        this.site = site;
    }

    /**
     * Retrieves a previously-stored page
     *
     * @return A Page that had been stored
     */
    public Page getStoredPage() {
        return storedPage;
    }

    /**
     * Stores a single page for later retrieval. TODO: Remove this. It's dangerous
     *
     * @param page A Page to be stored
     */
    public void setStoredPage(Page page) {
        this.storedPage = page;
    }

    /**
     * Retrieves the browser being used for this particular test
     *
     * @return Browser: the enum of the browser
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Retrieves the Selenium driver instance
     *
     * @return WebDriver: access to the driver controlling the browser via webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Reloads the current page
     */
    public void refresh() {
        String action = "Reloading current page";
        String expected = "Page is refreshed";
        try {
            driver.navigate().refresh();
        } catch (Exception e) {
            reporter.fail(action, expected, "Browser was unable to be refreshed. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Will handle all verifications performed on the actual application itself.
     * These asserts are custom to the framework, and in addition to providing easy
     * object oriented capabilities, they take screenshots with each verification to
     * provide additional traceability, and assist in troubleshooting and debugging
     * failing tests.
     *
     * @return the class containing different asserts
     */
    public Assert azzert() {
        return azzert;
    }

    /**
     * Retrieves information about the app in general, not specific to any
     * particular page or element. If an object isn't present, null will be returned
     */
    public Get get() {
        return get;
    }

    /**
     * Checks information about the app in general, not specific to any particular
     * page or element. A boolean is always returning, indicating if an object is
     * present or not
     */
    public Is is() {
        return is;
    }

    /**
     * Performs dynamic waits on the app in general, until a particular condition of
     * the application is met, not one for a particular page or element. Nothing is
     * ever returned. The default wait is 5 seconds, but can be overridden. If the
     * condition is not met in the allotted time, still nothing is returned, but an
     * error is logged
     */
    public WaitFor waitFor() {
        return waitFor;
    }

    /**
     * Pauses the test for a set amount of time
     *
     * @param seconds - the number of seconds to wait
     */
    public void wait(double seconds) {
        String action = "Wait " + seconds + SECONDS;
        String expected = WAITED + seconds + SECONDS;
        try {
            Thread.sleep((long) (seconds * SECOND_MULTIPLIER));
        } catch (InterruptedException e) {
            log.error(e);
            reporter.fail(action, expected, "Failed to wait " + seconds + SECONDS + ". " + e.getMessage());
            Thread.currentThread().interrupt();
            return;
        }
        reporter.pass(action, expected, WAITED + seconds + SECONDS);
    }

    /**
     * Takes a full screenshot of the entire page
     *
     * @param imageName - the name of the image typically generated via functions from
     *                  TestOutput.generateImageName
     */
    public void takeScreenshot(String imageName) {
        if (getDevice() == Device.HTMLUNIT || !Property.takeScreenshot()) {
            return;
        }
        try {
            // take a screenshot
            File srcFile;
            if (System.getProperty("hub") != null) {
                WebDriver augmented = new Augmenter().augment(getDriver());
                srcFile = ((TakesScreenshot) augmented).getScreenshotAs(OutputType.FILE);
            } else {
                srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            }
            // now we need to save the file
            FileUtils.copyFile(srcFile, new File(imageName));
        } catch (Exception e) {
            log.error("Error taking screenshot: " + e);
        }
    }

    /**
     * Creates and returns a Tag File in the output reporter directory
     *
     * @param stepName The name of the Step. We use this to help name the tagfile
     * @return A File ready for writing.
     */
    private File getTagFile(String stepName) {
        return new File(dirname + "/Tagfile" + runCount++ + step.replaceAll(" ", "_"));
    }

    /**
     * Scans the current page's source code for tags and identifiers
     *
     * @param step the step to capture tags from
     */
    public void captureTags(String step) {
        setStep(step);
        try (PrintStream tagFile = new PrintStream(getTagFile(step))) {
            String pageSource = getPageSource();
            List<Pattern> pList = getIdentifierPatterns();

            tagFile.println("=========================================");
            tagFile.println(getPageIdentifier(pageSource));
            tagFile.println(scenario);
            for (Pattern p : pList) {
                Matcher m = p.matcher(pageSource);
                while (m.find()) {
                    String tag = m.group(1);
                    String value = m.group(2);
                    if (!"".equals(value)) {
                        tagFile.println(tag + "=\"" + value + "\"");
                    }
                }
            }
            tagFile.flush();
        } catch (Exception e) {
            log.error(e);
            // Don't hold up tests on my account
        }
    }

    /**
     * Retrieves the source of the page
     *
     * @return A String contianing the souce code of this page
     */
    public String getPageSource() {
        return driver.getPageSource();
    }

    /**
     * Builds a title or other identifier from this page source
     *
     * @param pageSource A String containing the page source
     * @return A String containing a page identifier
     */
    public abstract String getPageIdentifier(String pageSource);

    /**
     * Retrieves the Regexp patterns to use to search for identifiers
     *
     * @return List<Pattern> A list contianing regexp pattern objects
     */
    public abstract List<Pattern> getIdentifierPatterns();
}
