package integration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.steps.Configuration;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class ConfigurationTest {

    private final Configuration configuration = new Configuration(null);
    JsonParser parser = new JsonParser();
    private final JsonObject mockedSysInfo = (JsonObject) parser.parse("{\"gitBranch" +
            "\":\"eca7aaaccfaa028b87bab87cc2110ac67c08dedc\",\"buildTime\":\"2019-06-17T11:05:40+0000\"," +
            "\"buildVersion\":\"3.1.0-SNAPSHOT\",\"commitId\":\"eca7aaaccfaa028b87bab87cc2110ac67c08dedc\"," +
            "\"commitTime\":\"2019-06-17T06:35:13+0000\"}");
    private String env;

    @BeforeClass
    public void storeDevice() {
        if (System.getProperty("environment") != null) {
            env = System.getProperty("environment");
        }
    }

    @AfterClass
    public void restoreDevice() {
        if (env != null) {
            System.setProperty("environment", env);
        }
    }

    @BeforeMethod
    @AfterMethod
    public void clearBrowser() {
        System.clearProperty("environment");
    }

    @Test
    public void getBuildTest() {
        System.setProperty("environment", "cot");
        Assert.assertTrue(configuration.getBuild(mockedSysInfo).matches("Build Version: .*"));
    }

    private void testURLConnection(URL environmentURL) throws IOException {
        HTTP http = new HTTP(environmentURL.toString());
        http.get("");
    }
}
