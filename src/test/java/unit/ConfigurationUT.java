package unit;

import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.steps.Configuration;
import org.testng.annotations.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ConfigurationUT {

    private String env;
    private String androidId;
    private Configuration configuration;

    @BeforeClass
    public void storeDevice() {
        if (System.getProperty("environment") != null) {
            env = System.getProperty("environment");
        }
        if (System.getProperty("androidID") != null) {
            androidId = System.getProperty("androidID");
        }
    }

    @AfterClass
    public void restoreDevice() {
        if (env != null) {
            System.setProperty("environment", env);
        }
        if (androidId != null) {
            System.setProperty("androidID", androidId);
        }
    }

    @AfterMethod
    @BeforeMethod
    public void clearBrowser() {
        configuration = new Configuration(null);
        System.clearProperty("environment");
        System.clearProperty("androidID");
    }

    @Test
    public void getURLTest() {
        assertNull(configuration.getSiteURL());
    }

    @Test
    public void setSiteURLTest() throws MalformedURLException {
        URL url = new URL("https://cotsub.joinallofus.org");
        configuration.setSiteURL(url);
        assertEquals(configuration.getSiteURL(), url);
    }

    @Test
    public void getProgramTest() {
        configuration = new Configuration(null);
        assertEquals(configuration.getProgram(), "pmi");
    }

    @Test
    public void getEnvironmentTest() {
        configuration = new Configuration(null);
        assertEquals(Configuration.getEnvironment(), "automation");
    }

    @Test
    public void setEnvironmentTest() {
        System.setProperty("environment", "qa");
        configuration = new Configuration(null);
        assertEquals(Configuration.getEnvironment(), "qa");
    }

    @Test
    public void setEnvironmentCapsTest() {
        System.setProperty("environment", "QA");
        configuration = new Configuration(null);
        assertEquals(Configuration.getEnvironment(), "qa");
    }

    @Test
    public void getEnvironmentURLBadTest() {
        System.setProperty("environment", "qa");
        configuration = new Configuration(null);
        assertNull(Configuration.getEnvironmentURL("badURLTest"));
    }

    @Test
    public void getEnvironmentURLSubQATest() {
        System.setProperty("environment", "qa");
        configuration = new Configuration(null);
        assertNull(Configuration.getSubscriberEnvironmentURL());
    }

    @Test
    public void getEnvironmentURLDefaultQATest() {
        System.setProperty("environment", "qa");
        assertNull(Configuration.getEnvironmentURL(""));
    }

    @Test
    public void getEnvironmentURLSubAutomationTest() throws MalformedURLException {
        System.setProperty("environment", "minikube");
        configuration = new Configuration(null);
        assertEquals(Configuration.getSubscriberEnvironmentURL(), new URL("http://sub.minikube"));
    }

    @Test
    public void getEnvironmentURLDefaultAutomationTest() throws MalformedURLException {
        System.setProperty("environment", "minikube");
        assertEquals(Configuration.getEnvironmentURL(""),
                new URL("http://sub.minikube"));
    }

    @Test
    public void getAppPackageNullTest() {
        System.setProperty("environment", "automation");
        configuration = new Configuration(null);
        assertEquals(configuration.getAppPackage(), "com.acadia.automation");
    }

    @Test
    public void getAppPackageTest() {
        System.setProperty("environment", "qa");
        configuration = new Configuration(null);
        assertEquals(configuration.getAppPackage(), "com.acadia.pmiqa");
    }

    @Test
    public void getAppPackagePassedTest() {
        System.setProperty("androidID", "hello.world");
        configuration = new Configuration(null);
        assertEquals(configuration.getAppPackage(), "hello.world");
    }

    @Test
    public void getAppPackageOverrideTest() {
        System.setProperty("environment", "qa");
        System.setProperty("androidID", "hello.world");
        configuration = new Configuration(null);
        assertEquals(configuration.getAppPackage(), "hello.world");
    }
}
