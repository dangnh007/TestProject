package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.UserValue;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;

import java.util.Arrays;
import java.util.List;

public class WebAccordionVerticalRadio extends WebVerticalRadio {

    private WebbElement accordion;

    public WebAccordionVerticalRadio(WebApp app, ApiInput api, String uiLabel, UserValue... values) {
        this(app, api, uiLabel, Arrays.asList(values));
    }

    /**
     * Custom constructor for a WebAccordionVerticalRadio (a pop-open/pop-closed) structure.
     *
     * @param app        Controlling WebApp
     * @param api        An ApiInput object
     * @param uiLabel    The label of the accordion to open
     * @param selections The userValues to make in the accordion.
     */
    public WebAccordionVerticalRadio(WebApp app, ApiInput api, String uiLabel, List<UserValue> selections) {
        super(app, api, uiLabel, selections);
        accordion = app.newElement(LocatorType.XPATH, "//div[@class='accordion-header-row']//following-sibling::div["
                + App.getSanitizedTranslate("@aria-label", uiLabel, true) + "]");
    }

    /**
     * Handles the input
     */
    @Override
    public void handleInput() {
        if (isAccordion()) {
            handleRadioInput();
        }
    }

    /**
     * Handles the radio input
     */
    @Override
    protected void handleRadioInput() {
        accordion.click();
        super.handleRadioInput();
    }

    /**
     * Checks if the accordion is displayed
     * @return  boolean value
     */
    private boolean isAccordion() {
        return accordion.is().displayed();
    }
}
