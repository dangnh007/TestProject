package com.pmt.health.workflows.inputs.web;

import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.selenified.Point;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.ApiInput;
import com.pmt.health.workflows.inputs.Input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WebSignature extends Input {

    private final WebbElement signatureCanvas;
    private final WebbElement typeSignInput;
    private final WebbElement typeSignRadio;
    private String username = null;
    private File signature = null;

    public WebSignature(WebApp app, ApiInput api, String username) {
        this(app, api);
        this.username = username;
    }

    public WebSignature(WebApp app, ApiInput api, File signature) {
        this(app, api);
        this.signature = signature;
    }

    private WebSignature(WebApp app, ApiInput api) {
        super(app, api, "");

        this.signatureCanvas = app.newElement(LocatorType.TAGNAME, "canvas");
        this.typeSignInput = app.newElement(LocatorType.XPATH,
                "//input[@aria-label=\"type your signature\" or @aria-label=\"type your full name\"]");
        this.typeSignRadio =
                app.newElement(LocatorType.XPATH, "//div[@class='display-flex flex-direction-row']/div[2]/label/input");
    }

    /**
     * Retrieves the initially set user value(s), so that either api or ui information can be extracted from it
     *
     * @return a list of the user values
     */
    @Override
    public Object getUserValues() {
        if (username != null) {
            return username;
        }
        return signature;
    }

    @Override
    public void handleInput() {
        if (isSignature()) {
            if (username != null) {
                typeSignRadio.click();
                typeSignInput.type(this.username);
            } else {
                for (int i = 0; i < signatureCanvas.get().matchCount(); i++) {
                    signatureCanvas.setMatch(i);
                    sign();
                }
            }
        }
    }

    private boolean isSignature() {
        return signatureCanvas.is().present();
    }

    private void sign() {
        List<Point<Integer, Integer>> signatureLine1 = new ArrayList<>();
        signatureLine1.add(new Point<>(10, 10));
        signatureLine1.add(new Point<>(100, 10));
        signatureCanvas.draw(signatureLine1);
        List<Point<Integer, Integer>> signatureLine2 = new ArrayList<>();
        signatureLine2.add(new Point<>(10, 30));
        signatureLine2.add(new Point<>(100, 30));
        signatureCanvas.draw(signatureLine2);
    }
}
