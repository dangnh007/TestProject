package unit;

import com.pmt.health.interactions.application.App;
import org.junit.Assert;
import org.junit.Test;

public class EncodingUT {

    /**
     * Checks all string variations from the sanitized function against their intended matches.
     */
    @Test
    public void positive() {
        String good = "'            --------C";
        String bad = "\u02BC\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\n\u058A" +
                "\u05BE\u2010\u2011\u2012\u2013\u2014\u2015\u00A9";
        String sanitized = App.getSanitizedString(bad);
        Assert.assertEquals(sanitized, good);
    }

    /**
     * Ensures that sanitized test has negative assert as well.
     */
    @Test
    public void negative() {
        String good = "Adam is kinda OK";
        String bad = "\u02BC\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\n\u058A" +
                "\u05BE\u2010\u2011\u2012\u2013\u2014\u2015";
        String sanitized = App.getSanitizedString(bad);
        Assert.assertNotSame(sanitized, good);
    }
}
