package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;
import org.openqa.selenium.Keys;

import java.util.Date;
import java.util.*;

public class WebVideo extends Input {

    private final WebbElement videoPlayButton;
    private final WebbElement videoLoadSpinner;
    private final WebbElement videoController;
    private final WebbElement iFrame;

    public WebVideo(WebApp app, ApiInput api) {
        super(app, api, "");

        videoPlayButton = app.newElement(LocatorType.XPATH, "//button[@aria-label='Play']");
        videoLoadSpinner = app.newElement(LocatorType.CLASSNAME, "ytp-spinner");
        videoController = app.newElement(LocatorType.XPATH, "//div[@aria-label='YouTube Video Player']");
        iFrame = app.newElement(LocatorType.XPATH, "//iframe[@title='YouTube video player']");
    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    @Override
    public Date getUserValues() {
        return new Date();
    }

    @Override
    public void handleInput() {
        handleVideo();
    }

    private void handleVideo() {
        WebbElement videoFrame = iFrame;
        play(videoFrame);
        end(videoFrame);
    }

    private void play(WebbElement videoFrame) {
        videoFrame.waitFor().enabled();
        videoFrame.selectFrame();
        videoPlayButton.waitFor().enabled();
        videoPlayButton.click();
        app.wait(0.5);
        videoLoadSpinner.waitFor().notDisplayed();
        ((WebApp) app).selectMainFrame();
    }

    private void end(WebbElement videoFrame) {
        videoFrame.selectFrame();
        videoController.type(Keys.END);
        ((WebApp) app).selectMainFrame();
    }

    @Override
    public List<String> scanInput() {
        // No real input to scan
        return new ArrayList<>();
    }
}
