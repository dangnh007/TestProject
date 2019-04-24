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

import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.Is;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

/**
 * Is retrieves information about a particular element. A boolean is always
 * returning, indicating if an object is present or not
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class WebIs extends Is {

    Logger log = Logger.getLogger(WebIs.class);

    public WebIs(Element element) {
        super(element);
    }

    /**
     * Determines whether the element is an input or not. An input could be an
     * input element, a textarea, or a select
     *
     * @return Boolean: whether the element is an input or not
     */
    @Override
    public boolean input() {
        boolean isInput = false;
        try {
            WebElement webElement = element.getWebElement();
            if ("input".equalsIgnoreCase(webElement.getTagName())
                    || "textarea".equalsIgnoreCase(webElement.getTagName())
                    || SEL.equalsIgnoreCase(webElement.getTagName())) {
                isInput = true;
            }
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isInput;
    }
}
