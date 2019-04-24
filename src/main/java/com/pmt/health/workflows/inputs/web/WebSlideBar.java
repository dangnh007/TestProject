package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;

import java.util.Collections;
import java.util.List;

public class WebSlideBar extends Input {


    private final int selection;

    private final WebbElement tick;

    public WebSlideBar(WebApp app, ApiInput api, String uiLabel, int selection) {
        super(app, api, uiLabel, selection);

        this.selection = selection;
        tick = app.newElement(LocatorType.XPATH,
                "//div[@class='server-text-label flex']/div[contains(text(), \"" + uiLabel + "\")]/following::li[" +
                        (selection + 2) + "]");
    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    @Override
    public Integer getUserValues() {
        return selection;
    }

    @Override
    public void handleInput() {
        if (isSlideBarSelect()) {
            handleSlideBarInput();
        }
    }

    @Override
    public List<String> scanInput() {
        return Collections.singletonList(tick.get().text());
    }

    private boolean isSlideBarSelect() {
        return tick.is().present();
    }

    private void handleSlideBarInput() {
        tick.click();
        recordedInputs.add(String.valueOf(this.selection));
    }
}
