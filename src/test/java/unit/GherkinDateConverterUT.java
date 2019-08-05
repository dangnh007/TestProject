package unit;

import com.pmt.health.utilities.GherkinDateConverter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GherkinDateConverterUT {

    @Test
    public void badDatePatternTest() {
        GherkinDateConverter gherkinDateConverter = new GherkinDateConverter();
        Date bad = gherkinDateConverter.transform("HelloWorld");
        Assert.assertNotNull(bad);
    }

    @Test
    public void yearMonthDayDashTest() {
        GherkinDateConverter gherkinDateConverter = new GherkinDateConverter();
        Date bad = gherkinDateConverter.transform("2000-01-01");
        Calendar c = new GregorianCalendar(2000, 0, 1);
        Assert.assertEquals(bad, c.getTime());
    }

    @Test
    public void dayMonthYearDashTest() {
        GherkinDateConverter gherkinDateConverter = new GherkinDateConverter();
        Date bad = gherkinDateConverter.transform("01-01-2000");
        Calendar c = new GregorianCalendar(2000, 0, 1);
        Assert.assertEquals(bad, c.getTime());
    }

    @Test
    public void yearMonthDaySlashTest() {
        GherkinDateConverter gherkinDateConverter = new GherkinDateConverter();
        Date bad = gherkinDateConverter.transform("2000/01/01");
        Calendar c = new GregorianCalendar(2000, 0, 1);
        Assert.assertEquals(bad, c.getTime());
    }

    @Test
    public void dayMonthYearSlashTest() {
        GherkinDateConverter gherkinDateConverter = new GherkinDateConverter();
        Date bad = gherkinDateConverter.transform("01/01/2000");
        Calendar c = new GregorianCalendar(2000, 0, 1);
        Assert.assertEquals(bad, c.getTime());
    }

    @Test
    public void epochTest() {
        GherkinDateConverter gherkinDateConverter = new GherkinDateConverter();
        Date bad = gherkinDateConverter.transform("946702800000");
        Assert.assertEquals(bad, new Date(946702800000L));
    }
}
