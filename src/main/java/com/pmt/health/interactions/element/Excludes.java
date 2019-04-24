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

import com.pmt.health.utilities.Reporter;

import java.util.Arrays;

/**
 * Excludes extends Asserts to provide some additional verification
 * capabilities. It will handle all verifications performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Excludes checks that elements
 * don't have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Excludes extends Assert {

    public Excludes(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element does not contain the provided expected attribute.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     */
    public void attribute(String attribute) {
        String[] allAttributes = getAttributes(attribute, "without");
        if (allAttributes == null) {
            return;
        }
        // reporter.record the element
        if (Arrays.asList(allAttributes).contains(attribute)) {
            reporter.fail(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>" +
                ONLYVALUE + Arrays.toString(allAttributes) + "</b>");
    }

    /**
     * Verifies that the element's class does not contain the provided expected
     * class. If the element isn't present, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param unexpectedClass - the unexpected class value
     */
    public void clazz(String unexpectedClass) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " without class <b>" + unexpectedClass + "</b>");
        // check our classes
        String actualClass = element.get().attribute(CLASS);
        // reporter.record the element
        if (actualClass.contains(unexpectedClass)) {
            reporter.fail(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>" +
                    unexpectedClass + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " does not contain a class value of <b>" + unexpectedClass + "</b>");
    }

    /**
     * Verifies that the element's options do not contain the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param option the option not expected in the list
     */
    public void selectOption(String option) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " without the option <b>" + option +
                "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the editable
        String[] allOptions = element.get().selectOptions();
        if (Arrays.asList(allOptions).contains(option)) {
            reporter.fail(element.prettyOutputStart() + " is editable and present and contains the option <b>" + option + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + " is editable and present but does not contain the option <b>" + option + "</b>");
    }

    /**
     * Verifies that the element's options do not contain the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param selectValue the unexpected input value of the element
     */
    public void selectValue(String selectValue) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " without a select value of <b>" + selectValue +
                "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the present on the page
        String[] elementValues = element.get().selectValues();
        if (Arrays.asList(elementValues).contains(selectValue)) {
            reporter.fail(element.prettyOutputStart() + HASVALUE + selectValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + HASNTVALUE + selectValue + "</b>, only the values <b>" +
                Arrays.toString(elementValues) + "</b>");
    }

    /**
     * Verifies that the element's text does not contain the provided expected text.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void text(String expectedValue) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + HASNTTEXT + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue = element.get().text();
        if (elementValue.contains(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + TEXT + elementValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + TEXT + elementValue + "</b>");
    }

    /**
     * Verifies that the element's value does not contain the provided expected
     * value. If the element isn't present or an input, this will constitute a
     * failure, same as a mismatch. This information will be logged and recorded,
     * with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void value(String expectedValue) {
        String elementValue = getValue(expectedValue, HASNTVALUE);
        if (elementValue == null) {
            return;
        }
        if (elementValue.contains(expectedValue)) {
            reporter.fail(element.prettyOutputStart() + VALUE + elementValue + "</b>");
            return;
        }
        reporter.pass(element.prettyOutputStart() + VALUE + elementValue + "</b>");
    }
}
