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

package com.pmt.health.interactions.application.selenified;

import com.pmt.health.interactions.application.App;
import com.pmt.health.utilities.Reporter;
import org.apache.commons.lang3.ArrayUtils;

import javax.mail.Message;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Assert will handle all verifications performed on the actual application
 * itself. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Assert {

    // constants
    private static final String ONPAGE = "</b> on the page";

    private static final String NOALERT = "No alert is present on the page";

    private static final String ALERTTEXT = "An alert with text <b>";
    private static final String NOCONFIRMATION = "No confirmation is present on the page";
    private static final String CONFIRMATIONTEXT = "A confirmation with text <b>";
    private static final String NOPROMPT = "No prompt is present on the page";
    private static final String PROMPTTEXT = "A prompt with text <b>";
    private static final String STORED = "</b> is stored for the page";
    private static final String VALUE = "</b> and a value of <b>";

    private static final String COOKIE = "A cookie with the name <b>";
    private static final String NOCOOKIE = "No cookie with the name <b>";
    private static final String TEXT = "The text <b>";
    private static final String PRESENT = "</b> is present on the page";
    private static final String PRESENT_EXPECTED = "</b> present on the page";
    private static final String NOT_PRESENT = "</b> is not present on the page";
    private static final String EXPECTED = "Expected to find text <b>";

    // this will be the name of the reporter we write all commands out to
    private final Reporter reporter;
    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private final App app;

    public Assert(App app, Reporter reporter) {
        this.app = app;
        this.reporter = reporter;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Verifies that an alert is not present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void alertNotPresent() {
        // reporter.record the action
        reporter.recordExpected("Expected not to find an alert on the page");
        // check for the object to be present
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            reporter.fail("An alert is present on the page");
            return;
        }
        reporter.pass(NOALERT);
    }

    /**
     * Verifies that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void alertPresent() {
        // reporter.record the action
        reporter.recordExpected("Expected to find an alert on the page");
        // check for the object to be present
        String alert = "";
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            alert = app.get().alert();
        }
        if (!isAlertPresent) {
            reporter.fail(NOALERT);
            return;
        }
        reporter.pass(ALERTTEXT + alert + PRESENT);
    }

    /**
     * Verifies that an alert present on the page has content equal to the expected
     * text. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedAlertText the expected text of the alert
     */
    public void alertPresent(String expectedAlertText) {
        // reporter.record the action
        reporter.recordExpected("Expected to find alert with the text <b>" + expectedAlertText + ONPAGE);
        // check for the object to be present
        String alert = "";
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            alert = app.get().alert();
        }
        if (!isAlertPresent) {
            reporter.fail(NOALERT);
            return;
        }
        Pattern patt = Pattern.compile(expectedAlertText);
        Matcher m = patt.matcher(alert);
        boolean isCorrect;
        if (expectedAlertText.contains("\\")) {
            isCorrect = m.matches();
        } else {
            isCorrect = alert.equals(expectedAlertText);
        }
        if (!isCorrect) {
            reporter.fail(ALERTTEXT + alert + PRESENT);
            return;
        }
        reporter.pass(ALERTTEXT + alert + PRESENT);
    }

    /**
     * Verifies that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void confirmationNotPresent() {
        // reporter.record the action
        reporter.recordExpected("Expected to find a confirmation on the page");
        // check for the object to be present
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            reporter.fail("A confirmation is present on the page");
            return;
        }
        reporter.pass(NOCONFIRMATION);
    }

    ///////////////////////////////////////////////////////
    // assertions about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * Verifies that a confirmation is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void confirmationPresent() {
        // reporter.record the action
        reporter.recordExpected("Expected to find a confirmation on the page");
        // check for the object to be present
        String confirmation = "";
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = app.get().confirmation();
        }
        if (!isConfirmationPresent) {
            reporter.fail(NOCONFIRMATION);
            return;
        }
        reporter.pass(CONFIRMATIONTEXT + confirmation + PRESENT);
    }

    /**
     * Verifies that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText the expected text of the confirmation
     */
    public void confirmationPresent(String expectedConfirmationText) {
        // reporter.record the action
        reporter.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationText + ONPAGE);
        // check for the object to be present
        String confirmation = "";
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = app.get().confirmation();
        }
        if (!isConfirmationPresent) {
            reporter.fail(NOCONFIRMATION);
            return;
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            reporter.fail(CONFIRMATIONTEXT + confirmation + PRESENT);
            return;
        }
        reporter.pass(CONFIRMATIONTEXT + confirmation + PRESENT);
    }

    /**
     * Verifies that a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedCookieName the name of the cookie
     */
    public void cookieExists(String expectedCookieName) {
        // reporter.record the action
        reporter.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
        // check for the object to be present
        String cookieValue = "";
        boolean isCookiePresent = app.is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = app.get().cookieValue(expectedCookieName);
        }
        if (!isCookiePresent) {
            reporter.fail(NOCOOKIE + expectedCookieName + STORED);
            return;
        }
        reporter.pass(COOKIE + expectedCookieName + VALUE + cookieValue + STORED);
    }

    /**
     * Verifies that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     */
    public void cookieExists(String cookieName, String expectedCookieValue) {
        // reporter.record the action
        reporter.recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
        // check for the object to be present
        String cookieValue = "";
        boolean isCookiePresent = app.is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = app.get().cookieValue(cookieName);
        }
        if (!isCookiePresent) {
            reporter.fail(NOCOOKIE + cookieName + STORED);
            return;
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            reporter.fail(COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue);
            return;
        }
        reporter.pass(COOKIE + cookieName + VALUE + cookieValue + STORED);
    }

    /**
     * Verifies that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param unexpectedCookieName the name of the cookie
     */
    public void cookieNotExists(String unexpectedCookieName) {
        // reporter.record the action
        reporter.recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
        // check for the object to be present
        boolean isCookiePresent = app.is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            reporter.fail(COOKIE + unexpectedCookieName + STORED);
            return;
        }
        reporter.pass(NOCOOKIE + unexpectedCookieName + STORED);
    }

    /**
     * Verifies that a prompt is not present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void promptNotPresent() {
        // reporter.record the action
        reporter.recordExpected("Expected not to find prompt on the page");
        // check for the object to be present
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            reporter.fail("A prompt is present on the page");
            return;
        }
        reporter.pass(NOPROMPT);
    }

    /**
     * Verifies that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void promptPresent() {
        // reporter.record the action
        reporter.recordExpected("Expected to find prompt on the page");
        // check for the object to be present
        String prompt = "";
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            prompt = app.get().prompt();
        }
        if (!isPromptPresent) {
            reporter.fail(NOPROMPT);
            return;
        }
        reporter.pass(PROMPTTEXT + prompt + PRESENT);
    }

    /**
     * Verifies that a prompt present on the page has content equal to the expected
     * text. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedPromptText the expected text of the prompt
     */
    public void promptPresent(String expectedPromptText) {
        // reporter.record the action
        reporter.recordExpected("Expected to find prompt with the text <b>" + expectedPromptText + ONPAGE);
        // check for the object to be present
        String prompt = "";
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            prompt = app.get().prompt();
        }
        if (!isPromptPresent) {
            reporter.fail(NOPROMPT);
            return;
        }
        if (!expectedPromptText.equals(prompt)) {
            reporter.fail(PROMPTTEXT + prompt + PRESENT);
            return;
        }
        reporter.pass(PROMPTTEXT + prompt + PRESENT);
    }

    /**
     * Verifies that provided text(s) are not on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedTexts the expected text to be not present
     */
    public void textNotPresent(String... expectedTexts) {
        // reporter.record the action
        for (String expectedText : expectedTexts) {
            reporter.recordExpected(EXPECTED + expectedText + NOT_PRESENT);
            // check for the object to be present
            boolean isPresent = app.is().textPresent(expectedText);
            if (isPresent) {
                reporter.fail(TEXT + expectedText + PRESENT);
            } else {
                reporter.pass(TEXT + expectedText + NOT_PRESENT);
            }
        }
    }

    ///////////////////////////////////////////////////////
    // assertions about cookies
    ///////////////////////////////////////////////////////

    /**
     * Verifies that provided text(s) are within the current page's body. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedTexts the expected text to be present
     */
    public void textPresent(String... expectedTexts) {
        // reporter.record the action
        for (String expectedText : expectedTexts) {
            reporter.recordExpected(EXPECTED + expectedText + PRESENT_EXPECTED);
            // check for the object to be present
            boolean isPresent = app.is().textPresent(expectedText);
            if (!isPresent) {
                reporter.fail(TEXT + expectedText + NOT_PRESENT);
            } else {
                reporter.pass(TEXT + expectedText + PRESENT);
            }
        }
    }

    /**
     * Verifies the provided title equals the actual title of the current page the
     * application is on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedTitle the friendly name of the page
     */
    public void titleEquals(String expectedTitle) {
        // reporter.record the action
        reporter.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
        String actualTitle = app.get().title();
        if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
            reporter.fail("The page title reads <b>" + actualTitle + "</b>");
            return;
        }
        reporter.pass("The page title reads <b>" + actualTitle + "</b>");
    }

    /**
     * Verifies that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedURL the URL of the page
     */
    public void urlEquals(String expectedURL) {
        // reporter.record the action
        reporter.recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
        String actualURL = app.get().location();
        if (!actualURL.equalsIgnoreCase(expectedURL)) {
            reporter.fail("The page URL  reads <b>" + actualURL + "</b>");
            return;
        }
        reporter.pass("The page URL reads <b>" + actualURL + "</b>");
    }

    /**
     * Verifies that the provided text(s) are anywhere within the current page's source. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedTexts the text expected to be present in the page source
     */
    public void textPresentInSource(String... expectedTexts) {
        // reporter.record the action
        for (String expectedText : expectedTexts) {
            reporter.recordExpected(EXPECTED + expectedText + PRESENT_EXPECTED);
            // check for the object to be present
            boolean isPresent = app.is().textPresentInSource(expectedText);
            if (!isPresent) {
                reporter.fail(TEXT + expectedText + NOT_PRESENT);
            } else {
                reporter.pass(TEXT + expectedText + PRESENT);
            }
        }
    }

    /**
     * Tests whether an email was received by determining if the array contains an object
     *
     * @param messages An Array (potentially empty) of Message objects.
     */
    public void emailReceived(Message[] messages) {
        //reporter.record the action
        reporter.recordExpected("Expect at least one email in the array.");
        if (ArrayUtils.isNotEmpty(messages)) {
            reporter.pass("Email found");
        } else {
            reporter.fail("Email not found");
        }
    }
}
