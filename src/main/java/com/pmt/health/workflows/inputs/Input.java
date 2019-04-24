package com.pmt.health.workflows.inputs;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.objects.user.UserValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Input {
    @SuppressWarnings("squid:S2885") // TODO Ditch this altogether.
    public static final SimpleDateFormat INPUT_SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    protected ApiInput api;
    protected UserValue value = null;
    protected List<UserValue> userValues = null;
    protected App app;
    protected String inputText = null;
    protected String uiLabel;
    protected List<String> recordedInputs;
    protected Element singleSelect;
    protected Element singleSelected;
    protected Element finalInput = null;
    protected boolean waitNeeded = false;

    public Input(App app, ApiInput api, String uiLabel) {
        this.app = app;
        this.api = api;
        this.uiLabel = uiLabel;
        this.recordedInputs = new ArrayList<>();
    }

    public Input(App app, ApiInput api, String uiLabel, UserValue value) {
        this(app, api, uiLabel, value.getValue(), false);
        this.value = value;
    }

    public Input(App app, ApiInput api, String uiLabel, int value) {
        this(app, api, uiLabel, String.valueOf(value), false);
    }

    public Input(App app, ApiInput api, String uiLabel, String inputText, boolean waitNeeded) {
        this(app, api, uiLabel);
        this.inputText = inputText;
        this.waitNeeded = waitNeeded;
    }

    /**
     * Custom constructor for a WebAccordionVerticalRadio (a pop-open/pop-closed) structure.
     *
     * @param app        Controlling WebApp
     * @param api        An ApiInput object
     * @param uiLabel    The label of the accordion to open
     * @param userValues The userValues to make in the accordion.
     */
    public Input(App app, ApiInput api, String uiLabel, List<UserValue> userValues) {
        this(app, api, uiLabel);
        this.userValues = userValues;
        this.recordedInputs = new ArrayList<>();
    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    public Object getUserValues() {
        if (value != null) {
            return value;
        }
        return inputText;
    }

    /**
     * Retrieves the stored label for the api call. This is used for filling out the forms without going through the ui
     *
     * @return the string value to perform a lookup for the form input id
     */
    public ApiInput getApi() {
        return this.api;
    }

    /**
     * General handle method that pulls info from the user object and appropriately fills out the current form.
     */
    public abstract void handleInput();

    /**
     * Gets all of the inputs that the handle believes it filled out. This is what SHOULD HAVE happened.
     *
     * @return list of values the input was told to select
     */
    public List<String> getRecordedInput() {
        Collections.sort(recordedInputs);
        return recordedInputs;
    }

    /**
     * Compensates for the fact that I couldn't figure out what AndroidSelect needed
     * to do with InputText that it couldn't have done with uiLabel.
     *
     * @param inputText The input text, ostensibly identical to uiLabel but named poorly
     * @deprecated Don't use this.
     */
    @Deprecated // TODO: Fix AndroidSelect so that it doesn't need this method.
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    /**
     * Gets all of the inputs that the page actually has selected. This is what DID happen.
     *
     * @return list of values the input actually selected
     */
    public List<String> scanInput() {
        if (finalInput == null || "".equals(this.finalInput.get().value())) {
            return new ArrayList<>();
        }
        return Collections.singletonList(this.finalInput.get().value());
    }
}
