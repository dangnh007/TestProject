/*
 * Copyright 2017 Coveros, Inc.
 *
 * This file is part of Selenified.
 *
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.pmt.health.interactions.element.selenified;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.*;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.utilities.Reporter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Element an object representative of a web element on a particular page that
 * is under test.
 * <p>
 * Elements should be directly interacted with, with actions performed on them,
 * and assertions make about their current state
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class WebbElement extends Element {

    private static final Logger log = Logger.getLogger(WebbElement.class);

    /**
     * Sets up the element object. Driver, and Output are defined here, which will
     * control actions and all logging and records. Additionally, information about
     * the element, the locator type, and the actual selector are defined to
     * indicate which element to interact with on the current page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param file    - the TestOutput reporter. This is provided by the SeleniumTestBase
     *                functionality
     * @param type    - the locator type e.g. LocatorType.id, LocatorType.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     */
    public WebbElement(WebDriver driver, Reporter reporter, LocatorType type, String locator) {
        super(driver, reporter, type, locator);
    }

    /**
     * Sets up the element object. Driver, and Output are defined here, which will
     * control actions and all logging and records. Additionally, information about
     * the element, the locator type, the actual selector, and the element's
     * uniqueness match are defined to indicate which element to interact with on
     * the current page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param file    - the TestOutput reporter. This is provided by the SeleniumTestBase
     *                functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     */
    public WebbElement(WebDriver driver, Reporter reporter, LocatorType type, String locator, int match) {
        super(driver, reporter, type, locator, match);
    }

    /**
     * Sets up the element object. Driver, and Output are defined here, which will
     * control actions and all logging and records. Additionally, information about
     * the element, the locator type, the actual selector, and the element's
     * uniqueness match are defined to indicate which element to interact with on
     * the current page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param file    - the TestOutput reporter. This is provided by the SeleniumTestBase
     *                functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     * @param parent  - the parent element to the searched for element
     */
    public WebbElement(WebDriver driver, Reporter reporter, LocatorType type, String locator, int match,
                       WebbElement parent) {
        super(driver, reporter, type, locator, match, parent);
    }

    /**
     * Sets up the element object. Driver, and Output are defined here, which will
     * control actions and all logging and records. Additionally, information about
     * the element, the locator type, and the actual selector are defined to
     * indicate which element to interact with on the current page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param file    - the TestOutput reporter. This is provided by the SeleniumTestBase
     *                functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param parent  - the parent element to the searched for element
     */
    public WebbElement(WebDriver driver, Reporter reporter, LocatorType type, String locator, WebbElement parent) {
        super(driver, reporter, type, locator, parent);
    }

    /**
     * Generates and logs an error (with a screenshot), stating that the element was
     * unable to me moved to
     *
     * @param e        - the exception that was thrown
     * @param action   - what is the action occurring
     * @param expected - what is the expected outcome of said action
     */
    private void cantMove(Exception e, String action, String expected) {
        log.error(e);
        reporter.fail(action, expected, CANTMOVE + prettyOutput() + ". " + e.getMessage());
    }

    ///////////////////////////////////////////////////////
    // instantiating our additional action classes for further use
    ///////////////////////////////////////////////////////

    public void draw(List<Point<Integer, Integer>> points) {
        StringBuilder pointString = new StringBuilder();
        String prefix = "";
        for (Point<Integer, Integer> point : points) {
            pointString.append(prefix);
            prefix = " to ";
            pointString.append("<i>").append(point.getX()).append("x").append(point.getY()).append("</i>");
        }
        String action = "Drawing object from " + pointString + " in " + prettyOutput();
        String expected = prettyOutput() + " now has object drawn on it from " + pointString;
        // wait for element to be present
        if (!isPresent(action, expected, "Unable to drawn in ")) {
            return;
        }
        WebElement webElement = getWebElement();
        try {
            Actions builder = new Actions(driver);
            Point<Integer, Integer> firstPoint = points.get(0);
            points.remove(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
            builder.moveToElement(webElement, firstPoint.getX(), firstPoint.getY()).clickAndHold();
            for (Point<Integer, Integer> point : points) {
                builder.moveByOffset(point.getX(), point.getY());
            }
            Action drawAction = builder.release().build();
            drawAction.perform();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, "Unable to draw in " + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Drew object in " + prettyOutput());
    }

    /**
     * Searches for a child element within the element, and creates and returns this
     * new child element
     *
     * @param child - the child element to search for within the element
     * @return Element: the full reference to the child element element
     */
    public WebbElement findChild(WebbElement child) {
        return new WebbElement(child.getDriver(), reporter, child.getType(), child.getLocator(), child.getMatch(), this);
    }


    /**
     * Retrieves the identified matching web element using Webdriver. Use this
     * sparingly, only when the action you want to perform on the element isn't
     * available, as commands from it won't be checked, logged, caught, or
     * screenshotted.
     *
     * @return WebElement: the element object, and all associated values with it
     */
    @Override
    public WebElement getWebElement() {
        NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
        ngWebDriver.waitForAngularRequestsToFinish();
        if (match > 0) {
            List<WebElement> elements = getWebElements();
            if (elements.size() > match) {
                return elements.get(match);
            }
        }
        if (parent != null) {
            return parent.getWebElement().findElement(defineByElement());
        }
        if (self == null || isCommonButton(locator)) {
            WebElement found = driver.findElement(defineByElement());
            self = found;
        }
        return self;
    }

    //////////////////////////////////
    // override the SE actions
    //////////////////////////////////


    /**
     * Hovers over the element, but only if the element is present and displayed. If
     * those conditions are not met, the hover action will be logged, but skipped
     * and the test will continue.
     */
    public void hover() {
        String cantHover = "Unable to hover over ";
        String action = "Hovering over " + prettyOutput();
        String expected = prettyOutput() + " is present, and displayed to be hovered over";
        // wait for element to be present
        if (!isPresent(action, expected, cantHover)) {
            return;
        }
        // wait for element to be displayed
        if (!isDisplayed(action, expected, cantHover)) {
            return;
        }
        try {
            Actions selAction = new Actions(driver);
            WebElement webElement = getWebElement();
            selAction.moveToElement(webElement).perform();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantHover + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Hovered over " + prettyOutput());
    }

    /**
     * A private method to finish setting up each element
     */
    @Override
    protected void init() {
        is = new WebIs(this);
        waitFor = new WaitFor(this, reporter, driver);
        get = new Get(driver, this);
        state = new State(this, reporter);
        contains = new Contains(this, reporter);
        excludes = new Excludes(this, reporter);
        equals = new Equals(this, reporter);
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
    @Override
    public boolean isDisplayed(String action, String expected, String extra) {
        if (!is.present()) {
            waitFor.present();
            if (!is.present()) {
                reporter.fail(action, expected, extra + prettyOutput() + NOTPRESENT);                // indicates element not displayed
                return false;
            }
        }
        // wait for element to be displayed
        if (!is.displayed()) {
            waitFor.displayed();
            if (!is.displayed()) {
                reporter.fail(action, expected, extra + prettyOutput() + NOTDISPLAYED);                // indicates element not displayed
                return false;
            }
        }
        return true;
    }

    /**
     * Scrolls the page to the element, making it displayed on the current viewport,
     * but only if the element is present. If that condition is not met, the move
     * action will be logged, but skipped and the test will continue.
     */
    public void move() {
        String action = "Moving screen to " + prettyOutput();
        String expected = prettyOutput() + " is now displayed within the current viewport";
        // wait for element to be present
        if (!isPresent(action, expected, CANTMOVE)) {
            return;
        }
        try {
            WebElement webElement = getWebElement();
            Actions builder = new Actions(driver);
            builder.moveToElement(webElement).perform();
        } catch (Exception e) {
            cantMove(e, action, expected);
            return;
        }
        isMoved(action, expected);
    }

    /**
     * Scrolls the page to the element, leaving X pixels at the top of the viewport
     * above it, making it displayed on the current viewport, but only if the
     * element is present. If that condition is not met, the move action will be
     * logged, but skipped and the test will continue.
     *
     * @param position - how many pixels above the element to scroll to
     */
    public void move(long position) {
        String action = "Moving screen to " + position + " pixels above " + prettyOutput();
        String expected = prettyOutput() + " is now displayed within the current viewport";
        // wait for element to be present
        if (!isPresent(action, expected, CANTMOVE)) {
            return;
        }
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            WebElement webElement = getWebElement();
            long elementPosition = webElement.getLocation().getY();
            long newPosition = elementPosition - position;
            jse.executeScript("window.scrollBy(0, " + newPosition + ")");
        } catch (Exception e) {
            cantMove(e, action, expected);
            return;
        }
        isMoved(action, expected);
    }

    /**
     * Selects the frame represented by the element, but only if the element is
     * present and displayed. If these conditions are not met, the move action will
     * be logged, but skipped and the test will continue.
     */
    public void selectFrame() {
        String cantSelect = "Unable to focus on frame ";
        String action = "Focusing on frame " + prettyOutput();
        String expected = "Frame " + prettyOutput() + " is present, displayed, and focused";
        // wait for element to be present
        if (!isPresent(action, expected, cantSelect)) {
            return;
        }
        // wait for element to be displayed
        if (!isDisplayed(action, expected, cantSelect)) {
            return;
        }
        WebElement webElement = getWebElement();
        try {
            driver.switchTo().frame(webElement);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantSelect + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Focused on frame " + prettyOutput());
    }

    /**
     * Selects the option from the dropdown matching the provided value, but only if
     * the element is present, displayed, enabled, and an input. If those conditions
     * are not met, the select action will be logged, but skipped and the test will
     * continue.
     *
     * @param option - the select option to be selected
     */
    @Override
    public void selectOption(String option) {
        String action = SELECTING + option + " in " + prettyOutput();
        String expected = prettyOutput() + PRESDISEN + option + SELECTED;
        int selectionIndex = 0;
        if (!isPresentDisplayedEnabledSelect(action, expected)) {
            return;
        }
        // ensure the option exists
        if (!Arrays.asList(get.selectOptions()).contains(App.getSanitizedString(option))) {
            reporter.fail(action, expected, CANTSELECT + "<b>" + option + INN + prettyOutput() + " as that option isn't present. Available options are:<i><br/>&nbsp;&nbsp;&nbsp;" + String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectOptions()) + "</i>");
            return;
        } else {
            for (String selectOption : get.selectOptions()) {
                if (selectOption.equals(App.getSanitizedString(option))) {
                    selectionIndex = Arrays.asList(get.selectOptions()).indexOf(selectOption);
                }
            }
        }
        // do the select
        try {
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByIndex(selectionIndex);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, CANTSELECT + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Selected <b>" + option + INN + prettyOutput());
    }

    /**
     * Type the supplied text into the element, but only if the element is present,
     * enabled, and an input. If those conditions are not met, the type action will
     * be logged, but skipped and the test will continue. If the element is not
     * displayed, a warning will be written in the log, to indicate this is not a
     * normal action as could be performed by the user.
     *
     * @param text - the text to be typed in
     */
    @Override
    public void type(String text) {
        String action = "Typing text '" + text + IN + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to have text " + text + " typed in";
        if (!isPresentDisplayedEnabled(action, expected, CANTTYPE)) {
            return;
        }
        boolean warning = false;
        if (!is.displayed()) {
            warning = true;
        }
        try {
            WebElement webElement = getWebElement();
            webElement.sendKeys(text);
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, CANTTYPE + prettyOutput() + ". " + e.getMessage());
            return;
        }
        if (warning) {
            reporter.warn(action, expected, TYPTED + text + IN + prettyOutput() +
                    ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>");
        } else {
            reporter.pass(action, expected, TYPTED + text + IN + prettyOutput());
        }
    }

    /**
     * Double clicks on the element, but only if the element is present and displayed. If
     * those conditions are not met, the hover action will be logged, but skipped
     * and the test will continue.
     */
    public void doubleClick() {
        String cantDoubleClick = "Unable to double click";
        String action = "Double clicking " + prettyOutput();
        String expected = prettyOutput() + " is present, and displayed to be double clicked";
        // wait for element to be present
        if (!isPresent(action, expected, cantDoubleClick)) {
            return;
        }
        // wait for element to be displayed
        if (!isDisplayed(action, expected, cantDoubleClick)) {
            return;
        }
        try {
            Actions selAction = new Actions(driver);
            WebElement webElement = getWebElement();
            webElement.click();
            selAction.doubleClick(webElement).perform();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, cantDoubleClick + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Double clicked " + prettyOutput());
    }
}
