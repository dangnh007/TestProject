package com.pmt.health.workflows.inputs;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.objects.user.UserValue;
import com.pmt.health.workflows.inputs.web.*;

import java.io.File;
import java.util.Date;
import java.util.List;

public class InputFactory {

    /**
     * Private constructor which does nothing, per factory standards
     */
    private InputFactory() {
    }

    /**
     * Returns a new Input in the date format, allowing entering any values. For iOS and Web, a generic input is
     * returned, as a specialized input field isn't provided for them
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the date input field
     * @param uiLabel - the ui label of the date input field
     * @param value   - the value to enter into the date input field
     * @return Input - a new date input
     */
    public static Input newDate(App app, ApiInput api, String uiLabel, Date value) {
        return new WebDate((WebApp) app, api, uiLabel, value);
    }

    /**
     * Returns a new Input in the date format, allowing entering any values. For iOS and Web, a generic input is
     * returned, as a specialized input field isn't provided for them
     *
     * @param app        - the deviceController application object
     * @param api        - the api label of the date input field
     * @param uiLabel    - the ui label of the date input field
     * @param value      - the value to enter into the date input field
     * @param waitNeeded - boolean to determine whether the field should
     *                   be waited for
     * @return Input - a new date input
     */
    public static Input newInput(App app, ApiInput api, String uiLabel, String value, boolean waitNeeded) {
        return new WebInput((WebApp) app, api, uiLabel, value, waitNeeded);
    }

    /**
     * Returns a new Input in the generic format, allowing entering any values
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the generic input field
     * @param uiLabel - the ui label of the generic input field
     * @param value   - the value to enter into the generic input field
     * @return Input - a new generic input
     */
    public static Input newInput(App app, ApiInput api, String uiLabel, UserValue value) {
        return new WebInput((WebApp) app, api, uiLabel, value);
    }

    /**
     * Returns a new Input in the generic format, allowing entering any values
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the generic input field
     * @param uiLabel - the ui label of the generic input field
     * @param value   - the value to enter into the generic input field
     * @return Input - a new generic input
     */
    public static Input newInput(App app, ApiInput api, String uiLabel, int value) {
        return new WebInput((WebApp) app, api, uiLabel, value);
    }

    /**
     * Returns a new Input in the generic format, allowing entering date values
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the generic input field
     * @param uiLabel - the ui label of the generic input field
     * @param value   - the value to enter into the generic input field
     * @return Input - a new generic input
     */
    public static Input newInput(App app, ApiInput api, String uiLabel, String value) {
        return new WebInput((WebApp) app, api, uiLabel, value, false);
    }

