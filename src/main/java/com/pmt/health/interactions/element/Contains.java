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
import com.pmt.health.utilities.Device;
import com.pmt.health.utilities.Reporter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Contains extends Asserts to provide some additional verification
 * capabilities. It will handle all verifications performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Contains checks that elements
 * have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Contains extends Assert {
    // constants
    private static final String WITH = " with the value of <b>";

    public Contains(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element has an attribute with a value that contains to the value
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
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " having an attribute of <i>" + attribute + " with a value of <b>" +
                expectedValue + "</b>");
        // get the actual attribute value
        String elementValue = element.get().attribute(attribute);
        if (elementValue == null) {
            reporter.fail(element.prettyOutputStart() + " does not have an attribute of <i>" + attribute + "</i>");
            return;
        }
        if (!elementValue.contains(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + " contains attribute of <i>" + attribute + WITH + elementValue + "</b>");
            return;
        }
        reporter.pass(
                element.prettyOutputStart() + " contains attribute of <i>" + attribute + WITH + elementValue + "</b>");
    }

    /**
     * Verifies that the element contains the provided expected attribute. If the
     * element isn't present, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     */
    public void attribute(String attribute) {
        String[] allAttributes = getAttributes(attribute, "with");
        if (allAttributes == null) {
            return;
        }
        // reporter.record the element
        if (!Arrays.asList(allAttributes).contains(attribute)) {
            reporter.fail(element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>" +
                    ONLYVALUE + Arrays.toString(allAttributes) + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>");
    }

    /**
     * Verifies that the element's class contains the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedClass - the expected class value
     */
    public void clazz(String expectedClass) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " containing class <b>" + expectedClass + "</b>");
        // get the class
        String actualClass = element.get().attribute(CLASS);
        // reporter.record the element
        if (!actualClass.contains(expectedClass)) {
            reporter.fail(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>" +
                expectedClass + "</b>");
    }

    /**
     * Verifies that the element has the expected number of columns. If the element
     * isn't present or a table, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     */
    public void columns(int numOfColumns) {
        // wait for the table
        if (!isPresentTable(
                EXPECTED + element.prettyOutput() + " with the number of table columns equal to <b>" + numOfColumns +
                        "</b>")) {
            return;
        }
        int actualNumOfCols = element.get().numOfTableColumns();
        if (actualNumOfCols != numOfColumns) {
            reporter.fail(element.prettyOutputStart() + " does not have the number of columns <b>" + numOfColumns +
                    "</b>. Instead, " + actualNumOfCols + " columns were found");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " has " + actualNumOfCols + "</b> columns");
    }

    /**
     * Verifies that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     */
    public void rows(int numOfRows) {
        // wait for the table
        if (!isPresentTable(
                EXPECTED + element.prettyOutput() + " with the number of table rows equal to <b>" + numOfRows +
                        "</b>")) {
            return;
        }
        int actualNumOfRows = element.get().numOfTableRows();
        if (actualNumOfRows != numOfRows) {
            reporter.fail(element.prettyOutputStart() + " does not have the number of rows <b>" + numOfRows +
                    "</b>. Instead, " + actualNumOfRows + " rows were found");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " has " + actualNumOfRows + "</b> rows");
    }

    /**
     * Verifies that the element's options contains the provided expected option. If
     * the element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param option the option expected in the list
     */
    public void selectOption(String option) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " with the option <b>" + option +
                "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the editable
        String[] allOptions = element.get().selectOptions();
        if (!Arrays.asList(allOptions).contains(option)) {
            reporter.fail(element.prettyOutputStart() + " is present but does not contain the option <b>" + option + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " is present and contains the option <b>" + option + "</b>");
    }

    /**
     * Verifies that the element has the expected number of options. If the element
     * isn't present or a select, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     */
    public void selectOptions(int numOfOptions) {
        // wait for the select
        if (!isPresentSelect(
                EXPECTED + element.prettyOutput() + " with number of select values equal to <b>" + numOfOptions +
                        "</b>")) {
            return;
        }
        // check for the object to the present on the page
        int elementValues = element.get().numOfSelectOptions();
        if (elementValues != numOfOptions) {
            reporter.fail(element.prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options");
    }

    /**
     * Verifies that the element's options contains the provided expected value. If
     * the element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param selectValue the expected input value of the element
     */
    public void selectValue(String selectValue) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " having a select value of <b>" + selectValue +
                "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the present on the page
        String[] elementValues = element.get().selectValues();
        if (!Arrays.asList(elementValues).contains(selectValue)) {
            reporter.fail(element.prettyOutputStart() + HASNTVALUE + selectValue + "</b>" + ONLYVALUE +
                    Arrays.toString(elementValues) + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + HASVALUE + selectValue + "</b>");
    }

    /**
     * Verifies that the element's text contains the provided expected text. If the
     * element isn't present, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void text(String expectedValue) {
        expectedValue = App.getSanitizedString(expectedValue);
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + HASTEXT + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue = element.get().text();
        if (!elementValue.contains(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + TEXT + elementValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + TEXT + elementValue + "</b>");
    }

    /**
     * Verifies that the element's value contains the provided expected value. If
     * the element isn't present or an input, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void value(String expectedValue) {
        String elementValue = getValue(expectedValue, HASVALUE);
        if (elementValue == null) {
            return;
        }
        if (!elementValue.contains(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + VALUE + elementValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + VALUE + elementValue + "</b>");
    }
}