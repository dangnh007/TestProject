package com.pmt.health.controllers;

import com.pmt.health.utilities.Device;
import cucumber.runtime.java.picocontainer.PicoFactory;

/**
 * Extension of the standard PicoContainer ObjectFactory which will register the
 * proper AutomatioApi implementation based on a system property.
 */
public class CustomPicoFactory extends PicoFactory {
    public CustomPicoFactory() {
        switch (Device.getDevice()) {
            case HTMLUNIT:
                addClass(HtmlUnitController.class);
                break;
            case FIREFOX:
                addClass(FirefoxController.class);
                break;
            case CHROME:
                addClass(ChromeController.class);
                break;
            case INTERNETEXPLORER:
                addClass(InternetExplorerController.class);
                break;
            case EDGE:
                addClass(EdgeController.class);
                break;
            case OPERA:
                addClass(OperaController.class);
                break;
            case SAFARI:
                addClass(SafariController.class);
                break;
            default: // if no device is specified, use chrome
                addClass(ChromeController.class);
        }
    }
}
