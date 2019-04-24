package com.pmt.health.interactions.element;

import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.utilities.Reporter;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jeff
 */
public abstract class Element {

    // constants
    protected static final String IN = "' in ";
    protected static final String INN = "</b> in ";
    protected static final String TYPTED = "Typed text '";
    protected static final String NOTPRESENT = " as it is not present";
    protected static final String NOTDISPLAYED = " as it is not displayed";
    protected static final String NOTENABLED = " as it is not enabled";
    protected static final String NOTINPUT = " as it is not an input";
    protected static final String NOTSELECT = " as it is not a select";
    protected static final String CANTTYPE = "Unable to type in ";
    protected static final String CANTMOVE = "Unable to move to ";
    protected static final String CANTSELECT = "Unable to select ";
    protected static final String SELECTING = "Selecting ";
    protected static final String SELECTED = " selected";
    protected static final String PRESDISEN = " is present, displayed, and enabled to have the value ";
    protected static final String PRESDISENC = " is present, displayed, and enabled to be clicked";
    private static final Logger log = Logger.getLogger(Element.class);
    protected LocatorType type;

    protected Pattern nextAndStuffPattern = Pattern.compile("(next)|(submit)|(previous)|(close)|(start)|(done)|(continue)");

    /**
     * This string describes the locator
     */
    protected String locator;

    /**
     * match tells us which instance of an element to retrieve from the app
     */
    protected int match = 0;

    /**
     * is there a parent element
     */
    protected WebbElement parent = null;

    protected WebElement self = null;

    /**
     * this will be the name of the reporter we write all commands out to
     */
    protected Reporter reporter;

    /**
     * The driver that connects us to the browser or device
     */
    protected WebDriver driver;

    /**
     * the Is class is to determine if something exists
     */
    protected Is is;

    /**
     * the WaitFor class to determine if we need to wait for something
     */
    protected WaitFor waitFor;

    /**
     * the get class to retrieve information about the element
     */
    protected Get get;

    /**
     * the is class to determine the state of an element
     */
    protected State state;

    /**
     * the is class to determine if an element contains something
     */
    protected Contains contains;

    /**
     * the is class to determine if an element doesn't contain something
     */
    protected Excludes excludes;

    /**
     * the is class to determine if an element has attributes equal to something
     */
    protected Equals equals;

    /**
     * Default constructor
     *
     * @param driver
     * @param file
     * @param type
     * @param locator
     */
    public Element(WebDriver driver, Reporter reporter, LocatorType type, String locator) {
        this.driver = driver;
        this.reporter = reporter;
        this.locator = locator;
        this.type = type;
        init();
    }

    /**
     * Default constructor
     *
     * @param driver
     * @param file
     * @param type
     * @param locator
     * @param match
     */
    public Element(WebDriver driver, Reporter reporter, LocatorType type, String locator, int match) {
        this(driver, reporter, type, locator);
        this.match = match;
    }

    /**
     * Default constructor
     *
     * @param driver
     * @param file
     * @param type
     * @param locator
     * @param match
     * @param parent
     */
    public Element(WebDriver driver, Reporter reporter, LocatorType type, String locator, int match, WebbElement parent) {
        this(driver, reporter, type, locator, match);
        this.parent = parent;
    }

    /**
     * Default constructor
     *
     * @param driver
     * @param file
     * @param type
     * @param locator
     * @param parent
     */
    public Element(WebDriver driver, Reporter reporter, LocatorType type, String locator, WebbElement parent) {
        this(driver, reporter, type, locator);
        this.parent = parent;
    }

    /**
     * Verifies that the element has a particular value contained within it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     *
     * @return The Contains associated with this object
     */
    public Contains assertContains() {
        return contains;
    }

    //////////////////////////////////////
    // Basic information about the element
    //////////////////////////////////////

    /**
     * Verifies that the element has a particular value associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     *
     * @return The Equals associated with this Element
     */
    public Equals assertEquals() {
        return equals;
    }

    /**
     * Verifies that the element doesn't have a particular value contained
     * within it. These asserts are custom to the framework, and in addition to
     * providing easy object oriented capabilities, they take screenshots with
     * each verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     *
     * @return The Excludes associated with this Element
     */
    public Excludes assertExcludes() {
        return excludes;
    }

    /**
     * Verifies that the element has a particular state associated to it. These
     * asserts are custom to the framework, and in addition to providing easy
     * object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     *
     * @return The State associated with this Element
     */
    public State assertState() {
        return state;
    }

