package com.pmt.health.utilities;

import com.pmt.health.steps.Configuration;
import org.openqa.selenium.Cookie;
import org.testng.log4testng.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Property {

    public static final String MAINTENANCE_COOKIE_NAME = "maintenance.cookie.name";
    public static final String MAINTENANCE_COOKIE_VALUE = "maintenance.cookie.value";
    private static final Logger log = Logger.getLogger(Property.class);
    private static final String PROGRAM = "src/test/resources/program.properties";
    private static final String APPIUM = "src/test/resources/appium.properties";
    private static final String JIRA = "src/test/resources/jira.properties";
    private static final int DEFAULT_WAIT = 5;


    private Property() {
    }

    public static Properties getPropertyFile(String propertyFile) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(propertyFile)) {
            prop.load(input);
        } catch (IOException e) {
            log.error(e);
        }
        return prop;
    }

    public static String getProgramProperty(String property) {
        if (System.getProperty(property) != null) {
            return System.getProperty(property);
        }
        return getPropertyFile(PROGRAM).getProperty(property);
    }

    public static String getDefaultProgramProperty(String property) {
        String value = Property.getProgramProperty(Configuration.getEnvironment() + property);
        if (value != null) {
            return value;
        }
        return Property.getProgramProperty("automation." + property);
    }

    public static String getAppiumProperty(String property) {
        return getPropertyFile(APPIUM).getProperty(property);
    }

    public static String getJiraProperty(String property) {
        if (System.getProperty(property) != null) {
            return System.getProperty(property);
        }
        return getPropertyFile(JIRA).getProperty(property);
    }

    /**
     * Retrieves a list of strings set in the jira properties file
     *
     * @param property the property name
     * @return List<String>
     */
    public static List<String> getJiraProperties(String property) {
        return Arrays.asList(getPropertyFile(JIRA).getProperty(property).split(","));
    }

    public static Boolean takeScreenshot() {
        return !(System.getProperty("disable.screenshots") != null &&
                "true".equals(System.getProperty("disable.screenshots")));
    }

    public static Boolean isHeadless() {
        return System.getProperty("headless") != null && "true".equals(System.getProperty("headless"));
    }

    /**
     * Gets the maximum wait time the application should wait until an element is present/visible/etc. This default
     * to 5, unless otherwise provided via cmd
     *
     * @return int - the maximum number of seconds to wait
     */
    public static int getDefaultWait() {
        if (System.getProperty("default.wait") != null) {
            try {
                return Integer.valueOf(System.getProperty("default.wait"));
            } catch (Exception e) {
                log.warn("Unable to convert default wait to integer");
                log.info(e);
                return DEFAULT_WAIT;
            }
        }
        return DEFAULT_WAIT;
    }

    public static Boolean maintenanceMode() {
        return (getProgramProperty(MAINTENANCE_COOKIE_NAME) != null &&
                getProgramProperty(MAINTENANCE_COOKIE_VALUE) != null);
    }

    public static Cookie getMaintenanceCookie() {
        if (maintenanceMode()) {
            LocalDate apptDate = LocalDate.now().plusDays(1);
            java.util.Date expiry = java.sql.Date.valueOf(apptDate);
            return new Cookie(getProgramProperty(MAINTENANCE_COOKIE_NAME),
                    getProgramProperty(MAINTENANCE_COOKIE_VALUE),
                    Configuration.getEnvironmentURL().toString().split("//")[1], "/", expiry);
        }
        return null;
    }

    public static String getMaintenanceHeader() {
        if (maintenanceMode()) {
            return getProgramProperty(MAINTENANCE_COOKIE_NAME) + "=" + getProgramProperty(MAINTENANCE_COOKIE_VALUE);
        }
        return null;
    }

    /**
     * Checks if property alert.enabled in program.properties is set to true, if so, returns true
     *
     * @return
     */
    public static Boolean isAlertEnabled() {
        String alert = Property.getProgramProperty(Configuration.getEnvironment() + ".alert");
        return "true".equals(alert);
    }
}
