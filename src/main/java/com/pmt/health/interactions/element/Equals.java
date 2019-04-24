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

import com.pmt.health.interactions.application.App;
import com.pmt.health.utilities.Reporter;

import java.util.Arrays;

/**
 * Equals extends Asserts to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Equals checks that elements have a particular value associated
 * to them.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/14/2017
 */
public class Equals extends Assert {

    // constants
    private static final String OPTION = " has the option of <b>";
    private static final String OPTIONS = " has options of <b>";
    private static final String VALUES = " has values of <b>";
    private static final String WITH = " with the value of <b>";

    public Equals(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element has an attribute with a value equals to the value
     * provided. If the element isn't present, or the element does not have the
     * attribute, this will constitute a failure, same as a mismatch. This
     * information will be logged and recorded, with a screenshot for traceability
     * and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue the expected value of the passed attribute of the element
     */
    public void attribute(String attribute, String expectedValue) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(
                EXPECTED + element.prettyOutput() + " having an attribute of <i>" + attribute + " with a value of <b>" +
                        expectedValue + "</b>");
        // get the actual attribute value
        String elementValue = element.get().attribute(attribute);
        if (elementValue == null) {
            reporter.fail(element.prettyOutputStart() + " does not have an attribute of <i>" + attribute + "</i>");
            return;
        }
        if (!elementValue.equals(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + "</b>");
            return;
        }
        reporter.pass(
                element.prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + "</b>");
    }

    /**
     * Verifies that the element's class equals the provided expected class. If the
     * element isn't present, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     */
    public void clazz(String expectedClass) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " with class <b>" + expectedClass + "</b>");
        // get the actual class value
        String actualClass = element.get().attribute(CLASS);
        // reporter.record the element
        if (!actualClass.equals(expectedClass)) {
            reporter.fail(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + CLASSVALUE + expectedClass + "</b>");
    }

    /**
     * Verifies that the element has a css attribute with a value equal to the value
     * provided. If the element isn't present, or the css doesn't contain the
     * desired attribute, this will constitute a failure, same as a mismatch. This
     * information will be logged and recorded, with a screenshot for traceability
     * and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     */
    public void cssValue(String attribute, String expectedValue) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " having a css attribute of <i>" + attribute +
                " with a value of <b>" + expectedValue + "</b>");
        // get the actual css element value
        String elementCssValue = element.get().css(attribute);
        if (elementCssValue == null) {
            reporter.fail("Unable to assess the css of " + element.prettyOutput());
            return;
        }
        if (!elementCssValue.equals(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH + elementCssValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH + elementCssValue + "</b>");
    }

    /**
     * Verifies that the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected input text of the element
     */
    public void selectedOption(String expectedText) {
        // wait for the select
        if (!isPresentSelect(
                EXPECTED + element.prettyOutput() + " having a selected option of <b>" + expectedText + "</b>")) {
            return;
        }
        // get the selected text
        String elementText = element.get().selectedOption();
        if (!elementText.equals(expectedText)) {
            reporter.fail(element.prettyOutputStart() + OPTION + elementText + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + OPTION + elementText + "</b>");
    }

    /**
     * Verifies that the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void selectedValue(String expectedValue) {
        // wait for the select
        if (!isPresentSelect(
                EXPECTED + element.prettyOutput() + " having a selected value of <b>" + expectedValue + "</b>")) {
            return;
        }
        // get the selected value
        String elementValue = element.get().selectedValue();
        if (!elementValue.equals(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + VALUE + elementValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + VALUE + elementValue + "</b>");
    }

    /**
     * Verifies that the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions the expected input value of the element
     */
    public void selectOptions(String... expectedOptions) {
        // wait for the select
        if (!isPresentSelect(
                EXPECTED + element.prettyOutput() + " with select options of <b>" + expectedOptions + "</b>")) {
            return;
        }
        // get the actual select options
        String[] elementOptions = element.get().selectOptions();
        if (!Arrays.toString(elementOptions).equals(Arrays.toString(expectedOptions))) {
            reporter.fail(element.prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + "</b>");
    }

    /**
     * Verifies that the element's select values equal the provided expected values.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValues the expected input value of the element
     */
    public void selectValues(String... expectedValues) {
        // wait for the select
        if (!isPresentSelect(
                EXPECTED + element.prettyOutput() + " with select values of <b>" + Arrays.toString(expectedValues) +
                        "</b>")) {
            return;
        }
        // get the actual select values
        String[] elementValues = element.get().selectValues();
        if (!Arrays.toString(elementValues).equals(Arrays.toString(expectedValues))) {
            reporter.fail(element.prettyOutputStart() + VALUES + Arrays.toString(elementValues) + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + VALUES + Arrays.toString(elementValues) + "</b>");
    }

    /**
     * Verifies that the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will constitute
     * a failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param row  - the number of the row in the table - note, row numbering starts
     *             at 1, NOT 0
     * @param col  - the number of the column in the table - note, column numbering
     *             starts at 1, NOT 0
     * @param text - what text do we expect to be in the table cell
     */
    public void text(int row, int col, String text) {
        String column = " and column ";
        String within = " within element ";
        // wait for the table
        if (!isPresentTable("Expected to find cell at row " + row + column + col + within + element.prettyOutput() +
                " to have the text value of <b>" + text + "</b>")) {
            return;
        }
        // get the table cell text
        String actualText = element.get().tableCell(row, col).getText();
        if (!actualText.equals(text)) {
            reporter.fail("Cell at row " + row + column + col + within + element.prettyOutput() +
                    " has the text value of <b>" + actualText + "</b>");
            return;
        }
        reporter.pass("Cell at row " + row + column + col + within + element.prettyOutput() + " has the text value of <b>" +
                actualText + "</b>");
    }

    /**
     * Verifies that the element's text equals the provided expected text. If the
     * element isn't present, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    public void text(String expectedText) {
        expectedText = App.getSanitizedString(expectedText);
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " having text of <b>" + expectedText + "</b>");
        // check for the object to the present on the page
        String elementText = element.get().text();
        if (!elementText.equals(expectedText)) {
            reporter.fail(element.prettyOutputStart() + VALUE + elementText + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + VALUE + elementText + "</b>");
    }

    /**
     * Verifies that the element's value equals the provided expected value. If the
     * element isn't present or an input, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void value(String expectedValue) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " having a value of <b>" + expectedValue + "</b>");
        // verify this is an input element
        if (!element.is().input()) {
            reporter.fail(element.prettyOutputStart() + " is not an input on the page");
            return;
        }
        // get the element value
        String elementValue = element.get().value();
        if (!elementValue.equals(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + VALUE + elementValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + VALUE + elementValue + "</b>");
    }
}
