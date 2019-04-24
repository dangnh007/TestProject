package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.UserValue;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WebHorizontalRadio extends Input {

    private WebbElement horizontalRadio;

    public WebHorizontalRadio(WebApp app, ApiInput api, String uiLabel, UserValue... values) {
        this(app, api, uiLabel, Arrays.asList(values));
    }

    public WebHorizontalRadio(WebApp app, ApiInput api, String uiLabel, List<UserValue> values) {
        super(app, api, uiLabel, values);

        horizontalRadio = app.newElement(LocatorType.XPATH, "//div[@class='server-text-label flex']/div[contains(" +
                App.getSanitizedTranslate("text()", uiLabel, false) + ")" +
                "]/following::preview-form-sub-field-radio-options");

    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    @Override
    public List<UserValue> getUserValues() {
        return userValues;
    }


    @Override
    public void handleInput() {
        if (horizontalRadio.is().present()) {
            selectRadio();
        }
    }

    @Override
    public List<String> scanInput() {
        List<String> actualSelections = new ArrayList<>();
        Element optionSelected = app.newElement(LocatorType.XPATH, "//div[contains(text(), \"" + uiLabel + "\")]/following::div[contains(@style,\"color: rgb(0, 152, 219); font-weight: bold;\")][1]");
        if (optionSelected.is().present()) {
            actualSelections.add(optionSelected.get().text());
        }
        Collections.sort(actualSelections);
        return actualSelections;
    }

    private void selectRadio() {
        for (UserValue selection : userValues) {
            if ("noanswer".equals(selection.getValue())) {
                return;
            }
            app.newElement(LocatorType.XPATH, "//div[@class='server-text-label flex']/div[contains(" +
                    App.getSanitizedTranslate("text()", uiLabel, false) +
                    ")]/following::preview-form-sub-field-radio-options/descendant::preview-form-sub-field-radio-option-value[contains(" +
                    App.getSanitizedTranslate("@title", selection.getValue(), false) + ")]").click();
            recordedInputs.add(selection.getValue());
        }
    }
}
