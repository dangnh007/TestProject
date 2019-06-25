package unit;

import com.pmt.health.exceptions.InvalidDeviceException;
import com.pmt.health.utilities.Device;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class DeviceUT {

    private String device;
    private String deviceDetails;

    @BeforeClass
    public void storeDevice() {
        if (System.getProperty("device") != null) {
            device = System.getProperty("device");
        }
        if (System.getProperty("deviceDetails") != null) {
            deviceDetails = System.getProperty("deviceDetails");
        }
    }

    @AfterClass
    public void restoreDevice() {
        if (device != null) {
            System.setProperty("device", device);
        }
        if (deviceDetails != null) {
            System.setProperty("deviceDetails", deviceDetails);
        }
    }

    @AfterMethod
    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("device");
        System.clearProperty("deviceDetails");
    }

    @Test(expectedExceptions = InvalidDeviceException.class)
    public void lookupNullTest() throws InvalidDeviceException {
        Device.lookup(null);
    }

    @Test(expectedExceptions = InvalidDeviceException.class)
    public void lookupBadTest() throws InvalidDeviceException {
        Device.lookup("Hello World");
    }

    @Test
    public void lookupUpperCaseTest() throws InvalidDeviceException {
        assertEquals(Device.lookup("CHROME"), Device.CHROME);
    }

    @Test
    public void lookupLowerCaseTest() throws InvalidDeviceException {
        assertEquals(Device.lookup("chrome"), Device.CHROME);
    }

    @Test
    public void lookupMixedCaseTest() throws InvalidDeviceException {
        assertEquals(Device.lookup("ChRoMe"), Device.CHROME);
    }

    @Test
    public void getDeviceDefaultTest() {
        assertEquals(Device.getDevice(), Device.CHROME);
    }

    @Test
    public void getDeviceChrome1Test() {
        System.setProperty("device", "FIREFOX");
        assertEquals(Device.getDevice(), Device.FIREFOX);
    }

    @Test
    public void getDeviceChrome2Test() {
        System.setProperty("device", "firefox");
        assertEquals(Device.getDevice(), Device.FIREFOX);
    }

    @Test
    public void getDeviceChrome3Test() {
        System.setProperty("device", "FiReFoX");
        assertEquals(Device.getDevice(), Device.FIREFOX);
    }

    @Test
    public void getDeviceFirefoxFallbackTest() {
        System.setProperty("device", "HelloBrowser");
        assertEquals(Device.getDevice(), Device.CHROME);
    }

    @Test
    public void getDevicePlatformDefaultTest() {
        assertEquals(Device.getDevicePlatform(), "");
    }

    @Test
    public void getDevicePlatformNullTest() {
        System.setProperty("deviceDetails", "deviceOrientation=vertical");
        assertEquals(Device.getDevicePlatform(), "");
    }

    @Test
    public void getDevicePlatformNotSetTest() {
        System.setProperty("deviceDetails", "platform");
        assertEquals(Device.getDevicePlatform(), "");
    }

    @Test
    public void getDevicePlatformEmptyTest() {
        System.setProperty("deviceDetails", "platform=");
        assertEquals(Device.getDevicePlatform(), "");
    }

    @Test
    public void getDeviceNoPlatformTest() {
        System.setProperty("deviceDetails", "version=1.3.4");
        assertEquals(Device.getDevicePlatform(), "");
    }

    @Test
    public void getDevicePlatformTest() {
        System.setProperty("deviceDetails", "platform=windows");
        assertEquals(Device.getDevicePlatform(), "windows");
    }

    @Test
    public void getDevicePlatformAndTest() {
        System.setProperty("deviceDetails", "platform=windows&version=1.3.4");
        assertEquals(Device.getDevicePlatform(), "windows");
    }
    @Test
    public void getDeviceNameDefaultTest() {
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceNameNullTest() {
        System.setProperty("deviceDetails", "deviceOrientation=vertical");
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceNameBrowserNotSetTest() {
        System.setProperty("deviceDetails", "browserName");
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceNameBrowserEmptyTest() {
        System.setProperty("deviceDetails", "browserName=");
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceNoNameTest() {
        System.setProperty("deviceDetails", "version=1.3.4");
        assertEquals(Device.getBrowserName(), "");
    }


    @Test
    public void getDeviceNameNotMobileTest() {
        System.setProperty("device", "chrome");
        System.setProperty("deviceDetails", "deviceName=samsungLg");
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceNameBrowserTest() {
        System.setProperty("device", "firefox");
        System.setProperty("deviceDetails", "browserName=firefox");
        assertEquals(Device.getBrowserName(), "firefox");
    }

    @Test
    public void getDeviceNameBrowserAndTest() {
        System.setProperty("device", "firefox");
        System.setProperty("deviceDetails", "platform=windows&browserName=firefox");
        assertEquals(Device.getBrowserName(), "firefox");
    }

    @Test
    public void getDeviceNameBothAndBrowserTest() {
        System.setProperty("device", "firefox");
        System.setProperty("deviceDetails", "platform=windows&deviceName=samsungLg&browserName=firefox");
        assertEquals(Device.getBrowserName(), "firefox");
    }

    @Test
    public void getDeviceVersionDefaultTest() {
        assertEquals(Device.getDeviceVersion(), "");
    }

    @Test
    public void getDeviceVersionNullTest() {
        System.setProperty("deviceDetails", "deviceOrientation=vertical");
        assertEquals(Device.getDeviceVersion(), "");
    }

    @Test
    public void getDeviceVersionBrowserNotSetTest() {
        System.setProperty("deviceDetails", "version");
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceVersionBrowserEmptyTest() {
        System.setProperty("deviceDetails", "version=");
        assertEquals(Device.getBrowserName(), "");
    }

    @Test
    public void getDeviceVersionNotMobileTest() {
        System.setProperty("device", "chrome");
        System.setProperty("deviceDetails", "platformVersion=1.3.4");
        assertEquals(Device.getDeviceVersion(), "");
    }

    @Test
    public void getDeviceVersionBrowserTest() {
        System.setProperty("device", "firefox");
        System.setProperty("deviceDetails", "version=1.3.4");
        assertEquals(Device.getDeviceVersion(), "1.3.4");
    }


    @Test
    public void getDeviceVersionBrowserAndTest() {
        System.setProperty("device", "firefox");
        System.setProperty("deviceDetails", "platform=windows&version=1.3.4");
        assertEquals(Device.getDeviceVersion(), "1.3.4");
    }

    @Test
    public void getDeviceVersionBothAndBrowserTest() {
        System.setProperty("device", "firefox");
        System.setProperty("deviceDetails", "platform=windows&platformVersion=1.3&version=1.3.4");
        assertEquals(Device.getDeviceVersion(), "1.3.4");
    }

    @Test
    public void getDeviceInfoDefaultTest() {
        assertEquals(Device.getDeviceInfo(), "Chrome");
    }

    @Test
    public void getDeviceInfoDeviceTest() {
        System.setProperty("device", "Firefox");
        assertEquals(Device.getDeviceInfo(), "Firefox");
    }

    @Test
    public void getDeviceInfoVersionTest() {
        System.setProperty("deviceDetails", "version=1.3.4");
        assertEquals(Device.getDeviceInfo(), "Chrome 1.3.4");
    }

    @Test
    public void getDeviceInfoPlatformTest() {
        System.setProperty("deviceDetails", "platform=windows");
        assertEquals(Device.getDeviceInfo(), "Chrome on Windows");
    }

    @Test
    public void getDeviceInfoNameTest() {
        System.setProperty("deviceDetails", "deviceName=samsungLg");
        assertEquals(Device.getDeviceInfo(), "Chrome");
    }

    @Test
    public void getDeviceOrientationDefaultTest() {
        assertEquals(Device.getDeviceOrientation(), "");
    }

    @Test
    public void getDeviceOrientationNullTest() {
        System.setProperty("deviceDetails", "platform=windows");
        assertEquals(Device.getDeviceOrientation(), "");
    }

    @Test
    public void getDeviceOrientationNotSetTest() {
        System.setProperty("deviceDetails", "deviceOrientation");
        assertEquals(Device.getDeviceOrientation(), "");
    }

    @Test
    public void getDeviceOrientationEmptyTest() {
        System.setProperty("deviceDetails", "deviceOrientation=");
        assertEquals(Device.getDeviceOrientation(), "");
    }

    @Test
    public void getDeviceNoOrientationTest() {
        System.setProperty("deviceDetails", "version=1.3.4");
        assertEquals(Device.getDeviceOrientation(), "");
    }

    @Test
    public void getDeviceOrientationTest() {
        System.setProperty("deviceDetails", "deviceOrientation=vertical");
        assertEquals(Device.getDeviceOrientation(), "vertical");
    }

    @Test
    public void getDeviceOrientationAndTest() {
        System.setProperty("deviceDetails", "platform=windows&deviceOrientation=horizontal");
        assertEquals(Device.getDeviceOrientation(), "horizontal");
    }
}