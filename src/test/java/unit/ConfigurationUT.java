package unit;

import com.pmt.health.steps.Configuration;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

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
        URL url = new URL("https://cotmissioncontrol.joinallofus.org");
        configuration.setSiteURL(url);
        assertEquals(configuration.getSiteURL(), url);
    }

    @Test
    public void getProgramTest() {
        configuration = new Configuration(null);
        assertEquals(configuration.getProgram(), "pmt");
    }

    @Test
    public void getEnvironmentTest() {
        configuration = new Configuration(null);
        assertEquals(Configuration.getEnvironment(), "qa");
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
    public void getEnvironmentURLDefaultQATest() {
        System.setProperty("environment", "na");
        assertNull(Configuration.getEnvironmentURL(""));
    }

    @Test
    public void getEnvironmentURLSubAutomationTest() throws MalformedURLException {
        System.setProperty("environment", "minikube");
        configuration = new Configuration(null);
        assertEquals(Configuration.getMissionControlEnvironmentURL(), new URL("http://missioncontrol.minikube"));
    }

    @Test
    public void getEnvironmentURLDefaultAutomationTest() throws MalformedURLException {
        System.setProperty("environment", "minikube");
        assertEquals(Configuration.getEnvironmentURL(""),
                new URL("http://missioncontrol.minikube"));
    }
}
