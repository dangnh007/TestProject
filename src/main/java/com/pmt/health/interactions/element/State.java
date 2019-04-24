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

/**
 * State extends Asserts to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. State checks that elements are in a particular state.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class State extends Assert {

    // constants
    private static final String PRES = "present";
    private static final String PRESENT = " is present on the page";
    private static final String NOTPRESENT = " is not present on the page";
    private static final String DISPLAYED = " is displayed on the page";
    private static final String NOTDISPLAYED = " is not displayed on the page";
    private static final String CHECKED = " is checked on the page";
    private static final String NOTCHECKED = " is not checked on the page";
    private static final String IS = " is ";
    private static final String SELECTED = "selected on the page";
    private static final String NOTSELECTED = "is not selected on the page";


    public State(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element is checked. If the element isn't present, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void checked() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
        // check for the object to be present
        if (!element.is().checked()) {
            reporter.fail(element.prettyOutputStart() + NOTCHECKED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + CHECKED);
    }

    /**
     * Verifies that the element is displayed. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void displayed() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + DISPLAYED);
        // check for the object to be present
        if (!element.is().displayed()) {
            reporter.fail(element.prettyOutputStart() + NOTDISPLAYED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + DISPLAYED);
    }

    /**
     * Verifies that the element is displayed and checked. If the element isn't
     * present, it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void displayedAndChecked() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
        // check for the object to be present
        if (!element.is().displayed()) {
            reporter.fail(element.prettyOutputStart() + NOTDISPLAYED);
            return;
        }
        // check for the object to the checked
        if (!element.is().checked()) {
            reporter.fail(element.prettyOutputStart() + NOTCHECKED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + " is checked and displayed on the page");
    }

    /**
     * Verifies that the element is displayed and editable. If the element isn't
     * present, it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. If the element isn't an input, this
     * will constitute a failure, same as it not being displayed or editable. This
     * information will be logged and recorded, with a screenshot for traceability
     * and added debugging support.
     */
    public void displayedAndEditable() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " displayed and editable on the page");
        // check for the object to be present
        if (!element.is().displayed()) {
            reporter.fail(element.prettyOutputStart() + NOTDISPLAYED);
            return;
        }
        editable("displayed");
    }

    /**
     * Verifies that the element is displayed but not editable. If the element isn't
     * present, it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. If the element isn't an input, but is
     * displayed, this will constitute a pass, as non input elements are not
     * editable. This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     */
    public void displayedAndNotEditable() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " displayed and not editable on the page");
        // check for the object to be present
        if (!element.is().displayed()) {
            reporter.fail(element.prettyOutputStart() + NOTDISPLAYED);
            return;
        }
        notEditable("displayed");
    }

    /**
     * Verifies that the element is displayed but not checked. If the element isn't
     * present, it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void displayedAndUnchecked() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
        // check for the object to be present
        if (!element.is().displayed()) {
            reporter.fail(element.prettyOutputStart() + NOTDISPLAYED);
            return;
        }
        // check for the object to be present
        if (element.is().checked()) {
            reporter.fail(element.prettyOutputStart() + CHECKED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + " is not checked and displayed on the page");
    }

    /**
     * Verifies that the element is editable. If the element isn't present, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information will be
     * logged and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void editable() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " editable on the page");
        editable(PRES);
    }

    /**
     * Verifies that the element is editable. If the element isn't an input, this
     * will constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param presence - what additional attribute is expected from the element
     */
    private void editable(String presence) {
        // check for the object to the editable
        if (!element.is().input()) {
            reporter.fail(element.prettyOutputStart() + IS + presence + " but not an input on the page");
            return;
        }
        if (!element.is().enabled()) {
            reporter.fail(element.prettyOutputStart() + IS + presence + " but not editable on the page");
            return;
        }
        reporter.pass(element.prettyOutputStart() + IS + presence + " and editable on the page");
    }

    /**
     * Verifies that the element is enabled. If the element isn't present, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void enabled() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " enabled on the page");
        if (!element.is().enabled()) {
            reporter.fail(element.prettyOutputStart() + IS + PRES + " but not enabled on the page");
            return;
        }
        reporter.pass(element.prettyOutputStart() + IS + PRES + " and enabled on the page");
    }

    /**
     * Verifies that the element is not checked. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void notChecked() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // outputFile.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
        // check for the object to be present
        if (element.is().checked()) {
            reporter.fail(element.prettyOutputStart() + " checked on the page");
            return;
        }
        reporter.pass(element.prettyOutputStart() + NOTCHECKED);
    }

    /**
     * Verifies that the element is not displayed. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void notDisplayed() {
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + NOTDISPLAYED);
        // check for the object to be present
        if (element.is().displayed()) {
            reporter.fail(element.prettyOutputStart() + DISPLAYED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + NOTDISPLAYED);
    }

    /**
     * Verifies that the element is not editable. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. If the element isn't an input, this will
     * constitute a pass, as non input elements are not editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void notEditable() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // outputFile.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " not editable on the page");
        notEditable(PRES);
    }

    /**
     * Verifies that the element is not editable. If the element isn't an input,
     * this will constitute a pass, as non input elements are not editable. This
     * information will be logged and recorded, with a screenshot for traceability
     * and added debugging support.
     *
     * @param presence - what additional attribute is expected from the element
     */
    private void notEditable(String presence) {
        // check for the object to the editable
        if (element.is().input() && element.is().enabled()) {
            reporter.fail(element.prettyOutputStart() + IS + presence + " but editable on the page");
            return;
        }
        reporter.pass(element.prettyOutputStart() + IS + presence + " and not editable on the page");
    }

    /**
     * Verifies that the element is not enabled. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void notEnabled() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + " not enabled on the page");
        if (element.is().enabled()) {
            reporter.fail(element.prettyOutputStart() + IS + PRES + " but enabled on the page");
            return;
        }
        reporter.pass(element.prettyOutputStart() + IS + PRES + " and not enabled on the page");
    }

    /**
     * Verifies that the element is not present. If the element is present, it waits
     * up to the default time (5 seconds) for the element to be removed, before
     * marking this verification as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void notPresent() {
        // wait for the element
        if (element.is().present()) {
            element.waitFor().notPresent();
            if (element.is().present()) {
                return;
            }
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + NOTPRESENT);
        reporter.pass(element.prettyOutputStart() + NOTPRESENT);
    }

    /**
     * Verifies that the element is present. If the element isn't present, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void present() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + PRESENT);
        reporter.pass(element.prettyOutputStart() + PRESENT);
    }

    /**
     * Verifies that the element is selected. If the element isn't selected, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void isSelected() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + SELECTED);
        // check for the object to be present
        if (!element.is().isSelected()) {
            reporter.fail(element.prettyOutputStart() + NOTSELECTED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + SELECTED);
    }

    /**
     * Verifies that the element is not selected. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void isNotSelected() {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // reporter.record the element
        reporter.recordExpected(EXPECTED + element.prettyOutput() + NOTSELECTED);
        // check for the object to be present
        if (element.is().isSelected()) {
            reporter.fail(element.prettyOutputStart() + SELECTED);
            return;
        }
        reporter.pass(element.prettyOutputStart() + NOTSELECTED);
    }
}
