package unit;

import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Property;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;

public class PropertyUT {

    private String screenshot;
    private String defaultWait;

    @BeforeClass
    public void storeDevice() {
        if (System.getProperty("disable.screenshots") != null) {
            screenshot = System.getProperty("disable.screenshots");
        }
        if (System.getProperty("default.wait") != null) {
            defaultWait = System.getProperty("default.wait");
        }
    }

    @AfterClass
    public void restoreDevice() {
        if (screenshot != null) {
            System.setProperty("disable.screenshots", screenshot);
        }
        if (defaultWait != null) {
            System.setProperty("default.wait", defaultWait);
        }
    }

    @AfterMethod
    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("disable.screenshots");
        System.clearProperty("default.wait");
    }

    @Test
    public void isScreenshotDefaultTest() {
        Assert.assertTrue(Property.takeScreenshot());
    }

    @Test
    public void isScreenshotTrueTest() {
        System.setProperty("disable.screenshots", "true");
        Assert.assertFalse(Property.takeScreenshot());
    }

    @Test
    public void isScreenshotFalseTest() {
        System.setProperty("disable.screenshots", "false");
        Assert.assertTrue(Property.takeScreenshot());
    }

    @Test
    public void isScreenshotOtherTest() {
        System.setProperty("disable.screenshots", "hello");
        Assert.assertTrue(Property.takeScreenshot());
    }

    @Test
    public void getDefaultWaitTimeTest() {
        Assert.assertEquals(Property.getDefaultWait(), 5);
    }

    @Test
    public void updateWaitTimeTest() {
        System.setProperty("default.wait", "10");
        Assert.assertEquals(Property.getDefaultWait(), 10);
    }

    @Test
    public void setWaitTimeBadValueTest() {
        System.setProperty("default.wait", "foo");
        Assert.assertEquals(Property.getDefaultWait(), 5);
    }

    @Test
    public void getMaintenanceHeaderDefaultTest() {
        Assert.assertEquals(Property.getMaintenanceHeader(), "allowqa=allowPMIQAToTest");
    }
}
