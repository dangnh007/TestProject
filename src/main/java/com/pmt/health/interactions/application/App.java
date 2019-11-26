package com.pmt.health.interactions.application;

import com.pmt.health.interactions.application.selenified.Assert;
import com.pmt.health.interactions.application.selenified.Get;
import com.pmt.health.interactions.application.selenified.Is;
import com.pmt.health.interactions.application.selenified.WaitFor;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    protected static final String SECONDS = " seconds";
    protected static final int SECOND_MULTIPLIER = 1000;
    // constants
    private static final String WAITED = "Waited ";
    private static final Map<String, String> MAPPING = new LinkedHashMap<>();
    private static final StringBuilder unSanitized = new StringBuilder();
    private static final StringBuilder sanitized = new StringBuilder();
    // constants
    private static final String AVAILABLE = "</b> is available and selected";
    private static final String NOTSELECTED = "</b> was unable to be selected";
    private static final String FRAME = "Frame <b>";
    private static final int SCREEN_WIDTH = 1600;
    private static final int SCREEN_HEIGHT = 900;

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
    // the assert class to verify information about the app
    private Assert azzert;
    private int runCount = 0;
    private String scenario = "none";
    private String dirname = "/tmp";
    // keeps track of the initial window open
    private String parentWindow;

    /**
     * Sets up the app object. Browser, and Output are defined here, which will
     * control actions and all logging and records
     *
     * @param driver - the driver we are running the tests with
     * @param device - what device is being used
     * @param reporter   - the TestOutput reporter. This is provided by the SeleniumTestBase
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

    /**
     * Accept (click 'OK' on) whatever popup is present on the page
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param popup    - the element we are interacting with
     */
    private void accept(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, "Unable to click 'OK' on the " + popup + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Clicked 'OK' on the " + popup);
    }

    /**
     * Accept (click 'OK' on) an alert box
     */
    public void acceptAlert() {
        String action = "Clicking 'OK' on an alert";
        String expected = "Alert is present to be clicked";
        // wait for element to be present
        if (!is.alertPresent()) {
            waitFor.alertPresent();
        }
        if (!is.alertPresent()) {
            reporter.fail(action, expected, "Unable to click alert as it is not present");
            return; // indicates element not present
        }
        accept(action, expected, "alert");
    }

    /**
     * Accept (click 'OK' on) a confirmation box
     */
    public void acceptConfirmation() {
        String action = "Clicking 'OK' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        if (!isConfirmation(action, expected)) {
            return;
        }
        accept(action, expected, "confirmation");
    }

    /**
     * Accept (click 'OK' on) a prompt box
     */
    public void acceptPrompt() {
        String action = "Clicking 'OK' on a prompt";
        String expected = "Prompt is present to be clicked";
        if (!isPrompt(action, expected, "click")) {
            return;
        }
        accept(action, expected, "prompt");
    }

    /**
     * Close the currently selected window. After this window is closed, ensure that
     * focus is shifted to the next window using switchToNewWindow or
     * switchToParentWindow methods. This is an alternative to closeTab, as this
     * works better for some systems and environments that others.
     */

    public void closeCurrentWindow() {
        String action = "Closing currently selected window";
        String expected = "Current window is closed";
        try {
            driver.close();
        } catch (Exception e) {
            reporter.fail(action, expected, "Current window was unable to be closed. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Closes the current tab. Note that if this is the only tab open, this will end
     * the test. No additional actions or asserts can be performed after this, as
     * the browser will be terminated as well
     */
    public void closeTab() {
        sendControlAndCommand("Closing currently open tab", "Tab is closed", "Tab was unable to be closed. ", "w");
    }

    /**
     * Delete all stored cookies for this particular test
     */

    public void deleteAllCookies() {
        String action = "Deleting all cookies";
        String expected = "All cookies are removed";
        try {
            driver.manage().deleteAllCookies();
        } catch (Exception e) {
            reporter.fail(action, expected, "Unable to remove all cookies. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Deletes a stored cookie, indicated by the cookieName for this particular
     * test. If the cookie by the provided name isn't present, than an error will be
     * logged and recorded
     *
     * @param cookieName - the name of the cookie to delete
     */
    public void deleteCookie(String cookieName) {
        String action = "Deleting cookie <i>" + cookieName + "</i>";
        String expected = "Cookie <i>" + cookieName + "</i> is removed";
        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        if (cookie == null) {
            reporter.fail(action, expected, "Unable to remove cookie <i>" + cookieName + "</i> as it doesn't exist.");
            return;
        }
        try {
            driver.manage().deleteCookieNamed(cookieName);
        } catch (Exception e) {
            reporter.fail(action, expected, "Unable to remove cookie <i>" + cookieName + "</i>. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Dismiss (click 'Cancel' on) whatever popup is present on the page
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param popup    - the element we are interacting with
     */
    private void dismiss(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, "Unable to click 'Cancel' on the " + popup + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Clicked 'Cancel' on the " + popup);
    }

    /**
     * Dismiss (click 'Cancel' on) a confirmation box
     */
    public void dismissConfirmation() {
        String action = "Clicking 'Cancel' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        if (!isConfirmation(action, expected)) {
            return;
        }
        dismiss(action, expected, "confirmation");
    }

    /**
     * Dismiss (click 'Cancel' on) a prompt box
     */
    public void dismissPrompt() {
        String action = "Clicking 'Cancel' on a prompt";
        String expected = "Prompt is present to be clicked";
        if (!isPrompt(action, expected, "click")) {
            return;
        }
        dismiss(action, expected, "prompt");
    }

    /**
     * Go back one page in the current test's browser history
     */
    public void goBack() {
        String action = "Going back one page";
        String expected = "Previous page from browser history is loaded";
        try {
            driver.navigate().back();
        } catch (Exception e) {
            reporter.fail(action, expected, "Browser was unable to go back one page. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Go forward one page in the current test's browser history
     */
    public void goForward() {
        String action = "Going forward one page";
        String expected = "Next page from browser history is loaded";
        try {
            driver.navigate().forward();
        } catch (Exception e) {
            reporter.fail(action, expected, "Browser was unable to go forward one page. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Navigates to a new url
     *
     * @param url - the URL to navigate to
     */
    public void goToURL(String url) {
        String linkedURL = "<a target='_blank' href='" + url + "'>" + url + "</a>";
        String action = "Loading " + linkedURL;
        String expected = "Loaded " + linkedURL;
        double start = System.currentTimeMillis();
        try {
            driver.get(url);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, "Fail to Load " + linkedURL + ". " + e.getMessage());
            return;
        }
        double timetook = System.currentTimeMillis() - start;
        timetook = timetook / SECOND_MULTIPLIER;
        reporter.pass(action, expected, expected + " in " + timetook + SECONDS);
    }

    /**
     * Determines if a confirmation is present or not, and can be interacted with.
     * If it's not present, an indication that the confirmation can't be clicked on
     * is written to the log reporter
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @return Boolean: is a confirmation actually present or not.
     */
    private boolean isConfirmation(String action, String expected) {
        // wait for element to be present
        if (!is.confirmationPresent()) {
            waitFor.confirmationPresent();
        }
        if (!is.confirmationPresent()) {
            reporter.fail(action, expected, "Unable to click confirmation as it is not present");
            return false; // indicates element not present
        }
        return true;
    }

    /**
     * Determines if a prompt is present or not, and can be interacted with. If it's
     * not present, an indication that the confirmation can't be clicked on is
     * written to the log reporter
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param perform  - the action occurring to the prompt
     * @return Boolean: is a prompt actually present or not.
     */
    private boolean isPrompt(String action, String expected, String perform) {
        // wait for element to be present
        if (!is.promptPresent()) {
            waitFor.promptPresent();
        }
        if (!is.promptPresent()) {
            reporter.fail(action, expected, "Unable to " + perform + " prompt as it is not present");
            return false; // indicates element not present
        }
        return true;
    }

    /**
     * Maximizes the current window
     */
    public void maximize() {
        String action = "Maximizing browser";
        String expected = "Browser is maximized";
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            reporter.warn(action, expected, "Browser was unable to be maximized. " + e.getMessage());
            log.warn(e);
            Dimension screensize = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
            action = "Setting browser size to " + screensize.getWidth() + "x" + screensize.getHeight();
            expected = "Browser size set to " + screensize.getWidth() + "x" + screensize.getHeight();
            try {
                driver.manage().window().setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            } catch (Exception e1) {
                reporter.warn(action, expected, "Browser was unable to be resized. " + e1.getMessage());
                log.warn(e1);
                return;
            }
            reporter.pass(action, expected, expected);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Resizes the current window to the specified size
     *
     * @param width  - the  desired width of the browser
     * @param height - the desired height of the browser
     */
    public void resize(int width, int height) {
        String action = "Resizing browser to " + width + " x " + height;
        String expected = "Browser is resized to " + width + " x " + height;
        try {
            Dimension dimension = new Dimension(width, height);
            driver.manage().window().setSize(dimension);
        } catch (Exception e) {
            reporter.warn(action, expected, "Browser was unable to be resized. " + e.getMessage());
            log.warn(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * setups a new element which is located on the page
     *
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Element: a page element to interact with
     */
    public WebbElement newElement(LocatorType type, String locator) {
        return new WebbElement(driver, reporter, type, locator);
    }

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
     * sets up a new element which is located on the page
     *
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     * @return Element: a page element to interact with
     */
    public WebbElement newElement(LocatorType type, String locator, int match) {
        return new WebbElement(driver, reporter, type, locator, match);
    }

    /**
     * Opens a new tab, and have it selected. Note, no content will be present on
     * this new tab, use the goToURL method to open load some content
     *
     * @return Boolean: returns a true if a tab was successfully opened, a false if
     * it was not.
     */
    private boolean openTab() {
        return sendControlAndCommand("Opening new tab", "New tab is opened", "New tab was unable to be opened. ", "t");
    }

    /**
     * Opens a new tab, and have it selected. The page provided will be loaded
     *
     * @param url - the url to load once the new tab is opened and selected
     */
    public void openTab(String url) {
        if (!openTab()) {
            return;
        }
        goToURL(url);
    }

    /**
     * Reloads the current page while clearing the cache. This only works for
     * certain browsers, as the command/control and F5 key combination is used to
     * perform the action. Note that each time a test is run, nothing is currently
     * in the cache, as each test is run in a completely new/fresh/clean session
     */
    public void refreshHard() {
        String action = "Reloading current page while clearing the cache";
        String expected = "Cache is cleared, and the page is refreshed";
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.F5));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.F5));
        } catch (Exception e) {
            reporter.fail(action, expected, "There was a problem clearing the cache and reloading the page. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * An custom script to scroll to a given position on the page, using javascript.
     * If the browser being used doesn't support javascript, or the page isn't long
     * enough to support scrolling to the desired position, than an error will be
     * logged and recorded
     *
     * @param desiredPosition - the position on the page to scroll to
     */
    public void scroll(int desiredPosition) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Long initialPosition = (Long) jse.executeScript("return window.scrollY;");

        String action = "Scrolling page from " + initialPosition + " to " + desiredPosition;
        String expected = "Page is now set at position " + desiredPosition;

        jse.executeScript("window.scrollBy(0, " + desiredPosition + ")");

        Long newPosition = (Long) jse.executeScript("return window.scrollY;");

        if (newPosition != desiredPosition) {
            reporter.fail(action, expected, "Page is set at position " + newPosition);
            return; // indicates page didn't scroll properly
        }
        reporter.pass(action, expected, "Page is now set at position " + newPosition);
    }

    /**
     * Select a frame by its (zero-based) index. That is, if a page has three
     * frames, the first frame would be at index 0, the second at index 1 and the
     * third at index 2. Once the frame has been selected, all subsequent calls on
     * the WebDriver interface are made to that frame.
     *
     * @param frameNumber - the frame number, starts at 0
     */
    public void selectFrame(int frameNumber) {
        String action = "Switching to frame <b>" + frameNumber + "</b>";
        String expected = FRAME + frameNumber + AVAILABLE;
        try {
            driver.switchTo().frame(frameNumber);
        } catch (Exception e) {
            reporter.fail(action, expected, FRAME + frameNumber + NOTSELECTED + ". " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Select a frame by its name or ID. Frames located by matching name attributes
     * are always given precedence over those matched by ID.
     *
     * @param frameIdentifier - the frame name or ID
     */
    public void selectFrame(String frameIdentifier) {
        String action = "Switching to frame <b>" + frameIdentifier + "</b>";
        String expected = FRAME + frameIdentifier + AVAILABLE;
        try {
            driver.switchTo().frame(frameIdentifier);
        } catch (Exception e) {
            reporter.fail(action, expected, FRAME + frameIdentifier + NOTSELECTED + ". " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Selects the parent frame of the currently selected frame.
     */
    public void selectParentFrame() {
        String action = "Switching to parent frame of currently selected frame";
        String expected = "Parent " + FRAME.toLowerCase() + AVAILABLE;
        try {
            driver.switchTo().parentFrame();
        } catch (Exception e) {
            reporter.fail(action, expected, "Parent " + FRAME.toLowerCase() + NOTSELECTED + ". " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Selects the main (most parent) frame of the currently selected frame.
     */
    public void selectMainFrame() {
        String action = "Switching to main frame of currently selected frame";
        String expected = "Main " + FRAME.toLowerCase() + AVAILABLE;
        try {
            driver.switchTo().parentFrame();
        } catch (Exception e) {
            reporter.fail(action, expected, "Main " + FRAME.toLowerCase() + NOTSELECTED + ". " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Sends a key combination both as control and command (PC and Mac compatibile)
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param fail     - the failed result
     * @param key      - what key to send along with control and/or command
     * @return Boolean: returns a true if the keys were successfully sent, a false
     * if they were not
     */
    private boolean sendControlAndCommand(String action, String expected, String fail, String key) {
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + key);
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + key);
        } catch (Exception e) {
            reporter.fail(action, expected, fail + e.getMessage());
            log.error(e);
            return false;
        }
        reporter.pass(action, expected, expected);
        return true;
    }

    /**
     * Adds a cookie to the application for this particular test
     *
     * @param cookie - the details of the cookie to set
     */
    public void setCookie(Cookie cookie) {
        String domain = cookie.getDomain();
        Date expiry = cookie.getExpiry();
        String name = cookie.getName();
        String path = cookie.getPath();
        String value = cookie.getValue();

        String action = "Setting up cookie with attributes:<div><table><tbody><tr><td>Domain</td><td>" + domain +
                "</tr><tr><td>Expiration</td><td>" + expiry.toString() + "</tr><tr><td>Name</td><td>" + name +
                "</tr><tr><td>Path</td><td>" + path + "</tr><tr><td>Value</td><td>" + value +
                "</tr></tbody></table></div><br/>";
        String expected = "Cookie is added";
        try {
            driver.manage().addCookie(cookie);
        } catch (Exception e) {
            reporter.fail(action, expected, "Unable to add cookie. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Switches to the next available tab. If the last tab is already selected, it
     * will loop back to the first tab. This is an alternative to switchToNewWindow,
     * as this works better for some systems and environments that others.
     */
    public void switchNextTab() {
        String action = "Switching to next tab ";
        String expected = "Next tab <b>" + AVAILABLE;
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.PAGE_DOWN));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.PAGE_DOWN));
        } catch (Exception e) {
            reporter.fail(action, expected, "Next tab <b>" + NOTSELECTED + ". " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Switch to the previous available tab. If the first tab is already selected,
     * it will loop back to the last tab. This is an alternative to
     * switchToNewWindow, as this works better for some systems and environments
     * that others.
     */
    public void switchPreviousTab() {
        String action = "Switching to previous tab ";
        String expected = "Previous tab <b>" + AVAILABLE;
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.PAGE_UP));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.PAGE_UP));
        } catch (Exception e) {
            reporter.fail(action, expected, "Previous tab <b>" + NOTSELECTED + ". " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Switches to the next window. This is an alternative to switchNextTab or
     * switchPreviousTab, as this works better for some systems and environments
     * that others.
     */

    public void switchToNewWindow() {
        String action = "Switching to the new window";
        String expected = "New window is available and selected";
        try {
            wait(1f);
            parentWindow = driver.getWindowHandle();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        } catch (Exception e) {
            reporter.fail(action, expected, "New window was unable to be selected. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Switches to the originally opened window. This is an alternative to
     * switchNextTab or switchPreviousTab, as this works better for some systems and
     * environments that others.
     */

    public void switchToParentWindow() {
        String action = "Switching back to parent window";
        String expected = "Parent window is available and selected";
        try {
            driver.switchTo().window(parentWindow);
        } catch (Exception e) {
            reporter.fail(action, expected, "Parent window was unable to be selected. " + e.getMessage());
            log.error(e);
            return;
        }
        reporter.pass(action, expected, expected);
    }

    /**
     * Type text into a prompt box
     *
     * @param text - the text to type into the prompt
     */
    public void typeIntoPrompt(String text) {
        String action = "Typing text '" + text + "' into prompt";
        String expected = "Prompt is present and enabled to have text " + text + " typed in";
        if (!isPrompt(action, expected, "type into")) {
            return;
        }
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, "Unable to type into prompt. " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Typed text '" + text + "' into prompt");
    }

    /**
     * Builds a title or other identifier from this page source.
     * TODO: Maybe make more reliable. May not be needed at all.
     *
     * @param pageSource A String containing the page source
     * @return A String containing a page identifier
     */
    public String getPageIdentifier(String pageSource) {
        String platform = "Web";
        StringBuilder identifier = new StringBuilder(platform + " page-");
        Pattern p = Pattern.compile("TextView.*text=\\\"([A-Z].*?)\\\"");
        Matcher m = p.matcher(pageSource);
        while (m.find()) {
            String value = m.group(1);
            identifier.append(value).append(" ");
        }
        return identifier.toString();
    }

    /**
     * Retrieves the Regexp patterns to use to search for identifiers
     *
     * @return List<Pattern> A list contianing regexp pattern objects
     */
    public List<Pattern> getIdentifierPatterns() {
        List<Pattern> pList = new ArrayList<>();
        pList.add(Pattern.compile("(aria-label)=\\\"(.*?)\\\""));
        pList.add(Pattern.compile("(alt)=\\\"(.*?)\\\""));
        pList.add(Pattern.compile("(class)=\\\"(.*?)\\\""));
        pList.add(Pattern.compile("(data-target)=\\\"(.*?)\\\""));
        return pList;
    }

}
