/*
 * Copyright 2017 Coveros, Inc.
 *
 * This file is part of Selenified.
 *
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.pmt.health.utilities;

import com.pmt.health.exceptions.InvalidDeviceException;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Select a browser to run Available options are: HtmlUnit, Firefox, Chrome,
 * InternetExplorer, Edge, Opera, Safari, Android, Iphone
 */
public enum Device {
    HTMLUNIT, BROWSER, FIREFOX, CHROME, INTERNETEXPLORER, EDGE, OPERA, SAFARI;

    private static final Logger log = Logger.getLogger(Device.class);
    private static final String DETAILS = "deviceDetails";

    /**
     * allows the browser selected to be passed in with a case insensitive name
     *
     * @param b - the string name of the browser
     * @return Browser: the enum version of the browser
     * @throws InvalidDeviceException If a browser that is not one specified in the
     *                                Selenium.Browser class is used, this exception will be thrown
     */
    public static Device lookup(String b) throws InvalidDeviceException {
        for (Device browser : Device.values()) {
            if (browser.name().equalsIgnoreCase(b)) {
                return browser;
            }
        }
        throw new InvalidDeviceException("The selected device " + b + " is not an applicable choice");
    }

    /**
     * Breaks up a string, and places it into a map. ampersands (&) are used to
     * split into key value pairs, while equals (=) are used to assign key vs
     * values
     *
     * @param input - a string, with key and values separated by an equals (=) and
     *              pairs separated by an ampersand (&)
     * @return Map: a map with values
     */
    private static Map<String, String> parseMap(final String input) {
        final Map<String, String> map = new HashMap<>();
        for (String pair : input.replaceAll("\"", "").split("&")) {
            String[] kv = pair.split("=");
            if (kv.length > 1) {
                map.put(kv[0], kv[1]);
            }
        }
        return map;
    }

    public static Device getDevice() {
        Device device = Device.CHROME;
        if (System.getProperty("device") != null) {
            try {
                device = Device.lookup(System.getProperty("device"));
            } catch (Exception e) {
                log.warn("Provided device does not match options. Using Chrome instead. " + e);
            }
        }
        return device;
    }
    // TODO Remove the side effects from this method

    private static Map<String, String> getDeviceDetails() {
        Map<String, String> deviceDetails = new HashMap<>();
        if (System.getProperty(DETAILS) != null && System.getProperty(DETAILS).contains("=")) {
            deviceDetails = Device.parseMap(System.getProperty(DETAILS));
        }
        return deviceDetails;
    }

    public static String getDeviceVersion() {
        if (getDeviceDetails().containsKey(CapabilityType.VERSION)) {
            return getDeviceDetails().get(CapabilityType.VERSION);
        }
        return "";
    }

    public static String getDevicePlatform() {
        if (getDeviceDetails().containsKey(CapabilityType.PLATFORM)) {
            return getDeviceDetails().get(CapabilityType.PLATFORM);
        }
        return "";
    }

    /**
     * Retrieves the browser name from the system parameters
     *
     * @return String - the browser name as a string
     */
    public static String getBrowserName() {
        if (getDeviceDetails().containsKey(CapabilityType.BROWSER_NAME)) {
            return getDeviceDetails().get(CapabilityType.BROWSER_NAME);
        }
        return "";
    }

    private static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

    /**
     * Gets a user friendly string of the device details being run over
     *
     * @return String - all of the device details, concatted together
     */
    public static String getDeviceInfo() {
        StringBuilder device = new StringBuilder(capitalizeFirstLetter(getDevice().toString()));
        if (!getDeviceVersion().isEmpty()) {
            device.append(" ").append(getDeviceVersion());
        }
        return device.toString();
    }

    public static String getDeviceOrientation() {
        if (getDeviceDetails().containsKey("deviceOrientation")) {
            return getDeviceDetails().get("deviceOrientation");
        }
        return "";
    }
}
