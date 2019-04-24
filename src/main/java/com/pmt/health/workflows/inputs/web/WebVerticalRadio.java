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

public class WebVerticalRadio extends Input {

    public static final String DIV_CLASS_SERVER_TEXT_LABEL_FLEX_DIV_CONTAINS = "//div[@class='server-text-label flex']/div[contains(";
    public static final String DIV_CLASS_SERVER_TEXT_LABEL_FLEX_SPAN_CONTAINS = "//div[@class='server-text-label flex']/following::span[contains(";
    public static final String TEXT = "text()";

    private WebbElement verticalRadio;

    public WebVerticalRadio(WebApp app, ApiInput api, String uiLabel, UserValue... values) {
        this(app, api, uiLabel, Arrays.asList(values));
    }

    public WebVerticalRadio(WebApp app, ApiInput api, String uiLabel, List<UserValue> values) {
        super(app, api, uiLabel, values);
        init(app, uiLabel);
    }

    private void init(WebApp app, String uiLabel) {
        verticalRadio = app.newElement(LocatorType.XPATH, DIV_CLASS_SERVER_TEXT_LABEL_FLEX_DIV_CONTAINS +
                App.getSanitizedTranslate(TEXT, uiLabel, false) + ")" +
                "]/following::preview-sub-field-multi-select-options | " +
                DIV_CLASS_SERVER_TEXT_LABEL_FLEX_SPAN_CONTAINS +
                App.getSanitizedTranslate(TEXT, uiLabel, false) + ")" +
                "]/following::preview-sub-field-multi-select-options");
    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    @Override
    public Object getUserValues() {
        return userValues;
    }

    /**
     * Handles the radio input depending on its type.
     */
    @Override
    public void handleInput() {
        if (isRadioInput()) {
            handleRadioInput();
        } else {
            Element singleRadio = app.newElement(LocatorType.XPATH,
                    "//div[contains(" + App.getSanitizedTranslate(TEXT, userValues.get(0).getValue(), false) + ")]");
            singleRadio.click();
            recordedInputs.add(userValues.get(0).getValue().toLowerCase());
        }
    }


    @Override
    public List<String> scanInput() {
        List<String> actualOptions = new ArrayList<>();
        WebbElement optionSelected = (WebbElement) (app.newElement(LocatorType.XPATH, "//div[contains(@style, 'color: rgb(0, 152, 219); font-weight: bold;')]"));
        WebbElement optionSelectedBlock = (WebbElement) (app.newElement(LocatorType.XPATH, "//div[@class='accordion-right-section ng-binding']"));
        // Need to click on each block to see selected option for Family History Survey - selenium can not get the text form not displayed element
        if (optionSelectedBlock.is().displayed()) {
            int optionSelectedBlocks = optionSelectedBlock.get().matchCount();
            for (int i = 0; i < optionSelectedBlocks; i++) {
                optionSelectedBlock.setMatch(i);
                optionSelectedBlock.click();
            }
        }
        int optionsSelected = optionSelected.get().matchCount();
        for (int i = 0; i < optionsSelected; i++) {
            optionSelected.setMatch(i);
            actualOptions.add(App.getSanitizedString(optionSelected.get().text().toLowerCase()));
        }
        Collections.sort(actualOptions);
        return actualOptions;
    }

    protected void handleRadioInput() {
        for (UserValue selection : this.userValues) {
            verticalRadio.setMatch(0);
            selectRadio(App.getSanitizedString(selection.getValue()));
        }
    }

    private boolean isRadioInput() {
        return verticalRadio.is().present();
    }

    private Element getExactRadioOption(String label, String selection) {
        return app.newElement(LocatorType.XPATH, DIV_CLASS_SERVER_TEXT_LABEL_FLEX_DIV_CONTAINS +
                App.getSanitizedTranslate(TEXT, label.toLowerCase(), false) +
                ")]/following::preview-sub-field-multi-select-options//div[" +
                App.getSanitizedTranslate(TEXT, selection, true) + "]");
    }

    private void checkForExactRadioOption(Element exactCurrentRadioOption) {
        // TODO: OMG, What on Earth is this!?
        for (int i = 0; i < 120; i++) {
            if (exactCurrentRadioOption.is().displayed()) {
                break;
            }
        }
    }

    /**
     * Clicks a radio option with text matching the specified text
     *
     * @param selection text of the radio option to click
     */
    private void selectRadio(String selection) {
        // Handles situations where "prefer not to" toggles userValues.
        if (selection.contains("prefer not to answer") || recordedInputs.contains("prefer not to answer")) {
            recordedInputs.clear();
        } else if ("noanswer".equals(selection)) {
            return;
        }
        Element exactCurrentRadioOption = getExactRadioOption(uiLabel, selection);
        checkForExactRadioOption(exactCurrentRadioOption);
        // Click the exact radio option if present, else click the radio option that contains the text
        if (exactCurrentRadioOption.is().displayed()) {
            exactCurrentRadioOption.click();
        } else {
            Element currentRadioOption = app.newElement(LocatorType.XPATH,
                    "//preview-sub-field-multi-select-option-value//div[contains(" +
                            App.getSanitizedTranslate(TEXT, selection, false) + ")]");
            currentRadioOption.click();
        }
        recordedInputs.add(selection.toLowerCase());
    }
}