    /**
     * Blurs (focuses and then unfocuses) the element, but only if the element
     * is present, displayed, enabled, and an input. If those conditions are not
     * met, the blur action will be logged, but skipped and the test will
     * continue.
     */
    public void blur() {
        String cantFocus = "Unable to focus on ";
        String action = "Focusing, then unfocusing (blurring) on " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to be blurred";
        if (!isPresentDisplayedEnabledInput(action, expected, cantFocus)) {
            return;
        }
        try {
            WebElement webElement = getWebElement();
            webElement.sendKeys("\t");
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantFocus + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Focused, then unfocused (blurred) on " + prettyOutput());
    }

    /////////////////////////////////////////
    // Some basic implementation
    /////////////////////////////////////////

    /**
     * Clears text from the element, but only if the element is present,
     * displayed, enabled, and an input. If those conditions are not met, the
     * clear action will be logged, but skipped and the test will continue.
     */
    public void clear() {
        String cantClear = "Unable to clear ";
        String action = "Clearing text in " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to have text cleared";
        if (!isPresentDisplayedEnabledInput(action, expected, cantClear)) {
            return;
        }
        WebElement webElement = getWebElement();
        try {
            webElement.clear();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantClear + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Cleared text in " + prettyOutput());
    }

    /**
     * Clicks on the element, but only if the element is present, displayed and
     * enabled. If those conditions are not met, the click action will be
     * logged, but skipped and the test will continue.
     */
    public void click() {
        String cantClick = "Unable to click ";
        String action = "Clicking " + prettyOutput();
        String expected = prettyOutput() + PRESDISENC;
        if (!isPresentDisplayedEnabled(action, expected, cantClick)) {
            return;
        }
        try {
            getWebElement().click();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantClick + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Clicked " + prettyOutput());
    }

    /**
     * Retrieves information about a particular element. If an object isn't
     * present, null will be returned
     *
     * @return
     */
    public Get get() {
        return get;
    }

    /**
     * Retrieves the Selenium driver instance
     *
     * @return WebDriver: access to the driver controlling the browser via
     * webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    //////////////////////////////////////////////////////
    // setup element values
    //////////////////////////////////////////////////////

    /**
     * Retrieves the selector set for this element
     *
     * @return String: the selector for the element
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Retrieves the uniqueness set for this element
     *
     * @return Integer: the uniqueness match for the element
     */
    public int getMatch() {
        return match;
    }

    /**
     * Indicates the uniqueness match of the element to interact with. By
     * default this is set to 0, meaning the first element matching the locator
     * and selector assumed
     *
     * @param match - if there are multiple matches of the selector, this is
     *              which match (starting at 0) to interact with
     */
    public void setMatch(int match) {
        this.match = match;
    }

    /**
     * Retrieves the Locator set for this element
     *
     * @return Locator: the locator for the element
     */
    public LocatorType getType() {
        return type;
    }

    /**
     * Retrieves the identified matching web element using Webdriver. Use this
     * sparingly, only when the action you want to perform on the element isn't
     * available, as commands from it won't be checked, logged, caught, or
     * screenshotted.
     *
     * @return WebElement: the element object, and all associated values with it
     */
    public WebElement getWebElement() {
        if (match > 0) {
            List<WebElement> elements = getWebElements();
            if (elements.size() > match) {
                return elements.get(match);
            }
        }
        return driver.findElement(defineByElement());
    }

    ///////////////////////////////////////////////////////
    // instantiating our additional action classes for further use
    ///////////////////////////////////////////////////////

    /**
     * This method is simply to determine whether this element falls into the
     * automated Next/Submit flow. If it is, we handle the exceptions slightly differently
     * so I have to know if the button is 'common'.
     *
     * @param locator A string containing the locator
     * @return true if this is a common button.
     */
    protected boolean isCommonButton(String locator) {
        String lowerLocator = locator.toLowerCase();
        Matcher m = nextAndStuffPattern.matcher(lowerLocator);
        return m.find();
    }

    /**
     * Retrieves all matching web elements using Webdriver. Use this sparingly,
     * only when the action you want to perform on the element isn't available,
     * as commands from it won't be checked, logged, caught, or screenshotted.
     *
     * @return List: a list of WebElement objects, and all associated values
     * with them
     */
    public List<WebElement> getWebElements() {
        if (parent != null) {
            return parent.getWebElement().findElements(defineByElement());
        }
        return driver.findElements(defineByElement());
    }

    /**
     * A private method to finish setting up each element You need to initialize
     * is, waitFor, get, state, contains, excludes, and equals
     */
    protected abstract void init();

    /**
     * Retrieves information about a particular element. A boolean is always
     * returning, indicating if an object is present or not
     *
     * @return Is object associated with this element
     */
    public Is is() {
        return is;
    }

    /**
     * Determines if the element is displayed. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be displayed
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element displayed?
     */
    public boolean isDisplayed(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.displayed()) {
            waitFor.displayed();
        }
        if (!is.displayed()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOTDISPLAYED);
            // indicates element not displayed
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is displayed. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be displayed
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    public boolean isEnabled(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.enabled()) {
            waitFor.enabled();
            if (!is.enabled()) {
                reporter.fail(action, expected, extra + prettyOutput() + NOTENABLED);
                // indicates element not enabled
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the element is an input.
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    public boolean isInput(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.input()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOTINPUT);            // indicates element not an input
            return false;
        }
        return true;
    }

    /**
     * Determines if the element moved towards is now currently displayed on the
     * screen
     *
     * @param action   - what is the action occurring
     * @param expected - what is the expected outcome of said action
     */
    public void isMoved(String action, String expected) {
        if (!is.displayed()) {
            reporter.fail(action, expected, prettyOutput() + " is not displayed within the current viewport");
            return; // indicates element not on displayed screen
        }
        reporter.pass(action, expected, prettyOutput() + " is displayed within the current viewport");
    }

    /**
     * Determines if the element is present. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be present
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present?
     */
    public boolean isPresent(String action, String expected, String extra) {
        // wait for element to be present
        if (!is.present()) {
            waitFor.present();
        }
        if (!is.present()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOTPRESENT);
            // indicates element not present
            return false;
        }
        return true;
    }

    /**
     * Determines if something is present, displayed, and enabled. This returns
     * true if all three are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present, displayed, and enabled?
     */
    public boolean isPresentDisplayedEnabled(String action, String expected, String extra) {
        // wait for element to be present
        if (!isPresent(action, expected, extra)) {
            return false;
        }
        // wait for element to be displayed
        if (!isDisplayed(action, expected, extra)) {
            return false;
        }
        // wait for element to be enabled
        return isEnabled(action, expected, extra);
    }

    /**
     * Determines if something is present, displayed, enabled, and an input.
     * This returns true if all four are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present, displayed, enabled, and an
     * input?
     */
    public boolean isPresentDisplayedEnabledInput(String action, String expected, String extra) {
        // wait for element to be present
        if (!isPresent(action, expected, extra)) {
            return false;
        }
        // wait for element to be displayed
        if (!isDisplayed(action, expected, extra)) {
            return false;
        }
        // wait for element to be enabled
        return isEnabled(action, expected, extra) && isInput(action, expected, extra);
    }

    /**
     * Determines if something is present, enabled, and an input. This returns
     * true if all three are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @return Boolean: is the element present, enabled, and an input?
     */
    public boolean isPresentEnabledInput(String action, String expected) {
        // wait for element to be present
        if (!isPresent(action, expected, Element.CANTTYPE)) {
            return false;
        }
        // wait for element to be enabled
        return isEnabled(action, expected, Element.CANTTYPE) && isInput(action, expected, Element.CANTTYPE);
    }

    // ///////////////////////////////////
    // basic actions functionality
    // ///////////////////////////////////

    /**
     * Determines if something is present, displayed, enabled, and a select.
     * This returns true if all four are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @return Boolean: is the element present, displayed, enabled, and an
     * input?
     */
    public boolean isPresentDisplayedEnabledSelect(String action, String expected) {
        // wait for element to be present
        if (!isPresent(action, expected, Element.CANTSELECT)) {
            return false;
        }
        // wait for element to be displayed
        if (!isDisplayed(action, expected, Element.CANTSELECT)) {
            return false;
        }
        // wait for element to be enabled
        return isEnabled(action, expected, Element.CANTSELECT)
                && isSelect(action, expected, Element.CANTSELECT);
    }

    /**
     * Determines if the element is a select.
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    public boolean isSelect(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.select()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOTSELECT);            // indicates element not an input
            return false;
        }
        return true;
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, framed with spaces, which can be used anywhere in a
     * sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutput() {
        return " " + prettyOutputLowercase() + " ";
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, which should be used to end a sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutputEnd() {
        return prettyOutputLowercase() + ".";
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, which can be used anywhere in a sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutputLowercase() {
        String output = prettyOutputStart();
        return Character.toLowerCase(output.charAt(0)) + output.substring(1);
    }

    /**
     * Determines Selenium's 'By' object using Webdriver
     *
     * @return By: the Selenium object
     */
    public By defineByElement() {
        // consider adding strengthening
        By byElement;
        switch (type) { // determine which locator type we are interested in
            case XPATH:
                byElement = By.xpath(locator);
                break;
            case ID:
                byElement = By.id(locator);
                break;
            case NAME:
                byElement = By.name(locator);
                break;
            case CLASSNAME:
                byElement = By.className(locator);
                break;
            case CSS:
                byElement = By.cssSelector(locator);
                break;
            case LINKTEXT:
                byElement = By.linkText(locator);
                break;
            case PARTIALLINKTEXT:
                byElement = By.partialLinkText(locator);
                break;
            case TAGNAME:
                byElement = By.tagName(locator);
                break;
            case ACCESSIBILITYID:
                byElement = MobileBy.AccessibilityId(locator);
                break;
            case DATATARGET:
                byElement = By.cssSelector("[data-target=\"" + locator + "\"]");
                break;
            default:
                byElement = By.id(locator);
        }
        return byElement;
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, which should be used at the beginning of a sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutputStart() {
        return "Element with <i>" + getType().toString() + "</i> of <i>" + getLocator() + "</i>";
    }

    /**
     * Selects the Nth option from the element, starting from 0, but only if the
     * element is present, displayed, enabled, and an input. If those conditions
     * are not met, the select action will be logged, but skipped and the test
     * will continue.
     *
     * @param index - the select option to be selected - note, row numbering
     *              starts at 0
     */
    public void select(int index) {
        String action = SELECTING + index + " in " + prettyOutput();
        String expected = prettyOutput() + PRESDISEN + index + SELECTED;
        if (!isPresentDisplayedEnabledSelect(action, expected)) {
            return;
        }
        String[] options = get.selectOptions();
        if (index > options.length) {
            reporter.fail(action, expected, "Unable to select the <i>" + index + "</i> option, as there are only <i>" + options.length + "</i> available.");
            return;
        }
        // do the select
        try {
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, CANTSELECT + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Selected option <b>" + index + INN + prettyOutput());
    }

    /**
     * Selects the value from the dropdown matching the provided value, but only
     * if the element is present, displayed, enabled, and an input. If those
     * conditions are not met, the select action will be logged, but skipped and
     * the test will continue.
     *
     * @param value - the select value to be selected
     */
    public void selectValue(String value) {
        String action = SELECTING + value + " in " + prettyOutput();
        String expected = prettyOutput() + PRESDISEN + value + SELECTED;
        if (!isPresentDisplayedEnabledSelect(action, expected)) {
            return;
        }
        // ensure the value exists
        if (!Arrays.asList(get.selectValues()).contains(value)) {
            reporter.fail(action, expected, CANTSELECT + value + " in " + prettyOutput() + " as that value isn't present. Available values are:<i><br/>&nbsp;&nbsp;&nbsp;" + String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectValues()) + "</i>");
            return;
        }
        // do the select
        try {
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, CANTSELECT + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Selected <b>" + value + INN + prettyOutput());
    }

    /**
     * Submits the element, but only if the element is present, displayed and
     * enabled. If those conditions are not met, the submit action will be
     * logged, but skipped and the test will continue.
     */
    public void submit() {
        String cantSubmit = "Unable to submit ";
        String action = "Submitting " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to be submitted    ";
        if (!isPresentDisplayedEnabled(action, expected, cantSubmit)) {
            return;
        }
        WebElement webElement = getWebElement();
        try {
            webElement.submit();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantSubmit + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Submitted " + prettyOutput());
    }

    /**
     * Type the supplied key into the element, but only if the element is
     * present, enabled, and an input. If those conditions are not met, the type
     * action will be logged, but skipped and the test will continue. If the
     * element is not displayed, a warning will be written in the log, to
     * indicate this is not a normal action as could be performed by the user.
     *
     * @param key - the key to be pressed
     */
    public void type(Keys key) {
        String action = "Typing key '" + key + IN + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to have text " + key + " entered";
        if (!isPresentDisplayedEnabled(action, expected, CANTTYPE)) {
            return;
        }
        Boolean warning = false;
        if (!is.displayed()) {
            warning = true;
        }
        try {
            WebElement webElement = getWebElement();
            webElement.sendKeys(key);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, CANTTYPE + prettyOutput() + ". " + e.getMessage());
            return;
        }
        if (warning) {
            reporter.warn(action, expected, TYPTED + key + IN + prettyOutput()
                    + ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>");
        } else {
            reporter.pass(action, expected, TYPTED + key + IN + prettyOutput());
        }
    }

    /**
     * Because of strange behavior, mobile and web type differently.
     *
     * @param text
     */
    public abstract void type(String text);

    /**
     * @param option
     */
    public abstract void selectOption(String option);

    /**
     * Performs dynamic waits on a particular element, until a particular
     * condition is met. Nothing is ever returned. The default wait is 5
     * seconds, but can be overridden. If the condition is not met in the
     * allotted time, still nothing is returned, but an error is logged
     *
     * @return
     */
    public WaitFor waitFor() {
        return waitFor;
    }
}