    /**
     * Returns a new Input in the quiz format, allowing entering quiz answers
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the quiz input fields
     * @param uiLabel - the ui label of the quiz input fields
     * @param values  - the values to enter into the quiz input fields
     * @return Input - a new quiz set input
     */
    public static Input newQuiz(App app, ApiInput api, String uiLabel, UserValue... values) {
        return new WebQuiz((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the quiz format, allowing entering quiz answers
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the quiz input fields
     * @param uiLabel - the ui label of the quiz input fields
     * @param values  - the values to enter into the quiz input fields
     * @return Input - a new quiz set input
     */
    public static Input newQuiz(App app, ApiInput api, String uiLabel, List<UserValue> values) {
        return new WebQuiz((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the vertical radio format, allowing selecting options
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the radio input field
     * @param uiLabel - the ui label of the radio input field
     * @param values  - the value to enter into the radio input field
     * @return Input - a new radio input
     */
    public static Input newVerticalRadio(App app, ApiInput api, String uiLabel, UserValue... values) {
        return new WebVerticalRadio((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the Vertical Radio format, allowing selecting options
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the radio input field
     * @param uiLabel - the ui label of the radio input field
     * @param values  - the value to enter into the radio input field
     * @return Input - a new vertical radio input
     */
    public static Input newVerticalRadio(App app, ApiInput api, String uiLabel, List<UserValue> values) {
        return new WebVerticalRadio((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the Accordion format, allowing selecting options
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the accordion input field
     * @param uiLabel - the ui label of the accordion input field
     * @param values  - the values to enter into the accordion input field
     * @return Input - a new accordion input
     */
    public static Input newAccordionVerticalRadio(App app, ApiInput api, String uiLabel, List<UserValue> values) {
        return new WebAccordionVerticalRadio((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the vertical radio format, allowing selecting options
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the radio input field
     * @param uiLabel - the ui label of the radio input field
     * @param values  - the value to enter into the radio input field
     * @return Input - a new radio input
     */
    public static Input newAccordionVerticalRadio(App app, ApiInput api, String uiLabel, UserValue... values) {
        return new WebAccordionVerticalRadio((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the horizontal radio format, allowing selecting options
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the horizontal radio input field
     * @param uiLabel - the ui label of the horizontal radio input field
     * @param values  - the value to enter into the horizontal radio input field
     * @return Input - a new horizontal radio input
     */
    public static Input newHorizontalRadio(App app, ApiInput api, String uiLabel, UserValue... values) {
        return new WebHorizontalRadio((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the horizontal radio format, allowing selecting options
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the radio input field
     * @param uiLabel - the ui label of the radio input field
     * @param values  - the value to enter into the radio input field
     * @return Input - a new horizontal radio input
     */
    public static Input newHorizontalRadio(App app, ApiInput api, String uiLabel, List<UserValue> values) {
        return new WebVerticalRadio((WebApp) app, api, uiLabel, values);
    }

    /**
     * Returns a new Input in the select format, allowing selecting one option
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the select input field
     * @param uiLabel - the ui label of the select input field
     * @param value   - the value to enter into the select input field
     * @return Input - a new select input
     */
    public static Input newSelect(App app, ApiInput api, String uiLabel, UserValue value) {
        return new WebSelect((WebApp) app, api, uiLabel, value);
    }

    /**
     * Returns a new Input in the select format, allowing selecting one option
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the select input field
     * @param uiLabel - the ui label of the select input field
     * @param value   - the value to enter into the select input field
     * @return Input - a new select input
     */
    public static Input newSelect(App app, ApiInput api, String uiLabel, String value) {
        return new WebSelect((WebApp) app, api, uiLabel, value);
    }

    /**
     * Returns a new Input in the signature format, allowing the user to sign something
     *
     * @param app      - the deviceController application object
     * @param api      - the api label of the signature input field
     * @param username - the name to type into the input field instead of signing
     * @return Input - a new signature input
     */
    public static Input newSignature(App app, ApiInput api, String username) {
        return new WebSignature((WebApp) app, api, username);
    }

    /**
     * Returns a new Input in the signature format, allowing the user to sign something
     *
     * @param app      - the deviceController application object
     * @param api      - the api label of the signature input field
     * @param username - the reporter to draw (send) for signing
     * @return Input - a new signature input
     */
    public static Input newSignature(App app, ApiInput api, File username) {
        return new WebSignature((WebApp) app, api, username);
    }

    /**
     * Returns a new Input in the slide bar format, allowing the user to select a number from a sliding measure. For
     * Android and iOS a simple select dropdown is provided, as this action doesn't yet exist for those application
     * types
     *
     * @param app     - the deviceController application object
     * @param api     - the api label of the slidebar input field
     * @param uiLabel - the ui label of the slidebar input field
     * @param value   - the value to enter into the slidebar input field
     * @return Input - a new slidebar input
     */
    public static Input newSlideBar(App app, ApiInput api, String uiLabel, int value) {
        return new WebSlideBar((WebApp) app, api, uiLabel, value);
    }

    /**
     * Returns a new Input in the video format, allowing the user to watch something
     *
     * @param app - the deviceController application object
     * @param api - the api label of the video input field
     * @return Input - a new video input
     */
    public static Input newVideo(App app, ApiInput api) {
        return new WebVideo((WebApp) app, api);
    }
}
