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

import java.util.Map;
import java.util.Set;

/**
 * Assert will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
class Assert {

    // constants
    static final String EXPECTED = "Expected to find ";

    static final String CLASS = "class";
    static final String VALUE = " has the value of <b>";
    static final String TEXT = " has the text of <b>";
    static final String HASVALUE = " contains the value of <b>";
    static final String HASNTVALUE = " does not contain the value of <b>";
    static final String HASTEXT = " contains the text of <b>";
    static final String HASNTTEXT = " does not contain the text of <b>";
    static final String ONLYVALUE = ", only the values <b>";
    static final String CLASSVALUE = " has a class value of <b>";
    private static final String NOTINPUT = " is not an input on the page";
    private static final String NOTSELECT = " is not a select on the page";
    private static final String NOTTABLE = " is not a table on the page";

    // this will be the name of the reporter we write all commands out to
    Reporter reporter;
    // what element are we trying to interact with on the page
    Element element;

    ///////////////////////////////////////////////////////
    // assertions about the element in general
    ///////////////////////////////////////////////////////

    /**
     * Retrieves all attributes of an element, and writes out the expected result of
     * checking for one particular attribute. If the element isn't present, or the
     * attributes can't be determined an error will be logged and null will be
     * returned
     *
     * @param attribute - the attribute to check for
     * @param expected  - is the attribute expected to be present, or not present
     * @return String[]: the list of all attributes from the element;
     */
    String[] getAttributes(String attribute, String expected) {
        // wait for the element
        if (!isPresent()) {
            return null; // NOSONAR - returning an empty array could be confused
            // with no attributes
        }
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " " + expected + " attribute <b>" + attribute + "</b>");
        // check our attributes
        Map<String, String> attributes = element.get().allAttributes();
        if (attributes == null) {
            reporter.fail("Unable to assess the attributes of " + element.prettyOutput());
            return null; // NOSONAR - returning an empty array could be confused
            // with no attributes
        }
        Set<String> keys = attributes.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    /**
     * Retrieves the value from the element, and writes out the value that is being
     * expected. If the element isn't present or an input, an error will be logged
     * and null will be returned
     *
     * @param value    the expected value of the element
     * @param expected - is the attribute expected to be present, or not present
     * @return String: the actual value from the input element
     */
    String getValue(String value, String expected) {
        // wait for the element
        if (!isPresent()) {
            return null;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + expected + value + "</b>");
        // verify this is an input element
        if (!element.is().input()) {
            reporter.fail(element.prettyOutputStart() + NOTINPUT);
            return null;
        }
        // check for the object to the present on the page
        return element.get().value();
    }

    /**
     * Determines if the element is present, and if it is not, waits up to the
     * default time (5 seconds) for the element
     *
     * @return whether or not the element is present
     */
    boolean isPresent() {
        if (!element.is().present()) {
            element.waitFor().present();
            return element.is().present();
        }
        return true;
    }

    /**
     * Determines if the element is a present, and if it is, it is a select
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is a select or not
     */
    boolean isPresentSelect(String expected) {
        // wait for the element
        if (!isPresent()) {
            return false;
        }
        reporter.recordExpected(expected);
        // verify this is a select element
        return isSelect();
    }

    /**
     * Determines if the element is a present, and if it is, it is a table
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is an table or not
     */
    boolean isPresentTable(String expected) {
        // wait for the element
        if (!isPresent()) {
            return false;
        }
        reporter.recordExpected(expected);
        // verify this is a select element
        return isTable();
    }

    /**
     * Determines if the element is a select element
     *
     * @return Boolean: whether the element is an select or not
     */
    private boolean isSelect() {
        if (!element.is().select()) {
            reporter.fail(element.prettyOutputStart() + NOTSELECT);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a table element
     *
     * @return Boolean: whether the element is an table or not
     */
    private boolean isTable() {
        if (!element.is().table()) {
            reporter.fail(element.prettyOutputStart() + NOTTABLE);
            return false;
        }
        return true;
    }
}
