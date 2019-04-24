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

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

/**
 * Is retrieves information about a particular element. A boolean is always
 * returning, indicating if an object is present or not
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public abstract class Is {

    // constants
    protected static final String SEL = "select";
    private static final Logger log = Logger.getLogger(Is.class);
    // what element are we trying to interact with on the page
    protected final Element element;

    public Is(Element element) {
        this.element = element;
    }

    /**
     * Determines whether the element is checked or not.
     *
     * @return Boolean: whether the element is checked or not
     */
    public boolean checked() {
        boolean isChecked = false;
        try {
            isChecked = getElement().getWebElement().isSelected();
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            log.info(e);
        }
        return isChecked;
    }

    /**
     * Determines whether the element is displayed or not.
     *
     * @return Boolean: whether the element is displayed or not
     */
    public boolean displayed() {
        boolean isDisplayed = false;
        try {
            isDisplayed = getElement().getWebElement().isDisplayed();
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            log.info(e);
        }
        return isDisplayed;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * Determines whether the element is enabled or not.
     *
     * @return Boolean: whether the element is present or not
     */
    public boolean enabled() {
        boolean isEnabled = false;
        try {
            isEnabled = getElement().getWebElement().isEnabled();
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            log.info(e);
        }
        return isEnabled;
    }

    public Element getElement() {
        return element;
    }

    public abstract boolean input();

    /**
     * This is the same as present except that this will not fail
     * if the element is not present. This is useful for waitFor
     * where the element will appear after some time.
     *
     * @return boolean true if the element is present.
     */
    public boolean presentNoFail() {
        try {
            return present();
        } catch (NoSuchElementException nse) {
            log.debug("No element yet. This is somewhat expected behavior.", nse);
            return false;
        }
    }

    /**
     * Determines whether the element is present or not.
     *
     * @return Boolean: whether the element is present or not
     */
    public boolean present() {
        boolean isPresent = false;
        try {
            isPresent = isPresent();
        } catch (StaleElementReferenceException se) {
            log.debug("Stale Element found, re-locating element on page: " + se);
            getElement().self = null;
            try {
                isPresent = isPresent();
            } catch (Exception e) {
                log.info(e);
            }
        } catch (Exception e) {
            log.info(e);
        }
        return isPresent;
    }

    private boolean isPresent() {
        WebElement webElement = getElement().getWebElement();
        webElement.getText();
        return true;
    }

    /**
     * Determines whether the element is a select or not.
     *
     * @return Boolean: whether the element is an input or not
     */
    public boolean select() {
        boolean isSelect = false;
        try {
            WebElement webElement = getElement().getWebElement();
            if (SEL.equalsIgnoreCase(webElement.getTagName())) {
                isSelect = true;
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            log.info(e);
        }
        return isSelect;
    }

    /**
     * Determines whether the element has something selected or not. Checkboxes,
     * radio buttons, and selects could all have something selected. Other
     * elements will public to false.
     *
     * @return Boolean: is something selected or not
     */
    public boolean isSelected() {
        boolean isSelected = false;
        if (input()) {
            WebElement webElement = getElement().getWebElement();
            if ("input".equalsIgnoreCase(webElement.getTagName())) {
                isSelected = webElement.isSelected();
            } else if (SEL.equalsIgnoreCase(webElement.getTagName())) {
                Select dropdown = new Select(webElement);
                isSelected = !dropdown.getAllSelectedOptions().isEmpty();
            }
        }
        return isSelected;
    }

    /**
     * Determines whether the element is a table or not.
     *
     * @return Boolean: whether the element is an input or not
     */
    public boolean table() {
        boolean isTable = false;
        try {
            WebElement webElement = getElement().getWebElement();
            if ("table".equalsIgnoreCase(webElement.getTagName())) {
                isTable = true;
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            log.info(e);
        }
        return isTable;
    }
}
