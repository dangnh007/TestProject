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
    private final JsonObject mockedSysInfo = (JsonObject) parser.parse("{\"buildName\":\"pmi-cot-api\"," +
            "\"buildNumber\":\"377\",\"branch\":\"release/2.14.0\",\"timestamp\":\"Fri Feb 23 09:12:59 UTC 2018\"," +
            "\"timezone\":\"Coordinated Universal Time\",\"currentTimeString\":\"Fri Feb 23 19:19:11 UTC 2018\"," +
            "\"dataVersion\":20,\"sha\":\"7f63441f130f989b309eab47b264f2bc04a8ad06\"}");
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
        Assert.assertTrue(configuration.getBuild(mockedSysInfo).matches("(?:\\w+-)*\\w+ - Build #\\d+"));
    }

    private void testURLConnection(URL environmentURL) throws IOException {
        HTTP http = new HTTP(environmentURL.toString());
        http.get("");
    }
}
