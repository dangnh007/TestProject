package com.pmt.health.interactions.element;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

public class Behavior {
    private static final Logger log = Logger.getLogger(Behavior.class);
    // what element are we trying to interact with on the page
    protected Element element;

    public Behavior() {
        // Empty method on purpose.
    }

    /**
     * This method is useful for avoiding Stale Element References. Call it if you are unsure
     * if your locator has been used on a previous page.
     *
     * @param webElement The web element that shouldn't be stale
     * @return A non-stale reference to the webelement.
     */
    protected WebElement getFreshest(WebElement webElement) {
        try {
            webElement.isDisplayed(); // We don't care if the element is displayed. We only care if it's stale.
        } catch (StaleElementReferenceException sere) {
            log.debug("Stale Element found. Refreshing.", sere);
            element.self = null;
            webElement = element.getWebElement();
        }
        return webElement;
    }

}
