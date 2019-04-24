/*
 * Copyright 2017 Coveros, Inc.
 *
 * This file is part of Selenified.
 *
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use This file except
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

package com.pmt.health.interactions.element;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

/**
 * WaitFor performs dynamic waits on a particular element, until a particular
 * condition is met. Nothing is ever returned. The default wait is 5 seconds,
 * but can be overridden. If the condition is not met in the allotted time,
 * still nothing is returned, but an error is logged
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class WaitFor extends Behavior {

    private static final Logger log = Logger.getLogger(WaitFor.class);

    // constants
    private static final String WAITED = "Waited ";
    private static final String WAITED_TOTAL = "Waited a total of ";

    private static final String UPTO = "Wait up to ";
    private static final String UPTO_TOTAL = "Wait up to a total of ";

    private static final String WAITING = "After waiting ";
    private static final String WAITING_TOTAL = "After a total of ";
    private static final String SECONDS_FOR = " seconds for ";
    private static final String PRESENT = " to be present";
    private static final String DISPLAYED = " to be displayed";
    private static final String ENABLED = " to be enabled";
    private static final String NOT_DISPLAYED = " to not be displayed";
    private static final String NOT_ENABLED = " to not be enabled";
    private static final String NOT_PRESENT = " to not be present";
    private static final String IS_DISPLAYED = " is displayed";
    private static final String IS_NOT_DISPLAYED = " is not displayed";
    private static final String IS_NOT_PRESENT = " is not present";
    private static final String IS_ENABLED = " is enabled";
    private static final String IS_NOT_ENABLED = " is not enabled";


    private static final int SECONDS_MULTIPLIER = 1000;

    // this will be the name of the reporter we write all commands out to
    private final Reporter reporter;

    private final WebDriver driver;

    private double defaultWait;
    private double defaultSecondaryWait = 25;

    public WaitFor(Element element, Reporter reporter, WebDriver driver) {
        this.element = element;
        this.reporter = reporter;
        this.driver = driver;
        this.defaultWait = Property.getDefaultWait();
    }


    /**
     * Changes the default wait time from 5.0 seconds to some custom number.
     *
     * @param seconds - how many seconds should WaitFor wait for the condition to be met
     */
    public void changeDefaultWait(double seconds) {
        defaultWait = seconds;
    }

    // ///////////////////////////////////////
    // waiting functionality
    // ///////////////////////////////////////

    /**
     * Waits for an element to be visible
     *
     * @param startTime     recorded time of the start of waiting
     * @param secondsToWait how long to wait for the element to be visible
     * @return amount of seconds waited for
     */
    private double waitForElementVisible(double startTime, double secondsToWait) {
        double end = System.currentTimeMillis() + (secondsToWait * SECONDS_MULTIPLIER);
        while (System.currentTimeMillis() < end) {
            if (!(driver instanceof IOSDriver) && !(driver instanceof AndroidDriver)) {
                NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
                ngWebDriver.waitForAngularRequestsToFinish();
            }
            WebElement webElement = getFreshest(element.getWebElement());
            // wait for up to XX seconds
            try {
                if (!webElement.isDisplayed()) {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    wait.until(ExpectedConditions.visibilityOf(webElement));
                }
                break;
            } catch (Exception e) {
                log.debug(e);
            }
        }
        return (System.currentTimeMillis() - startTime) / SECONDS_MULTIPLIER;
    }

    /**
     * Wait up to the default time (5 seconds) for the element to be displayed
     */
    public boolean displayed() {
        return displayed(defaultWait);
    }

    /**
     * Wait up to a specified time for the element to be displayed
     *
     * @param seconds - the number of seconds to wait
     */
    public boolean displayed(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + DISPLAYED;
        String expected = element.prettyOutputStart() + IS_DISPLAYED;
        double start = System.currentTimeMillis();
        if (!element.is().presentNoFail()) {
            double secondsWaited = present(seconds);
            action = UPTO_TOTAL + secondsWaited + SECONDS_FOR + element.prettyOutput() + DISPLAYED;
            if (!element.is().presentNoFail()) {
                reporter.fail(action, expected, WAITING + (System.currentTimeMillis() - start) / SECONDS_MULTIPLIER + SECONDS_FOR + element.prettyOutput() + " is not present, so it cannot be displayed");
                return false;
            }
        }
        double timetook = waitForElementVisible(start, seconds);
        try {
            if (!getFreshest(element.getWebElement()).isDisplayed()) {
                reporter.warn(action, expected,
                        WAITING + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_DISPLAYED);
                start = System.currentTimeMillis();
                timetook += waitForElementVisible(start, defaultSecondaryWait);
                action = UPTO + defaultSecondaryWait + SECONDS_FOR + element.prettyOutput() + DISPLAYED;
                if (!element.getWebElement().isDisplayed()) {
                    reporter.fail(action, expected,
                            WAITING_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_DISPLAYED);
                    return false;
                }
                reporter.pass(action, expected, WAITED_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + DISPLAYED);
                return true;
            }
        } catch (Exception e) {
            log.warn(e);
        }
        reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + DISPLAYED);
        return true;
    }

    /**
     * Waits for an element to be enabled
     *
     * @param startTime     recorded time of the start of waiting
     * @param secondsToWait how long to wait for the element to be enabled
     * @return amount of seconds waited for
     */
    private double waitForElementEnabled(double startTime, double secondsToWait) {
        double end = System.currentTimeMillis() + (secondsToWait * SECONDS_MULTIPLIER);
        while (System.currentTimeMillis() < end) {
            if (!(driver instanceof IOSDriver) && !(driver instanceof AndroidDriver)) {
                NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
                ngWebDriver.waitForAngularRequestsToFinish();
            }
            try {
                if (!getFreshest(element.getWebElement()).isEnabled()) {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    wait.until(ExpectedConditions.elementToBeClickable(element.getWebElement()));
                }
                break;
            } catch (Exception e) {
                log.debug(e);
            }
        }
        return (System.currentTimeMillis() - startTime) / SECONDS_MULTIPLIER;
    }

    /**
     * Wait up to the default time (5 seconds) for the element to be enabled
     */
    public boolean enabled() {
        return enabled(defaultWait);
    }

    /**
     * Wait up to a specified time for the element to be enabled
     *
     * @param seconds - the number of seconds to wait
     */
    private boolean enabled(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + ENABLED;
        String expected = element.prettyOutputStart() + IS_ENABLED;
        double start = System.currentTimeMillis();
        if (!element.is().presentNoFail()) {
            double secondsWaited = present(seconds);
            action = UPTO_TOTAL + secondsWaited + SECONDS_FOR + element.prettyOutput() + ENABLED;
            if (!element.is().presentNoFail()) {
                reporter.fail(action, expected, WAITING + (System.currentTimeMillis() - start) / SECONDS_MULTIPLIER + SECONDS_FOR + element.prettyOutput() + " is not present, so it cannot be enabled");
                return false;
            }
        }
        double timetook = waitForElementEnabled(start, seconds);
        if (!element.is().enabled()) {
            reporter.fail(action, expected, WAITING + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_ENABLED);
            start = System.currentTimeMillis();
            timetook += waitForElementEnabled(start, defaultSecondaryWait);
            action = UPTO + defaultSecondaryWait + SECONDS_FOR + element.prettyOutput() + ENABLED;
            if (!element.is().enabled()) {
                reporter.fail(action, expected, WAITING_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_ENABLED);
                return false;
            }
            reporter.pass(action, expected, WAITED_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + ENABLED);
            return true;
        }
        reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + ENABLED);
        return true;
    }

    /**
     * Wait up to the default time (5 seconds) for the element to not be displayed
     */
    public boolean notDisplayed() {
        return notDisplayed(defaultWait);
    }

    /**
     * Wait for a specific amount of time for the element to be not displayed
     *
     * @param seconds the number of seconds to wait
     */
    public boolean notDisplayed(double seconds) {
        // this might fail if the element disappears completely
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + NOT_DISPLAYED;
        String expected = element.prettyOutputStart() + IS_NOT_DISPLAYED;
        if (!element.is().presentNoFail()) {
            reporter.pass(action, expected, element.prettyOutput() + " is not present, and therefore not displayed");
            return true;
        }
        WebElement webElement;
        try {
            webElement = getFreshest(element.getWebElement());
        } catch (NoSuchElementException e) {
            log.debug(e);
            reporter.pass(action, expected, element.prettyOutput() + " is not present, and therefore not displayed");
            return true;
        }
        // wait for up to XX seconds
        double start = System.currentTimeMillis();
        double end = System.currentTimeMillis() + (seconds * SECONDS_MULTIPLIER);
        try {
            while (webElement.isDisplayed() && System.currentTimeMillis() < end) {
                // do nothing, just wait
            }
            double timetook = (System.currentTimeMillis() - start) / SECONDS_MULTIPLIER;
            if (webElement.isDisplayed()) {
                reporter.fail(action, expected, WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still displayed");
                end = System.currentTimeMillis() + (defaultSecondaryWait * SECONDS_MULTIPLIER);
                while (webElement.isDisplayed() && System.currentTimeMillis() < end) {
                    // do nothing, just wait
                }
                timetook += Math.round((System.currentTimeMillis() - start) / SECONDS_MULTIPLIER);
                action = UPTO + defaultSecondaryWait + SECONDS_FOR + element.prettyOutput() + NOT_DISPLAYED;
                if (webElement.isDisplayed()) {
                    reporter.fail(action, expected, WAITING_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + " is still displayed");
                    return false;
                }
                reporter.pass(action, expected, WAITED_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + NOT_DISPLAYED);
                return true;
            }
            reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + NOT_DISPLAYED);
        } catch (StaleElementReferenceException e) {
            log.debug(e);
            reporter.pass(action, expected, element.prettyOutput() + " has been removed from the page, and therefore not displayed");
            return true;
        }
        return true;
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////


    /**
     * Waits for an element to be not enabled
     *
     * @param startTime recorded time of the start of waiting
     * @param endTime   time at which to stop waiting
     * @return amount of seconds waited for
     */
    private double waitForElementNotEnabled(double startTime, double endTime) {
        while (getFreshest(element.getWebElement()).isEnabled() && System.currentTimeMillis() < endTime) {
            // do nothing, just wait
        }
        return (System.currentTimeMillis() - startTime) / SECONDS_MULTIPLIER;
    }

    /**
     * Wait up to the default time (5 seconds) for the element to not be enabled
     */
    public void notEnabled() {
        notEnabled(defaultWait);
    }

    /**
     * Wait up to a specified time for the element to no longer be enabled
     *
     * @param seconds - the number of seconds to wait
     */
    private void notEnabled(double seconds) {
        // this might fail if the element is no longer present
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + NOT_ENABLED;
        String expected = element.prettyOutputStart() + IS_NOT_ENABLED;
        if (!element.is().presentNoFail()) {
            reporter.pass(action, expected, element.prettyOutput() + " is not present, and therefore not enabled");
            return;
        }
        double start = System.currentTimeMillis();
        WebElement webElement = getFreshest(element.getWebElement());
        // wait for up to XX seconds
        double end = System.currentTimeMillis() + (seconds * SECONDS_MULTIPLIER);
        try {
            double timetook = waitForElementNotEnabled(start, end);
            if (webElement.isEnabled()) {
                reporter.fail(action, expected, WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still enabled");
                end = System.currentTimeMillis() + (defaultSecondaryWait * SECONDS_MULTIPLIER);
                timetook += waitForElementNotEnabled(start, end);
                action = UPTO + defaultSecondaryWait + SECONDS_FOR + element.prettyOutput() + NOT_ENABLED;
                if (webElement.isEnabled()) {
                    reporter.fail(action, expected, WAITING_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + " is still enabled");
                    return;
                }
                reporter.pass(action, expected, WAITED_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + NOT_ENABLED);
                return;
            }
            reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + NOT_ENABLED);
        } catch (StaleElementReferenceException e) {
            log.debug(e);
            reporter.pass(action, expected, element.prettyOutput() + " has been removed from the page, and therefore not displayed");
        }
    }

    /**
     * Waits for an element to not be present
     *
     * @param endTime       time at which to stop waiting
     * @param secondsToWait how many seconds to wait for
     * @return amount of seconds waited for
     */
    private double waitForElementNotPresent(double endTime, double secondsToWait) {
        for (int i = 0; i < 3; i++) {
            while (element.is().presentNoFail() && System.currentTimeMillis() < endTime) {
                // do nothing, just wait
            }
        }
        return (secondsToWait * SECONDS_MULTIPLIER) - (endTime - System.currentTimeMillis());
    }

    /**
     * Wait up to the default time (5 seconds) for the element to not be present
     */
    public void notPresent() {
        notPresent(defaultWait);
    }

    /**
     * Wait up to a specified time for the element to no longer be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void notPresent(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + NOT_PRESENT;
        String expected = element.prettyOutputStart() + IS_NOT_PRESENT;
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * SECONDS_MULTIPLIER);
        double timetook = waitForElementNotPresent(end, seconds);
        timetook = timetook / SECONDS_MULTIPLIER;
        if (element.is().presentNoFail()) {
            reporter.fail(action, expected, WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still present");
            end = System.currentTimeMillis() + (defaultSecondaryWait * SECONDS_MULTIPLIER);
            timetook += (waitForElementNotPresent(end, defaultSecondaryWait) / SECONDS_MULTIPLIER);
            action = UPTO + defaultSecondaryWait + SECONDS_FOR + element.prettyOutput() + NOT_PRESENT;
            if (element.is().presentNoFail()) {
                reporter.fail(action, expected, WAITING_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + " is still present");
                return;
            }
            reporter.pass(action, expected, WAITED_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + NOT_PRESENT);
            return;
        }
        reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + NOT_PRESENT);
    }

    /**
     * Waits for an element to be present
     *
     * @param endTime       time at which to stop waiting
     * @param secondsToWait how many seconds to wait for
     * @return amount of seconds waited for
     */
    private double waitForElementPresent(double endTime, double secondsToWait) {
        while (System.currentTimeMillis() < endTime) {
            if (!(driver instanceof IOSDriver) && !(driver instanceof AndroidDriver)) {
                NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
                ngWebDriver.waitForAngularRequestsToFinish();
            }
            try {
                WebDriverWait wait = new WebDriverWait(driver, 1);
                wait.until(ExpectedConditions.presenceOfElementLocated(element.defineByElement()));
                break;
            } catch (Exception e) {
                log.debug(e);
            }
        }
        return Math.min((secondsToWait * SECONDS_MULTIPLIER) - (endTime - System.currentTimeMillis()),
                secondsToWait * SECONDS_MULTIPLIER);
    }

    /**
     * Wait up to the default time (5 seconds) for the element to be present
     */
    public void present() {
        present(defaultWait);
    }

    /**
     * Wait up to a specified time for the element to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public double present(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + PRESENT;
        String expected = element.prettyOutputStart() + " is present";
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * SECONDS_MULTIPLIER);
        double timetook = waitForElementPresent(end, seconds);
        timetook = timetook / SECONDS_MULTIPLIER;
        if (!element.is().presentNoFail()) {
            reporter.fail(action, expected, WAITING + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_PRESENT);
            end = System.currentTimeMillis() + (defaultSecondaryWait * SECONDS_MULTIPLIER);
            timetook += waitForElementPresent(end, defaultSecondaryWait) / SECONDS_MULTIPLIER;
            action = UPTO + defaultSecondaryWait + SECONDS_FOR + element.prettyOutput() + PRESENT;
            if (!element.is().presentNoFail()) {
                reporter.fail(action, expected, WAITING_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_PRESENT);
                return defaultSecondaryWait;
            }
            reporter.pass(action, expected, WAITED_TOTAL + timetook + SECONDS_FOR + element.prettyOutput() + PRESENT);
            return defaultSecondaryWait;
        }
        reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + PRESENT);
        return seconds;
    }

    /**
     * Wait up to a specified time for the element to be present and then click it
     * This method exists to click an element as soon as it's seen, instead of
     * having to make another click call, after the element may have disappeared
     *
     * @param seconds - the number of seconds to wait
     */
    public void presentAndClick(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + PRESENT;
        String expected = element.prettyOutputStart() + " is present";
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * SECONDS_MULTIPLIER);
        boolean clicked = false;
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                // a drop down.
                getFreshest(element.getWebElement()).click();
                clicked = true;
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                log.debug(e);
            }
        }
        double timetook = Math.min((seconds * SECONDS_MULTIPLIER) - (end - System.currentTimeMillis()),
                seconds * SECONDS_MULTIPLIER);
        timetook = timetook / SECONDS_MULTIPLIER;
        if (!clicked) {
            reporter.fail(action, expected, WAITING + timetook + SECONDS_FOR + element.prettyOutput() + IS_NOT_PRESENT);
            return;
        }
        reporter.pass(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + PRESENT + "and to be clicked");
    }
}
