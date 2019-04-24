package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.objects.user.UserValue;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;

import java.util.Collections;
import java.util.List;

public class WebSelect extends Input {

    public WebSelect(WebApp app, ApiInput api, String uiLabel, UserValue value) {
        super(app, api, uiLabel, value);
        init(app, uiLabel);
    }

    public WebSelect(WebApp app, ApiInput api, String uiLabel, String inputText) {
        super(app, api, uiLabel, inputText, false);
        init(app, uiLabel);
    }

    private void init(WebApp app, String uiLabel) {
        singleSelect = app.newElement(LocatorType.XPATH, "//div[@class='server-text-label flex']/div[contains(" +
                App.getSanitizedTranslate("text()", uiLabel, false) + ")]/following::select");
        singleSelected = app.newElement(LocatorType.XPATH, "//div[@class='server-text-label flex']/div[contains(" +
                App.getSanitizedTranslate("text()", uiLabel, false) +
                ")]/following::select//option[@selected='selected']");
    }

    @Override
    public void handleInput() {
        if (isSelectInput()) {
            handleSelectInput();
        }
    }


    @Override
    public List<String> scanInput() {
        if (singleSelected.is().present()) {
            return Collections.singletonList(singleSelected.get().text());
        }
        return Collections.singletonList(singleSelect.get().text());
    }

    private boolean isSelectInput() {
        return singleSelect.is().present();
    }

    private void handleSelectInput() {
        // if selection is empty string, ignore
        if (!"".equals(inputText) && !"Unknown".equals(inputText)) {
            singleSelect.selectOption(inputText);
            recordedInputs.add(inputText);
        }
    }
}
