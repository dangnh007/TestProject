package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.objects.user.UserValue;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WebInput extends Input {


    public WebInput(WebApp app, ApiInput api, String uiLabel, UserValue value) {
        this(app, api, uiLabel, value.getValue(), false);
    }

    public WebInput(WebApp app, ApiInput api, String uiLabel, int value) {
        this(app, api, uiLabel, String.valueOf(value), false);
    }

    public WebInput(WebApp app, ApiInput api, String uiLabel, String inputText, boolean waitNeeded) {
        super(app, api, uiLabel, inputText, waitNeeded);
        finalInput = app.newElement(LocatorType.XPATH, "//*[contains(@placeholder, \"" + uiLabel + "\")]");
    }

    @Override
    public void handleInput() {
        // Check to see if the input text is an age
        if ((isInput() && !inputText.matches("^-?\\d{1,3}$")) ||
                (inputText.matches("^-?\\d{1,3}$") && Integer.parseInt(inputText) >= 0)) {
            handleWebInput();
        }
    }

    private boolean isInput() {
        // Workaround for fields that pops up immediately after radio button selection
        if (waitNeeded) {
            finalInput.waitFor().present();
        }
        return finalInput.is().present();
    }

    private void handleWebInput() {
        finalInput.type(inputText);
        recordedInputs.add(inputText);
    }

    /**
     * Gets all of the inputs that the page actually has selected. This is what DID happen.
     *
     * @return list of values the input actually selected
     */
    @Override
    public List<String> scanInput() {
        if ("".equals(this.finalInput.get().value())) {
            return new ArrayList<>();
        }
        JavascriptExecutor javaScriptExecutor = ((JavascriptExecutor) app.getDriver());
        return Collections.singletonList(javaScriptExecutor.executeScript("return arguments[0].value", app.getDriver().findElement(By.xpath(finalInput.getLocator()))).toString());
    }
}
