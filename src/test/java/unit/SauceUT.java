package unit;

import com.pmt.health.utilities.Sauce;
import org.testng.Assert;
import org.testng.annotations.*;

public class SauceUT {

    private String hub;

    @BeforeClass
    public void setupArrays() {
        if (System.getProperty("hub") != null) {
            hub = System.getProperty("hub");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (hub != null) {
            System.setProperty("hub", hub);
        }
    }

    @AfterMethod
    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("hub");
    }

    @Test
    public void isSauceNoHubTest() {
        Assert.assertFalse(Sauce.isUsed());
    }

    @Test
    public void isSauceNotSauceTest() {
        System.setProperty("hub", "Hello World");
        Assert.assertFalse(Sauce.isUsed());
    }

    @Test
    public void isSauceNotSauceURLTest() {
        System.setProperty("hub", "http://localhost:4444");
        Assert.assertFalse(Sauce.isUsed());
    }

    @Test
    public void isSauceSauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertTrue(Sauce.isUsed());
    }

    @Test
    public void getUserNoHubTest() {
        Assert.assertNull(Sauce.getUser());
    }

    @Test
    public void getUserNoUserTest() {
        System.setProperty("hub", "https://ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getUser());
    }

    @Test
    public void getUserOnlyUserTest() {
        System.setProperty("hub", "https://sauceusername@ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getUser());
    }

    @Test
    public void getUserBadURLTest() {
        System.setProperty("hub", "https:///sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getUser());
    }

    @Test
    public void getUserSauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertEquals(Sauce.getUser(), "sauceusername");
    }

    @Test
    public void getKeyNoHubTest() {
        Assert.assertNull(Sauce.getKey());
    }

    @Test
    public void getKeyNoKeyTest() {
        System.setProperty("hub", "https://ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getKey());
    }

    @Test
    public void getKeyOnlyKeyTest() {
        System.setProperty("hub", "https://sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getKey());
    }

    @Test
    public void getKeySauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertEquals(Sauce.getKey(), "sauceaccesskey");
    }
}